package com.aries.phoenix.controller;

import com.aries.phoenix.biz.FileBiz;
import com.aries.phoenix.model.FileResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestController
public class FileController {
    @Resource
    private FileBiz fileBiz;

    @RequestMapping(value = "/upload")
    public FileResponse upload(MultipartFile file) throws IOException {
        int upload = fileBiz.uploadFile(file);
        if (upload < 0) {
            return FileResponse.error(500, "上传文件失败");
        }
        return FileResponse.ok();
    }

    @RequestMapping(value = "/getPhotoById")
    public void getPhotoById(Long id, final HttpServletResponse response) throws IOException {
        fileBiz.getPhotoById(id, response);
    }

    @RequestMapping(value = "/getSpacePhoto")
    public void getSpacePhoto(Long id, int width, int height, final HttpServletResponse response) throws IOException {
        fileBiz.getSpacePhoto(id, width, height, response);
    }
}
