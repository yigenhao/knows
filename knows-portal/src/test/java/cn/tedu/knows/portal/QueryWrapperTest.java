package cn.tedu.knows.portal;

import cn.tedu.knows.portal.mapper.ClassroomMapper;
import cn.tedu.knows.portal.model.Classroom;
import cn.tedu.knows.portal.service.IUserService;
import cn.tedu.knows.portal.vo.RegisterVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author snake
 * @create 2021-09-18 21:57
 */
@SpringBootTest
public class QueryWrapperTest {
    @Autowired
    private ClassroomMapper classroomMapper;
    @Test
    void testQuery(){
        //QueryWrapper实际上是一个可以包含查询条件的对象
        QueryWrapper<Classroom> queryWrapper = new QueryWrapper<>();
        /*
        eq: 等于
        lt：小于
        gt：大于
        ge：大于等于
        le：小于等于
        ne：不等于
        in：
        or：
        and：
        like：模糊查询
        参数column：在数据库中的列名. val:值
        */
        queryWrapper.eq("invite_code","JSD2002-525416");
        //等于只有一个匹配结果，使用selectOne(QueryWrapper wrapper)即可
        Classroom classroom = classroomMapper.selectOne(queryWrapper);
        System.out.println(classroom);
    }

    @Autowired
    IUserService userService;
    @Test
    void testRegister(){
        RegisterVo registerVo = new RegisterVo();
        registerVo.setPhone("13311313669");
        registerVo.setNickname("狂风");
        registerVo.setInviteCode("JSD2002-525416");
        registerVo.setPassword("888888");
        userService.registerStudent(registerVo);
        System.out.println("注册成功");

    }
}
