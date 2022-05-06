package com.service;

import com.Util.BaseBeanUtil;
import com.Util.BaseEnumUtil;
import com.constant.FileModuleTypeEnum;
import com.constant.FileResponseCodeConst;
import com.constant.FileServiceTypeEnum;
import com.constant.RequestTokenBO;
import com.dao.FileDao;
import com.dto.FileAddDTO;
import com.dto.ResponseDTO;
import com.entity.FileEntity;
import com.vo.FileVO;
import com.vo.UploadVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * @author fanteng
 * @date 2022/5/5 17:13
 * @description
 */
@Service
public class FileService {
    @Autowired
    private FileDao fileDao;

    @Autowired
    private java.util.Map<String, IFileService> fileServiceMap;

    /**
     * 获取文件服务实现
     *
     * @param typeEnum
     * @return
     */
    private IFileService getFileService(FileServiceTypeEnum typeEnum) {
        /**
         * 获取文件服务
         */
        String serviceName = typeEnum.getServiceName();
        IFileService fileService = fileServiceMap.get(serviceName);
        if (null == fileService) {
            throw new RuntimeException("未找到文件服务实现类：" + serviceName);
        }
        return fileService;
    }

    /**
     * 文件上传服务
     * @param file
     * @param typeEnum   文件服务类型枚举类
     * @param moduleType 文件夹类型
     * @return
     */
    public ResponseDTO<UploadVO> fileUpload(MultipartFile file, FileServiceTypeEnum typeEnum, Integer moduleType) {
        FileModuleTypeEnum moduleTypeEnum = BaseEnumUtil.getEnumByValue(moduleType, FileModuleTypeEnum.class);
        if (null == moduleTypeEnum) {
            return ResponseDTO.wrap(FileResponseCodeConst.FILE_MODULE_ERROR);
        }
        // 获取文件服务
        IFileService fileService = this.getFileService(typeEnum);
        ResponseDTO<UploadVO> response = fileService.fileUpload(file, moduleTypeEnum.getPath());
        return response;
    }

    /**
     * 根据文件绝对路径 获取文件URL
     *
     * @param path
     * @return
     */
    public ResponseDTO<String> getFileUrl(String path, FileServiceTypeEnum typeEnum) {
        IFileService fileService = this.getFileService(typeEnum);
        return fileService.getFileUrl(path);
    }
//
//    /**
//     * 批量插入
//     *
//     * @param fileDTOList
//     */
//    public void insertFileBatch(List<FileDTO> fileDTOList) {
//        fileDao.insertFileBatch(fileDTOList);
//    }

    /**
     * 根据module 删除文件信息
     *
     * @param moduleId
     * @return
     */
    public void deleteFilesByModuleId(String moduleId) {
        fileDao.deleteFilesByModuleId(moduleId);
    }

    /**
     * 根据module 获取文件信息
     *
     * @param moduleId
     * @return
     */
    public List<FileVO> listFilesByModuleId(String moduleId) {
        return fileDao.listFilesByModuleId(moduleId);
    }

//    /**
//     * @param filesStr 逗号分隔文件id字符串
//     * @return
//     */
//    public List<FileVO> getFileDTOList(String filesStr) {
//        if (StringUtils.isEmpty(filesStr)) {
//            return Lists.newArrayList();
//        }
//        String[] fileIds = filesStr.split(",");
//        List<Long> fileIdList = Arrays.asList(fileIds).stream().map(e -> Long.valueOf(e)).collect(Collectors.toList());
//        List<FileVO> files = fileDao.listFilesByFileIds(fileIdList);
//        return files;
//    }

    /**
     * 系统文件保存通用接口
     * @param addDTO
     * @return
     */
    public ResponseDTO<String> saveFile(FileAddDTO addDTO, RequestTokenBO requestToken) {
        FileEntity entity = BaseBeanUtil.copy(addDTO,FileEntity.class);
        entity.setCreaterUser(requestToken.getRequestUserId());
        entity.setCreateTime(new Date());
        fileDao.insert(entity);
        return ResponseDTO.succ();
    }

}
