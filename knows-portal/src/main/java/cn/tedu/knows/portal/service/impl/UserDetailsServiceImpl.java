package cn.tedu.knows.portal.service.impl;

import cn.tedu.knows.portal.mapper.UserMapper;
import cn.tedu.knows.portal.model.Permission;
import cn.tedu.knows.portal.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 根据用户名封装成UserDetails方便Spring Security在SecurityConfig类中验证，
 * 需要实现Spring Security提供的UserDetailsService
 * @author snake
 * @create 2021-09-18 11:11
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    /**
     *  实例化后有Spring Security自动调用
     * @param username
     * @return UserDetails 其中封装用户信息
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1.根据用户名获得用户信息
        User user = userMapper.findUserByUsername(username);

        if (user != null) {
            //2.如果user存在，根据用户id获取其所有权限
            List<Permission> userPermissions = userMapper.findUserPermissionsById(user.getId());
            //3.将权限存入String数组中
            String[] auth = new String[userPermissions.size()];
            int i = 0;
            for (Permission p : userPermissions){
                auth[i++] = p.getName();
            }
            //4.将用户信息封装在UserDetails对象中
            UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername()) //用户名
                    .password(user.getPassword()) //密码
                    .authorities(auth)          //拥有的权限
                    .accountLocked(user.getLocked() == 1)   //设置账户是否锁定（数据苦衷全部未锁定）
                    .disabled(user.getEnabled() == 0)       //设置账户是否被封
                    .build();
            //5.返回UserDetails对象
            return userDetails;
        }

        return null;
    }
}
