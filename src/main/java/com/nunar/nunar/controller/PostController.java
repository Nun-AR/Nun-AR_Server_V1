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
    public BaseResponse<List<PostResponse>> getAllPost() {
        List<PostResponse> postResponses = postService.getAllPost();
        return new BaseResponse<>(200, "모든 게시글 조회 성공", postResponses);
    }

    @GetMapping("/{postIdx}")
    public BaseResponse<PostResponse> getPostByIdx(@PathVariable int postIdx) throws CustomException{
        PostResponse postResponse = postService.getPostByIdx(postIdx);
        return new BaseResponse<>(200, "게시글 조회 성공", postResponse);
    }

    @DeleteMapping("/{postIdx}")
    public BaseResponse<Void> deletePost(@RequestHeader(value="Authorization") String token, @PathVariable int postIdx) throws CustomException{
        postService.deletePost(token.substring(7), postIdx);
        return new BaseResponse<>(200, "성공적으로 삭제했습니다", null);

    }

    @GetMapping("/user/{userIdx}")
    public BaseResponse<List<PostResponse>> getPostByUserIdx(@PathVariable int userIdx) throws CustomException{
        List<PostResponse> postResponses = postService.getPostByUserIdx(userIdx);
        return new BaseResponse<>(200, "성공적으로 조회하였습니다", postResponses);
    }

    @PostMapping("")
    public BaseResponse<Void> writePost(@RequestHeader(value="Authorization") String token, @RequestBody PostRequest postRequest) throws CustomException{
        postService.writePost(token.substring(7), postRequest);
        return new BaseResponse<>(200, "성공적으로 작성했습니다.", null);
    }

    @GetMapping("/popular")
    public BaseResponse<List<PostResponse>> getPopularResponse() {
        List<PostResponse> postResponses = postService.getPopularPost();
        return new BaseResponse<>(200, "성공적으로 조회하였습니다.", postResponses);
    }

}
