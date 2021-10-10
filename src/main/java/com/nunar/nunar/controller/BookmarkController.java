package com.nunar.nunar.controller;

import com.nunar.nunar.exception.CustomException;
import com.nunar.nunar.response.BaseResponse;
import com.nunar.nunar.response.PostResponse;
import com.nunar.nunar.service.BookmarkService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/bookmark")
public class BookmarkController {

    final BookmarkService bookmarkService;

    public BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    @GetMapping("/my")
    public BaseResponse<List<PostResponse>> getMyBookmarks(@RequestHeader("Authorization") String token) throws CustomException {
        return new BaseResponse<>(200,
                "성공적으로 조회하였습니다",
                bookmarkService.getMyBookmarks(token.substring(7)));
    }

    @PostMapping("/{postIdx}")
    public BaseResponse<Void> postBookmark(@RequestHeader("Authorization") String token, @PathVariable int postIdx) throws CustomException {
        bookmarkService.postBookmark(token.substring(7), postIdx);
        return new BaseResponse<>(200, "북마크하였습니다", null);
    }

    @DeleteMapping("/{postIdx}")
    public BaseResponse<Void> deleteBookmark(@RequestHeader("Authorization") String token, @PathVariable int postIdx) throws CustomException {
        bookmarkService.deleteBookmark(token.substring(7), postIdx);
        return new BaseResponse<>(200, "북마크를 삭제했습니다", null);
    }

    @GetMapping("/user/{userIdx}")
    public BaseResponse<List<PostResponse>> getBookmarksByUserIdx(@RequestHeader("Authorization") String token, @PathVariable int userIdx) throws CustomException {
        return new BaseResponse<>(200, "성공적으로 조회하였습니다", bookmarkService.getBookmarksByUserIdx(token.substring(7), userIdx));
    }

}
