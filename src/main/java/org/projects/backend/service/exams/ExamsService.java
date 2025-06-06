package org.projects.backend.service.exams;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.projects.backend.pojo.Chapter;
import org.projects.backend.pojo.Exams;
import org.projects.backend.pojo.Questions;

import java.util.List;
import java.util.Map;

public interface ExamsService {
    JSONObject getAllExams();
    JSONObject getExamById(Map<String, String> data);
    JSONObject insertExamById(Map<String, String> data);
    JSONObject deleteExamById(Map<String, String> data);
    JSONObject updateExamById(Map<String, String> data);
    JSONObject setExamScoreById(Map<String, String> data);
    String getAllExamsClassification();
}
