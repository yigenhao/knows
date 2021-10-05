package cn.tedu.knows.portal.service.impl;

import cn.tedu.knows.portal.exception.ServiceException;
import cn.tedu.knows.portal.mapper.QuestionMapper;
import cn.tedu.knows.portal.mapper.UserMapper;
import cn.tedu.knows.portal.model.Answer;
import cn.tedu.knows.portal.mapper.AnswerMapper;
import cn.tedu.knows.portal.model.Question;
import cn.tedu.knows.portal.model.User;
import cn.tedu.knows.portal.service.IAnswerService;
import cn.tedu.knows.portal.vo.AnswerVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tedu.cn
 * @since 2021-09-17
 */
@Service
public class AnswerServiceImpl extends ServiceImpl<AnswerMapper, Answer> implements IAnswerService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private AnswerMapper answerMapper;
    @Autowired
    private QuestionMapper questionMapper;

    @Override
    @Transactional
    public Answer saveAnswer(AnswerVo answerVo, String username) {
        User user = userMapper.findUserByUsername(username);
        Answer answer = new Answer()
                .setQuestId(answerVo.getQuestionId())
                .setContent(answerVo.getContent())
                .setUserId(user.getId())
                .setUserNickName(user.getNickname())
                .setCreatetime(LocalDateTime.now())
                .setLikeCount(0)
                .setAcceptStatus(0);
        int num = answerMapper.insert(answer);
        if (num != 1){
            throw new ServiceException("数据库异常");
        }

        Question question = questionMapper.selectById(answerVo.getQuestionId());
        question.setStatus(Question.SOLVING);
        int num2 = questionMapper.updateById(question);
        if (num2 != 1){
            throw new ServiceException("数据库异常");
        }
        return answer;
    }


    @Override
    public List<Answer> getQuestionAnswers(Integer questionId) {
        List<Answer> answers = answerMapper.findAnswersWithCommentByQuestionId(questionId);
        return answers;
    }

    @Override
    @Transactional
    public boolean accept(Integer answerId, String username) {
        User user = userMapper.findUserByUsername(username);
        //查询answer对象
        Answer answer = answerMapper.selectById(answerId);
        Question question = questionMapper.selectById(answer.getQuestId());
        if (question.getUserId() == user.getId()){
            int num = answerMapper.updateAcceptStatus(1, answerId);
            if (num != 1){
                throw new ServiceException("数据库异常");
            }
            question.setStatus(Question.SOLVED);
            int num1 = questionMapper.updateById(question);
            if (num1 != 1){
                throw new ServiceException("数据库异常");
            }
            return true;
        }
        return false;
    }
}
