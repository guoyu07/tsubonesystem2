/*
 * Copyright (C) 2014-2016  Kagucho <kagucho.net@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.

 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package tsuboneSystem.action.individuals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.util.TokenProcessor;
import org.seasar.framework.beans.util.Beans;
import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

import tsuboneSystem.code.SexCode;
import tsuboneSystem.dto.LoginIndividualsDto;
import tsuboneSystem.dto.LoginMemberDto;
import tsuboneSystem.entity.TClub;
import tsuboneSystem.entity.TMember;
import tsuboneSystem.entity.TMemberClub;
import tsuboneSystem.form.MemberForm;
import tsuboneSystem.original.util.DigestUtil;
import tsuboneSystem.service.TAdminService;
import tsuboneSystem.service.TClubService;
import tsuboneSystem.service.TLeadersService;
import tsuboneSystem.service.TMemberClubService;
import tsuboneSystem.service.TMemberService;

public class MemberUpdateAction {
	
	public String actionName = "MemberUpdate";
	
	/** Memberのアクションフォーム */
	@ActionForm
	@Resource
	protected MemberForm memberForm;
	
	/** Member用のDto */
	@Resource
	public LoginMemberDto loginMemberDto;
	
	/** ログインDTO */
	@Resource
	protected LoginIndividualsDto loginIndividualsDto;
	
	/** TMemberのサービスクラス */
	@Resource
	protected TMemberService tMemberService;
	
	/** TClubのサービスクラス */
	@Resource
	protected TClubService tClubService;
	
	/** TMemberClubServiceのサービスクラス */
	@Resource
	protected TMemberClubService tMemberClubService;
	
	/** TLeadersServiceのサービスクラス */
	@Resource
	protected TLeadersService tLeadersService;
	
	/** TAdminServiceのサービスクラス */
	@Resource
	protected TAdminService tAdminService;
	
	/** HttpServlet */
	@Resource
	protected HttpServletRequest request;
	
	/** Memberのリスト */
	public List<TMember> memberItems;
	
	/** Clubのリスト */
	public List<TClub> clubItems;
	
	/** 編集前の情報 */
	public TMember tMemberOld;
	
	
	@SuppressWarnings("boxing")
	@Execute(validator = false)
	public String input() {
		
    	/** 2重登録防止のためのTokenの生成　**/
        TokenProcessor.getInstance().saveToken(request);
        
        memberForm.id = loginIndividualsDto.tMemberLogin.id;
        memberForm.clubMap = tClubService.getClubMapIS();
        
        //すでに所属している部のチェックボックスはonにする
        memberForm.tMemberClubUpOldId = tMemberClubService.findByMemberId(memberForm.id.toString());
        for (TMemberClub tMemberClubUpOldOne : memberForm.tMemberClubUpOldId){
        	memberForm.clubListChecked.add(tMemberClubUpOldOne.ClubId.toString());
        };
        
        memberForm.sexMap = new HashMap<String, String>();
        for (Integer i=1; i<=3; i++) {
        	memberForm.sexMap.put(i.toString(), SexCode.getnameByCode(i.toString()));
        }
		
		TMember member = tMemberService.findById(memberForm.id);
		Beans.copy(member, memberForm).excludes("password").execute();
		memberForm.tMemberOld = member;
		memberForm.password = null;

        return viewinput();
	}
	
	 //confirmのバリデータに引っかかった時はここに戻ってくる。(入力した値保持のため)
    @Execute(validator = false)
	public String viewinput() {
    	// OBのチェックをした状態で戻るボタンを押下した場合、はチェックされた状態が表示されなければならない
    	if (!loginIndividualsDto.tMemberLogin.obFlag && memberForm.obFlag.equals("true")) {
    		memberForm.obFlagDisply = memberForm.obFlag;
    		memberForm.obFlag = null;
    	}
    	return "memberInput.jsp";
    }
    
    @Execute(validator = true, validate="validateBaseInd", input="memberInput.jsp", stopOnValidationError = false, reset = "resetInput")
	public String confirmUp() {
    	
    	if(memberForm.tMemberOld.obFlag){
    		memberForm.obFlag = "true";
    	}
    	
    	//選択した部を表示する
    	memberForm.tMemberClubList = new ArrayList<TMemberClub>();
        for(String one : memberForm.clubListChecked){
        	TMemberClub tMemberClub = new TMemberClub();
        	tMemberClub.tClub = tClubService.findById(Integer.valueOf(one));
        	memberForm.tMemberClubList.add(tMemberClub);
        }
        
        return "memberConfirm.jsp";
	}
    
    @Execute(validator = false)
	public String complete() {
    	
    	/** 2重登録防止のためTokenが正常な場合にのみ レコード追加処理を行う	　**/
        if (TokenProcessor.getInstance().isTokenValid(request, true)){  	
        	TMember memberUp = new TMember();
        	Beans.copy(memberForm, memberUp).execute();
        	
        	
        	//ログインDTOを入れなおす
        	loginIndividualsDto.tMemberLogin = memberUp;
        	
        	//パスワードの更新
        	if (StringUtils.isNotEmpty(memberForm.password)){
        		//パスワードのハッシュ化
            	memberUp.password = DigestUtil.md5(memberForm.password);
        	}else{
        		TMember tMember = tMemberService.findById(memberForm.id);
        		memberUp.password = tMember.password;
        	}
        	
        	//不達フラグはfalse
        	memberUp.sendErrorFlag = false;
        	
        	//DB更新
        	tMemberService.update(memberUp);
        	memberForm.id = memberUp.id;

        	/** メンバーが所属していた情報を一回削除した上で、選択された部とメンバーのIDをtMemberClubに登録していく。複数なので選択した回数だけレコードを登録する。*/
        	//メンバーが所属していた情報を削除する
        	memberForm.tMemberClubUpOldId = tMemberClubService.findByMemberId(memberForm.id.toString());
        	for (TMemberClub tMemberClubUpOldOne : memberForm.tMemberClubUpOldId) {
        		//DB削除
        		tMemberClubService.delete(tMemberClubUpOldOne);
        	}
        	
        	//新しく選択された情報で新規追加する。
        	for (String check : memberForm.clubListChecked){
        		TMemberClub memberClub = new TMemberClub();
        		memberClub.MemberId = memberUp.id;
        		memberClub.ClubId = Integer.valueOf(check);
        		//DB登録
        		tMemberClubService.insert(memberClub);
        	}
        }
    return "memberComplete.jsp";
    }
}
    
