package cn.tedu.knows.portal.controller;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author snake
 * @create 2021-09-29 15:01
 */
//@Controller注解标记的控制其类中可以使用返回字符串的方式跳转页面
@Controller
public class HomeController {
    //SpringSecurity框架提供了判断是否具有权限或角色的类型
    public static final GrantedAuthority STUDENT = new SimpleGrantedAuthority("ROLE_STUDENT");
    public static final GrantedAuthority TEACHER = new SimpleGrantedAuthority("ROLE_TEACHER");
    @GetMapping(value = {"/index.html","/"})

    public String index(@AuthenticationPrincipal UserDetails userDetails){
        //角色是学生
        if (userDetails.getAuthorities().contains(STUDENT)){
            return "redirect:/index_student.html";
        } else if(userDetails.getAuthorities().contains(TEACHER)){
            //角色是老师
            return "redirect:/index_teacher.html";
        }
        return "redirect:/login.html";
    }
}
