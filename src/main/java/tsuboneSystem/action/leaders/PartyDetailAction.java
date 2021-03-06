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

package tsuboneSystem.action.leaders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.util.TokenProcessor;
import org.seasar.framework.beans.util.Beans;
import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

import tsuboneSystem.code.MailBrowsingRightsCode;
import tsuboneSystem.dto.LoginLeadersDto;
import tsuboneSystem.dto.LoginMemberDto;
import tsuboneSystem.dto.PartyDto;
import tsuboneSystem.entity.TMember;
import tsuboneSystem.entity.TParty;
import tsuboneSystem.entity.TPartyClub;
import tsuboneSystem.entity.TPartyQuestion;
import tsuboneSystem.form.PartyForm;
import tsuboneSystem.original.util.MailManagerUtil;
import tsuboneSystem.service.TClubService;
import tsuboneSystem.service.TMailSendMemberService;
import tsuboneSystem.service.TMailService;
import tsuboneSystem.service.TPartyClubService;
import tsuboneSystem.service.TPartyQuestionService;
import tsuboneSystem.service.TPartyService;

public class PartyDetailAction {
	
	public String actionName = "PartyDetail";
	
	/** PartyFormのアクションフォーム */
	@ActionForm
	@Resource
	protected PartyForm partyForm;
	
	/** Member用のDto */
	@Resource
	public LoginMemberDto loginMemberDto;
	
	/** LoginAdminDto */
	@Resource
	protected LoginLeadersDto loginLeadersDto;
	
	/** PartyDtoのサービスクラス */
	@Resource
	protected PartyDto partyDto;
	
	/** TPartyのサービスクラス */
	@Resource
	protected TPartyService tPartyService;
	
	/** TClubServiceのサービスクラス */
	@Resource
	protected TClubService tClubService;
	
	/** TPartyClubServiceのサービスクラス */
	@Resource
	protected TPartyClubService  tPartyClubService;
	
	/** TPartyQuestionServiceのサービスクラス */
	@Resource
	protected TPartyQuestionService  tPartyQuestionService;
	
	/** TMailのサービスクラス */
	@Resource
	protected TMailService tMailService;
	
	/** TMailSendMemberServiceのサービスクラス */
	@Resource
	protected TMailSendMemberService tMailSendMemberService;
	
	/** HttpServlet */
	@Resource
	protected HttpServletRequest request;
	
	/** 送信エラーフラグ */
	public boolean errorFlag;
	
	public boolean resultPower = false;
	
    @Execute(validator = false, urlPattern = "detail/{id}", reset = "resetInput")
	public String index() {
    	
    	/** 2重送信防止のためのTokenの生成　**/
        TokenProcessor.getInstance().saveToken(request);
    	
    	TParty party = tPartyService.findById(partyForm.id);
    	Beans.copy(party, partyForm).execute();
    	Beans.copy(party, partyDto).execute();
    	
    	 int i = 0;
         partyForm.attendClub = new String[party.tPartyClubList.size()];
         partyDto.attendClub = new String[party.tPartyClubList.size()];
         for (TPartyClub tPartyClubOne : party.tPartyClubList) {
         	partyForm.attendClub[i] = tPartyClubOne.ClubId.toString();
         	partyDto.attendClub[i] = tPartyClubOne.ClubId.toString();
         	i++;
         }
    	
    	//現在時刻を取得し、期限内か判断する
       	Date dateNow = new Date();
        partyForm.deadFlag = partyDto.deadFlag(party, dateNow);
        
        //マップを作る。形はkey(数値)とvalu(名称)の２個セットの形
        partyForm.clubMapIS = tClubService.getClubMapIS();
        partyForm.clubMapSS = tClubService.getClubMap();
        
        //結果を入力できるか
        if(partyForm.resultEditMemberId != null && !partyForm.resultEditMemberId.equals(loginMemberDto.memberId)){
        	if(!partyForm.resultEditEndFlag){
        		resultPower = true;
        	}
        }else{
        	//まだ誰も編集していないか、最終編集者が自分の時
        	resultPower = true;
        }
        
        return "partyDetail.jsp";
	}
    
    //質問確認画面
    @Execute(validator = true, input = "partyDetail.jsp")
    public String questionConfirm(){
    	
    	return "partyQuestionConfirm.jsp";
    }
    
    //質問完了画面
    @Execute(validator = false)
    public String questionComplete(){
    	
    	/** 2重送信防止のためTokenが正常な場合にのみ レコード追加処理を行う	　**/
        if (TokenProcessor.getInstance().isTokenValid(request, true)) {
        	
        	//質問内容を登録する
        	TPartyQuestion tPartyQuestion = new TPartyQuestion();
        	tPartyQuestion.partyId = partyForm.id;
        	tPartyQuestion.memberId = getLoginMemberId();
        	tPartyQuestion.question = partyForm.question;
        	tPartyQuestion.questionSend = partyForm.questionSend;
        	tPartyQuestionService.insert(tPartyQuestion);
        	
        	//会議の登録者にメールを送る
        	if(partyForm.questionSend){
        		
        		//送信対象
        		List<TMember> tSendMember = new ArrayList<TMember>();
        		tSendMember.add(partyForm.tMember);
        		    		
        		//タイトルを作る
    	    	String title = getMailtitle();
    	    	
    	    	//内容を作る
    	    	String content = getMailContents();
    	    	
    	    	//メールを送信する
            	MailManagerUtil mailUtil = new MailManagerUtil();
            	mailUtil.setRegistId(loginMemberDto.memberId);
            	mailUtil.setBrowsingRights(MailBrowsingRightsCode.MEMBER.getCodeNumber());
            	mailUtil.setTitle(title);
            	mailUtil.setContent(content);	
            	mailUtil.setLinkUrlFlag(false);
            	mailUtil.setToAddressActorSplit(tSendMember);
            	mailUtil.sendMail();
            	if (!mailUtil.getSendMailResult()) {
            		errorFlag = false;
            	} else {
            		errorFlag = true;
            	}
        	}
        }
    	return "partyQuestionComplete.jsp";
    }

	protected String getMailContents() {
		StringBuilder sbc = new StringBuilder();
		sbc.append("会議名:　");
		sbc.append(partyForm.meetingName);
		sbc.append("\n");
		sbc.append("質問者:　");
		sbc.append(getLoginTMember().hname);
		sbc.append("\n\n");
		sbc.append("質問内容:　\n");
		sbc.append(partyForm.question);
		String content = new String(sbc);
		return content;
	}

	protected String getMailtitle() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append(partyForm.meetingName);
		sb.append("]に対して質問を受け付けました");
		String title = new String(sb);
		return title;
	}

	protected Integer getLoginMemberId() {
		return loginLeadersDto.memberId;
	}

	protected TMember getLoginTMember() {
		return loginLeadersDto.tMemberLogin;
	}
}
