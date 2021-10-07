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
    public BaseResponse<List<PostResponse>> getAllPost() throws CustomException{
        List<PostResponse> postResponses = postService.getAllPost();
        return new BaseResponse<List<PostResponse>>(200, "모든 게시글 조회 성공", postResponses);
    }

    @GetMapping("/{postIdx}")
    public BaseResponse<PostResponse> getPostByIdx(@PathVariable int postIdx) throws CustomException{
        PostResponse postResponse = postService.getPostByIdx(postIdx);
        return new BaseResponse<PostResponse>(200, "게시글 조회 성공", postResponse);
    }

    @DeleteMapping("/{postIdx}")
    public BaseResponse<Void> deletePost(@RequestHeader(value="x-access-token") String token, @PathVariable int userIdx) throws CustomException{
        postService.deletePost(token, userIdx);
        return new BaseResponse<>(200, "성공적으로 삭제했습니다", null);

    }

    @GetMapping("/user/{userIdx}")
    public BaseResponse<List<PostResponse>> getPostByUserIdx(@PathVariable int userIdx) throws CustomException{
        List<PostResponse> postResponses = postService.getPostByUserIdx(userIdx);
        return new BaseResponse<>(200, "성공적으로 조회하였습니다", postResponses);
    }

    @PostMapping("")
    public BaseResponse<Void> writePost(@RequestHeader(value="x-access-token") String token, @RequestBody PostRequest postRequest) throws CustomException{
        postService.writePost(token, postRequest);
        return new BaseResponse<>(200, "성공적으로 작성했습니다.", null);
    }

    @GetMapping("/popular")
    public BaseResponse<List<PostResponse>> getPopularResponse() throws CustomException{
        List<PostResponse> postResponses = postService.getPopularPost();
        return new BaseResponse<>(200, "성공적으로 조회하였습니다.", postResponses);
    }

}
