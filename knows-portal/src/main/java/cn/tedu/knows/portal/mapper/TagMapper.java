package cn.tedu.knows.portal.mapper;

import cn.tedu.knows.portal.model.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
* <p>
    *  Mapper 接口
    * </p>
*
* @author tedu.cn
* @since 2021-09-17
*/
    @Repository
    public interface TagMapper extends BaseMapper<Tag> {

    void selectList();
}
