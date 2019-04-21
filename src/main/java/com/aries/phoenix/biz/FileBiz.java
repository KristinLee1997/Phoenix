package com.aries.phoenix.biz;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface FileBiz {
    int upload(MultipartFile file) throws IOException;

    void getPhotoById(Long id, final HttpServletResponse response) throws IOException;

    void getSpacePhoto(Long id, int width, int height, final HttpServletResponse response) throws IOException;
}
