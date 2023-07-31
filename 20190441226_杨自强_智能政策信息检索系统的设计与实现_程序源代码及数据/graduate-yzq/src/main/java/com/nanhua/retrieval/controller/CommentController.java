package com.nanhua.retrieval.controller;

import com.nanhua.common.vo.Result;
import com.nanhua.retrieval.entity.Comment;
import com.nanhua.retrieval.mapper.CommentMapper;
import com.nanhua.retrieval.service.impl.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yzq
 * @since 2023-04-27
 */
@RestController
@RequestMapping("/retrieval/comment")
public class CommentController {
    @Autowired
    private CommentServiceImpl commentService;
    @Autowired
    private CommentMapper commentMapper;
    @PostMapping("/savec")
    public void saveComment(@RequestBody Comment comment){
         commentMapper.save(comment);
    }
    @GetMapping("/comments")
    public Result<List<Comment>> getComments(@RequestParam("policyId") Long policyId) {
        List<Comment> comments=commentService.findByPolicyId(policyId);

        return Result.success(comments);
    }
}
