package com.fireal99.springboot_springmvc.Controllers;

import com.fireal99.springboot_springmvc.domain.Post;
import com.fireal99.springboot_springmvc.services.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {
        @Autowired
        private PostService postService;
    
        @GetMapping
        @ResponseStatus(HttpStatus.OK)
        public List<Post> findAllPosts(@RequestParam(name = "limit", defaultValue = "10", required = false) Integer limit,
                                                       @RequestParam(name = "pageNumber", defaultValue = "1", required = false) Integer pageNumber) {
            return postService.findAllPosts(limit, pageNumber);
        }
    
        @GetMapping("/{id}")
        public ResponseEntity<Post> findPostById(@PathVariable Long id) {
            Optional<Post> post = postService.findPostById(id);
            return post.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }
    
        @PostMapping
        //@ResponseStatus(HttpStatus.CREATED)
        public ResponseEntity<List<String>> createPost(@Valid @RequestBody Post post, BindingResult result) {
            if  (result.hasErrors()) {
                var errors = result.getAllErrors().stream().map(ObjectError::getDefaultMessage).toList();
                return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
            }

            postService.createPost(post);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    
        @DeleteMapping("/{id}")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public void deletePostById(@PathVariable Long id) {
            postService.deletePostById(id);
        }
    
        @PutMapping
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public void updatePost(@RequestBody Post post) {
            postService.updatePost(post);
        }
}
