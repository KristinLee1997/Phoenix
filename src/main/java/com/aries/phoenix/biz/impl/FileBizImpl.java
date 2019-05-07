package com.aries.phoenix.biz.impl;

import com.aries.phoenix.biz.FileBiz;
import com.aries.phoenix.constants.FileConstants;
import com.aries.phoenix.enums.FileType;
import com.aries.phoenix.enums.TextType;
import com.aries.phoenix.model.po.FileModel;
import com.aries.phoenix.model.po.FileModelExample;
import com.aries.phoenix.model.thrift.FileData;
import com.aries.phoenix.model.thrift.FileResponse;
import com.aries.phoenix.model.thrift.PhoenixResponse;
import com.aries.phoenix.service.FileService;
import com.aries.phoenix.utils.FileTypeFormatUtil;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class FileBizImpl implements FileBiz {
    @Autowired
    private FileService fileService;

    @Override
    public Long uploadFile(MultipartFile multipartFile) throws IOException {
        InputStream is = multipartFile.getInputStream();
        byte[] data = new byte[(int) multipartFile.getSize()];
        is.read(data);
        String fileName = multipartFile.getOriginalFilename();

        FileModel fileModel = new FileModel();
        fileModel.setName(fileName);
        fileModel.setFormat(FileTypeFormatUtil.getTypeByOriginFileName(fileName));
        fileModel.setData(data);
        fileModel.setSize(Long.parseLong(String.valueOf(data.length)));
        return upload(fileModel);
    }

    @Override
    public Long uploadFile(FileData data) {
        FileModel fileModel = new FileModel();
        fileModel.setName(data.getName());
        fileModel.setFormat(FileTypeFormatUtil.getTypeByOriginFileName(data.getName()));
        fileModel.setData(data.getData());
        fileModel.setSize(Long.parseLong(String.valueOf(data.getSize())));
        Long id = upload(fileModel);
        return id;
    }

    @Override
    public Long upload(FileModel fileModel) {
        if (fileModel.getName() == null) {
            log.warn("name:" + fileModel.getName() + "传入数据名称为空，请检查数据后重新传入");
            return 0L;
        }
        if (!FileType.isExist(FileTypeFormatUtil.getTypeByOriginFileName(fileModel.getName()))) {
            log.warn("name:" + fileModel.getName() + "传入数据格式不正确，请检查数据后重新传入");
            return 0L;
        }
        if (fileModel.getData() == null) {
            log.warn("name:" + fileModel.getName() + "上传数据为空");
            return 0L;
        }
        int id = fileService.insert(fileModel);
        if (id <= 0) {
            log.error("name:" + fileModel.getName() + "插入数据库失败");
            return 0L;
        }
        return fileModel.getId();
    }


    @Override
    public void getPhotoById(Long id, final HttpServletResponse response) throws IOException {
        FileModelExample example = new FileModelExample();
        example.createCriteria().andIdEqualTo(id).andFormatIn(FileConstants.PHOTO_TYPE);
        FileModel fileModel = fileService.selectByExample(example);
        if (fileModel == null) {
            log.warn("id:" + id + "图片不存在!");
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
    public PhoenixResponse getPhotoById(Long id) {
        FileModelExample example = new FileModelExample();
        example.createCriteria().andIdEqualTo(id).andFormatIn(FileConstants.PHOTO_TYPE);
        FileModel fileModel = fileService.selectByExample(example);
        PhoenixResponse phoenixResponse = new PhoenixResponse();
        FileResponse fileResponse = new FileResponse();
        if (fileModel == null) {
            log.warn("id:{}图片不存在!", id);
            phoenixResponse.setCode(400);
            phoenixResponse.setMsg("id:" + id + "图片不存在!");
            return phoenixResponse;
        }
        fileResponse.setName(fileModel.getName());
        fileResponse.setFormat(fileModel.getFormat());
        fileResponse.setData(fileModel.getData());
        fileResponse.setSize(fileModel.getSize());
        phoenixResponse.setCode(200);
        phoenixResponse.setMsg("获取id:{" + id + "}图片成功");
        phoenixResponse.setFileResponse(fileResponse);
        return phoenixResponse;
    }

    @Override
    public PhoenixResponse getFileById(Long id) {
        FileModelExample example = new FileModelExample();
        example.createCriteria().andIdEqualTo(id).andFormatIn(FileConstants.TEXT_TYPE);
        FileModel fileModel = fileService.selectByExample(example);
        PhoenixResponse phoenixResponse = new PhoenixResponse();
        FileResponse fileResponse = new FileResponse();
        if (fileModel == null) {
            log.warn("id:" + id + "对应的文件不存在");
            phoenixResponse.setCode(400);
            phoenixResponse.setMsg("id:" + id + "对应的文件不存在");
            return phoenixResponse;
        }
        if (!TextType.isExist(fileModel.getFormat())) {
            log.warn("文件格式存在问题!");
            phoenixResponse.setCode(400);
            phoenixResponse.setMsg("文件格式存在问题!");
            return phoenixResponse;
        }
        fileResponse.setName(fileModel.getName());
        fileResponse.setData(fileModel.getData());
        fileResponse.setFormat(fileModel.getFormat());
        fileResponse.setSize(fileModel.getSize());
        phoenixResponse.setCode(200);
        phoenixResponse.setMsg("获取文件name:" + fileModel.getName() + "成功");
        phoenixResponse.setFileResponse(fileResponse);
        return phoenixResponse;
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
