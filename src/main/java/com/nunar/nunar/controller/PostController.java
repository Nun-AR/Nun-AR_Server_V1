package com.nunar.nunar.controller;

import com.nunar.nunar.exception.CustomException;
import com.nunar.nunar.request.PostRequest;
import com.nunar.nunar.response.BaseResponse;
import com.nunar.nunar.response.PostResponse;
import com.nunar.nunar.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("")
    public BaseResponse<List<PostResponse>> getAllPost(@RequestHeader("Authorization") String token) {
        List<PostResponse> postResponses = postService.getAllPost(token.substring(7));
        return new BaseResponse<>(200, "모든 게시글 조회 성공", postResponses);
    }

    @GetMapping("/{postIdx}")
    public BaseResponse<PostResponse> getPostByIdx(@RequestHeader("Authorization") String token, @PathVariable int postIdx) throws CustomException{
        PostResponse postResponse = postService.getPostByIdx(token.substring(7), postIdx);
        return new BaseResponse<>(200, "게시글 조회 성공", postResponse);
    }

    @DeleteMapping("/{postIdx}")
    public BaseResponse<Void> deletePost(@RequestHeader(value="Authorization") String token, @PathVariable int postIdx) throws CustomException{
        postService.deletePost(token.substring(7), postIdx);
        return new BaseResponse<>(200, "성공적으로 삭제했습니다", null);

    }

    @GetMapping("/user/{userIdx}")
    public BaseResponse<List<PostResponse>> getPostByUserIdx(@RequestHeader("Authorization") String token, @PathVariable int userIdx) throws CustomException{
        List<PostResponse> postResponses = postService.getPostByUserIdx(token.substring(7), userIdx);
        return new BaseResponse<>(200, "성공적으로 조회하였습니다", postResponses);
    }

    @PostMapping("")
    public BaseResponse<Void> writePost(@RequestHeader(value="Authorization") String token, @RequestBody PostRequest postRequest) throws CustomException{
        postService.writePost(token.substring(7), postRequest);
        return new BaseResponse<>(200, "성공적으로 작성했습니다.", null);
    }

    @GetMapping("/popular")
    public BaseResponse<List<PostResponse>> getPopularResponse(@RequestHeader("Authorization") String token) {
        return new BaseResponse<>(200, "성공적으로 조회하였습니다.", postService.getPopularPost(token.substring(7)));
    }

    @GetMapping("/search")
    public BaseResponse<List<PostResponse>> searchPost(@RequestHeader("Authorization") String token, @RequestParam String searchWord) {
        return new BaseResponse<>(200, "", postService.searchPost(token.substring(7), searchWord));
    }

}
