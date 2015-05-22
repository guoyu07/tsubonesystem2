package tsuboneSystem.action.admin;

import java.io.IOException;

import javax.annotation.Resource;

import org.markdown4j.Markdown4jProcessor;
import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

import tsuboneSystem.dto.LoginMemberDto;
import tsuboneSystem.entity.TBbsDetail;
import tsuboneSystem.entity.TBbsSubject;
import tsuboneSystem.form.BbsForm;
import tsuboneSystem.service.TBbsDetailService;
import tsuboneSystem.service.TBbsSubjectService;
import tsuboneSystem.service.TMemberService;

public class BbsDetailListAction {
	
	public String actionName = "Kagucho.BBS";
	
	/** BbsFormのアクションフォーム */
	@ActionForm
	@Resource
	protected BbsForm bbsForm;
	
	/** LoginMemberDto */
	@Resource
	public LoginMemberDto loginMemberDto;
	
	/** TMemberのサービスクラス */
	@Resource
	protected TMemberService tMemberService;
	
	/** TBbsSubjectServiceのサービスクラス */
	@Resource
	protected TBbsSubjectService tBbsSubjectService;
	
	/** TBbsDetailServiceのサービスクラス */
	@Resource
	protected TBbsDetailService tBbsDetailService;
	
	//選択されたスレ
	public TBbsSubject tBbsSubject;
	
    @Execute(validator = false, urlPattern = "{id}")
	public String index() {
    	bbsForm.tBbsDetailList = tBbsDetailService.findBySubjectId(bbsForm.id);
    	
    	return "BbsDetailList.jsp";
	}

    //confirmのバリデータに引っかかった時はここに戻ってくる。(入力した値保持のため)
    @Execute(validator = false)
	public String viewinput() {
    	return "BbsDetailList.jsp";
    }
    
    @Execute(validator = true,input = "viewinput")
	public String bbsDetailRegist() {
    	
    	//MarkDownに変換する
    	String markDown = new String();
    	Markdown4jProcessor pross = new Markdown4jProcessor();
		try {
			markDown = pross.process(bbsForm.detail);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	//DBに書き込み
    	TBbsDetail tBbsDetail = new TBbsDetail();
    	tBbsDetail.subjectId = bbsForm.id;
    	tBbsDetail.detail = markDown;
    	tBbsDetail.memberId = loginMemberDto.memberId;
    	tBbsDetailService.insert(tBbsDetail);
    	bbsForm.detail = null;
    	
    return "/" + getDirectory() + "/bbsDetailList/?redirect=true";
	}
    
    protected String getDirectory() {
    	return "admin";
    }
}
