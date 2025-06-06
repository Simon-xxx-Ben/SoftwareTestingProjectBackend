package org.projects.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.projects.backend.pojo.ExamQuestion;

@Mapper
public interface ExamQuestionMapper extends BaseMapper<ExamQuestion> {
}
