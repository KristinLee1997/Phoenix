package com.aries.phoenix.service;

import com.aries.phoenix.model.FileModel;
import com.aries.phoenix.model.FileModelExample;

public interface FileService {
    int insert(FileModel file);

    FileModel selectByPrimaryKey(Long id);

    FileModel selectByExample(FileModelExample example);
}
