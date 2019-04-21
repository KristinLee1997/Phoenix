package com.aries.phoenix.biz.impl;

import com.aries.phoenix.biz.FileBiz;
import com.aries.phoenix.enums.FileType;
import com.aries.phoenix.enums.ImageType;
import com.aries.phoenix.enums.TextType;
import com.aries.phoenix.model.FileModel;
import com.aries.phoenix.model.thrift.FileData;
import com.aries.phoenix.model.thrift.Response;
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
    private FileService fileService;

    @Override
    public int uploadImage(MultipartFile multipartFile) throws IOException {
        InputStream is = multipartFile.getInputStream();
        byte[] data = new byte[(int) multipartFile.getSize()];
        is.read(data);
        String fileName = multipartFile.getOriginalFilename();
        FileModel fileModel = new FileModel();
        fileModel.setName(fileName);
        fileModel.setFormat(FileTypeFormatUtil.getTypeByOriginFileName(fileName));
        fileModel.setData(data);
        fileModel.setSize(Long.parseLong(String.valueOf(data.length)));
        return fileService.insert(fileModel);
    }

    @Override
    public int uploadText(FileData data) {
        if (data.getName() == null) {
            logger.warn("传入数据名称为空，请检查数据后重新传入");
            return 0;
        }
        if (!FileType.isExist(FileTypeFormatUtil.getTypeByOriginFileName(data.getName()))) {
            logger.warn("传入数据格式不正确，请检查数据后重新传入");
            return 0;
        }
        if (data.getData() == null) {
            logger.warn("上传数据为空");
            return 0;
        }
        FileModel fileModel = new FileModel();
        fileModel.setName(data.getName());
        fileModel.setFormat(FileTypeFormatUtil.getTypeByOriginFileName(data.getName()));
        fileModel.setData(data.getData());
        fileModel.setSize(Long.parseLong(String.valueOf(data.getSize())));
        return fileService.insert(fileModel);
    }

    @Override
    public void getPhotoById(Long id, final HttpServletResponse response) throws IOException {
        FileModel fileModel = fileService.selectByPrimaryKey(id);
        if (fileModel == null) {
            logger.warn("id:" + id + "图片不存在!");
            return;
        }
        if (!ImageType.isExist(fileModel.getFormat())) {
            logger.warn("图片格式存在问题!");
            return;
        }
        byte[] data = fileModel.getData();
        response.setContentType(FileTypeFormatUtil.getContentTypeByName(fileModel.getName()));
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
    public Response getFileById(Long id) {
        FileModel fileModel = fileService.selectByPrimaryKey(id);
        Response response = new Response();
        if (fileModel == null) {
            logger.warn("id:" + id + "对应的文件不存在");
            response.setCode(400);
            return response;
        }
        if (!TextType.isExist(fileModel.getFormat())) {
            logger.warn("文件格式存在问题!");
            response.setCode(400);
            return response;
        }
        response.setName(fileModel.getName());
        response.setData(fileModel.getData());
        response.setFormat(fileModel.getFormat());
        response.setSize(fileModel.getSize());
        return response;
    }

    @Override
    public void getSpacePhoto(Long id, int width, int height, HttpServletResponse response) throws IOException {
        FileModel fileMoel = fileService.selectByPrimaryKey(id);
        byte[] data = fileMoel.getData();
        if (width != 0 && height != 0) {
            data = scaleImage(fileMoel, width, height);
        }
        response.setContentType(FileTypeFormatUtil.getContentTypeByName(fileMoel.getName()));
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

    private static byte[] scaleImage(FileModel fileModel, int width, int height) throws IOException {
        BufferedImage buffered_oldImage = ImageIO.read(new ByteArrayInputStream(fileModel.getData()));
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
        ImageIO.write(buffered_newImage, Objects.requireNonNull(FileTypeFormatUtil.getTypeByOriginFileName(fileModel.getName())), outPutStream);
        return outPutStream.toByteArray();
    }
}
