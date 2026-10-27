package com.fireal99.springboot_springmvc.Controllers;

import com.fireal99.springboot_springmvc.domain.Post;
import com.fireal99.springboot_springmvc.services.PostService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {
    private final PostService postService;

    public  HomeController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping()
    public List<Post> home(@RequestParam(name = "limit", defaultValue = "10", required = false) Integer limit,
                           @RequestParam(name = "pageNumber", defaultValue = "0", required = false) Integer pageNumber) {
        return postService.findAllPosts(limit, pageNumber);
    }
}
