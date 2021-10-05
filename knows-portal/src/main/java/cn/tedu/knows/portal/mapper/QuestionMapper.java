package cn.tedu.knows.portal.mapper;

import cn.tedu.knows.portal.model.Question;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
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
    public interface QuestionMapper extends BaseMapper<Question> {
        //根据讲师id查询老师任务列表(包括自己的提问和学生给他的提问)
        @Select("select q.* \n" +
                "from question q\n" +
                "left join user_question uq \n" +
                "on q.id=uq.question_id\n" +
                "where q.user_id=#{id} or uq.user_id=#{id}\n" +
                "order by q.createtime desc")
        List<Question>  findTeacherQuestions(Integer id);

        //修改问题状态
        @Update("update question set")
        int updateStatus();

    }
