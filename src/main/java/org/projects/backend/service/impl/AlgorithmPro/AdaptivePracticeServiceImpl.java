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

import java.util.*;

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
        JSONObject resp = new JSONObject();
        if (!data.containsKey("chapterId")) {
            resp.put("is_successful", false);
            resp.put("error_message", "请保证存在章节ID chapterId属性！");
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
            } catch (NumberFormatException e) {
                resp.put("is_successful", false);
                resp.put("error_message", "请确保questionId可转为Int！");
                return JSON.toJSONString(resp);
            }
            isCorrect = data.get("isCorrect").equals("true") || data.get("isCorrect").equals("1");
            System.out.println("isCorrect is " + isCorrect);
            if (questionsMapper.selectById(questionId) == null) {
                resp.put("is_successful", false);
                resp.put("error_message", "未找到该id！");
                return JSON.toJSONString(resp);
            }
            UpdateWrapper<Questions> updateWrapperQuestions = new UpdateWrapper<>();
            UpdateWrapper<Chapter> updateWrapperChapter = new UpdateWrapper<>();
            updateWrapperQuestions.eq("id", questionId);
            updateWrapperChapter.eq("id", chapterId);
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

        System.out.print("当前已过的练习题目链表：");
        for (Integer question_id : practicedQuestionsIdList) {
            System.out.print(question_id     + " ");
        }
        System.out.println();

        Integer curPracticeHardValue = chapterMapper.selectById(chapterId).getPracticeHardValue();
        QueryWrapper<Questions> queryWrapperQuestions = new QueryWrapper<>();
        queryWrapperQuestions.eq("chapter_id", chapterId)
                .eq("hard_value", curPracticeHardValue)
                .orderBy(true, true, "RAND()")
                .last("limit 1");
        if (!practicedQuestionsIdList.isEmpty()) queryWrapperQuestions.notIn("id", practicedQuestionsIdList);
        Questions question = questionsMapper.selectOne(queryWrapperQuestions);
        if (question == null) {
            return JSON.toJSONString(new JSONObject());
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
            resp.put("myAnswer", question.getMyAnswer());
            resp.put("explanation", question.getExplanation());
            resp.put("isCorrect", !question.getIsWrong());
            if (question.getQuestionType() == 0 || question.getQuestionType() == 1) {
                //        选项
                String[] options = question.getOptions().split(",");
                List<String> optionList = new LinkedList<>();
                for ( String option : options ) optionList.add(option.trim());
                resp.put("options", optionList);
                //        标准答案
                String[] answers = question.getAnswer().split(",");
                List<String> answerList = new LinkedList<>();
                for ( String answer : answers ) answerList.add(answer.trim());
                resp.put("correctAnswer", answerList.toString());
            } else resp.put("correctAnswer", question.getAnswer());
            return JSON.toJSONString(resp);
        }
    }
}