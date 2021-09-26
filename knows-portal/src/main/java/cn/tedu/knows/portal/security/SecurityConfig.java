package cn.tedu.knows.portal.security;
import cn.tedu.knows.portal.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 配置权限管理 需要继承Spring Security框架提供的WebSecurityConfigurerAdapter
 * 并重写configure方法
 * @author snake
 * @create 2021-09-17 20:55
 */
//配置类
@Configuration
//启动SpringSecurity的权限管理功能
//开启后默认所有资源都需要登录才能访问
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Override
    //auto开头：资格的意思
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 这个方法就是Spring Security提供的方法
        // 它会获得我们编写代码返回的UserDetails对象,并进行登录验证
        auth.userDetailsService(userDetailsService);

    }
    //SpringSecurity框架默认所有资源都需要登录才能访问
    //指定资源不需要登录即可访问并指定自己的登录界面
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()    //设置请求限制
                .antMatchers(       //指定受影响的资源
                        "/img/**",  // **表示包括该子包中的所有资源
                        "/js/*",
                        "/css/*",
                        "/bower_components/**",
                        "/login.html",
                        "/register.html",
                        "/register"
                ).permitAll()       //全部允许，不需要登录也可以访问
                .anyRequest()       //其他请求、资源
                .authenticated()    //需要登录才能访问
                .and()              //上面的设置已完成
                .formLogin()        //使用表单登录
                .loginPage("/login.html")       //指定登录页面
                .loginProcessingUrl("/login")   //设置登录提交路径
                .failureUrl("/login.html?error")//登录失败时跳转的网页
                .defaultSuccessUrl("/index_student.html")   //登录成功时默认跳转网页即用户未指定访问路径时
                .and()  //登录设置完毕
                .logout()   //登出设置
                .logoutUrl("/logout")   //登出访问路径
                .logoutSuccessUrl("/login.html?logout") //登出成功跳转页面
                .and().csrf().disable();//关闭防跨域攻击
    }
}
