package com.aries.phoenix.service.impl;

import com.aries.phoenix.mapper.PhotoMapper;
import com.aries.phoenix.model.Photo;
import com.aries.phoenix.service.PhotoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PhotoServiceImpl implements PhotoService {

    @Resource
    private PhotoMapper photoMapper;

    @Override
    public int insert(Photo photo) {
        return photoMapper.insert(photo);
    }

    @Override
    public Photo selectByPrimaryKey(Long id) {
        return photoMapper.selectByPrimaryKey(id);
    }
}
