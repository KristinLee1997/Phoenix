package com.aries.phoenix.controller;

import com.aries.phoenix.biz.ImageBiz;
import com.aries.phoenix.utils.Response;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestController
public class ImageController {
    @Resource
    private ImageBiz imageBiz;

    @RequestMapping("/upload")
    public Response upload(MultipartFile file) throws IOException {
        int upload = imageBiz.upload(file);
        if (upload < 0) {
            return Response.error(500, "上传图片失败");
        }
        return Response.ok();
    }

    @RequestMapping(value = "getPhotoById")
    public void getPhotoById(Long id, final HttpServletResponse response) throws IOException {
        imageBiz.getPhotoById(id, response);
    }

    @RequestMapping(value = "getSpacePhoto")
    public void getSpacePhoto(Long id, int width, int height, final HttpServletResponse response) throws IOException {
        imageBiz.getSpacePhoto(id, width, height, response);
    }
}
