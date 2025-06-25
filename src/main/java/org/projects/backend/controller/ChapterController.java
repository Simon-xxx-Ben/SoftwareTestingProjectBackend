package org.projects.backend.controller;

import com.alibaba.fastjson2.JSONObject;
import org.projects.backend.pojo.Chapter;
import org.projects.backend.service.chapter.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    @GetMapping("practices")
    public String getAllChapters() {
        return chapterService.getAllChapters();
    }

    @GetMapping("/api/chapter/get_one_by_id/")
    public JSONObject getChapterById(@RequestParam Map<String, String> data) {
        return chapterService.getChapterById(data);
    }

    @PostMapping("/api/chapter/insert_one/")
    public JSONObject insertChapter(@RequestParam Map<String, String> data) {
        return chapterService.insertChapter(data);
    }

    @DeleteMapping("/api/chapter/delete_one_by_id/")
    public JSONObject deleteChapterById(@RequestParam Map<String, String> data) {
        return chapterService.deleteChapterById(data);
    }

    @PutMapping("/api/chapter/update_one_by_id/")
    public JSONObject updateChapterById(@RequestParam Map<String, String> data) {
        return chapterService.updateChapterById(data);
    }
}
