package ru.home.main.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.home.main.model.Post;
import ru.home.main.model.PostModerationStatus;
import ru.home.main.model.PostRepository;

import java.util.*;

@RestController
public class ApiPostController
{
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/api/post")
    public List<Post> list(@RequestParam int offset, @RequestParam int limit, @RequestParam String mode)
    {
        Iterable<Post> postIterable = postRepository.findAll();

        List<Post> list = new ArrayList<>();

        for(Post post: postIterable)
        {
            if(post.isActive() && post.getStatus() == PostModerationStatus.ACCEPTED && post.getTime().before(new Date()))
            {
                list.add(post);
            }
        }

        switch (mode){
            case ("recent"):
                list.sort(Comparator.comparing(Post::getTime));
                break;
            case ("popular"):

                break;
            case ("best"):
                break;
            case ("early"):
                break;
        }

        return list;
    }
}
