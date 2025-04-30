package com.hansen.BulletinPost.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hansen.BulletinPost.model.Post;
import com.hansen.BulletinPost.service.PostService;

import jakarta.persistence.EntityManager;

@Controller
@RequestMapping("/posts")
public class PostController {
	private final PostService postService;
    private final EntityManager entityManager;

    @Autowired
    public PostController(PostService postService, EntityManager entityManager) {
        this.postService = postService;
        this.entityManager = entityManager;
    }
    
    // show all available post
    @GetMapping
    public String listPosts(Model model) {
    	List<Post> posts = postService.getAllActivePosts();
        model.addAttribute("posts", posts);
        return "posts/list";
    }
    
    // View Post
    @GetMapping("/{id}")
    public String viewPosts(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
    	Post post = postService.getPostById(id);
    	if(post == null || post.getIsDeleted()) {
    		redirectAttributes.addFlashAttribute("error", "Post not exist or deleted!");
    		return "redirect:/posts";
    	}
    	else{
    		model.addAttribute("post", post);
    		postService.incrementViewCount(id);
    	}
    	return "posts/view";
    }
    
    // show delete form
    @GetMapping("/{id}/delete")
    public String showDeleteForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
    	Post existingPost = postService.getPostById(id);
    	if(existingPost == null || existingPost.getIsDeleted()) {
    		redirectAttributes.addFlashAttribute("error", "Post not exist or deleted!");
    		return "redirect:/posts";
    	}
    	else model.addAttribute("post", existingPost);
    	return "posts/delete";
    }
    
    // process the deletion 
    @PostMapping("/{id}/delete")
    @Transactional
    public String deletePost(@PathVariable Long id, 
            				@RequestParam String password,
            				RedirectAttributes redirectAttributes) {
    	// Verify password
        if (!postService.verifyPassword(id, password)) {
            redirectAttributes.addFlashAttribute("error", "Incorrect password!");
            return "redirect:/posts/" + id + "/delete";
        }
        
        postService.softDelete(id);
        redirectAttributes.addFlashAttribute("message", "Post deleted succesfuly!");
    	return("redirect:/posts");
    }
    
    // Show the create post form
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("post", new Post());
        return "posts/create";
    }

    // Process the form submission
    @PostMapping("/create")
    @Transactional
    public String createPost(@ModelAttribute Post post, RedirectAttributes redirectAttributes) {
    	post.setCreatedAt(LocalDateTime.now());
        post.setViewCount(0);
        post.setIsDeleted(false);
        entityManager.persist(post);
        
        redirectAttributes.addFlashAttribute("message", "Post created successfully!");
        return "redirect:/posts";
    }
    
    // Show edit form
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Post existingPost = postService.getPostById(id);
        if (existingPost == null || existingPost.getIsDeleted() == true) {
            return "redirect:/posts";
        }
        model.addAttribute("post", existingPost);
        return "posts/edit";
    }
   
    // Process edit form submission
    @PostMapping("/{id}/edit")
    @Transactional
    public String updatePost(@PathVariable Long id, 
                            @ModelAttribute Post post,
                            @RequestParam String password,
                            RedirectAttributes redirectAttributes) {
        
        // Verify password
        if (!postService.verifyPassword(id, password)) {
            redirectAttributes.addFlashAttribute("error", "Incorrect password!");
            return "redirect:/posts/" + id + "/edit";
        }
        
        Post existingPost = postService.getPostById(id);
        
        existingPost.setTitle(post.getTitle());
        existingPost.setContent(post.getContent());
        existingPost.setModifiedAt(LocalDateTime.now());
        
        entityManager.merge(existingPost);
        
        redirectAttributes.addFlashAttribute("message", "Post updated successfully!");
        return "redirect:/posts/" + id;
    }
}
