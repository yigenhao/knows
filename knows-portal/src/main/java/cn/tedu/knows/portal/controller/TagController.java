package cn.tedu.knows.portal.controller;


import cn.tedu.knows.portal.model.Tag;
import cn.tedu.knows.portal.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tedu.cn
 * @since 2021-09-17
 */
@RestController
@RequestMapping("/v1/tags")
public class TagController {
    @Autowired
    private ITagService tagService;
    //获取所有标签集合的控制方法
    //@GetMapper("")表示用类上映射的路径
    @GetMapping("")
    public List<Tag> getTags(){
       return tagService.getTags();

    }

}
