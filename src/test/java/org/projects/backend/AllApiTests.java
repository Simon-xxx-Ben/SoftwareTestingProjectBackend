package org.projects.backend;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.junit.jupiter.api.Test;
import org.projects.backend.pojo.Questions;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
class AllApiTest {

    @Test
    void getAllChaptersTest() {
        System.out.println("getAllChaptersTest：" + getAllChapters());
    }

    @Test
    void getQuestionByIdTest() {
        Map<String, String> data = new HashMap<>();
        data.put("id", "x");
        System.out.println("getQuestionByIdTest：" + getQuestionById(data));
    }

    @Test
    void getWrongQuestionsListTest() {
        Map<String, String> data = new HashMap<>();
        data.put("pageNo", "1");
        data.put("pageSize", "10");
        System.out.println("getWrongQuestionsListTest：" + getWrongQuestionsList(data));
    }

    public String getAllChapters() {
        List<JSONObject> practice_list = new ArrayList<>();
        JSONObject temp1 = new JSONObject();
        JSONObject temp2 = new JSONObject();
        JSONObject temp3 = new JSONObject();
        temp1.put("id", 1);
        temp2.put("id", 2);
        temp3.put("id", 3);
        temp1.put("name", "高等数学");
        temp2.put("name", "线性代数");
        temp3.put("name", "概率论与数理统计");
        temp1.put("questionCount", "100");
        temp2.put("questionCount", "90");
        temp3.put("questionCount", "80");
        temp1.put("completedCount", "68");
        temp2.put("completedCount", "0");
        temp3.put("completedCount", "0");
        temp1.put("chapters", new JSONArray());
        temp2.put("chapters", new JSONArray());
        temp3.put("chapters", new JSONArray());
        practice_list.add(temp1);
        practice_list.add(temp2);
        practice_list.add(temp3);
        System.out.println("测试数据输出结果：" + JSON.toJSONString(practice_list));
        return JSON.toJSONString(true);
    }

    public String getQuestionById(Map<String, String> data) {
        JSONObject resp = new JSONObject();
        Integer id = Integer.parseInt(data.get("id"));
        Questions questions1 = new Questions(1, "jx", "测试题目1", "测试解析1", "测试答案1", 90.0, 2, "测试我的答案1", 5, 3, true, 3);
        Questions questions2 = new Questions(2, "wf", "测试题目2", "测试解析2", "测试答案2", 90.0, 2, "测试我的答案2", 5, 3, true, 3);
        Questions questions3 = new Questions(2, "wf", "测试题目2", "测试解析2", "测试答案2", 90.0, 2, "测试我的答案2", 5, 3, true, 3);
        List<Questions> questionsList = new ArrayList<>();
        questionsList.add(questions1);
        questionsList.add(questions2);
        questionsList.add(questions3);
        Questions question_temp = questionsList.get(id + 1);
        resp.put("id", question_temp.getId());
        resp.put("difficulty", question_temp.getHardValue());
        resp.put("content", question_temp.getContent());
        if (question_temp.getQuestionType() == 0) resp.put("questionType", "SINGLE_CHOICE");
        else if (question_temp.getQuestionType() == 1) resp.put("questionType", "MULTIPLE_CHOICE");
        else if (question_temp.getQuestionType() == 2) resp.put("questionType", "TRUE_FALSE");
        else if (question_temp.getQuestionType() == 3) resp.put("questionType", "FILL_IN_THE_BLANK");
        else if (question_temp.getQuestionType() == 4) resp.put("questionType", "SHORT_ANSWER");
        resp.put("correctAnswer", question_temp.getAnswer());
        resp.put("myAnswer", question_temp.getMyAnswer());
        resp.put("explanation", question_temp.getExplanation());
        resp.put("isCorrect", !question_temp.getIsWrong());
        System.out.println("测试数据输出结果：" + JSON.toJSONString(resp));
        return JSON.toJSONString(true);
    }

    public String getWrongQuestionsList(Map<String, String> data) {
        JSONObject resp = new JSONObject();
        System.out.println("pageNo: " + data.get("pageNo"));
        System.out.println("pageSize: " + data.get("pageSize"));
        Integer pageNo = Integer.parseInt(data.get("pageNo")), pageSize = Integer.parseInt(data.get("pageSize"));
        System.out.println("转换后的 pageNo, pageSize: " + pageNo + ", " + pageSize);
        Questions questions1 = new Questions(1, "jx", "测试题目1", "测试解析1", "测试答案1", 90.0, 2, "测试我的答案1", 5, 3, true, 3);
        Questions questions2 = new Questions(2, "wf", "测试题目2", "测试解析2", "测试答案2", 90.0, 2, "测试我的答案2", 5, 3, true, 3);
        Questions questions3 = new Questions(2, "wf", "测试题目2", "测试解析2", "测试答案2", 90.0, 2, "测试我的答案2", 5, 3, true, 3);
        List<Questions> wrongQuestionsList_temp = new ArrayList<>();
        wrongQuestionsList_temp.add(questions1);
        wrongQuestionsList_temp.add(questions2);
        wrongQuestionsList_temp.add(questions3);
        List<JSONObject> wrongQuestionsList = new LinkedList<>();
        for (Questions wrong_question : wrongQuestionsList_temp) {
            JSONObject wrongQuestion = new JSONObject();
            wrongQuestion.put("questionId", wrong_question.getId());
            wrongQuestion.put("content", wrong_question.getContent());
            wrongQuestion.put("myAnswer", wrong_question.getMyAnswer());
            wrongQuestion.put("correctAnswer", wrong_question.getAnswer());
            wrongQuestionsList.add(wrongQuestion);
        }
        System.out.println("测试数据输出结果：" + JSON.toJSONString(wrongQuestionsList));
        return JSON.toJSONString(true);
    }

}
