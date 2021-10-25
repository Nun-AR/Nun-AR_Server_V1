package com.nunar.nunar.service;

import com.nunar.nunar.exception.CustomException;
import com.nunar.nunar.model.Bookmark;
import com.nunar.nunar.model.Post;
import com.nunar.nunar.model.User;
import com.nunar.nunar.repository.BookmarkRepository;
import com.nunar.nunar.repository.PostRepository;
import com.nunar.nunar.repository.UserRepository;
import com.nunar.nunar.response.PostResponse;
import com.nunar.nunar.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookmarkService {

    final BookmarkRepository bookmarkRepository;
    final UserRepository userRepository;
    final PostRepository postRepository;

    final JwtUtil jwtUtil;

    public BookmarkService(BookmarkRepository bookmarkRepository, UserRepository userRepository, PostRepository postRepository, JwtUtil jwtUtil) {
        this.bookmarkRepository = bookmarkRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.jwtUtil = jwtUtil;
    }

    public List<PostResponse> getMyBookmarks(String token) throws CustomException {
        String userId = jwtUtil.extractUsername(token);
        User user = userRepository.findById(userId);
        return getBookmarksByUserIdx(token, user.getUserIdx());
    }

    public void postBookmark(String token, int postIdx) throws CustomException {
        User user = userRepository.findById(jwtUtil.extractUsername(token));
        Post post = postRepository.findById(postIdx)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 게시물이 존재하지 않습니다"));
        Bookmark bookmark = new Bookmark(user, post);
        if (bookmarkRepository.findById(new Bookmark.BookmarkId(user.getUserIdx(), post.getPostIdx())).isPresent())
            throw new CustomException(HttpStatus.CONFLICT, "이미 북마크 하였습니다");

        bookmarkRepository.save(bookmark);
    }

    public void deleteBookmark(String token, int postIdx) throws CustomException {
        User user = userRepository.findById(jwtUtil.extractUsername(token));
        Post post = postRepository.findById(postIdx)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 게시물이 존재하지 않습니다"));
        Bookmark bookmark = new Bookmark(user, post);
        bookmarkRepository.findById(new Bookmark.BookmarkId(user.getUserIdx(), post.getPostIdx()))
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 북마크가 존재하지 않습니다"));

        bookmarkRepository.delete(bookmark);
    }

    public List<PostResponse> getBookmarksByUserIdx(String token, int userIdx) throws CustomException {
        User my = userRepository.findById(jwtUtil.extractUsername(token));
        List<Post> myBookmark = bookmarkRepository.getBookmarkByUser(my).stream().map(Bookmark::getPost).collect(Collectors.toList());
        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 사용자가 존재하지 않습니다"));
        List<Bookmark> bookmarks = bookmarkRepository.getBookmarkByUser(user);
        return bookmarks.stream()
                .map(Bookmark::getPost)
                .map(it -> new PostResponse(it.getPostIdx(),
                        it.getUser().getUserIdx(),
                        it.getWriter(),
                        it.getTitle(),
                        bookmarkRepository.getBookmarkByPost(it).size(),
                        myBookmark.contains(it),
                        it.getTag(),
                        it.getThumbnail(),
                        it.getFileUrl(),
                        it.getUser().getProfileUrl()
                        ))
                .collect(Collectors.toList());
    }

}
