package com.nanhua.retrieval.service.impl;

import com.nanhua.retrieval.entity.Comment;
import com.nanhua.retrieval.mapper.CommentMapper;

import com.nanhua.retrieval.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yzq
 * @since 2023-04-27
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {
@Autowired
private CommentMapper commentMapper;

    @Override
    public List<Comment> findByPolicyId(long policyId) {
       return commentMapper.findByPolicyId(policyId);

    }

    @Override
    public void savecomment(Comment comment) {

    }
}
