package org.projects.backend.service.impl.exams;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.projects.backend.mapper.ExamQuestionMapper;
import org.projects.backend.mapper.ExamsMapper;
import org.projects.backend.mapper.QuestionsMapper;
import org.projects.backend.pojo.ExamQuestion;
import org.projects.backend.pojo.Exams;
import org.projects.backend.pojo.Questions;
import org.projects.backend.service.exams.ExamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class ExamsServiceImpl implements ExamsService {

    @Autowired
    ExamsMapper examsMapper;

    @Autowired
    ExamQuestionMapper examQuestionMapper;

    @Override
    public JSONObject getAllExams() {
        return null;
    }

    @Override
    public JSONObject getExamById(Map<String, String> data) {
        return null;
    }

    @Override
    public JSONObject insertExamById(Map<String, String> data) {
        return null;
    }

    @Override
    public JSONObject deleteExamById(Map<String, String> data) {
        return null;
    }

    @Override
    public JSONObject updateExamById(Map<String, String> data) {
        return null;
    }

    @Override
    public JSONObject setExamScoreById(Map<String, String> data) {
        JSONObject resp = new JSONObject();
        if (!data.containsKey("id")){
            resp.put("is_successful", false);
            resp.put("error_message", "请保证存在id属性！");
            return resp;
        }
        Integer id;
        try {
            id = Integer.parseInt(data.get("id"));
        } catch (Exception e) {
            resp.put("is_successful", false);
            resp.put("error_message", "id格式错误，请确保id能够转为Int！");
            return resp;
        }
        if (!data.containsKey("score")){
            resp.put("is_successful", false);
            resp.put("error_message", "请保证存在score属性！");
            return resp;
        }
        Double score;
        try {
            score = Double.valueOf(data.get("score"));
        } catch (Exception e) {
            resp.put("is_successful", false);
            resp.put("error_message", "score格式错误，请确保score能够转为Double！");
            return resp;
        }
        if (score > 100 || score < 0){
            resp.put("is_successful", false);
            resp.put("error_message", "成绩应在0-100之间！");
            return resp;
        }
        if (examsMapper.selectById(id) == null) {
            resp.put("is_successful", false);
            resp.put("error_message", "没有找到该id！");
            return resp;
        }
        UpdateWrapper<Exams> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id);
        updateWrapper.set("score", score);
        updateWrapper.set("complete_time", new Date());
        updateWrapper.set("is_completed", true);
        examsMapper.update(updateWrapper);
        resp.put("is_successful", true);
        resp.put("error_message", "Congratulations! Success!");
        return resp;
    }

    @Override
    public String getAllExamsClassification() {
        JSONObject resp = new JSONObject();
        List<JSONObject> examsList = new LinkedList<>();
        List<Exams> examsList_temp = examsMapper.selectList(null);
        for (Exams exam : examsList_temp) {
            JSONObject exam_temp = new JSONObject();
            exam_temp.put("id", exam.getId());
            exam_temp.put("name", exam.getName());
            exam_temp.put("startTime", exam.getStartTime());
            exam_temp.put("endTime", exam.getEndTime());
            exam_temp.put("duration", exam.getDuration());
            if (exam.getScore() != null) exam_temp.put("score", exam.getScore());
//            else exam_temp.put("score", null);
            Date currentDate = new Date();
            if (exam.getIsCompleted()) exam_temp.put("status", "COMPLETED");
            else if (currentDate.after(exam.getStartTime()) && currentDate.before(exam.getEndTime())) exam_temp.put("status", "PENDING");
            else exam_temp.put("status", "EXPIRED");
//            System.out.println(exam_temp.get("status"));
            QueryWrapper<ExamQuestion> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("exam_id", exam.getId());
            List<ExamQuestion> examQuestionList = examQuestionMapper.selectList(queryWrapper);
            List<Integer> examQuestionIdList = new LinkedList<>();
            for (ExamQuestion examQuestion : examQuestionList) {
                examQuestionIdList.add(examQuestion.getId());
            }
            exam_temp.put("questionList", examQuestionIdList);
            examsList.add(exam_temp);
        }
//        resp.put("examsList", JSON.toJSON(examsList));
//        resp.put("is_successful", true);
//        resp.put("error_message", "Congratulations! Success!");
//        return resp;
        return JSON.toJSONString(examsList);
    }
}
