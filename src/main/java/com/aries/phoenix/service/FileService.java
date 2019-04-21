package com.aries.phoenix.service;

import com.aries.phoenix.model.File;

public interface FileService {
    int insert(File file);

    File selectByPrimaryKey(Long id);
}
