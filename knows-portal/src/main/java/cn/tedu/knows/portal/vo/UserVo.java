package cn.tedu.knows.portal.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户面板显示类
 * @author snake
 * @create 2021-09-26 16:07
 */
@Data
@Accessors(chain = true)
public class UserVo implements Serializable {
    private Integer id;
    private String username;
    private String nickname;
    //问题数
    private Integer questions;
    //收藏数
    private Integer collections;
}
