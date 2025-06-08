package org.projects.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.projects.backend.pojo.Questions;

@Mapper
public interface QuestionsMapper extends BaseMapper<Questions> {
}
