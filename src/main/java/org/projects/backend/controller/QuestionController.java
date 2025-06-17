package org.projects.backend.controller;

import com.alibaba.fastjson2.JSONObject;
import org.projects.backend.service.questions.QuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class QuestionController {

    @Autowired
    QuestionsService questionsService;

//    @GetMapping("/api/questions/get_one_by_id/")
//    public JSONObject getQuestionById(@RequestParam Map<String, String> data) {
//        return questionsService.getQuestionById(data);
//    }

    @GetMapping("question/{id}")
    public String getQuestionById(@PathVariable Integer id) {
        Map<String, String> data = new HashMap<>();
        data.put("id", String.valueOf(id));
        return questionsService.getQuestionById(data);
    }

//    @PostMapping("/api/questions/set_one_correct_or_incorrect_count/")
//    public JSONObject updateCorrectOrIncorrectCount(@RequestParam Map<String, String> data) {
//        return questionsService.updateCorrectOrIncorrectCount(data);
//    }

    @PostMapping("question/{id}/submit")
    public JSONObject updateCorrectOrIncorrectCount(@PathVariable int id, @RequestBody JSONObject jsonObject) {
        Map<String, String> data = new HashMap<>();
        data.put("id", String.valueOf(id));
        data.put("myAnswer", jsonObject.getString("answer"));
        data.put("isCorrect", jsonObject.getString("isCorrect"));
        System.out.println(id);
        System.out.println(jsonObject.getString("answer"));
        System.out.println(jsonObject.getString("isCorrect"));
        return questionsService.updateCorrectOrIncorrectCount(data);
    }

    @GetMapping("/api/questions/get_questions_ten_rand/")
    public JSONObject getQuestionsRandomTenByHardValue(@RequestParam Map<String, String> data) {
        return questionsService.getQuestionsRandomTenByHardValue(data);
    }

    @GetMapping("/api/questions/get_questions_hard_values_by_chapter_id/")
    public JSONObject getQuestionHardValueListByChapterId(@RequestParam Map<String, String> data) {
        return questionsService.getQuestionHardValueListByChapterId(data);
    }

    @GetMapping("wrong-questions")
    public String getWrongQuestions(@RequestParam String page, @RequestParam String limit) {
        Map<String, String> data = new HashMap<>();
        data.put("pageNo", page);
        data.put("pageSize", limit);
        System.out.println(page);
        System.out.println(limit);
        return questionsService.getWrongQuestionsList(data);
    }

    @DeleteMapping("wrong-questions/{id}")
    public String deleteWrongQuestion(@PathVariable String id) {
        return questionsService.deleteQuestionById(id);
    }
}
