package com.challenge.warmup.repositories;



import java.util.ArrayList;

import com.challenge.warmup.models.Post;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {

public abstract ArrayList<Post> findByTitle(String title);

public abstract ArrayList<Post> findByCategory(String category);

public abstract ArrayList<Post> findByTitleAndCategory(String title, String Category);

}
