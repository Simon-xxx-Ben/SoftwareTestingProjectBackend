package org.projects.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Questions {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String chapterId;
    private String content;
    private String options;
    private String explanation;
    private String answer;
    private Double scoreValue;
    private Integer hardValue;
    private String myAnswer;
    private Integer correctCount;
    private Integer incorrectCount;
    private Boolean isWrong;
    private Integer questionType;
    private String recommendQuestionsList;
}
