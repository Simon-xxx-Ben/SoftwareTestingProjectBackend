package org.projects.backend.service.questions;

import com.alibaba.fastjson2.JSONObject;
import org.projects.backend.pojo.ExamQuestion;
import org.projects.backend.pojo.Questions;

import java.util.List;
import java.util.Map;

public interface QuestionsService {
    JSONObject getAllQuestion();
    String getQuestionById(Map<String, String> data);
    JSONObject getQuestionsRandomTenByHardValue(Map<String, String> data);
    JSONObject getQuestionHardValueListByChapterId(Map<String, String> data);
    String getWrongQuestionsList(Map<String, String> data);
    JSONObject insertQuestionById(Map<String, String> data);
    String deleteQuestionById(String id);
    JSONObject updateQuestionById(Map<String, String> data);
    JSONObject updateCorrectOrIncorrectCount(Map<String, String> data);
}
