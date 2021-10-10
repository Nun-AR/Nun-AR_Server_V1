package com.nunar.nunar.service;

import com.nunar.nunar.exception.CustomException;
import com.nunar.nunar.model.Post;
import com.nunar.nunar.model.User;
import com.nunar.nunar.repository.PostRepository;
import com.nunar.nunar.repository.UserRepository;
import com.nunar.nunar.request.PostRequest;
import com.nunar.nunar.response.PostResponse;
import com.nunar.nunar.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public PostService(PostRepository postRepository, UserRepository userRepository, JwtUtil jwtUtil) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public List<PostResponse> getAllPost() {
        List<Post> postList = postRepository.findAll();

        return postList.stream().map(it ->
                new PostResponse(it.getPostIdx(),
                        it.getUser().getUserIdx(),
                        it.getWriter(),
                        it.getTitle(),
                        it.getBookmarks(),
                        it.getIsBookmarks(),
                        it.getTag(),
                        it.getThumbnail(),
                        it.getFileUrl())
        ).collect(Collectors.toList());
    }

    public PostResponse getPostByIdx(int postIdx) throws CustomException {
        Post post = postRepository.findById(postIdx).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "게시글을 찾지 못하였습니다"));

        return new PostResponse(
                post.getPostIdx(),
                post.getUser().getUserIdx(),
                post.getWriter(),
                post.getTitle(),
                post.getBookmarks(),
                post.getIsBookmarks(),
                post.getTag(),
                post.getThumbnail(),
                post.getFileUrl()
        );
    }

    public void deletePost(String token, int postIdx) throws CustomException {
        String userId = jwtUtil.extractUsername(token);
        String writerId = postRepository.findById(postIdx)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "게시글을 찾지 못하였습니다")).getUser().getId();

        if (userId.equals(writerId)) {
            postRepository.deleteById(postIdx);
        } else {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "게시글은 작성자만 삭제할 수 있습니다.");
        }
    }

    public List<PostResponse> getPostByUserIdx(int userIdx) throws CustomException {
        userRepository.findById(userIdx).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당하는 사용자가 존재하지 않습니다."));
        List<Post> postList = postRepository.getPostByUser(userIdx);

        return postList.stream().map(it ->
                new PostResponse(it.getPostIdx(),
                        it.getUser().getUserIdx(),
                        it.getWriter(),
                        it.getTitle(),
                        it.getBookmarks(),
                        it.getIsBookmarks(),
                        it.getTag(),
                        it.getThumbnail(),
                        it.getFileUrl())
        ).collect(Collectors.toList());
    }

    public void writePost(String token, PostRequest postRequest) throws CustomException {
        if (token == null) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "토큰이 전송되지 않았습니다.");
        }

        User user = userRepository.findById(jwtUtil.extractUsername(token));
        Post post = new Post(0, user, user.getName(), postRequest.getTitle(), 0, false, postRequest.getTag(), postRequest.getThumbnail(), postRequest.getFileUrl());
        postRepository.save(post);
    }

    public List<PostResponse> getPopularPost() {
        List<Post> postList = postRepository.getPopularPost();

        return postList.stream().map(it ->
                new PostResponse(it.getPostIdx(),
                        it.getUser().getUserIdx(),
                        it.getWriter(),
                        it.getTitle(),
                        it.getBookmarks(),
                        it.getIsBookmarks(),
                        it.getTag(),
                        it.getThumbnail(),
                        it.getFileUrl())
        ).collect(Collectors.toList());
    }
}
