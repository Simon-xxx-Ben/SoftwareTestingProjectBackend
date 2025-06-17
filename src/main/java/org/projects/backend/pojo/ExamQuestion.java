package org.projects.backend.pojo;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamQuestion {
//    @TableId(type = IdType.AUTO)
//    private Integer id;
    @TableField
    private Integer examId;
    @TableField
    private Integer questionId;
}
