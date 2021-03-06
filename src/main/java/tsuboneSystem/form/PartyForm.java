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

package tsuboneSystem.form;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.seasar.framework.container.annotation.tiger.Component;
import org.seasar.framework.container.annotation.tiger.InstanceType;
import org.seasar.framework.util.StringUtil;
import org.seasar.struts.annotation.Msg;
import org.seasar.struts.annotation.Required;

import tsuboneSystem.entity.TClub;
import tsuboneSystem.entity.TMember;
import tsuboneSystem.entity.TPartyClub;
import tsuboneSystem.entity.TPartyQuestion;

@Component(instance = InstanceType.SESSION) 
public class PartyForm implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/** id　*/
	public Integer id;
	
	/** 会議の登録者Id　*/
	public Integer creatorId;
	
	/** 会議名　*/
	@Required(msg=@Msg(key="errors.meetingName", resource=true))
	public String meetingName;
	
	/** 会議の必須判定　*/
	public boolean meetingNecessaryFlag;
	
	/** 出席対象者　*/
	public String[] attendClub = new String[0];
	
	/** 会議日時　*/
	public String meetingDay;
	
	/** 会議日時(終了日)　*/
	public String meetingEndDay;
	
	/** 会議時間　*/
	public String meetingTime;
	
	/** 会議場所　*/
	public String meetingRoom;
	
	/** 会議内容　*/
	@Required(msg=@Msg(key="errors.meetingMemo", resource=true))
	public String meetingMemo;
	
	/** 会議出欠席締め切り日　*/
	public String meetingDeadlineDay;
	
	/** 審議結果 */
    public String  meetingResult;
    
    /** 会議結果の最終編集者　*/
	public Integer resultEditMemberId;
    
    /** 最終編集フラグ */
    public boolean  resultEditEndFlag;
	
	/** 削除フラグ */
    public String  deleteFlag;
    
    /** 期限切れフラグ */
    public boolean deadFlag;
    
    /** OB出席フラグ */
    public String ObAttendFlag;
    
    /** 人数通知設定値 */
    public String noticeMemberNum;
    
    /** 会議の制作者 */
    public TMember tMember;
    
    /** チェックボックス等の使用禁止フラグ */
    public boolean disabledFlag;
	
	//以下の項目はメール配信関係
		
		/* メールの送信可否　*/
		public boolean mailSendFlag;
		
		/** 現役生かOBか 1:現役生　2:OB */
		public String activeOrOb;
		
		/** 全員か部ごとか 1:全員 2:部ごと */
		public String allOrClub;
		
		/* 選択した部 */
		public String[] clubListCheck = new String[0];
		
		/* メールのタイトル　*/
		public String title;
		
		/* メールの内容　*/
		public String content;
		
		/* メールの送信相手　*/
		public Set<Integer> MemberSendSet = new HashSet<Integer>();
		
		/* メールの送信相手　*/
		public Set<TMember> MemberSendSet2 = new HashSet<TMember>();
		
		/* メールの送信相手一覧 */
		public List<TMember> tMemberSendList;
		
		/* メールの送信者　*/
		public Integer registMemberId;
		
	//メール配信項目ここまで	
	
	//会議質問	
		
		//会議質問
		@Required(target = "questionConfirm")	
		public String question;
		
		//質問を会議登録者に送る
		public boolean questionSend;
		
		//既存の質問一覧
		public List<TPartyQuestion> tPartyQuestionList;
		
	//会議質問ここまで

	/** 部のリスト **/
	public List<TClub> clubList;
	
	/** 出席対象部のリスト **/
	public List<TPartyClub> tPartyClubList;
	
	/** 部のマップ **/
	public Map<String, String> clubMapSS;
	
	/** 部のマップ **/
	public Map<Integer, String> clubMapIS;
	
	/** 出席対象が部で選択されている場合　**/
	public Set<Integer> MemberSet = new HashSet<Integer>();

	
	
	//リッセットメソッド(※命名注意！！"reset"にすると、このformに関わるすべてのメソッドで呼び出される。)
	public void resetInput() {
		meetingName = null;
		meetingDay = null;
		meetingEndDay = null;
		meetingTime = null;
		meetingDeadlineDay = null;
		meetingRoom = null;
		meetingMemo = null;
		clubListCheck = new String[0];
		attendClub = null;
		mailSendFlag = false;
		activeOrOb = null;
		allOrClub = null;
		ObAttendFlag = null;
		meetingDeadlineDay = null;
		meetingNecessaryFlag = false;
		meetingResult = null;
		resultEditEndFlag = false;
		resultEditMemberId = null;
		question = null;
		questionSend = false;
		resultEditEndFlag = false;
		content = null;
		title = null;
	}
	
	//オリジナルチェック
    public ActionMessages validateBase(){
    	
        ActionMessages errors = new ActionMessages();
        
        //メール配信関係
        if (mailSendFlag) {
        	if (StringUtil.isNotEmpty(activeOrOb)) {
    			if ("1".equals(activeOrOb)) {
    				// 現役生の場合は全員か部ごとか
    				if (StringUtil.isNotEmpty(allOrClub)) {
    					if ("2".equals(allOrClub)) {
    						//　部ごとのメールの場合は部が選択されている必要がある
    						if ("1".equals(activeOrOb)) {
    							if (clubListCheck.length == 0) {
    								errors.add("clubListCheck",new ActionMessage("部を選択してください",false));
    							}
    						}
    					}
    				} else {
    					errors.add("allOrClub",new ActionMessage("送る範囲を選択してください",false));
    				}
    			}
    		} else {
    			errors.add("activeOrOb",new ActionMessage("送り相手を選択してください。",false));
    		}
        }
        
        //開催日が空の時の処理
		if (StringUtils.isNotEmpty(meetingTime)) {
				if (StringUtils.isEmpty(meetingDay)) {
				errors.add("meetingDay",new ActionMessage("開始時間を入力するときは開催日も入力してください。",false));
			}
		}
		
		//開催終了日が入力され、開始日が空白のとき
		if (StringUtils.isNotEmpty(meetingEndDay)) {
			if(StringUtils.isEmpty(meetingDay)){
				errors.add("meetingDay",new ActionMessage("終了日があるのに開始日がないのはおかしいです",false));
			}
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		
		// 会議日は過去にはできない
		if (StringUtils.isNotEmpty(meetingEndDay)) {
			try {
				Date mDay = sdf.parse(meetingEndDay);
				if (mDay.before(new Date())) {
					errors.add("meetingEndDay", new ActionMessage("過去に会議を予定したければタイムマシンを作ってからにしてください", false));
				}
			} catch (ParseException e) {
				errors.add("meetingEndDay", new ActionMessage("開催日はyyyy/mm/ddで入力する必要があります。(例:2014/05/11)", false));
			}
		}
		
		// 会議日は過去にはできない
				if (StringUtils.isNotEmpty(meetingDay)) {
					try {
						Date mDay = sdf.parse(meetingDay);
						if (mDay.before(new Date())) {
							errors.add("meetingDay", new ActionMessage("過去に会議を予定したければタイムマシンを作ってからにしてください", false));
						}
					} catch (ParseException e) {
						errors.add("meetingDay", new ActionMessage("開催日はyyyy/mm/ddで入力する必要があります。(例:2014/05/11)", false));
					}
				}
		
		// 締め切り日を過去にはできない
		if (StringUtils.isNotEmpty(meetingDeadlineDay)) {
			try {
				Date dDay = sdf.parse(meetingDeadlineDay);
				if (dDay.before(new Date())) {
					errors.add("meetingDeadlineDay", new ActionMessage("過去に会議を予定したければタイムマシンを作ってからにしてください", false));
				}
			} catch (ParseException e) {
				errors.add("meetingDeadlineDay", new ActionMessage("締切日はyyyy/mm/ddで入力する必要があります。(例:2014/05/12)", false));
			}
		}
		
		if (StringUtils.isNotEmpty(meetingDeadlineDay) && StringUtils.isNotEmpty(meetingDay) 
				&& errors.size("meetingDay") == 0 && errors.size("meetingDeadlineDay") == 0) {
		    Date mDay = null;
		    Date dDay = null;
		    
		    // 日付を作成します。
		    try {
		        dDay = sdf.parse(meetingDeadlineDay);
		        mDay = sdf.parse(meetingDay);
		    } catch (ParseException e) {
		    	//上でチェックしているので起こりえない
		    }
			
		    //開催日と締め切り日の差を取り割る
		    if (dDay.after(mDay)) {
		    	errors.add("meetingDay",new ActionMessage("締め切りの方が開催日より後になるとか意味がわかりません",false));
			    errors.add("meetingDeadlineDay",new ActionMessage("締め切りの方が開催日より後になるとか意味がわかりません",false));
			}
		}
		
		//型チェック
		if (StringUtils.isNotEmpty(meetingTime)) {
			try {
				new SimpleDateFormat("HH:mm").parse(meetingTime);
			} catch (ParseException e) {
				errors.add("meetingTime",new ActionMessage("開催時間はhh:mmで入力する必要があります。(例：09:21)",false));
			}
		}
		
        return errors;
    }
	
}
