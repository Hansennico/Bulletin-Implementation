package com.hansen.BulletinPost.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hansen.BulletinPost.model.Post;
import com.hansen.BulletinPost.repository.PostRepository;

import jakarta.persistence.EntityManager;

@Service
public class PostService {
	private final PostRepository postRepository;
    
    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
    
    public List<Post> getAllActivePosts() {
        return postRepository.findByIsDeletedFalseOrderByIdDesc();
    }
    
    public Post getPostById(Long id) {
    	return postRepository.findByIdAndIsDeletedFalse(id);
    }
    
    public void incrementViewCount(Long id) {
    	postRepository.incrementViewCount(id);
    }
    
    public void softDelete(Long id) {
    	postRepository.softDeleteById(id);
    }
    
    public Boolean verifyPassword(Long id, String password) {
    	return postRepository.existsByIdAndPassword(id, password);
    }
}
