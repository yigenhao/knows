package cn.tedu.knows.portal.controller;


import cn.tedu.knows.portal.exception.ServiceException;
import cn.tedu.knows.portal.model.Answer;
import cn.tedu.knows.portal.service.IAnswerService;
import cn.tedu.knows.portal.vo.AnswerVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tedu.cn
 * @since 2021-09-17
 */
@RestController
@RequestMapping("/v1/answers")
@Slf4j
public class AnswerController {
    @Resource
    private IAnswerService answerService;

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public Answer postAnswer(
            @Validated AnswerVo answerVo,
            BindingResult result,
            @AuthenticationPrincipal UserDetails userDetails){
        log.debug("接收表单信息：{}",answerVo);
        if (result.hasErrors()){
            String msg = result.getFieldError().getDefaultMessage();
            throw new ServiceException(msg);
        }
        Answer answer = answerService.saveAnswer(answerVo, userDetails.getUsername());
        return answer;
    }

    @GetMapping("question/{id}")
    public List<Answer> questionAnswers(@PathVariable Integer id){
        List<Answer> answers = answerService.getQuestionAnswers(id);
        return answers;

    }

    //采纳回答的方法
    @GetMapping("/{answerId}/solved")
    public String solved(
            @PathVariable Integer answerId,
            @AuthenticationPrincipal UserDetails userDetails){
        boolean accept = answerService.accept(answerId, userDetails.getUsername());
        if (accept){
            return "ok";
        }else {
            return "您没有权限或回答已被采纳";
        }
    }

}
