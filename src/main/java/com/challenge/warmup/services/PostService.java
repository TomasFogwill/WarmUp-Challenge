package com.challenge.warmup.services;

import java.util.ArrayList;
import java.util.Optional;

import com.challenge.warmup.models.Post;
import com.challenge.warmup.repositories.PostRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    public ArrayList<Post> getPosts() {
        return (ArrayList<Post>) postRepository.findAll();
    }

    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    public ArrayList<Post> getByTitle(String title) {
        return postRepository.findByTitle(title);
    }

    public ArrayList<Post> getByCategory(String category) {
        return postRepository.findByCategory(category);
    }

    public ArrayList<Post> getByTitleAndCategory(String title, String category) {
        return postRepository.findByTitleAndCategory(title, category);
    }

    public Optional<Post> getById(Long id) {
        return postRepository.findById(id);
    }

    public boolean deletePost(Long id) {
        try {
            postRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}