package cn.tedu.knows.portal.controller;


import cn.tedu.knows.portal.exception.ServiceException;
import cn.tedu.knows.portal.model.Question;
import cn.tedu.knows.portal.service.IQuestionService;
import cn.tedu.knows.portal.vo.QuestionVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author tedu.cn
 * @since 2021-09-17
 */
@RestController
@RequestMapping("/v1/questions")
@Slf4j
public class QuestionController {

    @Autowired
    private IQuestionService questionService;

    //@AuthenticationPrincipal
    @GetMapping("/my")
    public PageInfo<Question> getQuestions(
            @AuthenticationPrincipal UserDetails userDetails, Integer pageNum) {
        if (pageNum == null) {
            pageNum = 1;
        }
        Integer pageSize = 8;
        PageInfo<Question> pageInfo = questionService.getMyQuestions(userDetails.getUsername(), pageNum, pageSize);
        return pageInfo;

    }

    //学生发布问题的controller方法
    @PostMapping("")
    public String createQuestion(
            @Validated QuestionVo questionVo,
            BindingResult result,
            @AuthenticationPrincipal UserDetails userDetails) {
        log.debug("接收到表单信息：{}", questionVo);
        if (result.hasErrors()) {
            return result.getFieldError().getDefaultMessage();
        }
        questionService.saveQuestion(questionVo, userDetails.getUsername());
        return "ok";


    }

    //显示讲师首页任务列表
    @GetMapping("/teacher")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public PageInfo<Question> teacher(@AuthenticationPrincipal UserDetails userDetails,Integer pageNum){
        if (pageNum == null){
            pageNum = 1;
        }
        PageInfo<Question> pageInfo = questionService.getTeacherQuestions(userDetails.getUsername(), pageNum, 8);
        return pageInfo;


    }

    //获取question的id
    /*
        restful风格
        /{id}的含义时匹配任何在/v1/question之后的内容
    */
    @GetMapping("/{id}")
    public Question getQuestion(@PathVariable Integer id){
        if (id == null){
            throw new ServiceException("问题id无效");
        }
        Question question = questionService.getQuestionById(id);
        return question;

    }
}
