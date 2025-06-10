package org.projects.backend.service.impl.questions;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.projects.backend.mapper.QuestionsMapper;
import org.projects.backend.pojo.Questions;
import org.projects.backend.service.questions.QuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class QuestionsServiceImpl implements QuestionsService {

    @Autowired
    QuestionsMapper questionsMapper;

    @Override
    public JSONObject getAllQuestion() {
        return null;
    }

    @Override
    public String getQuestionById(Map<String, String> data) {
        JSONObject resp = new JSONObject();
//        JSONObject question = new JSONObject();
        if (!data.containsKey("id")){
            resp.put("is_successful", false);
            resp.put("error_message", "请保证存在id属性！");
            return JSON.toJSONString(resp);
        }
//        System.out.println(data.get("id"));
        QueryWrapper<Questions> queryWrapper = new QueryWrapper<>();
        Integer id;
        try {
            id = Integer.parseInt(data.get("id"));
            System.out.println(id);
            queryWrapper.eq("id", id);
        } catch (Exception e) {
            resp.put("is_successful", false);
            resp.put("error_message", "id格式错误，请确保id能够转为Int！");
            return JSON.toJSONString(resp);
        }
        Questions question_temp = questionsMapper.selectOne(queryWrapper);
        if (question_temp == null) {
            resp.put("is_successful", false);
            resp.put("error_message", "没有找到该id！");
            return JSON.toJSONString(resp);
        }
//        question.put("id", question_temp.getId());
////        question.put("hardValue", question_temp.getHardValue());
//        question.put("difficulty", question_temp.getHardValue());
//        question.put("content", question_temp.getContent());
//        if (question_temp.getQuestionType() == 0) question.put("questionType", "SINGLE_CHOICE");
//        else if (question_temp.getQuestionType() == 1)question.put("questionType", "MULTIPLE_CHOICE");
//        else if (question_temp.getQuestionType() == 2) question.put("questionType", "TRUE_FALSE");
//        else if (question_temp.getQuestionType() == 3)question.put("questionType", "FILL_IN_THE_BLANK");
//        else if (question_temp.getQuestionType() == 4) question.put("questionType", "SHORT_ANSWER");
//        question.put("correctAnswer", question_temp.getAnswer());
//        question.put("myAnswer", question_temp.getMyAnswer());
//        question.put("explanation", question_temp.getExplanation());
//        resp.put("question", question);
//        resp.put("is_successful", true);
//        resp.put("error_message", "Congratulations! Success!");

        resp.put("id", question_temp.getId());
//        resp.put("hardValue", question_temp.getHardValue());
//        resp.put("scoreValue", question_temp.getScoreValue());
        resp.put("difficulty", question_temp.getHardValue());
        resp.put("content", question_temp.getContent());
        if (question_temp.getQuestionType() == 0) resp.put("questionType", "SINGLE_CHOICE");
        else if (question_temp.getQuestionType() == 1) resp.put("questionType", "MULTIPLE_CHOICE");
        else if (question_temp.getQuestionType() == 2) resp.put("questionType", "TRUE_FALSE");
        else if (question_temp.getQuestionType() == 3) resp.put("questionType", "FILL_IN_THE_BLANK");
        else if (question_temp.getQuestionType() == 4) resp.put("questionType", "SHORT_ANSWER");
        resp.put("correctAnswer", question_temp.getAnswer());
        resp.put("myAnswer", question_temp.getMyAnswer());
        resp.put("explanation", question_temp.getExplanation());
        resp.put("isCorrect", !question_temp.getIsWrong());
        return JSON.toJSONString(resp);
    }

    @Override
    public JSONObject getQuestionsRandomTenByHardValue(Map<String, String> data) {
        JSONObject resp = new JSONObject();
        if (!data.containsKey("hardValue")){
            resp.put("is_successful", false);
            resp.put("error_message", "请保证存在题目难度hardValue属性！");
            return resp;
        }
        Integer hardValue;
        try {
            hardValue = Integer.parseInt(data.get("hardValue"));
        } catch (Exception e) {
            resp.put("is_successful", false);
            resp.put("error_message", "hardValue格式错误，请确保hardValue能够转为Int！");
            return resp;
        }
        if (hardValue != 1 && hardValue != 2 && hardValue != 3){
            resp.put("is_successful", false);
            resp.put("error_message", "请保证题目难度hardValue属性为1或2或3！");
            return resp;
        }        QueryWrapper<Questions> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("hard_value", Integer.parseInt(data.get("hardValue")))
                .orderBy(true, true, "RAND()")
                .last("limit 10");
        List<Questions> questionsList = questionsMapper.selectList(queryWrapper);
        resp.put("questionsList", JSON.toJSON(questionsList));
        resp.put("is_successful", true);
        resp.put("error_message", "Congratulations! Success!");
        return resp;
    }

    @Override
    public JSONObject getQuestionHardValueListByChapterId(Map<String, String> data) {
        JSONObject resp = new JSONObject();
        if (!data.containsKey("chapterId")){
            resp.put("is_successful", false);
            resp.put("error_message", "请保证存在章节chapterId属性！");
            return resp;
        }
        Integer chapterId;
        try {
            chapterId = Integer.parseInt(data.get("chapterId"));
        } catch (Exception e) {
            resp.put("is_successful", false);
            resp.put("error_message", "chapterId格式错误，请确保chapterId能够转为Int！");
            return resp;
        }
        QueryWrapper<Questions> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("chapter_id", chapterId)
                .select("DISTINCT hard_value");
        List<Integer> distinctHardValuesList = questionsMapper.selectObjs(queryWrapper);
        resp.put("distinctHardValuesList", JSON.toJSON(distinctHardValuesList));
        resp.put("is_successful", true);
        resp.put("error_message", "Congratulations! Success!");
        return resp;
    }

    @Override
    public String getWrongQuestionsList(Map<String, String> data) {
        JSONObject resp = new JSONObject();
        if (!data.containsKey("pageNo")){
            resp.put("is_successful", false);
            resp.put("error_message", "请保证存在pageNo属性！");
            return JSON.toJSONString(resp);
        }
        System.out.println(data.get("pageNo"));
        if (!data.containsKey("pageSize")){
            resp.put("is_successful", false);
            resp.put("error_message", "请保证存在pageSize属性！");
            return JSON.toJSONString(resp);
        }
        System.out.println(data.get("pageSize"));
        Integer pageNo, pageSize;
        try {
            pageNo = Integer.parseInt(data.get("pageNo"));
        } catch (Exception e) {
            resp.put("is_successful", false);
            resp.put("error_message", "pageNo格式错误，请确保pageNo能够转为Int！");
            return JSON.toJSONString(resp);
        }
        try {
            pageSize = Integer.parseInt(data.get("pageSize"));
        } catch (Exception e) {
            resp.put("is_successful", false);
            resp.put("error_message", "pageSize格式错误，请确保pageSize能够转为Int！");
            return JSON.toJSONString(resp);
        }
//        pageNo = Integer.parseInt(data.get("pageNo"));
//        pageSize = Integer.parseInt(data.get("pageSize"));
        System.out.println(pageNo + " " + pageSize);
        IPage<Questions> page = new Page<>(pageNo, pageSize);
        QueryWrapper<Questions> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_wrong", 1);
        IPage<Questions> wrongQuestionsPage = questionsMapper.selectPage(page, queryWrapper);
        List<Questions> wrongQuestionsList_temp = wrongQuestionsPage.getRecords();
        List<JSONObject> wrongQuestionsList = new LinkedList<>();
        for (Questions wrong_question : wrongQuestionsList_temp) {
            JSONObject wrongQuestion = new JSONObject();
            wrongQuestion.put("questionId", wrong_question.getId());
            wrongQuestion.put("content", wrong_question.getContent());
            wrongQuestion.put("myAnswer", wrong_question.getMyAnswer());
            wrongQuestion.put("correctAnswer", wrong_question.getAnswer());
//            System.out.println(wrong_question.getId());
            wrongQuestionsList.add(wrongQuestion);
        }
//        resp.put("wrongQuestionsList", JSON.toJSON(wrongQuestionsList));
//        resp.put("is_successful", true);
//        resp.put("error_message", "Congratulations! Success!");
//        return resp;
        return JSON.toJSONString(wrongQuestionsList);
    }

    @Override
    public JSONObject insertQuestionById(Map<String, String> data) {
        return null;
    }

    @Override
    public String deleteQuestionById(String id) {
        JSONObject resp = new JSONObject();
        Integer questionId;
        try {
            questionId = Integer.parseInt(id);
        } catch (Exception e) {
            resp.put("is_successful", false);
            resp.put("error_message", "没找到该id！");
            return JSON.toJSONString(resp);
        }
        if (questionsMapper.selectById(questionId) == null) {
            resp.put("is_successful", false);
            resp.put("error_message", "没找到该id！");
            return JSON.toJSONString(resp);
        }
        UpdateWrapper<Questions> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", questionId)
                        .set("is_wrong", 0);
        questionsMapper.update(updateWrapper);
        resp.put("is_successful", true);
        resp.put("error_message", "Congratulations! Success!");
        return JSON.toJSONString(resp);
    }

    @Override
    public JSONObject updateQuestionById(Map<String, String> data) {
        return null;
    }

    @Override
    public JSONObject updateCorrectOrIncorrectCount(Map<String, String> data) {
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
        Questions question_temp = questionsMapper.selectById(id);
        if (question_temp == null) {
            resp.put("is_successful", false);
            resp.put("error_message", "没有找到该id！");
            return resp;
        }
        String myAnswer_temp = data.get("myAnswer");
        Questions question = questionsMapper.selectById(id);
        UpdateWrapper<Questions> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id);
        if (myAnswer_temp.equals(question.getAnswer())) {
            updateWrapper.set("correct_count", (question.getCorrectCount() + 1));
        } else {
            updateWrapper.set("incorrect_count", (question.getIncorrectCount() + 1));
            updateWrapper.set("is_wrong", true);
        }
        updateWrapper.set("my_answer", myAnswer_temp);
        questionsMapper.update(question, updateWrapper);
        resp.put("is_successful", true);
        resp.put("error_message", "Congratulations! Success!");
        return resp;
    }
}
