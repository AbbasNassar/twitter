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

    private final PostService postService;
    private final UserService userService;
    private final ReactionService reactionService;

    PebbleEngine engine = new PebbleEngine.Builder().loader(new ClasspathLoader()).build();

    @Inject
    public PostController(PostService postService, UserService userService, ReactionService reactionService) {
        this.postService = postService;
        this.userService = userService;
        this.reactionService = reactionService;

    }

    public void registerRoutes(Javalin app) {
        app.get("/fetchPosts", this::fetchAllPosts);
        app.post("/post/create", this::createPost);
        app.get("/fetchUserPosts", this::fetchUserPosts);
        app.post("repost", this::repostPost);
    }

    private void repostPost(Context ctx){
        String id = ctx.formParam("postId");
        String viewerEmail = ctx.formParam("userEmail"); 
        int viewerId = userService.getUserId(viewerEmail);
        int idInt = Integer.parseInt(id);
        Post originPost = postService.getPost(idInt);
        if (viewerId != originPost.getUserId()){
            Post referencePost = new Post(0, viewerId, originPost.getContent(), originPost.getCreatedAt(), originPost.getUpdatedAt(), originPost.getUserId());
            postService.addPost(referencePost);
            
        }
    }
    private void fetchAllPosts(Context ctx) throws IOException {

        List<Post> allPosts = postService.getAllPosts();
        List<Post> sortedAllPosts = allPosts.stream()
                .sorted((e1, e2) -> e2.getCreatedAt().compareTo(e1.getCreatedAt()))
                .collect(Collectors.toList());
        if (sortedAllPosts.isEmpty() != true) {
            Post firstChild = sortedAllPosts.get(0);
            String name = userService.getUserName(firstChild.getUserId());
            String username = userService.getUserUsername(firstChild.getUserId());
            Reaction reaction = reactionService.getPostReaction(firstChild.getId());
            PebbleTemplate compiledTemplate = engine.getTemplate("templates/pebble/post.peb");
            Writer writer = new StringWriter();
            HashMap<String, Object> context = new HashMap<>();
            if (firstChild.getRetweetId() == -1){
                context.put("postId",firstChild.getId());
                context.put("userId", firstChild.getUserId());
                context.put("name", name);
                context.put("username", username);
                context.put("createDate", firstChild.getCreatedAt().toString());
                context.put("textContent", firstChild.getContent());
                context.put("likes", reaction.getLikes());
                context.put("comments", reaction.getComments());
                context.put("retweets", reaction.getRetweets());
                context.put("views", reaction.getViews());
                compiledTemplate.evaluate(writer, context);
                
            }
            String output = writer.toString();
            if (allPosts.size() > 1){
                for (int i =1; i<sortedAllPosts.size(); i++){
                    Post p = sortedAllPosts.get(i);
                    Reaction react = reactionService.getPostReaction(p.getId());
                    if (p.getRetweetId() == -1){
                        String nameUser = userService.getUserName(p.getUserId());
                        String usernameUser = userService.getUserUsername(p.getUserId());
                        context.put("postId",p.getId());
                        context.put("userId", p.getUserId());
                        context.put("name", nameUser);
                        context.put("username", usernameUser);
                        context.put("createDate", p.getCreatedAt().toString());
                        context.put("textContent", p.getContent());
                        context.put("likes", react.getLikes());
                        context.put("comments", react.getComments());
                        context.put("retweets", react.getRetweets());
                        context.put("views", react.getViews());
                        compiledTemplate.evaluate(writer, context);
                        output = writer.toString();
                    }
                }
            }
            ctx.result(output);
        }

    }
    private void createPost(Context ctx) throws IOException{
        String postContent = ctx.formParam("postContent");
        String userEmail = ctx.formParam("userEmail");
        if (postContent != null){
            if (postContent.isEmpty() != true){
                int userId = userService.getUserId(userEmail);
                LocalDateTime createTime = LocalDateTime.now();
                Post post = new Post(0, userId, postContent, createTime,createTime, -1);
                postService.addPost(post);
            }
        }
        fetchAllPosts(ctx);
        
    }
    private void fetchUserPosts(Context ctx)throws IOException{
    String userEmail = ctx.queryParam("userEmail");
    int id = userService.getUserId(userEmail);
    List <Post> userPosts = postService.getUserPosts(id);
    List<Post> sortedUserPosts = userPosts.stream()
    .sorted((e1, e2) -> e2.getCreatedAt().compareTo(e1.getCreatedAt()))
    .collect(Collectors.toList());
    if (sortedUserPosts.isEmpty() != true){
        PebbleTemplate compiledTemplate = engine.getTemplate("templates/pebble/post.peb");
        Writer writer = new StringWriter();
        HashMap<String, Object> context = new HashMap<>();
        String output = "";
        for (Post p : sortedUserPosts){
            String name;
            String username;
            Reaction react = reactionService.getPostReaction(p.getId());
            if (p.getRetweetId() == -1){
                name = userService.getUserName(p.getUserId());
                username = userService.getUserUsername(p.getUserId());
            }
            else{
                name = userService.getUserName(p.getRetweetId());
                username = userService.getUserUsername(p.getUserId());
            }
            context.put("name", name);
            context.put("username", username);
            context.put("createDate", p.getCreatedAt().toString());
            context.put("textContent", p.getContent());
            context.put("likes", react.getLikes());
            context.put("comments", react.getComments());
            context.put("retweets", react.getRetweets());
            context.put("views", react.getViews());
            compiledTemplate.evaluate(writer, context);
            output = writer.toString();
        }
        ctx.result(output);
    }    
    }
}
