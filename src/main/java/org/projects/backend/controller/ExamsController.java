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

//    @GetMapping("/api/exams/get_all_classification/")
//    public JSONObject getAllExamsClassification() {
//        return examsService.getAllExamsClassification();
//    }

    @GetMapping("exams")
    public String getAllExamsClassification() {
        return examsService.getAllExamsClassification();
    }

//    @PostMapping("/api/exams/set_one_score/")
//    public JSONObject setExamScoreById(@RequestParam Map<String, String> data) {
//        return examsService.setExamScoreById(data);
//    }

    @PostMapping("exams/{id}/submit")
    public JSONObject setExamScoreById(@PathVariable int id, @RequestBody JSONObject jsonObject) {
        Map<String, String> data = new HashMap<>();
        data.put("id", String.valueOf(id));
        data.put("score", String.valueOf(jsonObject.get("score")));
        return examsService.setExamScoreById(data);
    }
}
