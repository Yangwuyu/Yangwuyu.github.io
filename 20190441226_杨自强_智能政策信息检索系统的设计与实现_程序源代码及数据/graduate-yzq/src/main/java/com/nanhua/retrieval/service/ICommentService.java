package com.nanhua.retrieval.service;

import com.nanhua.retrieval.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yzq
 * @since 2023-04-27
 */
public interface ICommentService extends IService<Comment> {

    List<Comment> findByPolicyId(long policyId);

    void savecomment(Comment comment);
}
