package com.nunar.nunar.repository;

import com.nunar.nunar.model.Bookmark;
import com.nunar.nunar.model.Post;
import com.nunar.nunar.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Bookmark.BookmarkId> {

    @Query(value = "select b from Bookmark b where b.user = :user")
    List<Bookmark> getPostByUser(User user);

}
