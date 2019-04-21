package com.aries.phoenix.mapper;

import com.aries.phoenix.model.FileModel;
import com.aries.phoenix.model.FileModelExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FileModelMapper {
    int countByExample(FileModelExample example);

    int deleteByExample(FileModelExample example);

    int deleteByPrimaryKey(Long id);

    int insert(FileModel record);

    int insertSelective(FileModel record);

    List<FileModel> selectByExampleWithBLOBs(FileModelExample example);

    List<FileModel> selectByExample(FileModelExample example);

    FileModel selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") FileModel record, @Param("example") FileModelExample example);

    int updateByExampleWithBLOBs(@Param("record") FileModel record, @Param("example") FileModelExample example);

    int updateByExample(@Param("record") FileModel record, @Param("example") FileModelExample example);

    int updateByPrimaryKeySelective(FileModel record);

    int updateByPrimaryKeyWithBLOBs(FileModel record);

    int updateByPrimaryKey(FileModel record);
}