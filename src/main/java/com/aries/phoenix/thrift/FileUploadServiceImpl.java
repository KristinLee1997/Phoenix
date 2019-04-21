package com.aries.phoenix.thrift;

import com.aries.phoenix.biz.FileBiz;
import com.aries.phoenix.model.thrift.FileData;
import com.aries.phoenix.model.thrift.Response;
import com.aries.phoenix.thrift.idl.FileUploadService;
import org.apache.thrift.TException;

import javax.annotation.Resource;

public class FileUploadServiceImpl implements FileUploadService.Iface {

    @Resource
    private FileBiz fileBiz;

    @Override
    public int uploadFile(FileData data) throws TException {
        return fileBiz.uploadText(data);
    }

    @Override
    public Response getFileById(long id) throws TException {
        return fileBiz.getFileById(id);
    }
}