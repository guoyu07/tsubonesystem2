/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package tsuboneSystem.action.admin;

import java.util.HashMap;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.seasar.framework.beans.util.Beans;
import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

import tsuboneSystem.form.ClubForm;
import tsuboneSystem.code.LeadersKindCode;
import tsuboneSystem.entity.TClub;
import tsuboneSystem.entity.TLeaders;
import tsuboneSystem.entity.TMember;
import tsuboneSystem.entity.TMemberClub;
import tsuboneSystem.service.TClubService;
import tsuboneSystem.service.TLeadersService;
import tsuboneSystem.service.TMemberClubService;
import tsuboneSystem.service.TMemberService;

public class ClubRegistAction {
	
	public String actionName = "ClubRegist";
	
	/** ClubFormのアクションフォーム */
	@ActionForm
	@Resource
	protected ClubForm clubForm;
	
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
	
	/** HttpServlet */
	@Resource
	protected HttpServletRequest request;
	
	@Execute(validator = false)
	public String index() {
		
		//部長になり得るメンバーのマップを作成する。
		clubForm.memberMap = new HashMap<String,String>();
		clubForm.tMemberAllList = tMemberService.findByIdNoOBAll();
		for (TMember memberOne : clubForm.tMemberAllList) {
		 clubForm.memberMap.put(memberOne.id.toString(), memberOne.hname);	
		}
		
        return "clubInput.jsp";
	}
    
    @Execute(validator = true, validate="validateBase", input="clubInput.jsp", stopOnValidationError = false)
	public String confirm() {
        return "clubConfirm.jsp";
	}
    
    @Execute(validator = false)
	public String complete() {
    	
    	//新しい部長を登録する
    	TLeaders tLeader = new TLeaders();
    	tLeader.OfficerKind = Integer.valueOf(LeadersKindCode.getCodeByName("部長"));
    	tLeader.MemberId = Integer.valueOf(clubForm.OfficerId);
    	tLeadersService.insert(tLeader);
    	
    	//新しいブを登録する
    	TClub tClub = Beans.createAndCopy(TClub.class, clubForm).execute();	
    	tClub.deleteFlag = Boolean.valueOf(false);
    	tClub.LeadersId = tLeader.id;
    	tClubService.insert(tClub);
    	
    	//新しい部の部員一号(必然的に部長)を登録する
    	TMemberClub memberClub = new TMemberClub();
    	memberClub.MemberId = Integer.valueOf(clubForm.OfficerId);
    	memberClub.ClubId = tClub.id;
    	tMemberClubService.insert(memberClub);
    	
        return "clubComplete.jsp";
	}
    
  //オリジナルチェック
    public ActionMessages validateBase(){
    	
        ActionMessages errors = new ActionMessages();
    	
    	//選択されたMemberが連絡先をすべて登録しているかを確認する。
    	TMember tMember = tMemberService.findById(Integer.valueOf(clubForm.OfficerId));
    	if (tMember.mail.isEmpty() || tMember.tel1.isEmpty() || tMember.tel2.isEmpty() || tMember.tel3.isEmpty()) {
    		errors.add("OfficerId",new ActionMessage("このメンバーには連絡先のどれかが登録されていません。メール、電話番号をすべて登録するか他のメンバーを選択してください。",false));
    	}
    	
        return errors;
    }
}
