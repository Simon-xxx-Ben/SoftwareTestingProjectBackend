package org.projects.backend.service.impl.examquestion;

import com.alibaba.fastjson2.JSONObject;
import org.projects.backend.mapper.ExamQuestionMapper;
import org.projects.backend.service.examquestion.ExamQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ExamQuestionServiceImpl implements ExamQuestionService {

    @Autowired
    ExamQuestionMapper examQuestionMapper;

    @Override
    public JSONObject getAllExamQuestion() {
        return null;
    }

    @Override
    public JSONObject getExamQuestionById(Map<String, String> data) {
        return null;
    }

    @Override
    public JSONObject insertExamQuestionById(Map<String, String> data) {
        return null;
    }

    @Override
    public JSONObject deleteExamQuestionById(Map<String, String> data) {
        return null;
    }

    @Override
    public JSONObject updateExamQuestionById(Map<String, String> data) {
        return null;
    }
}
