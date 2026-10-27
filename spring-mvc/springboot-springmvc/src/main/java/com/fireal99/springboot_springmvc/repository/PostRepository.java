package com.fireal99.springboot_springmvc.repository;

import com.fireal99.springboot_springmvc.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
