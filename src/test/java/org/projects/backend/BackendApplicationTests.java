package org.projects.backend;

import org.junit.jupiter.api.Test;
import org.projects.backend.service.chapter.ChapterService;
import org.projects.backend.service.questions.QuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class BackendApplicationTests {

    @Autowired
    QuestionsService questionsService;

    @Test
    void contextLoads() {
        Map<String, String> data = new HashMap<>();
        data.put("pageNo", "sj");
        data.put("pageSize", "10");
        System.out.println(questionsService.getWrongQuestionsList(data));
    }

}
