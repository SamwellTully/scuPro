package com.controller;

import com.constant.FileServiceTypeEnum;
import com.constant.RequestTokenBO;
import com.Util.RequestTokenUtil;
import com.constant.SwaggerTagConst;
import com.dto.FileAddDTO;
import com.dto.ResponseDTO;
import com.service.FileService;
import com.vo.UploadVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 * @author fanteng
 * @date 2022/5/5 17:22
 * @description
 */
@RestController
@Api(tags = {SwaggerTagConst.Admin.MANAGER_FILE})
public class FileController {
    @Autowired
    private FileService fileService;

    @ApiOperation(value = "文件本地上传", notes = "文件本地上传")
    @PostMapping("/api/file/localUpload/{moduleType}")
    public ResponseDTO<UploadVO> localUpload(MultipartFile file, @PathVariable Integer moduleType) throws Exception {
        return fileService.fileUpload(file, FileServiceTypeEnum.LOCAL, moduleType);
    }

    @ApiOperation(value = "获取本地文件URL", notes = "获取文件URL")
    @PostMapping("/api/file/get")
    public ResponseDTO<String> localGetFile(String path) {
        return fileService.getFileUrl(path, FileServiceTypeEnum.LOCAL);
    }


    @ApiOperation(value = "系统文件保存通用接口")
    @PostMapping("/api/file/save")
    public ResponseDTO<String> saveFile(@Valid @RequestBody FileAddDTO addDTO) {
        RequestTokenBO requestToken = RequestTokenUtil.getRequestUser();
        return fileService.saveFile(addDTO,requestToken);
    }
}
