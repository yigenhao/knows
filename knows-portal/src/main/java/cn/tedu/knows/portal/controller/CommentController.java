package cn.tedu.knows.portal.controller;


import cn.tedu.knows.portal.exception.ServiceException;
import cn.tedu.knows.portal.model.Comment;
import cn.tedu.knows.portal.service.ICommentService;
import cn.tedu.knows.portal.vo.CommentVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tedu.cn
 * @since 2021-09-17
 */
@RestController
@RequestMapping("/v1/comments")
@Slf4j
public class CommentController {
    @Resource
    private ICommentService commentService;

    //@PostMapping等价于@PostMapping("")
    @PostMapping
    public Comment postComment(
            @Validated CommentVo commentVo,
            BindingResult result,
            @AuthenticationPrincipal UserDetails userDetails){
        log.debug("收到的评论信息：{}",commentVo);
        if (result.hasErrors()){
            String message = result.getFieldError().getDefaultMessage();
            throw new ServiceException(message);
        }
        Comment comment = commentService.saveComment(commentVo, userDetails.getUsername());
        return comment;
    }

    //删除评论的方法
    @GetMapping("/{id}/delete")
    public String remove(
            @PathVariable int id,
            @AuthenticationPrincipal UserDetails userDetails){
        boolean isDelete = commentService.removeComment(id, userDetails.getUsername());
        if (isDelete){
            return "ok";
        }else{
            return "没有权限删除";
        }

    }

    //修改评论的方法
    @PostMapping("/{id}/update")
    public Comment update(
            @PathVariable Integer id,
            @Validated CommentVo commentVo,
            BindingResult result,
            @AuthenticationPrincipal UserDetails userDetails){
        log.debug("修改表单的信息为：{}",commentVo);
        if (commentVo == null){
            String msg = result.getFieldError().getDefaultMessage();
            throw new ServiceException(msg);
        }
        Comment comment = commentService.updateComment(id, commentVo, userDetails.getUsername());
        return comment;

    }

}
