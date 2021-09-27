package cn.tedu.knows.portal.controller;


import cn.tedu.knows.portal.model.User;
import cn.tedu.knows.portal.service.IUserService;
import cn.tedu.knows.portal.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tedu.cn
 * @since 2021-09-17
 */
@RestController
@RequestMapping("/v1/users")
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping("/get")
    public String get(){
        return "hello";
    }

    @GetMapping("/list")
    //前置资格要想访问这个list方法当前用户需要拥有下面注解指定的资格
    @PreAuthorize("hasAnyAuthority('abc')")
    public String list(){
        return "假装看到了用户的全部信息";

    }

    @GetMapping("/master")
    public List<User> getTeachers(){
        return userService.getTeachers();
    }

    @GetMapping("/me")
    public UserVo getUserVo(@AuthenticationPrincipal UserDetails userDetails){
        return userService.getCurrentUserVo(userDetails.getUsername());
    }

}
