package com.eastreach.pest.controller;

import com.eastreach.pest.error.BusinessException;
import com.eastreach.pest.error.EnumBusinessError;
import com.eastreach.pest.response.CommonReturnType;
import com.eastreach.pest.util.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * 文件上传网关
 **/
@CrossOrigin
@RestController
public class FileUploadGateWay extends RootGateWay {

    @Value("${crs.imagesPath}")
    private String mImagesPath;

    @Value("${crs.urlPath}")
    private String mURLPath;

    //处理文件上传
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public CommonReturnType uploadImg(@RequestParam("file") MultipartFile file) throws BusinessException {
        String fileNameOriginal = file.getOriginalFilename();
        String suffixName = fileNameOriginal.substring(fileNameOriginal.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString() + suffixName;
        String filePath = mImagesPath.replace("file:", "");
        String urlPath;
        if (mURLPath.endsWith("/")) {
            urlPath = mURLPath + fileName;
        } else {
            urlPath = mURLPath + "/" + fileName;
        }
        try {
            FileUtil.uploadFile(file.getBytes(), filePath, fileName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR, e.getMessage());
        }
        return CommonReturnType.create(urlPath);
    }


}
