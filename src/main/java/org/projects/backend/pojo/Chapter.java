package org.projects.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Chapter {
    @TableId(type = IdType.AUTO)
    private String id;
    private String name;
    private Double degree;
    private Integer practiceHardValue;
}
