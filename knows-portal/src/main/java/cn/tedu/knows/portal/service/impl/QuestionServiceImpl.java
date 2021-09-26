package cn.tedu.knows.portal.service.impl;

import cn.tedu.knows.portal.exception.ServiceException;
import cn.tedu.knows.portal.mapper.*;
import cn.tedu.knows.portal.model.*;
import cn.tedu.knows.portal.service.IQuestionService;
import cn.tedu.knows.portal.service.ITagService;
import cn.tedu.knows.portal.service.IUserService;
import cn.tedu.knows.portal.vo.QuestionVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.TagName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tedu.cn
 * @since 2021-09-17
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements IQuestionService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private ITagService tagService;

    @Override
    public PageInfo<Question> getMyQuestions(String username,Integer pageNum,Integer pageSize) {
        User user = userMapper.findUserByUsername(username);
        if (user != null){
            QueryWrapper<Question> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id",user.getId());
            wrapper.eq("delete_status",0);
            wrapper.orderByDesc("createtime");
            PageHelper.startPage(pageNum,pageSize);
            List<Question> questions = questionMapper.selectList(wrapper);
            for(Question question :questions){
                question.setTags(tagName2Tags(question.getTagNames()));
            }

            return new PageInfo<>(questions);

        }
        return null;

    }
    //将标签名转换为List<Tag>
    private List<Tag> tagName2Tags(String tagName){
        String[] tagNames = tagName.split(",");
        ArrayList<Tag> tags = new ArrayList<>();
        Map<String, Tag> tagsMap = tagService.getTagsMap();
        for (int i = 0;i < tagNames.length;i++){
            tags.add(tagsMap.get(tagNames[i]));
        }
        return  tags;
    }

    @Autowired
    private QuestionTagMapper questionTagMapper;
    @Autowired
    private UserQuestionMapper userQuestionMapper;
    @Autowired
    private IUserService userService;

    /**
     * @Transactional功能，保证当前方法中的全部操作处于一个事务中
     * 所有数据库操作要么都执行, 要么都不执行
     * 如果没有发生异常,就都执行,如果任何位置发生了任何异常,就都不执行
     * 如果发生异常时已经有数据库操作完成了,这个操作会自动取消(回滚)
     * 使用时机:业务逻辑层方法
     * 如果方法中有增删改方法,尤其是两次或以上的增删改操作时
     * @param questionVo
     * @param username
     */
    @Transactional
    @Override
    public void saveQuestion(QuestionVo questionVo, String username) {
        //1.根据用户名获取用户信息
        User user = userMapper.findUserByUsername(username);
        //2.将用户选中的标签拼接为字符串
        StringBuilder stringBuilder = new StringBuilder();
        for (String tagName : questionVo.getTagNames()){
            stringBuilder.append(tagName).append(",");
        }
        String tagNames = stringBuilder.deleteCharAt(stringBuilder.length()-1).toString();
        //3.构建并注入Question对象
        Question question = new Question()
                .setTitle(questionVo.getTitle())
                .setContent(questionVo.getContent())
                .setUserNickName(user.getNickname())
                .setUserId(user.getId())
                .setCreatetime(LocalDateTime.now())
                .setTagNames(tagNames)
                .setStatus(0)
                .setPageViews(0)
                .setDeleteStatus(0)
                .setPublicStatus(0);
        //4.执行新增问题操作
        int num = questionMapper.insert(question);
        if (num != 1){
            throw new ServiceException("数据库异常,新增失败");
        }
        //新增成功后Mybatis-Plus自动将数据库中对应的自增列id赋值给question
        //5.执行和标签关系表(question_tag表)的数据新增操作
        //：
        List<Tag> tags = tagName2Tags(tagNames);
        for (Tag tag : tags){
            QuestionTag questionTag = new QuestionTag();
            questionTag.setQuestionId(question.getId())
                    .setTagId(tag.getId());
            int i = questionTagMapper.insert(questionTag);
            if (i != 1){
                throw new ServiceException("数据库异常，新增失败");
            }

        }
        //提问题者指定多名讲师，讲师与问题存在多对多关系
        //执行和用户表(讲师)关系表的数据新增操作（user_question）
        //先获取包含所有讲师的Map方便查找
        Map<String, User> teacherMap = userService.getTeacherMap();
        //遍历所有讲师集合，
        for (String teacherNickName : questionVo.getTeacherNames()){
            //根据昵称得到相关多个老师
            User teacher = teacherMap.get(teacherNickName);
            //构建UserQuestion对象并注入值
            UserQuestion userQuestion = new UserQuestion();
            userQuestion.setUserId(teacher.getId())
                    .setQuestionId(question.getId())
                    .setCreatetime(LocalDateTime.now());
            //执行新增操作
            int i = userQuestionMapper.insert(userQuestion);
            if (i != 1){
                throw new ServiceException("数据库异常，新增失败");
            }
        }


    }
}
