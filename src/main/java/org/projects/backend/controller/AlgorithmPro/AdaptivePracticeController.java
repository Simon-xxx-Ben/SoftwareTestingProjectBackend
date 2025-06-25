package org.projects.backend.controller.AlgorithmPro;

import com.alibaba.fastjson2.JSONObject;
import org.projects.backend.service.AlgorithmPro.AdaptivePracticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AdaptivePracticeController {

    List<Integer> practicedQuestionsIdList = new ArrayList<>();

    @Autowired
    AdaptivePracticeService adaptivePracticeService;

    @PostMapping("practice-question/{chap_id}")
    public Object getNextPractice(@PathVariable String chap_id, @RequestBody JSONObject jsonObject) {
        Map<String, String> data = new HashMap<>();
        data.put("chapterId", chap_id);
        if (jsonObject.containsKey("answer")) data.put("myAnswer", jsonObject.getString("answer"));
        if (jsonObject.containsKey("questionId")) data.put("questionId", String.valueOf(jsonObject.get("questionId")));
        if (jsonObject.containsKey("isCorrect")) data.put("isCorrect", String.valueOf(jsonObject.get("isCorrect")));
        System.out.println(chap_id);
        return adaptivePracticeService.getNextPractice(data, practicedQuestionsIdList);
    }
}
