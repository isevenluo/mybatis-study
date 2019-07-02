package ccgogoing.architecture.mybatis.test;

import ccgogoing.architecture.mybatis.dao.UserDao;
import ccgogoing.architecture.mybatis.dao.UserDaoImpl;
import ccgogoing.architecture.mybatis.entity.User;
import com.architecture.mybatis.framework.sqlsession.SqlSessionFactory;
import com.architecture.mybatis.framework.sqlsession.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

public class MybatisTest {

    /**
     * SqlSessionFactory 加载依赖于全局配置文件的加载方式
     * 所以我们需要定制哪一种加载方式去创建SqlSessionFactory
     * 使用构建者模式去定制SqlSessionFactory
     */
    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void init() {
        String resource = "SqlMap-Config.xml";
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void testQueryUserById() {
        UserDao userDao = new UserDaoImpl(sqlSessionFactory);
        User user = userDao.queryUserById(1);
        System.out.println(user);
    }

    @Test
    public void testQueryUserByName() {
        UserDao userDao = new UserDaoImpl(sqlSessionFactory);
//        User user = new User();
//        user.setName("张三");
        User result = userDao.queryUserByName("张三");
        System.out.println(result);
    }
}
