package ccgogoing.architecture.mybatis.dao;

import ccgogoing.architecture.mybatis.entity.User;

/**
 * @description:
 * @outhor: chong
 * @create: 2019-06-30 18:29
 */
public interface UserDao {

    User queryUserById(Integer id);

    User queryUserByName(String name);
}
