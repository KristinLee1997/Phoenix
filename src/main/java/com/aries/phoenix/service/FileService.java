package com.aries.phoenix.service;

import com.aries.phoenix.model.po.FileModel;
import com.aries.phoenix.model.po.FileModelExample;

public interface FileService {
    int insert(FileModel file);

    FileModel selectByPrimaryKey(Long id);

    FileModel selectByExample(FileModelExample example);
}
