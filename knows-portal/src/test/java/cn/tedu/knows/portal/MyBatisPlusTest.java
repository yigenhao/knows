package cn.tedu.knows.portal;

import cn.tedu.knows.portal.mapper.TagMapper;

import cn.tedu.knows.portal.model.Tag;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

/**模块测试类最好与模块包结构一致
 * @author snake
 * @create 2021-09-16 21:35
 */
//这个注解必须写，才能正常测试SpringBoot项目中的内容
@SpringBootTest
//使用Lombok提供的日志
@Slf4j
public class MyBatisPlusTest {
    //从Spring容器中获得TagMapper
    @Autowired
    private TagMapper tagMapper;

    @Test
    public void addTest(){
        Tag tag = new Tag(23, "狂风亦有归途3", "admin", LocalDateTime.now());
        //下面这个insert方法就是MybatisPlus提供给我们的
        int insert = tagMapper.insert(tag);
        System.out.println(insert);

    }
    @Test
    public void getOne(){
        Tag tag = tagMapper.selectById(11);
        System.out.println(tag);
    }

    @Test
    public void getAll(){
        List<Tag> tags = tagMapper.selectList(null);
        tags.forEach(System.out::println);
    }
    @Test
    public void updateTest(){
        Tag tag = tagMapper.selectById(21);
        tag.setName("SpringBoot快速入门1");
        int update = tagMapper.updateById(tag);
        if (update > 0){
//            System.out.println("修改成功");
            log.info("修改成功");
        }


    }

    @Test
    public void deleteTest(){
        int i = tagMapper.deleteById(22);
        if (i > 0){
//            System.out.println("删除成功");
            //SpringBoot默认不会在控制台打印info以下级别的日志，可以通过修改日志打印配置来修改
            //实际上Mybatis/MyBatis Plus
            log.debug("删除成功");
        }
    }

}
