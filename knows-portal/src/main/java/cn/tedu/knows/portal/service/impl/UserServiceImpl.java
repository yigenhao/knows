package cn.tedu.knows.portal.service.impl;

import cn.tedu.knows.portal.exception.ServiceException;
import cn.tedu.knows.portal.mapper.ClassroomMapper;
import cn.tedu.knows.portal.mapper.UserRoleMapper;
import cn.tedu.knows.portal.model.Classroom;
import cn.tedu.knows.portal.model.User;
import cn.tedu.knows.portal.mapper.UserMapper;
import cn.tedu.knows.portal.model.UserRole;
import cn.tedu.knows.portal.service.IQuestionService;
import cn.tedu.knows.portal.service.IUserService;
import cn.tedu.knows.portal.vo.RegisterVo;
import cn.tedu.knows.portal.vo.UserVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tedu.cn
 * @since 2021-09-17
 */
@Service
public class

UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ClassroomMapper classroomMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    private PasswordEncoder encoder = new BCryptPasswordEncoder();

    //注册学生的实现方法
    @Override
    public void registerStudent(RegisterVo registerVo) {
        //1.验证邀请码
        QueryWrapper<Classroom> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("invite_code", registerVo.getInviteCode());
        Classroom classroom = classroomMapper.selectOne(queryWrapper);
        if (classroom == null) {
            throw new ServiceException("验证码未找到");
        }
        //2.验证手机号是否注册
        User user = userMapper.findUserByUsername(registerVo.getPhone());
        if (user != null) {
            throw new ServiceException("手机号已被注册");
        }

        //3.用户输入密码加密
        String encode = "{bcrypt}" + encoder.encode(registerVo.getPassword());

        //4.构建user对象并赋值
        User u = new User();
        u.setClassroomId(classroom.getId());
        u.setCreatetime(LocalDateTime.now());
        u.setLocked(0);
        u.setEnabled(1);
        //学生注册，类型只有学生
        u.setType(0);
        u.setNickname(registerVo.getNickname());
        u.setPassword(encode);
        u.setUsername(registerVo.getPhone());

        //5.user新增到数据库中
        /*
         * Mybatis Plus的insert()方法除了将对象新增到数据库外
         * 还会将自增列的值赋给传入参数对应的属性
         * */
        int num = userMapper.insert(u);
        if (num != 1) {
            throw new ServiceException("数据库异常,注册失败");
        }
        //6.构建用户和角色的关系对象，并新增到关系表中
        UserRole userRole = new UserRole();
        userRole.setRoleId(2);
        //u对象的id属性是上面insert()方法自动注入的
        userRole.setUserId(u.getId());
        int num2 = userRoleMapper.insert(userRole);
        if (num2 != 1) {
            throw new ServiceException("数据库异常，注册失败");
        }

    }
    //这里是普通初始化块,和java虚拟机加载无关
    //只要实例化当前类对象,在运行构造方法之前会先运行这里的代码
    {
        //利用Timer周期调用方法，每隔30分钟
        //以保证讲师集合每隔30分钟和数据库同步一次
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                synchronized (teachers){
                    synchronized (teacherMap){
                        teachers.clear();
                        teacherMap.clear();
                    }
                }
                System.out.println("缓存清除完毕");
            }
        },1000*60*30,1000*60*30);
    }

    private List<User> teachers = new CopyOnWriteArrayList<>();
    private Map<String, User> teacherMap = new ConcurrentHashMap<>();

    @Override
    public List<User> getTeachers() {
        if (teachers.isEmpty()) {
            synchronized (teachers) {
                if (teachers.isEmpty()) {
                    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("type", 1);
                    List<User> list = userMapper.selectList(queryWrapper);
                    teachers.addAll(list);
                    for (User teacher : teachers){
                        teacherMap.put(teacher.getNickname(),teacher);
                    }
                }
            }
        }
        return teachers;
    }

    @Override
    public Map<String, User> getTeacherMap() {
        if (teacherMap.isEmpty()){
            getTeachers();
        }
        return teacherMap;
    }

    //
    @Resource
    private IQuestionService questionService;

    @Override
    public UserVo getCurrentUserVo(String username) {
        User user = userMapper.findUserByUsername(username);
        Integer count = questionService.getQuestionNumbers(user.getId());
        UserVo userVo = new UserVo();
        userVo.setId(user.getId())
                .setNickname(user.getNickname())
                .setUsername(username)
                .setQuestions(count)
                .setCollections(0);
        return userVo;
    }
}
