package org.projects.backend.controller.AlgorithmPlus;

import org.projects.backend.service.AlgorithmPlus.AIApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AIApiController {

    @Autowired
    AIApiService apiService;

    @GetMapping("wrong-questions/ai/{id}")
    public String getWrongQuestionsByQuestionIdThroughAI(@PathVariable int id) {
        return apiService.getWrongQuestionsByQuestionIdThroughAI(id);
    }
}
