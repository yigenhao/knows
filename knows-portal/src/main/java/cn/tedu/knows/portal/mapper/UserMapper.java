package cn.tedu.knows.portal.mapper;

import cn.tedu.knows.portal.model.Permission;
import cn.tedu.knows.portal.model.User;
import cn.tedu.knows.portal.vo.UserVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author tedu.cn
 * @since 2021-09-17
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
    //根据用户名查询用户信息
    @Select("select * from user where username = #{username}")
    User findUserByUsername(String username);

    //根据用户id查询这个用户的所有权限
    @Select("select p.id,p.name" +
            " FROM user u" +
            " left JOIN user_role ur on u.id=ur.user_id" +
            " LEFT JOIN role r on r.id=ur.role_id" +
            " left join role_permission rp on r.id=rp.role_id" +
            " left join permission p on rp.permission_id=p.id" +
            " where u.id=#{id}")
    List<Permission> findUserPermissionsById(Integer id);

    //更具用户名那个查询UserVo类型对象
    @Select("select id,username,nickname from user where username={username}")
    UserVo findUserVoByUsername(String username);

}
