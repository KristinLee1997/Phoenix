package com.aries.phoenix.service.impl;

import com.aries.phoenix.mapper.FileMapper;
import com.aries.phoenix.model.File;
import com.aries.phoenix.service.FileService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class FileServiceImpl implements FileService {

    @Resource
    private FileMapper fileMapper;

    @Override
    public int insert(File file) {
        return fileMapper.insert(file);
    }

    @Override
    public File selectByPrimaryKey(Long id) {
        return fileMapper.selectByPrimaryKey(id);
    }
}
