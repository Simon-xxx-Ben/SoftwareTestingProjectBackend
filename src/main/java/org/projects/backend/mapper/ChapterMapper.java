package org.projects.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.projects.backend.pojo.Chapter;

@Mapper
public interface ChapterMapper extends BaseMapper<Chapter> {
}
