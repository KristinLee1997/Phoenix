package com.aries.phoenix.service.impl;

import com.aries.phoenix.mapper.FileModelMapper;
import com.aries.phoenix.model.FileModel;
import com.aries.phoenix.service.FileService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class FileServiceImpl implements FileService {

    @Resource
    private FileModelMapper fileMapper;

    @Override
    public int insert(FileModel file) {
        return fileMapper.insert(file);
    }

    @Override
    public FileModel selectByPrimaryKey(Long id) {
        return fileMapper.selectByPrimaryKey(id);
    }
}
