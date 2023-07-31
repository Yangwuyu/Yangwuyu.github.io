package com.nanhua.retrieval.mapper;

import com.nanhua.retrieval.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nanhua.retrieval.entity.History;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yzq
 * @since 2023-04-27
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
    List<Comment> findByPolicyId(Long policyId);
    @Insert("INSERT INTO comment(userId, policyId, content, pubDate) VALUES(#{userId}, #{policyId}, #{content},#{pubDate})")
    void save(Comment comment);
}
