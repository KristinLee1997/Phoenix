package com.aries.phoenix.controller;

import com.aries.phoenix.biz.FileBiz;
import com.aries.phoenix.model.PhoenixHttpResponse;
import com.aries.phoenix.model.thrift.PhoenixResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestController
@RequestMapping("/phoenix")
@CrossOrigin
@Slf4j
public class FileController {
    @Resource
    private FileBiz fileBiz;

    @RequestMapping("/upload")
    public PhoenixHttpResponse upload(MultipartFile file) throws IOException {
        Long id = fileBiz.uploadFile(file);
        if (id < 0) {
            log.error(file.getOriginalFilename() + "文件上传失败");
            return PhoenixHttpResponse.error(500, "上传文件失败");
        }
        log.info("文件上传成功,id:{}", id);
        return PhoenixHttpResponse.ok(id);
    }

    @RequestMapping("/getPhotoById")
    public void getPhotoById(Long id, final HttpServletResponse response) throws IOException {
        fileBiz.getPhotoById(id, response);
    }

    @RequestMapping("/getSpacePhoto")
    public void getSpacePhoto(Long id, int width, int height, final HttpServletResponse response) throws IOException {
        fileBiz.getSpacePhoto(id, width, height, response);
    }

    @RequestMapping("getFileById")
    public PhoenixHttpResponse getFileById(Long id) {
        PhoenixHttpResponse phoenixHttpResponse = new PhoenixHttpResponse();
        PhoenixResponse response = fileBiz.getFileById(id);
        phoenixHttpResponse.setCode(200);
        phoenixHttpResponse.setMessage(String.format("获取文件id:%s成功", id));
        phoenixHttpResponse.setData(response.getFileResponse());
        return PhoenixHttpResponse.ok(response);
    }
}
