package com.aries.phoenix.server;

import com.aries.phoenix.biz.FileBiz;
import com.aries.phoenix.model.thrift.FileData;
import com.aries.phoenix.model.thrift.Response;
import com.aries.phoenix.server.idl.FileUploadService;
import org.apache.thrift.TException;

import javax.annotation.Resource;

public class FileUploadServiceImpl implements FileUploadService.Iface {

    @Resource
    private FileBiz fileBiz;

    @Override
    public int uploadFile(FileData data) throws TException {
        return fileBiz.uploadFile(data);
    }

    @Override
    public Response getFileById(long id) throws TException {
        return fileBiz.getFileById(id);
    }

    @Override
    public Response getPhotoById(long id) throws TException {
        return fileBiz.getPhotoById(id);
    }
}