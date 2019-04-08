package com.aries.phoenix.biz.impl;

import com.aries.phoenix.biz.ImageBiz;
import com.aries.phoenix.model.Photo;
import com.aries.phoenix.service.PhotoService;
import com.aries.phoenix.utils.PictureFormatUtil;
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
public class ImageBizImpl implements ImageBiz {

    @Autowired
    private PhotoService photoService;

    @Override
    public int upload(MultipartFile file) throws IOException {
        InputStream is = file.getInputStream();
        byte[] studentPhotoData = new byte[(int) file.getSize()];
        is.read(studentPhotoData);
        String fileName = file.getOriginalFilename();
        Photo photo = new Photo();
        photo.setPhotoData(studentPhotoData);
        photo.setName(fileName);
        return photoService.insert(photo);
    }

    @Override
    public void getPhotoById(Long id, final HttpServletResponse response) throws IOException {
        Photo photo = photoService.selectByPrimaryKey(id);
        byte[] data = photo.getPhotoData();
        response.setContentType(PictureFormatUtil.getContentTypeByName(photo.getName()));
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
        Photo photo = photoService.selectByPrimaryKey(id);
        byte[] data = photo.getPhotoData();
        if (width != 0 && height != 0) {
            data = scaleImage(photo, width, height);
        }
        response.setContentType(PictureFormatUtil.getContentTypeByName(photo.getName()));
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

    private static byte[] scaleImage(Photo photo, int width, int height) throws IOException {
        BufferedImage buffered_oldImage = ImageIO.read(new ByteArrayInputStream(photo.getPhotoData()));
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
        ImageIO.write(buffered_newImage, Objects.requireNonNull(PictureFormatUtil.getTypeByName(photo.getName())), outPutStream);
        return outPutStream.toByteArray();
    }
}
