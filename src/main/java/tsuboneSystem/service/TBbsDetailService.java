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

package tsuboneSystem.service;

import java.util.List;

import javax.annotation.Generated;

import org.seasar.extension.jdbc.where.SimpleWhere;

import tsuboneSystem.entity.TBbsDetail;
import static org.seasar.extension.jdbc.operation.Operations.*;
import static tsuboneSystem.names.TBbsDetailNames.*;

/**
 * {@link TBbsDetail}のサービスクラスです。
 * 
 */
@Generated(value = {"S2JDBC-Gen 2.4.46", "org.seasar.extension.jdbc.gen.internal.model.ServiceModelFactoryImpl"}, date = "2014/07/10 13:17:31")
public class TBbsDetailService extends AbstractService<TBbsDetail> {

    /**
     * 識別子でエンティティを検索します。
     * 
     * @param id
     *            識別子
     * @return エンティティ
     */
    public TBbsDetail findById(Integer id) {
        return select().id(id).getSingleResult();
    }

    /**
     * 識別子の昇順ですべてのエンティティを検索します。
     * 
     * @return エンティティのリスト
     */
    public List<TBbsDetail> findAllOrderById() {
        return select().orderBy(asc(id())).getResultList();
    }
    
    /**
     * スレのタイトルIDですべてのエンティティを検索します。
     * 
     * @return エンティティのリスト
     */
    public List<TBbsDetail> findBySubjectId(Integer subjectId) {
    	SimpleWhere where = new SimpleWhere();
    	where.eq(deleteFlag(), Boolean.valueOf(false));
    	where.eq(subjectId(), subjectId);
        return select().where(where).innerJoin(tMember()).orderBy(asc(updateTime())).getResultList();
    }
}