package org.projects.backend.service.chapter;

import com.alibaba.fastjson2.JSONObject;
import org.projects.backend.pojo.Chapter;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

public interface ChapterService {
    String getAllChapters();
    JSONObject getChapterById(Map<String, String> data);
    JSONObject insertChapter(Map<String, String> data);
    JSONObject deleteChapterById(Map<String, String> data);
    JSONObject updateChapterById(Map<String, String> data);
    String test();
}
