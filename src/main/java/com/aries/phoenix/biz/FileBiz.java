package com.aries.phoenix.biz;

import com.aries.phoenix.model.FileModel;
import com.aries.phoenix.model.thrift.FileData;
import com.aries.phoenix.model.thrift.Response;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface FileBiz {
    int uploadFile(MultipartFile file) throws IOException;

    int uploadFile(FileData data);

    int upload(FileModel data);

    void getPhotoById(Long id, final HttpServletResponse response) throws IOException;

    Response getPhotoById(Long id);

    Response getFileById(Long id);

    void getSpacePhoto(Long id, int width, int height, final HttpServletResponse response) throws IOException;
}
