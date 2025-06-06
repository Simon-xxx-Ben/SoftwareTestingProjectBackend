package org.projects.backend.service.impl.AlgorithmPlus;

import org.projects.backend.service.AlgorithmPlus.AIApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AIApiServiceImpl implements AIApiService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String updateChapterDegree() {
        return "";
    }

    @Override
    public String getWrongQuestionsByQuestionIdThroughAI(Integer questionId) {

        // 目标 API 地址
        String url = "http://localhost:8081/ai/bailian/agent/call?message=请你帮我推荐几道跟id为" + questionId + "的题目类似的题";

        // 发送 GET 请求（其他方法：postForObject, exchange 等）
        String result = restTemplate.getForObject(url, String.class);

        return result;
    }
}
