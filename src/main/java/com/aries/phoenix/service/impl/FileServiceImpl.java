package com.aries.phoenix.service.impl;

import com.aries.phoenix.mapper.FileModelMapper;
import com.aries.phoenix.model.po.FileModel;
import com.aries.phoenix.model.po.FileModelExample;
import com.aries.phoenix.service.FileService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Resource
    private FileModelMapper fileMapper;

    @Override
    public int insert(FileModel file) {
        return fileMapper.insertSelective(file);
    }

    @Override
    public FileModel selectByPrimaryKey(Long id) {
        return fileMapper.selectByPrimaryKey(id);
    }

    @Override
    public FileModel selectByExample(FileModelExample example) {
        List<FileModel> fileModels = fileMapper.selectByExampleWithBLOBs(example);
        if (fileModels != null && fileModels.size() > 0) {
            return fileModels.get(0);
        }
        return null;
    }
}
