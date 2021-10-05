package cn.tedu.knows.portal;

import cn.tedu.knows.portal.mapper.AnswerMapper;
import cn.tedu.knows.portal.model.Answer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author snake
 * @create 2021-10-03 14:51
 */
@SpringBootTest
public class TestAnswersMapper {
    @Resource
    AnswerMapper answerMapper;
    @Test
    public void testMapper(){
        List<Answer> answers = answerMapper.findAnswersWithCommentByQuestionId(154);
        answers.forEach(System.out::println);

    }
}
