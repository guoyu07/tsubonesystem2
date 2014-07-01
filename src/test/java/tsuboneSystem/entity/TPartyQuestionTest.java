package tsuboneSystem.entity;

import javax.annotation.Generated;
import org.seasar.extension.jdbc.JdbcManager;
import org.seasar.extension.unit.S2TestCase;

import static tsuboneSystem.names.TPartyQuestionNames.*;

/**
 * {@link TPartyQuestion}のテストクラスです。
 * 
 */
@Generated(value = {"S2JDBC-Gen 2.4.46", "org.seasar.extension.jdbc.gen.internal.model.EntityTestModelFactoryImpl"}, date = "2014/06/30 7:32:40")
public class TPartyQuestionTest extends S2TestCase {

    private JdbcManager jdbcManager;

    /**
     * 事前処理をします。
     * 
     * @throws Exception
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        include("s2jdbc.dicon");
    }

    /**
     * 識別子による取得をテストします。
     * 
     * @throws Exception
     */
    public void testFindById() throws Exception {
        jdbcManager.from(TPartyQuestion.class).id(1).getSingleResult();
    }

    /**
     * tPartyとの外部結合をテストします。
     * 
     * @throws Exception
     */
    public void testLeftOuterJoin_tParty() throws Exception {
        jdbcManager.from(TPartyQuestion.class).leftOuterJoin(tParty()).id(1).getSingleResult();
    }

    /**
     * tMemberとの外部結合をテストします。
     * 
     * @throws Exception
     */
    public void testLeftOuterJoin_tMember() throws Exception {
        jdbcManager.from(TPartyQuestion.class).leftOuterJoin(tMember()).id(1).getSingleResult();
    }
}