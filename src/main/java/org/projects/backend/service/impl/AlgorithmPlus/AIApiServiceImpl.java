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
import java.util.concurrent.*;
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

    // 共享线程池（按需调整参数）
    private static final ThreadPoolExecutor SHARED_EXECUTOR = new ThreadPoolExecutor(
            2, // 核心线程
            8, // 最大线程
            60, TimeUnit.SECONDS, // 空闲线程存活时间
            new LinkedBlockingQueue<>(10), // 任务队列
            new ThreadPoolExecutor.CallerRunsPolicy() // 拒绝策略
    );

    static {
        // 注册关闭钩子
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            SHARED_EXECUTOR.shutdown();
            try {
                if (!SHARED_EXECUTOR.awaitTermination(30, TimeUnit.SECONDS)) {
                    SHARED_EXECUTOR.shutdownNow();
                }
            } catch (InterruptedException ignored) {}
        }));
    }

    @Override
    public String updateChapterDegree() {
        List<Chapter> chapterList = chapterMapper.selectList(null);
        List<Double> chapterDegreeList = new ArrayList<>();
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
            mastery = (double)Math.round(mastery*100)/100;
            updateWrapper.set("degree", mastery);
            chapterDegreeList.add(mastery);
            chapterMapper.update(chapter, updateWrapper);
        }
        return chapterDegreeList.toString();
    }

    @Override
    public String refreshWrongQuestionsByQuestionIdThroughAI(Integer questionId) {

        Questions question = questionsMapper.selectById(questionId);

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
                try {
                    result.add(Integer.parseInt(part));
                } catch (Exception e) {
                    System.out.println("存在非Integer数据，返回空List。");
                    return (new ArrayList<>()).toString();
                }
            }
            System.out.println("提取到的 List<Integer>: " + result);
            UpdateWrapper<Questions> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", questionId)
                    .set("recommend_questions_list", result.toString());
            questionsMapper.update(question, updateWrapper);
            return result.toString();
        } else {
            System.out.println("未找到任何符合格式的列表。");
            return (new ArrayList<>()).toString();
        }
    }

    @Override
    public String getWrongQuestionsByQuestionIdThroughAI(String questionId) {

        if (questionId == null || questionId.isEmpty()) return "";

        try {
            Integer.parseInt(questionId);
        } catch (NumberFormatException e) {
            return "";
        }

        Questions question = questionsMapper.selectById(questionId);

        if (question == null) return "";

        if (question.getRecommendQuestionsList() != null) {
            final int id = Integer.parseInt(questionId);
            SHARED_EXECUTOR.submit(() -> {
                try {
                    refreshWrongQuestionsByQuestionIdThroughAI(id);
                } catch (Exception e) {
                    System.out.println("Refresh failed for question {}" + id + e);
                }
            });
            return question.getRecommendQuestionsList();
        }

        return refreshWrongQuestionsByQuestionIdThroughAI(Integer.valueOf(questionId));

//        // 目标 API 地址
//        String url = "http://localhost:8081/ai/bailian/agent/call?message=请你帮我推荐几道跟id为" + questionId + "的题目类似的题";
//
//        // 发送 GET 请求（其他方法：postForObject, exchange 等）
//        String result_url = restTemplate.getForObject(url, String.class);
//
//        if (result_url == null || result_url.isEmpty()) {
//            return (new ArrayList<>()).toString();
//        }
//
//        System.out.println("从ai端口获取到的信息：" + result_url);
//
//        Pattern pattern = Pattern.compile("\\[\\s*\\d+(\\s*,\\s*\\d+)*\\s*\\]");
//        Matcher matcher = pattern.matcher(result_url);
//
//        List<Integer> result = new ArrayList<>();
//
//        if (matcher.find()) {
//            String rawList = matcher.group();  // 拿到 "[1,   23,456,   7890]"
//            String inner = rawList.substring(1, rawList.length() - 1);  // 去掉 []
//
//            String[] parts = inner.split("\\s*,\\s*");  // 逗号分隔，自动忽略空格
//
//            for (String part : parts) {
//                try {
//                    result.add(Integer.parseInt(part));
//                } catch (Exception e) {
//                    System.out.println("存在非Integer数据，返回空List。");
//                    return (new ArrayList<>()).toString();
//                }
//            }
//            System.out.println("提取到的 List<Integer>: " + result);
//            UpdateWrapper<Questions> updateWrapper = new UpdateWrapper<>();
//            updateWrapper.eq("id", questionId)
//                    .set("recommend_questions_list", result.toString());
//            questionsMapper.update(question, updateWrapper);
//            return result.toString();
//        } else {
//            System.out.println("未找到任何符合格式的列表。");
//            return (new ArrayList<>()).toString();
//        }
    }

    @Override
    public String getChartOfGrowingCapacityThroughAI() {
        String url = "http://localhost:8081/ai/bailian/agent/call?message=绘制能力成长曲线";

        // 发送 GET 请求（其他方法：postForObject, exchange 等）
        String result_url = restTemplate.getForObject(url, String.class);

        if (result_url == null || result_url.isEmpty()) {
            return "";
        }

        // 正则表达式：匹配 ![](https://mdn.alipayobjects.com/...)
        String regex = "!\\[]\\((https://mdn\\.alipayobjects\\.com/[^)]+)\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(result_url);

        if (matcher.find()) {
            String reslut = matcher.group(1);  // 提取括号中的链接
            System.out.println("提取的链接是: " + reslut);
            return reslut;
        } else {
            System.out.println("未找到匹配的链接。");
            return "";
        }
    }

    @Override
    public String getChartOfMasteryDegreeThroughAI() {
        String url = "http://localhost:8081/ai/bailian/agent/call?message=请绘制一份知识点掌握程度柱状图";

        // 发送 GET 请求（其他方法：postForObject, exchange 等）
        String result_url = restTemplate.getForObject(url, String.class);

        if (result_url == null || result_url.isEmpty()) {
            return "";
        }

        // 正则表达式：匹配 ![](https://mdn.alipayobjects.com/...)
        String regex = "!\\[]\\((https://mdn\\.alipayobjects\\.com/[^)]+)\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(result_url);

        if (matcher.find()) {
            String reslut = matcher.group(1);  // 提取括号中的链接
            System.out.println("提取的链接是: " + reslut);
            return reslut;
        } else {
            System.out.println("未找到匹配的链接。");
            return "";
        }
    }
}
