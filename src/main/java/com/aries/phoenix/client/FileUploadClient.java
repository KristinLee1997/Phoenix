package com.aries.phoenix.client;

import com.aries.hera.client.thrift.ThriftHelper;
import com.aries.hera.client.thrift.exception.ServiceNotFoundException;
import com.aries.phoenix.model.thrift.FileData;
import com.aries.phoenix.model.thrift.PhoenixResponse;
import com.aries.phoenix.server.idl.FileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;

@Slf4j
public class FileUploadClient {
    public static Long upload(FileData data) throws TTransportException {
        Long id = null;
        try {
            id = ThriftHelper.call("Phoenix", FileUploadService.Client.class, client -> client.uploadFile(data));
        } catch (ServiceNotFoundException e) {
            throw new RuntimeException("Phoenix上传文件服务找不到了");
        }
        return id;
    }

    public static PhoenixResponse getFileById(Long id) throws TException {
        PhoenixResponse phoenixResponse = null;
        try {
            phoenixResponse = ThriftHelper.call("Phoenix", FileUploadService.Client.class, client -> client.getFileById(id));
        } catch (ServiceNotFoundException e) {
            log.error("获取文件id:{}失败", id);
            return phoenixResponse;
        }
        return phoenixResponse;
    }
}
