//package org.projects.backend;
//
//import com.alibaba.fastjson2.JSON;
//import com.alibaba.fastjson2.JSONArray;
//import com.alibaba.fastjson2.JSONObject;
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
//import org.junit.jupiter.api.Test;
//import org.projects.backend.pojo.Chapter;
//import org.projects.backend.pojo.ExamQuestion;
//import org.projects.backend.pojo.Exams;
//import org.projects.backend.pojo.Questions;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.*;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//@SpringBootTest
//class AllApiTest {
//
//    @Test
//    void getAllChaptersTest() {
//        System.out.println("getAllChaptersTest：" + getAllChapters());
//    }
//
//    @Test
//    void getQuestionByIdTest() {
//        Map<String, String> data = new HashMap<>();
//        data.put("id", "2");
//        System.out.println("getQuestionByIdTest：" + getQuestionById(data));
//    }
//
//    @Test
//    void getWrongQuestionsListTest() {
//        Map<String, String> data = new HashMap<>();
//        data.put("pageNo", "1");
//        data.put("pageSize", "10");
//        System.out.println("getWrongQuestionsListTest：" + getWrongQuestionsList(data));
//    }
//
//    @Test
//    void getAllExamsClassificationTest() throws ParseException {
//        System.out.println(getAllExamsClassification());
//    }
//
//    public String getAllChapters() {
//        List<JSONObject> practice_list = new ArrayList<>();
//        JSONObject temp1 = new JSONObject();
//        JSONObject temp2 = new JSONObject();
//        JSONObject temp3 = new JSONObject();
//        temp1.put("id", 1);
//        temp2.put("id", 2);
//        temp3.put("id", 3);
//        temp1.put("name", "高等数学");
//        temp2.put("name", "线性代数");
//        temp3.put("name", "概率论与数理统计");
//        temp1.put("questionCount", "100");
//        temp2.put("questionCount", "90");
//        temp3.put("questionCount", "80");
//        temp1.put("completedCount", "68");
//        temp2.put("completedCount", "0");
//        temp3.put("completedCount", "0");
//        temp1.put("chapters", new JSONArray());
//        temp2.put("chapters", new JSONArray());
//        temp3.put("chapters", new JSONArray());
//        practice_list.add(temp1);
//        practice_list.add(temp2);
//        practice_list.add(temp3);
//        System.out.println("测试数据输出结果：" + JSON.toJSONString(practice_list));
//        return JSON.toJSONString(true);
//    }
//
//    public String getQuestionById(Map<String, String> data) {
//        JSONObject resp = new JSONObject();
//        Integer id = Integer.parseInt(data.get("id"));
//        Questions questions1 = new Questions(0, "jx", "测试题目1", "测试解析1", "测试答案1", 90.0, 2, "测试我的答案1", 5, 3, true, 3);
//        Questions questions2 = new Questions(1, "wf", "测试题目2", "测试解析2", "测试答案2", 90.0, 2, "测试我的答案2", 5, 3, true, 3);
//        Questions questions3 = new Questions(2, "wf", "测试题目2", "测试解析2", "测试答案2", 90.0, 2, "测试我的答案2", 5, 3, true, 3);
//        List<Questions> questionsList = new ArrayList<>();
//        questionsList.add(questions1);
//        questionsList.add(questions2);
//        questionsList.add(questions3);
//        Questions question_temp = questionsList.get(id);
//        resp.put("id", question_temp.getId());
//        resp.put("difficulty", question_temp.getHardValue());
//        resp.put("content", question_temp.getContent());
//        if (question_temp.getQuestionType() == 0) resp.put("questionType", "SINGLE_CHOICE");
//        else if (question_temp.getQuestionType() == 1) resp.put("questionType", "MULTIPLE_CHOICE");
//        else if (question_temp.getQuestionType() == 2) resp.put("questionType", "TRUE_FALSE");
//        else if (question_temp.getQuestionType() == 3) resp.put("questionType", "FILL_IN_THE_BLANK");
//        else if (question_temp.getQuestionType() == 4) resp.put("questionType", "SHORT_ANSWER");
//        resp.put("correctAnswer", question_temp.getAnswer());
//        resp.put("myAnswer", question_temp.getMyAnswer());
//        resp.put("explanation", question_temp.getExplanation());
//        resp.put("isCorrect", !question_temp.getIsWrong());
//        System.out.println("测试数据输出结果：" + JSON.toJSONString(resp));
//        return JSON.toJSONString(true);
//    }
//
//    public String getWrongQuestionsList(Map<String, String> data) {
//        JSONObject resp = new JSONObject();
//        System.out.println("pageNo: " + data.get("pageNo"));
//        System.out.println("pageSize: " + data.get("pageSize"));
//        Integer pageNo = Integer.parseInt(data.get("pageNo")), pageSize = Integer.parseInt(data.get("pageSize"));
//        System.out.println("转换后的 pageNo, pageSize: " + pageNo + ", " + pageSize);
//        Questions questions1 = new Questions(1, "jx", "测试题目1", "测试解析1", "测试答案1", 90.0, 2, "测试我的答案1", 5, 3, true, 3);
//        Questions questions2 = new Questions(2, "wf", "测试题目2", "测试解析2", "测试答案2", 90.0, 2, "测试我的答案2", 5, 3, true, 3);
//        Questions questions3 = new Questions(2, "wf", "测试题目2", "测试解析2", "测试答案2", 90.0, 2, "测试我的答案2", 5, 3, true, 3);
//        List<Questions> wrongQuestionsList_temp = new ArrayList<>();
//        wrongQuestionsList_temp.add(questions1);
//        wrongQuestionsList_temp.add(questions2);
//        wrongQuestionsList_temp.add(questions3);
//        List<JSONObject> wrongQuestionsList = new LinkedList<>();
//        for (Questions wrong_question : wrongQuestionsList_temp) {
//            JSONObject wrongQuestion = new JSONObject();
//            wrongQuestion.put("questionId", wrong_question.getId());
//            wrongQuestion.put("content", wrong_question.getContent());
//            wrongQuestion.put("myAnswer", wrong_question.getMyAnswer());
//            wrongQuestion.put("correctAnswer", wrong_question.getAnswer());
//            wrongQuestionsList.add(wrongQuestion);
//        }
//        System.out.println("测试数据输出结果：" + JSON.toJSONString(wrongQuestionsList));
//        return JSON.toJSONString(true);
//    }
//
//    public String getAllExamsClassification() throws ParseException {
//        JSONObject resp = new JSONObject();
//        List<JSONObject> examsList = new LinkedList<>();
//        Exams exams1 = new Exams(1, "测试考试1", (new SimpleDateFormat("yyyy-MM-dd")).parse("2025-06-07"), (new SimpleDateFormat("yyyy-MM-dd")).parse("2025-06-12"), 120, (new SimpleDateFormat("yyyy-MM-dd")).parse("2025-06-08"), 90.0, true);
//        Exams exams2 = new Exams(2, "测试考试2", (new SimpleDateFormat("yyyy-MM-dd")).parse("2025-06-07"), (new SimpleDateFormat("yyyy-MM-dd")).parse("2025-06-13"), 120, null, 0.0, false);
//        Exams exams3 = new Exams(3, "测试考试3", (new SimpleDateFormat("yyyy-MM-dd")).parse("2025-06-07"), (new SimpleDateFormat("yyyy-MM-dd")).parse("2025-06-10"), 120, (new SimpleDateFormat("yyyy-MM-dd")).parse("2025-06-10"), 80.0, true);
//        List<Exams> examsList_temp = new ArrayList<>();
//        examsList_temp.add(exams1);
//        examsList_temp.add(exams2);
//        examsList_temp.add(exams3);
//        for (Exams exam : examsList_temp) {
//            JSONObject exam_temp = new JSONObject();
//            exam_temp.put("id", exam.getId());
//            exam_temp.put("name", exam.getName());
//            exam_temp.put("startTime", exam.getStartTime());
//            exam_temp.put("endTime", exam.getEndTime());
//            exam_temp.put("duration", exam.getDuration());
//            if (exam.getScore() != null) exam_temp.put("score", exam.getScore());
//            Date currentDate = new Date();
//            if (exam.getIsCompleted()) exam_temp.put("status", "COMPLETED");
//            else if (currentDate.after(exam.getStartTime()) && currentDate.before(exam.getEndTime())) exam_temp.put("status", "PENDING");
//            else exam_temp.put("status", "EXPIRED");
//            QueryWrapper<ExamQuestion> queryWrapper = new QueryWrapper<>();
//            queryWrapper.eq("exam_id", exam.getId());
//            List<ExamQuestion> examQuestionList = new ArrayList<>();
//            examQuestionList.add(new ExamQuestion(1, 1, 2));
//            examQuestionList.add(new ExamQuestion(2, 1, 1));
//            examQuestionList.add(new ExamQuestion(3, 3, 3));
//            List<Integer> examQuestionIdList = new LinkedList<>();
//            for (ExamQuestion examQuestion : examQuestionList) {
//                examQuestionIdList.add(examQuestion.getId());
//            }
//            exam_temp.put("questionList", examQuestionIdList);
//            examsList.add(exam_temp);
//        }
//        return JSON.toJSONString(examsList);
//    }
//
//    @Test
//    void getNextPractice() {
//        Map<String, String> data = new HashMap<>();
//        List<Integer> practicedQuestionsIdList = new ArrayList<>();
////        加入data数据（模拟初始，一个参数）
//        data.put("chapterId", "jx");
////        加入data数据（模拟下一题，四个参数）
////        data.put("chapterId", "1");
////        data.put("myAnswer", "1");
////        data.put("questionId", "1");
////        data.put("isCorrect", "true");
////        加入practicedQuestionsIdList，表示已过题号链表
////        practicedQuestionsIdList.add(1);
//        System.out.println(getNextPractice(data, practicedQuestionsIdList));
//    }
//
//    public String getNextPractice(Map<String, String> data, List<Integer> practicedQuestionsIdList) {
//        JSONObject resp = new JSONObject();
//        String chapterId = data.get("chapterId");
//        String myAnswer;
//        Integer questionId;
//        boolean isCorrect;
//        Questions questions1 = new Questions(1, "jx", "测试题目1", "测试解析1", "测试答案1", 90.0, 1, "测试我的答案1", 5, 3, true, 3);
//        Questions questions2 = new Questions(2, "wf", "测试题目2", "测试解析2", "测试答案2", 90.0, 2, "测试我的答案2", 5, 3, true, 3);
//        Questions questions3 = new Questions(3, "wf", "测试题目3", "测试解析3", "测试答案3", 90.0, 3, "测试我的答案3", 5, 3, true, 3);
//        Questions questions4 = new Questions(4, "jx", "测试题目4", "测试解析4", "测试答案4", 90.0, 1, "测试我的答案4", 5, 3, true, 3);
//        Questions questions5 = new Questions(5, "wf", "测试题目5", "测试解析5", "测试答案5", 90.0, 2, "测试我的答案5", 5, 3, true, 3);
//        Questions questions6 = new Questions(6, "wf", "测试题目6", "测试解析6", "测试答案6", 90.0, 3, "测试我的答案6", 5, 3, true, 3);
//        List<Questions> questionsList = new ArrayList<>();
//        questionsList.add(questions1);
//        questionsList.add(questions2);
//        questionsList.add(questions3);
//        questionsList.add(questions4);
//        questionsList.add(questions5);
//        questionsList.add(questions6);
//        List<Integer> idList = new ArrayList<>();
//        for (Questions question : questionsList) idList.add(question.getId());
//        Chapter chapter = new Chapter("jx", "极限", 0.5, 2);
//        if (data.containsKey("questionId") && data.containsKey("isCorrect") && data.containsKey("myAnswer")) {
//            myAnswer = data.get("myAnswer");
//            questionId = Integer.valueOf(data.get("questionId"));
//            isCorrect = data.get("isCorrect").equals("true") || data.get("isCorrect").equals("1");
//            System.out.println("myAnswer is " + myAnswer);
//            System.out.println("questionId is " + questionId);
//            System.out.println("isCorrect is " + isCorrect);
//            if (!idList.contains(questionId)) {
//                resp.put("is_successful", false);
//                resp.put("error_message", "未找到该id！");
//                return JSON.toJSONString(resp);
//            }
//            Integer oldPracticeHardValue = chapter.getPracticeHardValue();
//            if (isCorrect) {
//                questionsList.get(questionId - 1).setCorrectCount(questionsList.get(questionId - 1).getCorrectCount() + 1);
//                if (oldPracticeHardValue != 3) {
//                    chapter.setPracticeHardValue(oldPracticeHardValue + 1);
//                }
//            } else {
//                questionsList.get(questionId - 1).setCorrectCount(questionsList.get(questionId - 1).getCorrectCount() - 1);
//                questionsList.get(questionId - 1).setIsWrong(false);
//                if (oldPracticeHardValue != 1) {
//                    chapter.setPracticeHardValue(oldPracticeHardValue - 1);
//                }
//            }
//        } else practicedQuestionsIdList.clear();
//
//        System.out.print("当前已过的练习题目链表：");
//        for (Integer question_id : practicedQuestionsIdList) {
//            System.out.print(question_id + " ");
//        }
//        System.out.println();
//
//        Integer curPracticeHardValue = chapter.getPracticeHardValue();
//        Questions question = null;
//        for (Questions question_temp : questionsList) {
//            if (question_temp.getHardValue() != curPracticeHardValue) continue;
//            if (practicedQuestionsIdList.contains(question_temp.getId())) continue;
//            question = question_temp;
//        }
//        if (question == null) {
//            return JSON.toJSONString(new JSONObject());
//        } else {
//            practicedQuestionsIdList.add(question.getId());
//            resp.put("id", question.getId());
//            resp.put("difficulty", question.getHardValue());
//            resp.put("content", question.getContent());
//            if (question.getQuestionType() == 0) resp.put("questionType", "SINGLE_CHOICE");
//            else if (question.getQuestionType() == 1) resp.put("questionType", "MULTIPLE_CHOICE");
//            else if (question.getQuestionType() == 2) resp.put("questionType", "TRUE_FALSE");
//            else if (question.getQuestionType() == 3) resp.put("questionType", "FILL_IN_THE_BLANK");
//            else if (question.getQuestionType() == 4) resp.put("questionType", "SHORT_ANSWER");
//            resp.put("correctAnswer", question.getAnswer());
//            resp.put("myAnswer", question.getMyAnswer());
//            resp.put("explanation", question.getExplanation());
//            resp.put("isCorrect", !question.getIsWrong());
//            return JSON.toJSONString(resp);
//        }
//    }
//
//    @Test
//    void updateChapterDegreeTest() {
//        System.out.println(updateChapterDegree());
//    }
//
//    public String updateChapterDegree() {
//        Chapter chapter1 = new Chapter("jx", "极限", 0.5, 2);
//        Chapter chapter2 = new Chapter("wf", "微分", 0.5, 2);
//        Chapter chapter3 = new Chapter("jf", "积分", 0.5, 2);
//        List<Chapter> chapterList = new ArrayList<>();
//        chapterList.add(chapter1);
//        chapterList.add(chapter2);
//        chapterList.add(chapter3);
//        List<Double> chapterDegreeList = new ArrayList<>();
//        for (Chapter chapter_temp : chapterList) {
//            chapterDegreeList.add(chapter_temp.getDegree());
//        }
//        Questions questions1 = new Questions(1, "jx", "测试题目1", "测试解析1", "测试答案1", 90.0, 1, "测试我的答案1", 5, 3, true, 3);
//        Questions questions2 = new Questions(2, "wf", "测试题目2", "测试解析2", "测试答案2", 90.0, 2, "测试我的答案2", 5, 3, true, 3);
//        Questions questions3 = new Questions(3, "wf", "测试题目3", "测试解析3", "测试答案3", 90.0, 3, "测试我的答案3", 5, 3, true, 3);
//        Questions questions4 = new Questions(4, "jx", "测试题目4", "测试解析4", "测试答案4", 90.0, 1, "测试我的答案4", 5, 3, true, 3);
//        Questions questions5 = new Questions(5, "wf", "测试题目5", "测试解析5", "测试答案5", 90.0, 2, "测试我的答案5", 5, 3, true, 3);
//        Questions questions6 = new Questions(6, "wf", "测试题目6", "测试解析6", "测试答案6", 90.0, 3, "测试我的答案6", 5, 3, true, 3);
//        List<Questions> questionsList = new ArrayList<>();
//        questionsList.add(questions1);
//        questionsList.add(questions2);
//        questionsList.add(questions3);
//        questionsList.add(questions4);
//        questionsList.add(questions5);
//        questionsList.add(questions6);
//        for (Chapter chapter : chapterList) {
//            double weightedCorrectSum = 0.0;
//            double weightedTotalSum = 0.0;
//
//            for (Questions question : questionsList) {
//                int correct = question.getCorrectCount();        // 答对次数
//                int incorrect = question.getIncorrectCount();    // 答错次数
//                int total = correct + incorrect;                 // 总作答次数
//
//                // 根据难度值设定权重
//                double weight;
//                if (question.getHardValue() == 1) weight = 0.8;
//                else if (question.getHardValue() == 2) weight = 1.0;
//                else weight = 1.2;
//
//                // 加权累加
//                weightedCorrectSum += correct * weight;
//                weightedTotalSum += total * weight;
//            }
//
//            // 计算并更新掌握度 mastery
//            UpdateWrapper<Chapter> updateWrapper = new UpdateWrapper<>();
//            updateWrapper.eq("id", chapter.getId());
//            double mastery = ((weightedTotalSum != 0) ?  (weightedCorrectSum / weightedTotalSum) : 0.0);
//            mastery = (double)Math.round(mastery*100)/100;
//            updateWrapper.set("degree", mastery);
//            chapterDegreeList.add(mastery);
//        }
//        return chapterDegreeList.toString();
//    }
//
//    @Test
//    void getWrongQuestionsByQuestionIdThroughAITest() {
//        String questionId = "1";
//        System.out.println(getWrongQuestionsByQuestionIdThroughAI(questionId));
//    }
//
//    public String getWrongQuestionsByQuestionIdThroughAI(String questionId) {
//
//        String result_url = "下面有一个List<Integer>，[1, 2, 3]，下面有一个List<Integer>";
//
//        if (result_url == null || result_url.isEmpty()) {
//            return (new ArrayList<>()).toString();
//        }
//
//        Integer id = Integer.parseInt(questionId);
//
//        System.out.println("从ai端口获取到的信息（单元测试模拟数据）：" + result_url);
//        System.out.println("从前端口获取到的questionId（单元测试模拟数据）：" + id);
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
//            return result.toString();
//        } else {
//            System.out.println("未找到任何符合格式的列表。");
//            return (new ArrayList<>()).toString();
//        }
//    }
//
//    @Test
//    void getChartOfGrowingCapacityThroughAITest() {
//        System.out.println(getChartOfGrowingCapacityThroughAI());
//    }
//
//    public String getChartOfGrowingCapacityThroughAI() {
//
//        String result_url = "下面是一个链接，![](https://mdn.alipayobjects.com/test/url/growing-capacity/)，上面是一个链接";
//
//        System.out.println("模拟返回值" + result_url);
//
//        String regex = "!\\[]\\((https://mdn\\.alipayobjects\\.com/[^)]+)\\)";
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(result_url);
//
//        if (matcher.find()) {
//            String reslut = matcher.group(1);
//            System.out.println("提取的链接是: " + reslut);
//            return reslut;
//        } else {
//            System.out.println("未找到匹配的链接。");
//            return "";
//        }
//    }
//
//    @Test
//    void getChartOfMasteryDegreeThroughAITest() {
//        System.out.println(getChartOfMasteryDegreeThroughAI());
//    }
//
//    public String getChartOfMasteryDegreeThroughAI() {
//
//        String result_url = "下面是一个链接，![](https://mdn.alipayobjects.com/test/url/mastery-degree/)，上面是一个链接";
//
//        System.out.println("模拟返回值" + result_url);
//
//        String regex = "!\\[]\\((https://mdn\\.alipayobjects\\.com/[^)]+)\\)";
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(result_url);
//
//        if (matcher.find()) {
//            String reslut = matcher.group(1);
//            System.out.println("提取的链接是: " + reslut);
//            return reslut;
//        } else {
//            System.out.println("未找到匹配的链接。");
//            return "";
//        }
//    }
//
//
//}
