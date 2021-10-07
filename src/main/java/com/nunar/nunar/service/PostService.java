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

    public List<PostResponse> getAllPost() throws CustomException {
        List<Post> postList = postRepository.findAll();

        List<PostResponse> list = postList.stream().map(it -> {
            return new PostResponse(it.getPostIdx(), it.getUser().getUserIdx(), it.getWriter(), it.getTitle(), it.getBookmarks(), it.getIsBookmarks(), it.getTag(), it.getThumbnail(), it.getFileUrl());
        }).collect(Collectors.toList());

        return list;
    }

    public PostResponse getPostByIdx(int postIdx) throws CustomException {
        Post post = postRepository.findById(postIdx).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "게시글을 찾지 못하였습니다"));

        PostResponse postResponse = new PostResponse(post.getPostIdx(), post.getUser().getUserIdx(), post.getWriter(), post.getTitle(), post.getBookmarks(), post.getIsBookmarks(), post.getTag(), post.getThumbnail(), post.getFileUrl());
        return postResponse;
    }

    public void deletePost(String token, int postIdx) throws CustomException {
        String userId = jwtUtil.extractUsername(token);
        String writterId =
                postRepository.findById(postIdx).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "게시글을 찾지 못하였습니다")).getUser().getId();

        System.out.println(userId + " " + writterId + " " + userId.equals(writterId));
        if (userId.equals(writterId)) {
            postRepository.deleteById(postIdx);
        } else {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "게시글은 작성자만 삭제할 수 있습니다.");
        }
    }

    public List<PostResponse> getPostByUserIdx(int userIdx) throws CustomException {
        User user = userRepository.findById(userIdx).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당하는 사용자가 존재하지 않습니다."));
        List<Post> postList = postRepository.getPostByUser(userIdx);
        List<PostResponse> list = postList.stream().map(it -> {
            return new PostResponse(it.getPostIdx(), it.getUser().getUserIdx(), it.getWriter(), it.getTitle(), it.getBookmarks(), it.getIsBookmarks(), it.getTag(), it.getThumbnail(), it.getFileUrl());
        }).collect(Collectors.toList());

        return list;
    }

    public void writePost(String token, PostRequest postRequest) throws CustomException {
        if (token == null) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "토큰이 전송되지 않았습니다.");
        }

        User user = userRepository.findById(jwtUtil.extractUsername(token));
        Post post = new Post(0, user, user.getName(), postRequest.getTitle(), 0, false, postRequest.getTag(), postRequest.getThumbnail(), postRequest.getFileUrl());
        postRepository.save(post);
    }

    public List<PostResponse> getPopularPost() throws CustomException{
        List<Post> postList = postRepository.getPopularPost();
        List<PostResponse> list = postList.stream().map(it -> {
            return new PostResponse(it.getPostIdx(), it.getUser().getUserIdx(), it.getWriter(), it.getTitle(), it.getBookmarks(), it.getIsBookmarks(), it.getTag(), it.getThumbnail(), it.getFileUrl());
        }).collect(Collectors.toList());

        return list;
    }
}
