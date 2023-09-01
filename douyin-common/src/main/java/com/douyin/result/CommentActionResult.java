package com.douyin.result;

import com.douyin.vo.CommentVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class CommentActionResult extends Result {
    private CommentVO commentVO;

    public static CommentActionResult success(CommentVO commentVO) {
        CommentActionResult commentActionResult = new CommentActionResult(commentVO);
        commentActionResult.setStatusCode(0);
        return commentActionResult;
    }
}
