package com.aries.phoenix.service;

import com.aries.phoenix.model.Photo;

public interface PhotoService {
    int insert(Photo photo);

    Photo selectByPrimaryKey(Long id);
}
