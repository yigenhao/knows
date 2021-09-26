package cn.tedu.knows.portal;

import cn.tedu.knows.portal.mapper.UserMapper;
import cn.tedu.knows.portal.model.Permission;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author snake
 * @create 2021-09-18 11:05
 */
@SpringBootTest
public class UsermapperTest {
    @Autowired
    UserMapper userMapper;
    @Test
    public void test1(){
        List<Permission> permissions = userMapper.findUserPermissionsById(3);
        for (Permission permission : permissions) {
            System.out.println(permission);

        }

    }
}
