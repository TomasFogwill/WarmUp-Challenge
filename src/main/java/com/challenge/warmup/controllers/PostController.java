package com.challenge.warmup.controllers;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import com.challenge.warmup.models.Post;
import com.challenge.warmup.services.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class PostController {
    
    @Autowired
    PostService postService;

    @GetMapping
    public ArrayList<Post> getPosts() {
        return postService.getPosts();
    }

    @PostMapping
    public Post savePost(@RequestBody Post post) {

        return this.postService.savePost(post);
    }

    @GetMapping(params = "title")
    public ArrayList<Post> getPostByTitle(@RequestParam String title) {
        return this.postService.getByTitle(title);
    }

    @GetMapping(params = "category")
    public ArrayList<Post> getPostByCategory(@RequestParam String category) {
        return this.postService.getByCategory(category);
    }

    @GetMapping(params = {"title","category"})
    public ArrayList<Post> getPostByTitleAndCategory(@RequestParam String title,@RequestParam String category) {
        return this.postService.getByTitleAndCategory(title,category);
    }

    @GetMapping(path = "/:{id}")
    public Optional<Post> getPostById(@PathVariable("id") Long id) {
        return this.postService.getById(id);
    }

    @PatchMapping(path = "/:{id}")
    public String updatePostById(@PathVariable("id") Long id, @RequestBody Map<Object,Object> fields) {
        Optional<Post> post = postService.getById(id);
        if(post.isPresent()){
            fields.forEach((key,value)-> {
                Field field = ReflectionUtils.findField(Post.class, (String) key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, post.get(), value);
            });
            this.postService.savePost(post.get());
            return "Se ha actualizado el post";
        }
        return "No se ha logrado actualizar el post";
    }

    @DeleteMapping(path = "/:{id}")
    public String deleteById(@PathVariable("id") Long id) {
        boolean ok = this.postService.deletePost(id);
        if (ok) {
            return "Se elimino el post de id: " + id;
        } else {
            return "No se pudo eliminar el post de id: " + id;
        }
    }

}
