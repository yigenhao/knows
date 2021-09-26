package cn.tedu.knows.portal.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * vo 值对象
 * Spring Validation 验证框架常用注解：
 * @NotBlank
 * @NotNull
 * @Pattern
 * @NoEmtym
 * @author snake
 * @create 2021-09-18 20:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterVo {
    //@NotBlank 规定当前属性不能为空，只能验证字符串类型
    @NotBlank(message = "邀请码不能为空")
    private String inviteCode;//邀请码
    @NotBlank(message = "手机号不能为空")
    //@pattern 设置正则表达式的验证
    @Pattern(regexp = "^1\\d{10}$",message = "手机号格式不正确")
    private String phone;
    @NotBlank(message = "昵称不能为空")
    @Pattern(regexp = "^.{2,20}$",message = "昵称是2-20个字符")
    private String nickname;//昵称

    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^.{6,20}$",message = "密码是6-20个字符")
    private String password;
    @NotBlank(message = "确认密码不能为空")
    private String confirm;//确认密码

}
