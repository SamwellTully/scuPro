package com.service;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.constant.FileResponseCodeConst;
import com.constant.FileServiceNameConst;
import com.dto.ResponseDTO;
import com.vo.UploadVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * @author fanteng
 * @date 2022/5/5 21:49
 * @description
 */
@Slf4j
@Service(FileServiceNameConst.LOCAL)
public abstract class FileServiceLocal implements IFileService {


    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;

    @Value("${file-upload-service.path}")
    private String fileParentPath;

    private static final Long DEFAULT_SIZE = 10 * 1024 * 1024L;

    @Override
    public ResponseDTO<UploadVO> fileUpload(MultipartFile multipartFile, String path) {
        if (null == multipartFile) {
            return ResponseDTO.wrap(FileResponseCodeConst.FILE_EMPTY);
        }
        Long maxSize = DEFAULT_SIZE;
        if (StringUtils.isNotEmpty(maxFileSize)) {
            String maxSizeStr = maxFileSize.toLowerCase().replace("mb", "");
            maxSize = Integer.valueOf(maxSizeStr) * 1024 * 1024L;
        }
        if (multipartFile.getSize() > maxSize) {
            return ResponseDTO.wrap(FileResponseCodeConst.FILE_SIZE_ERROR, String.format(FileResponseCodeConst.FILE_SIZE_ERROR.getMsg(), maxFileSize));
        }
        String filePath = fileParentPath;
        File directory = new File(filePath);
        if (!directory.exists()) {
            // 目录不存在，新建
            directory.mkdirs();
        }
        UploadVO localUploadVO = new UploadVO();
        String newFileName;
        File fileTemp;
        String originalFileName;
        originalFileName = multipartFile.getOriginalFilename();
        newFileName = this.generateFileName(originalFileName);
        fileTemp = new File(new File(filePath + newFileName).getAbsolutePath());
        try {
            multipartFile.transferTo(fileTemp);
            localUploadVO.setUrl(newFileName);
            localUploadVO.setFileName(newFileName);
            localUploadVO.setFilePath(path + "/" + newFileName);
            localUploadVO.setFileSize(multipartFile.getSize());
        } catch (IOException e) {
            if (fileTemp.exists() && fileTemp.isFile()) {
                fileTemp.delete();
            }
            log.error("", e);
            return ResponseDTO.wrap(FileResponseCodeConst.UPLOAD_ERROR);
        }
        return ResponseDTO.succData(localUploadVO);
    }

    @Override
    public ResponseEntity<byte[]> fileDownload(String key, String fileName, HttpServletRequest request) {

        String url = fileParentPath + key;
        // 创建文件
        File file = new File(url);
        return this.downloadMethod(file, request);
    }
}
