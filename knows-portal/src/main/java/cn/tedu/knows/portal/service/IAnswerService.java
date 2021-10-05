package cn.tedu.knows.portal.service;

import cn.tedu.knows.portal.model.Answer;
import cn.tedu.knows.portal.vo.AnswerVo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tedu.cn
 * @since 2021-09-17
 */
public interface IAnswerService extends IService<Answer> {
    //新增回答
    Answer saveAnswer(AnswerVo answerVo,String username);

    //根据问题id查询回答的集合
    List<Answer> getQuestionAnswers(Integer questionId);

    //学生采纳问题的方法
    boolean accept(Integer answerId,String username);

}
