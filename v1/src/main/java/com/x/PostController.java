package com.x;

import com.google.inject.Inject;
import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.loader.ClasspathLoader;

public class PostController {
        private final PostService postService;
        PebbleEngine engine = new PebbleEngine.Builder().loader(new ClasspathLoader()).build();

    @Inject
    public PostController(PostService postService) {
        this.postService = postService;
    }
}
