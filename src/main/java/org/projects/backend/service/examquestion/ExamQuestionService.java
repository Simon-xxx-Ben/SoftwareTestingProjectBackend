package org.projects.backend.service.examquestion;

import com.alibaba.fastjson2.JSONObject;
import org.projects.backend.pojo.ExamQuestion;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

public interface ExamQuestionService {
    JSONObject getAllExamQuestion();
    JSONObject getExamQuestionById(Map<String, String> data);
    JSONObject insertExamQuestionById(Map<String, String> data);
    JSONObject deleteExamQuestionById(Map<String, String> data);
    JSONObject updateExamQuestionById(Map<String, String> data);
}
