package cn.tedu.knows.portal.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author snake
 * @create 2021-10-02 22:36
 */
@Data
@Accessors(chain = true)
public class CommentVo implements Serializable {
    //评论内容
    @NotBlank(message = "评论内容不能为空")
    private String content;

    //回答id
    @NotNull(message = "问题id不能为空")
    private Integer answerId;
}
