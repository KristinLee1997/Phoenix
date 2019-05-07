package com.aries.phoenix.server;

import com.aries.phoenix.biz.FileBiz;
import com.aries.phoenix.model.thrift.FileData;
import com.aries.phoenix.model.thrift.PhoenixResponse;
import com.aries.phoenix.server.idl.FileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;

import javax.annotation.Resource;
import java.util.Date;

@Slf4j
public class FileUploadServiceImpl implements FileUploadService.Iface {

    @Resource
    private FileBiz fileBiz;

    @Override
    public long uploadFile(FileData data) throws TException {
        Long id = fileBiz.uploadFile(data);
        if (id <= 0) {
            log.error("文件上传失败，name:{},time:{}", data.getName(), new Date());
            return 0;
        }
        log.info("上传文件成功，id:{}", id);
        return id;
    }

    @Override
    public PhoenixResponse getFileById(long id) throws TException {
        PhoenixResponse response = new PhoenixResponse();
        if (id <= 0) {
            response.setCode(400);
            response.setMsg("获取文件时参数id为空,time:" + new Date());
            return response;
        }
        response = fileBiz.getFileById(id);
        return response;
    }

    @Override
    public PhoenixResponse getPhotoById(long id) throws TException {
        PhoenixResponse phoenixResponse = new PhoenixResponse();
        if (id <= 0) {
            phoenixResponse.setCode(400);
            phoenixResponse.setMsg("获取图片时参数id为空,time:" + new Date());
            return phoenixResponse;
        }
        phoenixResponse = fileBiz.getPhotoById(id);
        return phoenixResponse;
    }
}