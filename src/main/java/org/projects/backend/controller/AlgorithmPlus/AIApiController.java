package org.projects.backend.controller.AlgorithmPlus;

import org.apache.ibatis.annotations.Update;
import org.projects.backend.service.AlgorithmPlus.AIApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AIApiController {

    @Autowired
    AIApiService apiService;

    @GetMapping("wrong-questions/ai/{id}")
    public String getWrongQuestionsByQuestionIdThroughAI(@PathVariable String id) {
        return apiService.getWrongQuestionsByQuestionIdThroughAI(id);
    }

    @PutMapping("refresh-chapter-degree")
    public String refreshChapterDegree() {
        return apiService.updateChapterDegree();
    }

    @GetMapping("chart/growing-capacity")
    public String getChartOfGrowingCapacityThroughAI() {
        return apiService.getChartOfGrowingCapacityThroughAI();
    }

    @GetMapping("chart/mastery-degree")
    public String getChartOfMasteryDegreeThroughAI() {
        return apiService.getChartOfMasteryDegreeThroughAI();
    }
}
