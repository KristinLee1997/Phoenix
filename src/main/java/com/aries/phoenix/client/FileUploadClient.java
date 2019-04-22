package com.aries.phoenix.client;

import com.aries.phoenix.model.thrift.FileData;
import com.aries.phoenix.model.thrift.Response;
import org.apache.thrift.TException;

import javax.annotation.Resource;

public class FileUploadClient {

    @Resource
    private static FileUploadService.Iface fileUploadservice;

    public static void upload(FileData data) throws TException {
        fileUploadservice.uploadFile(data);
    }

    public static Response getFileById(Long id) throws TException {
        return fileUploadservice.getFileById(id);
    }
}
