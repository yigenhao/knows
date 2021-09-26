package cn.tedu.knows.portal.service.impl;

import cn.tedu.knows.portal.model.Tag;
import cn.tedu.knows.portal.mapper.TagMapper;
import cn.tedu.knows.portal.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tedu.cn
 * @since 2021-09-17
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {
    //CopyOnWriteArrayList是一个线程安全的List集合类型 jdk1.8加入
    private List<Tag> tags = new CopyOnWriteArrayList<>();
    //ConcurrentHashMap是一个线程安全的Map集合类型，jdk1.8加入
    private Map<String,Tag> tagMap = new ConcurrentHashMap<>();

    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<Tag> getTags() {
        if (tags.isEmpty()) {
            synchronized (tags) {
                if (tags.isEmpty()) {
                    List<Tag> tagList = tagMapper.selectList(null);
                    this.tags.addAll(tagList);
                    for (Tag tag : tags) {
                        tagMap.put(tag.getName(),tag);
                    }
                }
            }
        }
        return tags;
    }

    @Override
    public Map<String, Tag> getTagsMap() {
        if (tagMap.isEmpty()){
            getTags();
        }
        return tagMap;
    }
}
