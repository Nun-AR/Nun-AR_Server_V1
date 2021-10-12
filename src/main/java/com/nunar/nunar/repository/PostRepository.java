package com.nunar.nunar.repository;

import com.nunar.nunar.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query("FROM Post p WHERE p.user.userIdx = :userIdx")
    public List<Post> getPostByUser(@Param("userIdx") int userIdx);

    @Query(value = "select b.post from Bookmark b group by b.post order by count(b.post) desc")
    public List<Post> getPopularPost();
}
