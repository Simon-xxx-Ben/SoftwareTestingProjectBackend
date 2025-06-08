package org.projects.backend.service.impl.AlgorithmPro;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.projects.backend.mapper.ChapterMapper;
import org.projects.backend.mapper.QuestionsMapper;
import org.projects.backend.pojo.Chapter;
import org.projects.backend.pojo.Questions;
import org.projects.backend.service.AlgorithmPro.AdaptivePracticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class AdaptivePracticeServiceImpl implements AdaptivePracticeService {

    @Autowired
    QuestionsMapper questionsMapper;

    @Autowired
    ChapterMapper chapterMapper;

    @Override
    public boolean judgeAnswer(Integer id, String answer) {
        if (questionsMapper.selectById(id) == null) {
            return false;
        }
        Questions questions = questionsMapper.selectById(id);
        return answer.equals(questions.getAnswer());
    }

    @Override
    public Object getNextPractice(Map<String, String> data, List<Integer> practicedQuestionsIdList) {

        System.out.print("当前已过的练习题目链表：");
        for (Integer questionId : practicedQuestionsIdList) {
            System.out.print(questionId + " ");
        }
        System.out.println();

        JSONObject resp = new JSONObject();
        if (!data.containsKey("chapterId")) {
            resp.put("is_successful", false);
            resp.put("error_message", "请保证存在题目难度hardValue属性！");
            return JSON.toJSONString(resp);
        }
        String chapterId = data.get("chapterId");
        if (chapterMapper.selectById(chapterId) == null) {
            resp.put("is_successful", false);
            resp.put("error_message", "未找到该id！");
            return JSON.toJSONString(resp);
        }
        String myAnswer;
        Integer questionId;
        boolean isCorrect;
        if (data.containsKey("questionId") && data.containsKey("isCorrect") && data.containsKey("myAnswer")) {
            myAnswer = data.get("myAnswer");
            try {
                questionId = Integer.valueOf(data.get("questionId"));
                isCorrect = Boolean.parseBoolean(data.get("isCorrect"));
            } catch (NumberFormatException e) {
                resp.put("is_successful", false);
                resp.put("error_message", "questionId或isCorrect格式存在问题！");
                return JSON.toJSONString(resp);
            }
            if (questionsMapper.selectById(questionId) == null) {
                resp.put("is_successful", false);
                resp.put("error_message", "未找到该id！");
                return JSON.toJSONString(resp);
            }
            UpdateWrapper<Questions> updateWrapperQuestions = new UpdateWrapper<>();
            UpdateWrapper<Chapter> updateWrapperChapter = new UpdateWrapper<>();
            updateWrapperQuestions.eq("id", questionId);
            updateWrapperChapter.eq("id", chapterId);
//            if (isCorrect) {
//                updateWrapperQuestions.set("correct_count", questionsMapper.selectById(questionId).getCorrectCount() + 1);
//                if (chapterMapper.selectById(chapterId).getPracticeHardValue() == 2) updateWrapperChapter.set("practice_hard_value", 3);
//            }
//            else {
//                updateWrapperQuestions.set("incorrect_count", questionsMapper.selectById(questionId).getIncorrectCount() + 1);
//                if (chapterMapper.selectById(chapterId).getPracticeHardValue() == 2) updateWrapperChapter.set("practice_hard_value", 1);
//            }
            updateWrapperQuestions.set("my_answer", myAnswer);
            Integer oldPracticeHardValue = chapterMapper.selectById(chapterId).getPracticeHardValue();
            if (isCorrect) {
                updateWrapperQuestions.set("correct_count", questionsMapper.selectById(questionId).getCorrectCount() + 1);
                if (oldPracticeHardValue != 3) {
                    updateWrapperChapter.set("practice_hard_value", oldPracticeHardValue + 1);
                    chapterMapper.update(updateWrapperChapter);
                }
            } else {
                updateWrapperQuestions.set("correct_count", questionsMapper.selectById(questionId).getCorrectCount() - 1);
                updateWrapperQuestions.set("is_wrong", true);
                if (oldPracticeHardValue != 1) {
                    updateWrapperChapter.set("practice_hard_value", oldPracticeHardValue - 1);
                    chapterMapper.update(updateWrapperChapter);
                }
            }
            questionsMapper.update(updateWrapperQuestions);
        } else practicedQuestionsIdList.clear();

        Integer curPracticeHardValue = chapterMapper.selectById(chapterId).getPracticeHardValue();
        QueryWrapper<Questions> queryWrapperQuestions = new QueryWrapper<>();
        queryWrapperQuestions.eq("chapter_id", chapterId)
                .eq("hard_value", curPracticeHardValue)
                .orderBy(true, true, "RAND()")
                .last("limit 1");
        if (!practicedQuestionsIdList.isEmpty()) queryWrapperQuestions.notIn("id", practicedQuestionsIdList);
        Questions question = questionsMapper.selectOne(queryWrapperQuestions);
        if (question == null) {
//            resp.put("is_successful", false);
//            resp.put("error_message", "没有题目了！");
//            return JSON.toJSONString(resp);
            return new JSONObject();
        } else {
            practicedQuestionsIdList.add(question.getId());
            resp.put("id", question.getId());
            resp.put("difficulty", question.getHardValue());
            resp.put("content", question.getContent());
            if (question.getQuestionType() == 0) resp.put("questionType", "SINGLE_CHOICE");
            else if (question.getQuestionType() == 1) resp.put("questionType", "MULTIPLE_CHOICE");
            else if (question.getQuestionType() == 2) resp.put("questionType", "TRUE_FALSE");
            else if (question.getQuestionType() == 3) resp.put("questionType", "FILL_IN_THE_BLANK");
            else if (question.getQuestionType() == 4) resp.put("questionType", "SHORT_ANSWER");
            resp.put("correctAnswer", question.getAnswer());
            resp.put("myAnswer", question.getMyAnswer());
            resp.put("explanation", question.getExplanation());
            resp.put("isCorrect", !question.getIsWrong());
            return JSON.toJSONString(resp);
        }
    }
}