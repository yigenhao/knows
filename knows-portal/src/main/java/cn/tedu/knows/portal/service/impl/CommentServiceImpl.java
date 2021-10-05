package cn.tedu.knows.portal.service.impl;

import cn.tedu.knows.portal.exception.ServiceException;
import cn.tedu.knows.portal.mapper.UserMapper;
import cn.tedu.knows.portal.model.Comment;
import cn.tedu.knows.portal.mapper.CommentMapper;
import cn.tedu.knows.portal.model.User;
import cn.tedu.knows.portal.service.ICommentService;
import cn.tedu.knows.portal.vo.CommentVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tedu.cn
 * @since 2021-09-17
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public Comment saveComment(CommentVo commentVo, String username) {
        User user = userMapper.findUserByUsername(username);
        Comment comment = new Comment()
                .setUserId(user.getId())
                .setUserNickName(user.getNickname())
                .setAnswerId(commentVo.getAnswerId())
                .setContent(commentVo.getContent())
                .setCreatetime(LocalDateTime.now());
        int num = commentMapper.insert(comment);
        if (num != 1){
            throw new ServiceException("数据库异常");
        }
        return comment;
    }

    @Override
    public boolean removeComment(Integer commentId, String username) {
        User user = userMapper.findUserByUsername(username);
        if (user.getType() == 1){
            int num = commentMapper.deleteById(commentId);
            return num == 1;
        }
        //如果不是讲师，要对比评论发布者id和当前登录的用户id
        //按评论id查询评论对象
        Comment comment = commentMapper.selectById(commentId);
        if (comment.getUserId()==user.getId()){
            int num = commentMapper.deleteById(commentId);
            return num == 1;
        }
        return false;
    }

    @Override
    public Comment updateComment(Integer commentId, CommentVo commentVo, String username) {
        User user = userMapper.findUserByUsername(username);
        Comment comment = commentMapper.selectById(commentId);
        if (user.getType().equals(1) || comment.getUserId().equals(user.getId())){
            comment.setContent(commentVo.getContent());
            int num = commentMapper.updateById(comment);
            if (num != 1){
                throw new ServiceException("数据库异常");
            }
            return comment;

        }
        return null;
    }
}
