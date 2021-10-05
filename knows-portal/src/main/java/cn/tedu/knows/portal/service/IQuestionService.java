package cn.tedu.knows.portal.service;

import cn.tedu.knows.portal.model.Question;
import cn.tedu.knows.portal.model.Tag;
import cn.tedu.knows.portal.vo.QuestionVo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tedu.cn
 * @since 2021-09-17
 */
public interface IQuestionService extends IService<Question> {
    PageInfo<Question> getMyQuestions(String username,
                                      Integer pageNum,Integer pageSize);

    //学生发布问题
    void saveQuestion(QuestionVo questionVo,String username);

    //根据用户id查询问题数
    Integer getQuestionNumbers(Integer id);

    //根据用户id查询
//    Integer countCollectesById(Integer id);

    //根据讲师用户名分页查询讲师任务列表
    PageInfo<Question> getTeacherQuestions(String username,
                                           Integer pageNum,Integer pageSize);
    //根据问题id查询对应question
    Question getQuestionById(Integer id);

}
