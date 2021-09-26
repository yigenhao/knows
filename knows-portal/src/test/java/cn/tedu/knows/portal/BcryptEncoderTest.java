package cn.tedu.knows.portal;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 加密密码测试
 * @author snake
 * @create 2021-09-17 20:35
 */
@SpringBootTest
public class BcryptEncoderTest {
    PasswordEncoder encoder = new BCryptPasswordEncoder();
    //密码加密方法
    @Test
    public void pass(){
        String password = encoder.encode("123456");
        System.out.println(password);
        //$2a$10$Oxw3jdzdV2GF3lTH311HZOoGoJckNrJ0xihR96NOYaWMhvJRt4UkO
        //每次加密结果不同是为了保证安全性
        //每次加密结果不同的现象称为：随机加盐技术

    }
    @Test
    void matchTest(){
        boolean isMatch = encoder.matches("12346",
                "$2a$10$Oxw3jdzdV2GF3lTH311HZOoGoJckNrJ0xihR96NOYaWMhvJRt4UkO");
        System.out.println("是否匹配：" + isMatch);

    }
}
