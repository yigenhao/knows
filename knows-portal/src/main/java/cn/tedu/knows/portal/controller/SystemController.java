package cn.tedu.knows.portal.controller;

import cn.tedu.knows.portal.exception.ServiceException;
import cn.tedu.knows.portal.service.IUserService;
import cn.tedu.knows.portal.vo.RegisterVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * @author snake
 * @create 2021-09-18 20:47
 */
@RestController
@Slf4j
public class SystemController {
    @Autowired
    private IUserService userService;

    @PostMapping("/register")
    //@Validated表示启用Spring Validation框架
    public String registerStudent(@Validated RegisterVo registerVo, BindingResult result) {
        log.debug("接收到表单信息：{}", registerVo);
        if (result.hasErrors()) {
            return result.getFieldError().getDefaultMessage();
        }


        userService.registerStudent(registerVo);
        return "注册完成";

    }

    //获得配置文件中声明的数据
    @Value("${knows.resource.path}")
    private File resourcePath;
    @Value("${knows.resource.host}")
    private String resourceHost;

    @PostMapping("/upload/file")
    public String uploadFile(MultipartFile imageFile) throws IOException {
        //1.按日期创建要上传到的文件夹
        //path:2021/09/24
        String path = DateTimeFormatter.ofPattern("yyyy/MM/dd")
                .format(LocalDate.now());
        //folder:D:/upload/2021/09/24
        File folder = new File(resourcePath,path);
        //创建这个路径
        /*
        * mkdirs：无论有没有都创建这个路径
        * mkdir：上一级存在则创建，上一级不存在则报错
        * */
        folder.mkdirs();
        //2.处理上传的文件名，防止存储的文件名重名
        String fileName = imageFile.getOriginalFilename();
        //获取上传文件后缀名
        String ext = fileName.substring(fileName.lastIndexOf("."));
        //随机生成下载文件名前缀
        String name = UUID.randomUUID().toString() + ext;
        //name: fdjgjwghsdkfnsdjkfn.jpg
        //创建上传文件的最终位置
        File file = new File(folder,name);
        //执行上传
        imageFile.transferTo(file);

        //拼接上传成功文件的访问路径
        //http://localhost:8899/2021/09/24/xxx.jpg
        String url = resourceHost+ "/" + path + "/" + name;

        log.debug("访问路径为：{}",url);
        return url;


    }
}
