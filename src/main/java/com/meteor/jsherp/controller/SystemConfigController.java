package com.meteor.jsherp.controller;

import com.meteor.jsherp.constant.ErpAllConstant;
import com.meteor.jsherp.domain.SystemConfig;
import com.meteor.jsherp.response.BaseResponse;
import com.meteor.jsherp.service.SystemConfigService;
import com.meteor.jsherp.service.UserService;
import com.meteor.jsherp.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author 刘鑫
 * @version 1.0
 */
@RequestMapping("/systemConfig")
@RestController
public class SystemConfigController {
    @Resource
    private SystemConfigService systemConfigService;

    private SystemConfig systemConfig;

    @Resource
    private UserService userService;

    @Value("${server.servlet.multipart.max-file-size}")
    private Long maxFileSize;

    @Value("${server.servlet.multipart.max-request-size}")
    private Long maxRequestSize;

    @Value(value="${file.path}")
    private String filePath;

    @GetMapping("/getCurrentInfo")
    public BaseResponse getSystemConfig(){
        BaseResponse response = new BaseResponse();
        try {
            if (systemConfig == null){
                systemConfig = systemConfigService.getOne(null);
            }
            ResponseUtil.resSuccess(response, systemConfig);
        } catch (Exception exception) {
            exception.printStackTrace();
            ResponseUtil.defaultServiceExceptionResponse(response);
        }
        return response;
    }

    /**
     * 获取文件上传大小限制
     * @return
     */
    @GetMapping("/fileSizeLimit")
    public BaseResponse getFileSizeLimit(){
        BaseResponse response = new BaseResponse();
        Long size = null;
        try {
            size = maxFileSize > maxRequestSize ? maxRequestSize : maxFileSize;
            ResponseUtil.resSuccess(response, size);
        } catch (Exception exception) {
            exception.printStackTrace();
            ResponseUtil.defaultServiceExceptionResponse(response);
        }
        return response;
    }

    public SystemConfig getCurrent(){
        if (systemConfig == null){
            getSystemConfig();
        }
        return systemConfig;
    }

    /**
     * 上传附件
     * @param file
     * @param token
     * @return
     */
    @PostMapping("/upload")
    public BaseResponse upload(MultipartFile file, @RequestHeader(ErpAllConstant.REQUEST_TOKEN_KEY) String token, HttpServletRequest request){
        BaseResponse response = new BaseResponse();
        try {
            String bizPath = request.getParameter("biz");
            String name = request.getParameter("name");
            if(bizPath == null){
                bizPath = "";
            }
            Long tenantId = userService.getLoginUser(token).getTenantId();
            bizPath = bizPath + File.separator + tenantId;
            String savePath = systemConfigService.uploadFile(file, bizPath, name, filePath);
            if(StringUtils.hasText(savePath)){
                ResponseUtil.resSuccess(response, savePath);
            }else {
                ResponseUtil.defaultServiceExceptionResponse(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
            ResponseUtil.defaultServiceExceptionResponse(response);
        }

        return response;
    }

    /**
     * 预览图片&下载文件
     * 请求地址：http://localhost:8080/common/static/{financial/afsdfasdfasdf_1547866868179.txt}
     *
     * @param request
     * @param response
     */
    @GetMapping(value = "/static/**")
    public void viewAndDownload(HttpServletRequest request, HttpServletResponse response){
        // ISO-8859-1 ==> UTF-8 进行编码转换
        String fileName = systemConfigService.getPathVariable(request);
        if(StringUtils.hasText(fileName)) {
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                fileName = fileName.replace("..", "");
                if(fileName.endsWith(",")){
                    fileName = fileName.substring(0, fileName.length() - 1);
                }
                fileName = filePath + File.separator + fileName;
                File file = new File(fileName);
                if(!file.exists()){
                    throw new RuntimeException("文件不存在");
                }
                //设置强制下载不打开
                response.setContentType("application/force-download");
                response.addHeader("Content-Disposition", "attachment;fileName=" + new String(file.getName().getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
                inputStream = new FileInputStream(file);
                outputStream = response.getOutputStream();
                byte[] bytes = new byte[1024];
                while (true) {
                    int read = inputStream.read(bytes);
                    if (read < 0) break;
                    outputStream.write(bytes,0, read);
                }
                response.flushBuffer();
            } catch (IOException e) {
                //日志打印
                e.printStackTrace();
                response.setStatus(404);
            }finally {
                if (inputStream != null){
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (outputStream != null){
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return;
    }

}
