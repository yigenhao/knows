package cn.tedu.knows.portal.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 封装回答问题的vo类
 * @author snake
 * @create 2021-10-02 13:50
 */
@Data
@Accessors(chain = true)
public class AnswerVo implements Serializable {
    //回答问题
    @NotBlank(message = "回答内容不能为空")
    private String content;

    //问题id
    @NotNull(message = "问题id不能为空")
    private Integer questionId;
}
