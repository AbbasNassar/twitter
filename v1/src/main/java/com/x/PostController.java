package com.x;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.google.inject.Inject;
import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.loader.ClasspathLoader;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

import io.javalin.Javalin;
import io.javalin.http.Context;

public class PostController {

    private static PostService postService;

    PebbleEngine engine = new PebbleEngine.Builder().loader(new ClasspathLoader()).build();

    @Inject
    public PostController(PostService postService) {
        this.postService = postService;
    }

    public void registerRoutes(Javalin app) {
        app.get("/fetchPosts", this::fetchAllPosts);
        app.post("/post/create", this::createPost);
    }

    private void fetchAllPosts(Context ctx) throws IOException {

        List<Post> allPosts = postService.getAllPosts();
        List<Post> sortedAllPosts = allPosts.stream()
                .sorted((e1, e2) -> e2.getCreatedAt().compareTo(e1.getCreatedAt()))
                .collect(Collectors.toList());
        if (allPosts != null) {
            Post firstChild = sortedAllPosts.get(0);
            String name = UserController.getUserName(firstChild.getUserId());
            PebbleTemplate compiledTemplate = engine.getTemplate("templates/pebble/post.peb");
            Writer writer = new StringWriter();
            HashMap<String, Object> context = new HashMap<>();
            context.put("name", name);
            context.put("username", name + "Xo");
            context.put("createDate", firstChild.getCreatedAt().toString());
            context.put("textContent", firstChild.getContent());
            compiledTemplate.evaluate(writer, context);
            String output = writer.toString();
            if (allPosts.size() > 1){
                for (int i =1; i<sortedAllPosts.size(); i++){
                    Post p = sortedAllPosts.get(i);
                    String nameUser = UserController.getUserName(p.getUserId());
                    context.put("name", nameUser);
                    context.put("username", nameUser + "Xo");
                    context.put("createDate", p.getCreatedAt().toString());
                    context.put("textContent", p.getContent());
                    compiledTemplate.evaluate(writer, context);
                    output = writer.toString();
                }
            }
            ctx.result(output);
        }

    }
    private void createPost(Context ctx) throws IOException{
        String postContent = ctx.formParam("postContent");
        String userEmail = ctx.formParam("userEmail");
        System.out.println( postContent + " " + userEmail);
        if (postContent != null){
            if (postContent.isEmpty() != true){
                int userId = UserController.getUserId(userEmail);
                LocalDateTime createTime = LocalDateTime.now();
                Post post = new Post(0, userId, postContent, createTime,createTime);
                postService.addPost(post);
            }
        }
        fetchAllPosts(ctx);
        
    }
}
