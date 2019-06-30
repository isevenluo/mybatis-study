package ccgogoing.architecture.mybatis.test;

import ccgogoing.architecture.mybatis.entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;

public class MybatisTest {



    @Test
    public void test() throws Exception {
        // 指定全局配置文件的类路径
        String resource = "SqlMapConfig.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession sqlSession = sqlSessionFactory.openSession();

        User user = sqlSession.selectOne("findUserById", 1);

        System.out.println(user);
    }
}
