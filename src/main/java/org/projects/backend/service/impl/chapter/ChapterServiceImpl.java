package org.projects.backend.service.impl.chapter;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.projects.backend.mapper.ChapterMapper;
import org.projects.backend.mapper.QuestionsMapper;
import org.projects.backend.pojo.Chapter;
import org.projects.backend.pojo.Questions;
import org.projects.backend.service.chapter.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ChapterServiceImpl implements ChapterService {

    @Autowired
    ChapterMapper chapterMapper;

    @Autowired
    QuestionsMapper questionsMapper;

    @Override
    public String getAllChapters() {
        List<JSONObject> practice_list = new ArrayList<>();
        JSONObject temp1 = new JSONObject();
        JSONObject temp2 = new JSONObject();
        JSONObject temp3 = new JSONObject();
        temp1.put("id", 1);
        temp2.put("id", 2);
        temp3.put("id", 3);
        temp1.put("name", "高等數學");
        temp2.put("name", "綫性代數");
        temp3.put("name", "概率論與數理統計");
        temp1.put("questionCount", "100");
        temp2.put("questionCount", "90");
        temp3.put("questionCount", "80");
        temp1.put("completedCount", "50");
        temp2.put("completedCount", "40");
        temp3.put("completedCount", "30");
//        數據庫均爲高等數學，暫不做判斷
        List<Chapter> chapterList_temp = chapterMapper.selectList(null);
        List<JSONObject> chapterList = new ArrayList<>();
        for (Chapter chapter : chapterList_temp) {
            JSONObject chapter_temp = new JSONObject();
            chapter_temp.put("id", chapter.getId());
            chapter_temp.put("name", chapter.getName());
            chapter_temp.put("questionCount", questionsMapper.selectCount(new QueryWrapper<Questions>().eq("chapter_id", chapter.getId())));
            chapter_temp.put("completedCount", 5);
            chapter_temp.put("progress", chapter.getDegree());
//            chapter_temp.put("degree", chapter.getDegree());
            chapterList.add(chapter_temp);
        }
        temp1.put("chapters", chapterList);
        temp2.put("chapters", new JSONArray());
        temp3.put("chapters", new JSONArray());
        practice_list.add(temp1);
        practice_list.add(temp2);
        practice_list.add(temp3);
        return JSON.toJSONString(practice_list);
    }

    @Override
    public JSONObject getChapterById(Map<String, String> data) {
        JSONObject resp = new JSONObject();
        JSONObject item = new JSONObject();
//        Integer id;
//        try {
//            id = Integer.parseInt(data.get("id"));
//        }catch (Exception e) {
//            resp.put("is_successful", false);
//            resp.put("error_message", "id格式错误，请确保id非空且能够转为Int！");
//            return resp;
//        }
        String id = data.get("id");
        QueryWrapper<Chapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        Chapter chapter = chapterMapper.selectOne(queryWrapper);
        if (chapter == null) {
            item.put("chapter", JSON.toJSON(null));
            resp.put("data", item);
            resp.put("is_successful", false);
            resp.put("error_message", "没有找到该id！");
        } else {
            item.put("chapter", JSON.toJSON(chapter));
            resp.put("data", item);
            resp.put("is_successful", true);
            resp.put("error_message", "Congratulations! Success!");
        }
        return resp;
    }

    @Override
    public JSONObject insertChapter(Map<String, String> data) {
        JSONObject resp = new JSONObject();
        Chapter chapter = new Chapter();
        if (!data.containsKey("id")) {
            resp.put("is_successful", false);
            resp.put("error_message", "id不能为空！");
            return resp;
        }
        if (!data.containsKey("name")) {
            resp.put("is_successful", false);
            resp.put("error_message", "章节名不能为空！");
            return resp;
        }
        String id = data.get("id");
        if(id == null || id.equals("")) {
            resp.put("is_successful", false);
            resp.put("error_message", "id不能为空！");
            return resp;
        }else chapter.setName(id);
        String name = data.get("name");
        if(name == null || name.equals("")) {
            resp.put("is_successful", false);
            resp.put("error_message", "章节名不能为空！");
            return resp;
        }else chapter.setName(name);
        Double degree;
        if (data.containsKey("degree")) {
            try {
                degree = Double.valueOf(data.get("degree"));
                chapter.setDegree(degree);
            } catch (Exception e) {
                resp.put("is_successful", false);
                resp.put("error_message", "degree格式错误，请确保degree能够转为Double或不挟带degree参数！");
                return resp;
            }
        }
        chapterMapper.insert(chapter);
        resp.put("is_successful", true);
        resp.put("error_message", "Congratulations! Success!");
        return resp;
    }

    @Override
    public JSONObject deleteChapterById(Map<String, String> data) {
        JSONObject resp = new JSONObject();
//        Integer id;
//        try {
//            id = Integer.parseInt(data.get("id"));
//        }catch (Exception e) {
//            resp.put("is_successful", false);
//            resp.put("error_message", "id格式错误，请确保id非空且能够转为Int！");
//            return resp;
//        }
        String id = data.get("id");
        int check = chapterMapper.deleteById(id);
        if (check == 1) {
            resp.put("is_successful", true);
            resp.put("error_message", "Congratulations! Success!");
        } else {
            resp.put("is_successful", false);
            resp.put("error_message", "没有找到该id！");
        }
        return resp;
    }

    @Override
    public JSONObject updateChapterById(Map<String, String> data) {
        JSONObject resp = new JSONObject();
        Chapter chapter = new Chapter();
//        Integer id;
//        if (data.containsKey("id")) {
//            try {
//                id = Integer.parseInt(data.get("id"));
//                chapter.setId(id);
//            }catch (Exception e) {
//                resp.put("is_successful", false);
//                resp.put("error_message", "id格式错误，请确保id非空且能够转为Int！");
//                return resp;
//            }
//        } else {
//            resp.put("is_successful", false);
//            resp.put("error_message", "id不能为空！");
//            return resp;
//        }
        String id;
        if (data.containsKey("id")) id = data.get("id");
        else {
            resp.put("is_successful", false);
            resp.put("error_message", "id不能为空！");
            return resp;
        }
        String name;
        if (!data.containsKey("name")) {
            resp.put("is_successful", false);
            resp.put("error_message", "章节名不能为空！");
            return resp;
        }
        name = data.get("name");
        if(name == null || name.isEmpty()) {
            resp.put("is_successful", false);
            resp.put("error_message", "章节名不能为空！");
            return resp;
        }else chapter.setName(name);
        Double degree;
        if (data.containsKey("degree")) {
            try {
                degree = Double.valueOf(data.get("degree"));
                System.out.println(degree);
                chapter.setDegree(degree);
            } catch (Exception e) {
                resp.put("is_successful", false);
                resp.put("error_message", "degree格式错误，请确保degree能够转为Double或不挟带degree参数！");
                return resp;
            }
        }
        chapterMapper.updateById(chapter);
        resp.put("is_successful", true);
        resp.put("error_message", "Congratulations! Success!");
        return resp;
    }
}
