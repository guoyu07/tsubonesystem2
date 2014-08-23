package tsuboneSystem.mail;

import java.util.List;

import javax.annotation.Resource;

import tsuboneSystem.entity.TMail;
import tsuboneSystem.entity.TMailSendMember;
import tsuboneSystem.entity.TMember;
import tsuboneSystem.original.manager.MailManager;
import tsuboneSystem.service.TMailSendMemberService;
import tsuboneSystem.service.TMailService;

public class MailRegistDefault {
	
	/** TMailSendMemberServiceのサービスクラス */
	@Resource
	TMailSendMemberService tMailSendMemberService;
	
	/** TMailServiceのサービスクラス */
	@Resource
	TMailService tMailService;
	
	boolean errorFlag;
	
	@SuppressWarnings("deprecation")
	public boolean mailSend(List<TMember> tSendMember, String title, String content, Integer registMemberId){
		
		//内容を作る(最後に改行をつけると文字化けを防げる)
    	StringBuilder sbc = new StringBuilder();
    	sbc.append(content);
    	sbc.append("\n");
    	String sendContent = new String(sbc);
		
    	//メールを送信する
    	MailManager manager = new MailManager();
    	manager.setTitle(title);
    	manager.setContent(sendContent);
    	manager.setToAddress(tSendMember.toArray(new TMember[0]));
    	if (manager.sendMail()) {
    		errorFlag = false;
    	} else {
    		errorFlag = true;
    	}
    	
    	//TMailにメールの内容を追加する
    	TMail tMail = new TMail();
    	tMail.title = title;
    	tMail.content = content;
    	tMail.registMemberId = registMemberId;
    	tMail.errorFlag = errorFlag;
    	tMailService.insert(tMail);
    	
    	//TMailSendAttendにメールの送信相手を追加する
    	for (TMember tMemberOne : tSendMember) {
    		TMailSendMember tMailSendMember = new TMailSendMember();
    		tMailSendMember.mailId = tMail.id;
    		tMailSendMember.memberId = tMemberOne.id;
    		tMailSendMemberService.insert(tMailSendMember);
    	}
    	
		return errorFlag;
	}

}
