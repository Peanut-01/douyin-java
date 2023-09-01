package com.douyin.result;

import com.douyin.vo.CommentVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class CommentListResult extends Result {
    private List<CommentVO> commentList;

    public static CommentListResult success(List<CommentVO> commentList) {
        CommentListResult commentListResult = new CommentListResult(commentList);
        commentListResult.setStatusCode(0);
        return commentListResult;
    }
}
