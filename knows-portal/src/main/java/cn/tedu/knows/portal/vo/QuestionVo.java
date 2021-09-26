package cn.tedu.knows.portal.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author snake
 * @create 2021-09-22 14:37
 */
@Data
//当前类的对象支持链式set赋值
@Accessors(chain = true)
public class QuestionVo implements Serializable {
    //问题题目
    @NotBlank(message = "题目不能为空")
    @Pattern(regexp = "^.{3,50}$",message = "标题是3-50个字符")
    private String title;
    //用户选中的所有标签
    @NotEmpty(message = "至少选择一个标签")
    private String[] tagNames={};

    //用户选中的所有讲师
    @NotEmpty(message = "至少选择一个讲师")
    private String[] teacherNames = {};
    //问题描述
    @NotBlank(message = "内容不能为空")
    private String content;
}
