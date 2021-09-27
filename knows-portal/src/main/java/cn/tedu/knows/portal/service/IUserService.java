package cn.tedu.knows.portal.service;

import cn.tedu.knows.portal.model.User;
import cn.tedu.knows.portal.vo.RegisterVo;
import cn.tedu.knows.portal.vo.UserVo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tedu.cn
 * @since 2021-09-17
 */
public interface IUserService extends IService<User> {
    //注册学生
    void registerStudent(RegisterVo registerVo);

    //查询所有讲师
    List<User> getTeachers();

    //查询所有讲师的Map集合
    Map<String,User> getTeacherMap();

    //根据用户名详情获取UserVo对象
    UserVo getCurrentUserVo(String username);


}
