package com.meteor.jsherp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meteor.jsherp.domain.SystemConfig;
import com.meteor.jsherp.mapper.SystemConfigMapper;
import com.meteor.jsherp.service.LogService;
import com.meteor.jsherp.service.SystemConfigService;
import com.meteor.jsherp.service.common.ResourceInfo;
import com.meteor.jsherp.utils.FileUtils;
import com.meteor.jsherp.utils.StringUtil;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
* @author 刘鑫
* @description 针对表【jsh_system_config(系统参数)】的数据库操作Service实现
* @createDate 2023-04-13 18:07:56
*/
@Service
@ResourceInfo("systemConfig")
public class SystemConfigServiceImpl extends CommonServiceImpl<SystemConfigMapper, SystemConfig>
    implements SystemConfigService{

    @Resource
    private SystemConfigMapper systemConfigMapper;

    @Resource
    private LogService logService;

    @Override
    public String uploadFile(MultipartFile file, String bizPath, String name, String filePath) throws IOException {
        //储存文件的目录，如果不存在则新建目录
        File fileFold = new File(filePath + File.separator + bizPath + File.separator);
        if(!fileFold.exists()){
            fileFold.mkdirs();
        }
        String originalFilename = file.getOriginalFilename();
        originalFilename = FileUtils.getFileName(originalFilename);
        String fileName = "";
        if(originalFilename.indexOf(".") != -1){
            if(StringUtils.hasText(name) && name.indexOf(".") != -1){
                fileName = name.substring(0, name.lastIndexOf(".")) + "_" + System.currentTimeMillis() + name.substring(name.lastIndexOf("."));
            }else{

                fileName = originalFilename.substring(0, originalFilename.lastIndexOf(".")) + "_" + System.currentTimeMillis() + originalFilename.substring(originalFilename.lastIndexOf("."));
            }
        }else{
            fileName =originalFilename + "_" +System.currentTimeMillis();
        }
        String savePath = fileFold.getPath() + File.separator + fileName;
        File saveFile = new File(savePath);
        byte[] bytes = file.getBytes();
        FileOutputStream outputStream = new FileOutputStream(saveFile);
        try {
            outputStream.write(bytes);
        } catch (IOException e) {
            throw e;
        } finally {
            outputStream.close();
        }
        String result;
        if(StringUtils.hasText(bizPath)){
            result = bizPath + File.separator + fileName;
        }else {
            result = fileName;
        }
        if (result.contains("\\")){
            return result.replace("\\", "/");
        }
        return result;
    }

    @Override
    public String getPathVariable(HttpServletRequest request) {
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String bestMatchPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        return new AntPathMatcher().extractPathWithinPattern(bestMatchPattern, path);
    }

    @Override
    public List<? extends SystemConfig> select(Map<String, String> paramMap) {
        String search = paramMap.get("search");
        String companyName = StringUtil.getInfo(search, "companyName");
        Integer currentPage = Integer.parseInt(paramMap.get("currentPage"));
        Integer pageSize = Integer.parseInt(paramMap.get("pageSize"));
        Page<SystemConfig> page = new Page<>(currentPage, pageSize);
        QueryWrapper<SystemConfig> queryWrapper = new QueryWrapper<SystemConfig>().like(StringUtils.hasText(companyName), "company_name", companyName);
        page = systemConfigMapper.selectPage(page, queryWrapper);

        return page.getRecords();
    }

    @Override
    public int updateObj(JSONObject obj, String token) {
        SystemConfig systemConfig = JSONObject.parseObject(obj.toJSONString(), SystemConfig.class);
        int result = systemConfigMapper.updateById(systemConfig);
        logService.insertLog("系统配置","修改id为 " + systemConfig.getId() + " 的系统配置", 1);
        return result;
    }
}




