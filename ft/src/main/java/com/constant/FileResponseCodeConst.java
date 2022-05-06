package com.constant;

/**
 * @author fanteng
 * @date 2022/5/4 16:49
 * @description
 */
public class FileResponseCodeConst extends  ResponseCodeConst{

    /**
     * 4001 -4999
     */
    public static final FileResponseCodeConst FILE_EMPTY = new FileResponseCodeConst(4001, "上传文件不存在！");

    public static final FileResponseCodeConst FILE_SIZE_ERROR = new FileResponseCodeConst(4002, "上传文件超过%s，请重新上传！");

    public static final FileResponseCodeConst UPLOAD_ERROR = new FileResponseCodeConst(4005, "上传失败");

    public static final FileResponseCodeConst FILE_MODULE_ERROR = new FileResponseCodeConst(4007, "文件目录类型错误");

    public FileResponseCodeConst(int code, String msg) {
        super(code, msg);
    }
}
