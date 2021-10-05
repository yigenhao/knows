package cn.tedu.knows.portal.service;

import cn.tedu.knows.portal.model.Comment;
import cn.tedu.knows.portal.vo.CommentVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tedu.cn
 * @since 2021-09-17
 */
public interface ICommentService extends IService<Comment> {
    //新增评论的方法
    Comment saveComment(CommentVo commentVo,String username);

    //按id删除评论的方法
    boolean removeComment(Integer commentId,String username);

    //修改评论的方法
    Comment updateComment(Integer commentId,CommentVo commentVo,String username);
}
