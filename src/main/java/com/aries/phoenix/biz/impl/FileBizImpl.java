package com.aries.phoenix.biz.impl;

import com.aries.phoenix.biz.FileBiz;
import com.aries.phoenix.enums.ImageType;
import com.aries.phoenix.model.File;
import com.aries.phoenix.service.FileService;
import com.aries.phoenix.utils.FileTypeFormatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

@Service
public class FileBizImpl implements FileBiz {
    private static final Logger logger = LoggerFactory.getLogger(FileBizImpl.class);

    @Autowired
    private PhotoService photoService;

    @Autowired
    private FileService fileService;

    @Override
    public int upload(MultipartFile multipartFile) throws IOException {
        InputStream is = multipartFile.getInputStream();
        byte[] fileData = new byte[(int) multipartFile.getSize()];
        is.read(fileData);
        String fileName = multipartFile.getOriginalFilename();
        File file = new File();
        file.setName(fileName);
        file.setFormat(FileTypeFormatUtil.getTypeByOriginFileName(fileName));
        file.setData(fileData);
        file.setSize(Long.parseLong(String.valueOf(fileData.length)));
        return fileService.insert(file);
    }

    @Override
    public void getPhotoById(Long id, final HttpServletResponse response) throws IOException {
        File file = fileService.selectByPrimaryKey(id);
        if (!ImageType.isExist(file.getFormat())) {
            logger.error("图片格式存在问题!");
            return;
        }
        byte[] data = file.getData();
        response.setContentType(FileTypeFormatUtil.getContentTypeByName(file.getName()));
        response.setCharacterEncoding("UTF-8");
        OutputStream outputSream = response.getOutputStream();
        InputStream in = new ByteArrayInputStream(data);
        int len = 0;
        byte[] buf = new byte[1024];
        while ((len = in.read(buf, 0, 1024)) != -1) {
            outputSream.write(buf, 0, len);
        }
        outputSream.close();
    }

    @Override
    public void getSpacePhoto(Long id, int width, int height, HttpServletResponse response) throws IOException {
        File file = fileService.selectByPrimaryKey(id);
        byte[] data = file.getData();
        if (width != 0 && height != 0) {
            data = scaleImage(file, width, height);
        }
        response.setContentType(FileTypeFormatUtil.getContentTypeByName(file.getName()));
        response.setCharacterEncoding("UTF-8");
        OutputStream outputSream = response.getOutputStream();
        InputStream in = new ByteArrayInputStream(data);
        int len = 0;
        byte[] buf = new byte[1024];
        while ((len = in.read(buf, 0, 1024)) != -1) {
            outputSream.write(buf, 0, len);
        }
        outputSream.close();
    }

    private static byte[] scaleImage(File file, int width, int height) throws IOException {
        BufferedImage buffered_oldImage = ImageIO.read(new ByteArrayInputStream(file.getData()));
        int imageOldWidth = buffered_oldImage.getWidth();
        int imageOldHeight = buffered_oldImage.getHeight();
        double scale_x = (double) width / imageOldWidth;
        double scale_y = (double) height / imageOldHeight;
        double scale_xy = Math.min(scale_x, scale_y);
        int imageNewWidth = (int) (imageOldWidth * scale_xy);
        int imageNewHeight = (int) (imageOldHeight * scale_xy);
        BufferedImage buffered_newImage = new BufferedImage(imageNewWidth, imageNewHeight, BufferedImage.TYPE_INT_RGB);
        buffered_newImage.getGraphics().drawImage(buffered_oldImage.getScaledInstance(imageNewWidth, imageNewHeight, BufferedImage.SCALE_SMOOTH), 0, 0, null);
        buffered_newImage.getGraphics().dispose();
        ByteArrayOutputStream outPutStream = new ByteArrayOutputStream();
        ImageIO.write(buffered_newImage, Objects.requireNonNull(FileTypeFormatUtil.getTypeByOriginFileName(file.getName())), outPutStream);
        return outPutStream.toByteArray();
    }
}
