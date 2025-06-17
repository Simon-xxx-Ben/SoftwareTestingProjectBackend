package org.projects.backend;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.junit.jupiter.api.Test;
import org.projects.backend.config.AppConfig;
import org.projects.backend.pojo.Questions;
import org.projects.backend.service.chapter.ChapterService;
import org.projects.backend.service.exams.ExamsService;
import org.projects.backend.service.questions.QuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

// 配置与属性加载测试
@SpringBootTest
class ConfigPropertiesTest {

    // 直接注入配置属性类（@ConfigurationProperties 绑定类）
    @Autowired
    private AppConfig appConfig;

    @Test
    void testCriticalPropertiesLoaded() {
        // 验证属性是否正确引入
        assertEquals("test_successful", appConfig.test());
    }
}

// 自定义组件与自动配置测试
@SpringBootTest
class CustomComponentTest {

    // 直接注入自定义组件
    @Autowired
    private QuestionsService questionsService;

    @Autowired
    private ExamsService examsService;

    @Autowired
    private ChapterService chapterService;

    @Test
    void componentLoadedAndFunctional() {
        // 1. 验证组件存在（基本装配）
        assertNotNull(questionsService + "已正确装配");
        assertNotNull(examsService + "已正确装配");
        assertNotNull(chapterService + "已正确装配");

        // 2. 执行一个最简单的方法验证基础功能
        assertEquals("test_successful", questionsService.test());
        assertEquals("test_successful", examsService.test());
        assertEquals("test_successful", chapterService.test());
    }
}

// 上下文加载测试
@SpringBootTest
class ContextLoadsIntegrationTest {

    // 1. 空方法测试上下文加载本身
    @Test
    void contextLoads() {
        // 空方法，触发上下文加载
    }

    // 2. （增强）注入多个重要核心Bean、检查它们是否非空
    @Autowired // 自动注入关键Bean
    private QuestionsService questionsService; // 例如：核心业务服务、主配置类、数据源等

    @Autowired
    private ExamsService examsService; // 也可以注入整个上下文

    @Autowired
    private ChapterService chapterService;

    @Test
    void criticalBeansAreLoaded() {
        // 简单断言：这些关键Bean不为null，说明它们至少被成功创建并装配了
        assertNotNull(questionsService, "questionsService Bean 已经被成功加载");
        assertNotNull(examsService, "examsService Bean 已经被成功加载");
        assertNotNull(chapterService, "chapterService Bean 已经被成功加载");
    }

    @Test
    public void deleteQuestionById() {
        String id = "111";
        JSONObject resp = new JSONObject();
        Integer questionId;
        questionId = Integer.parseInt(id);
    }

}


