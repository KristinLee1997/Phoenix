package com.aries.phoenix;

import com.aries.hera.client.thrift.ThriftHelper;
import com.aries.hera.contract.thrift.dto.ServiceInfo;
import com.aries.hera.contract.thrift.service.DiscoverService;
import com.aries.phoenix.server.FileUploadServiceImpl;
import com.aries.phoenix.server.idl.FileUploadService;
import com.aries.phoenix.utils.PropertiesProxy;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

@Slf4j
public class ThriftServer {

    public void start() {
        try {
            TMultiplexedProcessor processor = new TMultiplexedProcessor();

            { // 准备注册 CategoryServer
                FileUploadService.Iface categoryServer = new FileUploadServiceImpl();
                FileUploadService.Processor categoryProcessor = new FileUploadService.Processor(categoryServer);
                String simpleName = FileUploadService.class.getSimpleName();
                log.info("simpleName:{}", simpleName);
                processor.registerProcessor(simpleName, categoryProcessor);
            }

            // 从配置文件获取端口 6022
            PropertiesProxy propertiesProxy = new PropertiesProxy("phoenix-service.properties");
            int port = Integer.parseInt(propertiesProxy.readProperty("port"));

            // 设置端口
            TServerTransport serverTransport = new TServerSocket(port);
            TServer server = new TSimpleServer(new TServer.Args(serverTransport).processor(processor));
            log.info("服务启动,端口:{}", port);

            // 用新线程开启服务。
            new Thread(() -> {
                try {
                    server.serve();
                } catch (Exception e) {
                    log.error("Phoenix服务异常,error:{}", e.getMessage(), e);
                }
            }, "thrift-service-starter-thread").start();


            // 注册服务
            PropertiesProxy heraProperties = new PropertiesProxy("phoenix-reg-service.properties");
            String apphost = heraProperties.readProperty("apphost");
            ServiceInfo serviceInfo = new ServiceInfo();
            serviceInfo.setName("Phoenix");
            serviceInfo.setHost(apphost);
            serviceInfo.setPort(port);
            ThriftHelper.call("Hera", DiscoverService.Client.class, client -> client.registe(serviceInfo));
            log.info("注册服务, appname:{}, host:{}, port:{}", "Hermes", apphost, port);
        } catch (Exception x) {
            log.error("创建服务失败,error:{}", x.getMessage(), x);
        }
    }
}