package cn.tedu.knows.portal.mapper;

import cn.tedu.knows.portal.model.Answer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* <p>
    *  Mapper 接口
    * </p>
*
* @author tedu.cn
* @since 2021-09-17
*/
    @Repository
    public interface AnswerMapper extends BaseMapper<Answer> {
        //根据问题id查询这个问题对应的所有回答，和各个回答对应的所有评论
        List<Answer> findAnswersWithCommentByQuestionId(Integer questionId);
        //上面的方法没有直接写sql语句的注解，mybatis会去对应的xml文件中进行映射

        //修改回答的采纳状态的方法
        @Update("update answer set accept_status = #{acceptStatus}" +
                " where id = #{answerId}")
        /*
        * @param注解是MyBatis框架提供的指定参数名
        * SpringBoot官方脚手架对JVM底层进行了优化
        * 多参数时建议使用@param
        * */
        int updateAcceptStatus(@Param("acceptStatus") Integer acceptStatus,
                                @Param("answerId") Integer id);

    }
