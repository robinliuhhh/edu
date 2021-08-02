package com.robin.educommentboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.robin.educommentboot.entity.CourseCommentFavoriteRecord;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CourseCommentFavoriteRecordDao extends BaseMapper<CourseCommentFavoriteRecord> {

    @Select({"select * from course_comment_favorite_record where comment_id = #{comment_id} and is_del = 0"})
    List<CourseCommentFavoriteRecord> getFavorites(@Param("comment_id") Integer comment_id);
}
