package com.robin.educourseboot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CourseLesson implements Serializable {

    private static final long serialVersionUID = 753365067980240096L;
    /**
    * id
    */
    private Object id;
    /**
    * 课程id
    */
    private Integer courseId;
    /**
    * 章节id
    */
    private Integer sectionId;
    /**
    * 课时主题
    */
    private String theme;
    /**
    * 课时时长(分钟)
    */
    private Integer duration;
    /**
    * 是否免费
    */
    private Object isFree;
    /**
    * 记录创建时间
    */
    private Date createTime;
    /**
    * 更新时间
    */
    private Date updateTime;
    /**
    * 是否删除
    */
    private Object isDel;
    /**
    * 排序字段
    */
    private Integer orderNum;
    /**
    * 课时状态,0-隐藏，1-未发布，2-已发布
    */
    private Integer status;
}