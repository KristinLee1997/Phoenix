package com.aries.phoenix.service;

import com.aries.phoenix.model.FileModel;

public interface FileService {
    int insert(FileModel file);

    FileModel selectByPrimaryKey(Long id);
}
