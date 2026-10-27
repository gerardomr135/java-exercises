package com.fireal99.springboot_springmvc.services;

import com.fireal99.springboot_springmvc.domain.Post;
import com.fireal99.springboot_springmvc.repository.PostRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> findAllPosts(int limit, int pageNumber) {
        if (limit < 1) {
            limit = 10;
        }

        // Adjust for zero-based indexing if input is 1-based
        int adjustedPage = (pageNumber > 0) ? pageNumber - 1 : 0;

        PageRequest pageRequest = PageRequest.of(adjustedPage, limit, Sort.by("id").descending());

        return postRepository.findAll(pageRequest).toList();
    }

    public Optional<Post> findPostById(Long id) {
        return postRepository.findById(id);
    }

    public void createPost(Post post) {
        postRepository.save(post);
    }

    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }

    public void updatePost(Post post) {
        Optional<Post> optStoredPost = findPostById(post.getId());
        if (optStoredPost.isEmpty()) {return;}


        postRepository.save(post);
    }
}
