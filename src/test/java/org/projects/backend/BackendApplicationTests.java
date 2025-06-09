package org.projects.backend;

import org.junit.jupiter.api.Test;
import org.projects.backend.service.chapter.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendApplicationTests {

    @Autowired
    ChapterService chapterService;

    @Test
    void contextLoads() {
        System.out.println(chapterService.getAllChapters());
    }

}
