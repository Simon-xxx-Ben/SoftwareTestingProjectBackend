package org.projects.backend.service.impl.AlgorithmPlus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.projects.backend.mapper.ChapterMapper;
import org.projects.backend.mapper.QuestionsMapper;
import org.projects.backend.pojo.Chapter;
import org.projects.backend.pojo.Questions;
import org.projects.backend.service.AlgorithmPlus.AIApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AIApiServiceImpl implements AIApiService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private QuestionsMapper questionsMapper;

    @Autowired
    private ChapterMapper chapterMapper;

    @Override
    public boolean updateChapterDegree() {
        List<Chapter> chapterList = chapterMapper.selectList(null);
        for (Chapter chapter : chapterList) {
            double weightedCorrectSum = 0.0;
            double weightedTotalSum = 0.0;

            QueryWrapper<Questions> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("chapter_id", chapter.getId());
            List<Questions> questionsList = questionsMapper.selectList(queryWrapper);

            for (Questions question : questionsList) {
                int correct = question.getCorrectCount();        // 答对次数
                int incorrect = question.getIncorrectCount();    // 答错次数
                int total = correct + incorrect;                 // 总作答次数

                // 根据难度值设定权重
                double weight;
                if (question.getHardValue() == 1) weight = 0.8;
                else if (question.getHardValue() == 2) weight = 1.0;
                else weight = 1.2;

                // 加权累加
                weightedCorrectSum += correct * weight;
                weightedTotalSum += total * weight;
            }

            // 计算并更新掌握度 mastery
            UpdateWrapper<Chapter> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", chapter.getId());
            double mastery = ((weightedTotalSum != 0) ?  (weightedCorrectSum / weightedTotalSum) : 0.0);
            updateWrapper.set("degree", (double)Math.round(mastery*100)/100);
            chapterMapper.update(chapter, updateWrapper);
        }
        return true;
    }

    @Override
    public String getWrongQuestionsByQuestionIdThroughAI(Integer questionId) {

        // 目标 API 地址
        String url = "http://localhost:8081/ai/bailian/agent/call?message=请你帮我推荐几道跟id为" + questionId + "的题目类似的题";

        // 发送 GET 请求（其他方法：postForObject, exchange 等）
        String result_url = restTemplate.getForObject(url, String.class);

        if (result_url == null || result_url.isEmpty()) {
            return (new ArrayList<>()).toString();
        }

        System.out.println("从ai端口获取到的信息：" + result_url);

        Pattern pattern = Pattern.compile("\\[\\s*\\d+(\\s*,\\s*\\d+)*\\s*\\]");
        Matcher matcher = pattern.matcher(result_url);

        List<Integer> result = new ArrayList<>();

        if (matcher.find()) {
            String rawList = matcher.group();  // 拿到 "[1,   23,456,   7890]"
            String inner = rawList.substring(1, rawList.length() - 1);  // 去掉 []

            String[] parts = inner.split("\\s*,\\s*");  // 逗号分隔，自动忽略空格

            for (String part : parts) {
                result.add(Integer.parseInt(part));
            }
            System.out.println("提取到的 List<Integer>: " + result);
            return result.toString();
        } else {
            System.out.println("未找到任何符合格式的列表。");
            return (new ArrayList<>()).toString();
        }
    }
}
