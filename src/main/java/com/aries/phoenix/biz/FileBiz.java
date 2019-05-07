package com.aries.phoenix.biz;

import com.aries.phoenix.model.po.FileModel;
import com.aries.phoenix.model.thrift.FileData;
import com.aries.phoenix.model.thrift.PhoenixResponse;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface FileBiz {
    Long uploadFile(MultipartFile file) throws IOException;

    Long uploadFile(FileData data);

    Long upload(FileModel data);

    void getPhotoById(Long id, final HttpServletResponse response) throws IOException;

    PhoenixResponse getPhotoById(Long id);

    PhoenixResponse getFileById(Long id);

    void getSpacePhoto(Long id, int width, int height, final HttpServletResponse response) throws IOException;
}
