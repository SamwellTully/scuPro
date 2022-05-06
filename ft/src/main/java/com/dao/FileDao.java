package com.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dto.FileDTO;
import com.entity.FileEntity;
import com.vo.FileVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author fanteng
 * @date 2022/5/4 22:16
 * @description
 */
@Mapper
@Component
public interface FileDao extends BaseMapper<FileEntity> {
    /**
     * 批量添加上传文件
     *
     * @param fileDTOList
     * @return
     */
    Integer insertFileBatch(List<FileDTO> fileDTOList);


    /**
     * 批量删除
     *
     * @param moduleId
     * @return
     */
    Integer deleteFilesByModuleId(@Param("moduleId") String moduleId);

    /**
     * @param moduleId
     * @return
     */
    List<FileVO> listFilesByModuleId(@Param("moduleId") String moduleId);

    List<FileVO> listFilesByFileIds(@Param("fileIds") List<Long> fileIds);

}
