package com.fireal99.springboot_springmvc.Controllers;

import com.fireal99.springboot_springmvc.domain.Post;
import com.fireal99.springboot_springmvc.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/posts/html")
public class PostHtmlController {
    @Autowired
    private PostService postService;

    @GetMapping
    public String findAllPosts(@RequestParam(name = "limit", defaultValue = "10", required = false) Integer limit,
                               @RequestParam(name = "pageNumber", defaultValue = "1", required = false) Integer pageNumber,
                               Model model) {

        List<Post> posts =  postService.findAllPosts(limit, pageNumber);

        model.addAttribute("posts", posts);

        return "posts";
    }

    @GetMapping("/{id}")
    public String findPostById(@PathVariable Long id, Model model) {
        Optional<Post> post = postService.findPostById(id);

        model.addAttribute("pageName", post.get().getTitle());
        model.addAttribute("post", post.get());

        return "single-post";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createPost(@RequestBody Post post) {
        postService.createPost(post);
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

