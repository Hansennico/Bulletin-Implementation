package com.hansen.BulletinPost.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.hansen.BulletinPost.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
	
	@Query("SELECT p FROM Post p WHERE p.isDeleted = false ORDER BY p.id DESC")
	List<Post> findByIsDeletedFalseOrderByIdDesc();
	
    @Query("SELECT p FROM Post p WHERE p.id = :id AND p.isDeleted = false")
    Post findByIdAndIsDeletedFalse(@Param("id") Long id);
    
    @Modifying
    @Transactional
    @Query("UPDATE Post p SET p.viewCount = p.viewCount + 1 WHERE p.id = :id")
    void incrementViewCount(@Param("id") Long id);
    
    @Query("SELECT COUNT(p) > 0 FROM Post p WHERE p.id = :id AND p.password = :password")
    boolean existsByIdAndPassword(@Param("id") Long id, @Param("password") String password);
    
    @Modifying
    @Transactional
    @Query("UPDATE Post p SET p.isDeleted = true WHERE p.id = :id")
    void softDeleteById(@Param("id") Long id);
}
