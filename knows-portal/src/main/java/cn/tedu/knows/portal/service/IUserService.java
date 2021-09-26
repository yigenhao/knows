package cn.tedu.knows.portal.service;

import cn.tedu.knows.portal.model.User;
import cn.tedu.knows.portal.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

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

}
