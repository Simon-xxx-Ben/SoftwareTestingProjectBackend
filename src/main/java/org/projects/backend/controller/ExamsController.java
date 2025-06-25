package org.projects.backend.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.projects.backend.pojo.Questions;
import org.projects.backend.service.exams.ExamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ExamsController {

    @Autowired
    private ExamsService examsService;

    @GetMapping("exams")
    public String getAllExamsClassification() {
        return examsService.getAllExamsClassification();
    }

    @PostMapping("exams/{id}/submit")
    public JSONObject setExamScoreById(@PathVariable int id, @RequestBody JSONObject jsonObject) {
        Map<String, String> data = new HashMap<>();
        data.put("id", String.valueOf(id));
        if (!jsonObject.containsKey("score")) {
            JSONObject error = new JSONObject();
            error.put("is_successful", false);
            error.put("error_message", "请保证存在score属性！");
            return error;
        }
        data.put("score", String.valueOf(jsonObject.get("score")));
        return examsService.setExamScoreById(data);
    }
}
