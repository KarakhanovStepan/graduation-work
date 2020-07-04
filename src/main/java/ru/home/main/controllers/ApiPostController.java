package ru.home.main.controllers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.home.main.model.Post;
import ru.home.main.model.PostModerationStatus;
import ru.home.main.model.PostRepository;
import ru.home.main.model.PostVote;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@RestController
public class ApiPostController
{
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/api/post")
    public JSONObject list(@RequestParam int offset, @RequestParam int limit, @RequestParam String mode)
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
                list.sort((p1, p2) -> p1.getPostComments().size() > p2.getPostComments().size() ? 1 : 0);
                break;

            case ("best"):
                list.sort((p1, p2) -> {
                    int p1Likes = 0;

                    for (PostVote pv: p1.getPostVotes()) {
                        if(pv.isValue())
                            p1Likes++;
                    }

                    int p2Likes = 0;

                    for (PostVote pv: p2.getPostVotes()) {
                        if(pv.isValue())
                            p2Likes++;
                    }

                    return p1Likes > p2Likes ? 1 : 0;
                });
                break;

            case ("early"):
                list.sort((p1, p2) -> p1.getTime().after(p2.getTime()) ? 1 : 0);
                break;
        }

        JSONObject mainObject = new JSONObject();

        mainObject.put("count", list.size());

        JSONArray postsArray = new JSONArray();

        if(offset + limit > list.size())
            limit = list.size() - offset;

        if(limit != 0) {
            for (int i = offset; i < offset + limit; i++) {

                Post post = list.get(i);
                JSONObject postObject = new JSONObject();

                postObject.put("id", post.getId());
                postObject.put("time", post.getTime());

                JSONObject user = new JSONObject();

                user.put("id", post.getUser().getId());
                user.put("name", post.getUser().getName());

                postObject.put("user", user);

                postObject.put("title", post.getTitle());
                postObject.put("announce", post.getText());

                int likes = 0;
                int dislikes = 0;

                for (PostVote vote : post.getPostVotes()) {
                    if (vote.isValue())
                        likes++;
                    else
                        dislikes++;
                }

                postObject.put("likeCount", likes);
                postObject.put("dislikeCount", dislikes);
                postObject.put("commentCount", post.getPostComments().size());
                postObject.put("viewCount", post.getViewCount());

                postsArray.add(postObject);
            }
        }

        mainObject.put("posts", postsArray);

        return mainObject;
    }
}