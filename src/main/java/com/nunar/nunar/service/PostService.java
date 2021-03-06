package com.nunar.nunar.service;

import com.nunar.nunar.exception.CustomException;
import com.nunar.nunar.model.Bookmark;
import com.nunar.nunar.model.Post;
import com.nunar.nunar.model.User;
import com.nunar.nunar.repository.BookmarkRepository;
import com.nunar.nunar.repository.PostRepository;
import com.nunar.nunar.repository.UserRepository;
import com.nunar.nunar.request.PostRequest;
import com.nunar.nunar.response.PostResponse;
import com.nunar.nunar.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;
    private final JwtUtil jwtUtil;

    public PostService(PostRepository postRepository, UserRepository userRepository, BookmarkRepository bookmarkRepository, JwtUtil jwtUtil) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.bookmarkRepository = bookmarkRepository;
        this.jwtUtil = jwtUtil;
    }

    public List<PostResponse> getAllPost(String token) {
        List<Post> postList = postRepository.findAll();
        Collections.reverse(postList);
        return getPostResponses(token, postList);
    }

    public PostResponse getPostByIdx(String token, int postIdx) throws CustomException {
        Post post = postRepository.findById(postIdx).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "게시글을 찾지 못하였습니다"));
        User user = userRepository.findById(jwtUtil.extractUsername(token));
        List<Post> userBookmarks = bookmarkRepository.getBookmarkByUser(user).stream().map(Bookmark::getPost).collect(Collectors.toList());

        return new PostResponse(
                post.getPostIdx(),
                post.getUser().getUserIdx(),
                post.getUser().getName(),
                post.getTitle(),
                bookmarkRepository.getBookmarkByPost(post).size(),
                userBookmarks.contains(post),
                post.getTag(),
                post.getThumbnail(),
                post.getFileUrl(),
                user.getProfileUrl()
        );
    }

    @Transactional
    public void deletePost(String token, int postIdx) throws CustomException {
        String userId = jwtUtil.extractUsername(token);
        String writerId = postRepository.findById(postIdx)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "게시글을 찾지 못하였습니다")).getUser().getId();

        if (userId.equals(writerId)) {
            bookmarkRepository.deleteBookmarkByPost(postRepository.getById(postIdx));
            postRepository.deleteById(postIdx);
        } else {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "게시글은 작성자만 삭제할 수 있습니다.");
        }
    }

    public List<PostResponse> getPostByUserIdx(String token, int userIdx) throws CustomException {
        userRepository.findById(userIdx).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당하는 사용자가 존재하지 않습니다."));
        return getPostResponses(token, postRepository.getPostByUser(userIdx));
    }

    public int writePost(String token, PostRequest postRequest) throws CustomException {
        if (token == null) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "토큰이 전송되지 않았습니다.");
        }

        User user = userRepository.findById(jwtUtil.extractUsername(token));
        Post post = new Post(0, user, postRequest.getTitle(), postRequest.getTag(), postRequest.getThumbnail(), postRequest.getFileUrl());
        return postRepository.save(post).getPostIdx();
    }

    public List<PostResponse> getPopularPost(String token) {
        List<Post> postList = postRepository
                .getPopularPost();
        return getPostResponses(token, postList.subList(0, Math.min(postList.size(), 3)));
    }

    private List<PostResponse> getPostResponses(String token, List<Post> postList) {
        User user = userRepository.findById(jwtUtil.extractUsername(token));
        List<Post> userBookmarks = bookmarkRepository.getBookmarkByUser(user).stream().map(Bookmark::getPost).collect(Collectors.toList());

        return postList.stream().map(it ->
                new PostResponse(it.getPostIdx(),
                        it.getUser().getUserIdx(),
                        it.getUser().getName(),
                        it.getTitle(),
                        bookmarkRepository.getBookmarkByPost(it).size(),
                        userBookmarks.contains(it),
                        it.getTag(),
                        it.getThumbnail(),
                        it.getFileUrl(),
                        it.getUser().getProfileUrl())
        ).collect(Collectors.toList());
    }

    public List<PostResponse> searchPost(String token, String _searchWord) {
        List<String> searchWordList = Arrays.asList(_searchWord.toLowerCase(Locale.ROOT).replace("#", " ").split(""));

        return getAllPost(token).stream().filter(post -> {
                    String title = post.getTitle().toLowerCase(Locale.ROOT);
                    List<String> tags = Arrays.stream(post.getTag().split("#")).map(it -> it.toLowerCase(Locale.ROOT)).collect(Collectors.toList());
                    return searchWordList.stream().anyMatch(searchWord ->
                            title.contains(searchWord) || tags.stream().anyMatch(tag -> tag.contains(searchWord))
                    );
                }
        ).collect(Collectors.toList());

    }

}
