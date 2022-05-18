package com.isi.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.isi.dto.ProjectItem;
import com.isi.dto.Result;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author fanteng
 * @date 2022/5/13 10:27
 * @description
 */
public interface ExcelService extends IService<ProjectItem> {
    Result importProject(MultipartFile file);
    Result getExcelFiled(MultipartFile file);
}
