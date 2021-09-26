package cn.tedu.knows.portal.service;

import cn.tedu.knows.portal.model.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tedu.cn
 * @since 2021-09-17
 */
public interface ITagService extends IService<Tag> {
    //展示所有标签List标签
    List<Tag> getTags();

    //展示标签的Map集合
    Map<String,Tag> getTagsMap();

}
