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

package tsuboneSystem.entity;
import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * TMemberエンティティクラス
 *
 * @author oowada
 */
@Entity
@Table(name = "T_MEMBER")
public class TMember implements Serializable {

	private static final long serialVersionUID = 1L;

    /* idプロパティ */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    public Integer id;

    /* 名前 */
    @Column(nullable = true, unique = false)
    public String  name;

    /* ハンドルネーム */
    @Column(nullable = false, unique = false)
    public String  hname;

    /* 性別 */
    @Column(nullable = true, unique = false)
    public String sex;

    /* メールアドレス */
    @Column(nullable = false, unique = false)
    public String mail;

    /* 学科 */
    @Column(nullable = true, unique = false)
    public String curriculum;

    /* 入学年度*/
    @Column(nullable = true, unique = false)
    public Integer entrance;

    /* 電話番号1 */
    @Column(nullable = true, unique = false)
    public String tel1;

    /* 電話番号2 */
    @Column(nullable = true, unique = false)
    public String tel2;

    /* 電話番号3 */
    @Column(nullable = true, unique = false)
    public String tel3;

    /* ID */
    @Column(nullable = false, unique = true)
    public String userName;

    /* パスワード */
    @Column(nullable = false, unique = false)
    public String password;

    /* OBフラグ */
    @Column(columnDefinition ="boolean default '0'")
    public boolean obFlag;

    /* 配信停止フラグ */
    @Column(columnDefinition ="boolean default '0'")
    public boolean sendStopFlag;

    /* メール不達フラグ */
    @Column(columnDefinition ="boolean default '0'")
    public boolean sendErrorFlag;

    /* 削除フラグ */
    @Column(columnDefinition ="boolean default '0'")
    public boolean deleteFlag;

    /* 仮登録メンバー */
    @Column(columnDefinition ="boolean default '0'")
    public boolean tempMemberFlag;

    /** memberIdをTAdminに結びつける */
    @OneToMany(mappedBy = "tMember")
    public List<TAdmin> tAdminList;

    /** memberIdをTLeadersに結びつける */
    @OneToMany(mappedBy = "tMember")
    public List<TLeaders> tLeadersList;

    /** memberIdをTPatyAttendに結びつける */
    @OneToMany(mappedBy = "tMember")
    public List<TPartyAttend> tPaertAttendList;

    /** memberIdをTPatyAttendに結びつける */
    @OneToMany(mappedBy = "tMember")
    public List<TParty> tPaertyList;

    /** memberIdをTPatyAttendに結びつける */
    @OneToMany(mappedBy = "editTMember")
    public List<TParty> tPaertyEditList;

    /** memberIdをTMailSendMemberに結びつける */
    @OneToMany(mappedBy = "tMember")
    public List<TMailSendMember> tMailSendMemberList;

    /** memberIdをTMailに結びつける */
    @OneToMany(mappedBy = "TMember")
    public List<TMail> tMailList;

    /** memberIdをTPartyQuestionに結びつける */
    @OneToMany(mappedBy = "TMember")
    public List<TPartyQuestion> tPartyQuestionList;

    /** memberIdをTPartyAnswerに結びつける */
    @OneToMany(mappedBy = "TMember")
    public List<TPartyAnswer> tPartyAnswerList;

    /** memberIdをTEnqueteAnswerに結びつける */
    @OneToMany(mappedBy = "tMember")
    public List<TEnqueteAnswer> tEnqueteAnswerList;
    
    /** memberIdをTTopAnnounceに結びつける */
    @OneToMany(mappedBy = "tMember")
    public List<TTopAnnounce> tTopAnnounceList;
    
    /** memberIdをTSubmitに結びつける */
    @OneToMany(mappedBy = "tMember")
    public List<TSubmit> tSubmitList;


}