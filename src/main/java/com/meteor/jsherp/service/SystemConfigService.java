package com.meteor.jsherp.service;

import com.meteor.jsherp.domain.SystemConfig;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
* @author 刘鑫
* @description 针对表【jsh_system_config(系统参数)】的数据库操作Service
* @createDate 2023-04-13 18:07:56
*/
public interface SystemConfigService extends IService<SystemConfig>, CommonService<SystemConfig> {

    /**
     * 上传文件
     * @param file 需要上传的文件
     * @param bizPath 文件自定义路径
     * @param name 文件名称
     * @return
     */
    String uploadFile(MultipartFile file, String bizPath, String name, String filePath) throws IOException;

    /**
     * ISO-8859-1 ==> UTF-8 进行编码转换
     * 获取请求中，Rest风格请求中带斜杠等特殊字符的参数
     * @param request
     * @return
     */
    String getPathVariable(HttpServletRequest request);
}
