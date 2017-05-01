package blog.controller;

import blog.service.BlogService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by BASA on 2017/5/1.
 */

@Controller
public class LikeController {

    @Autowired
    private BlogService blogService;


    private static Gson gson = new Gson();
    private static final Type hashSetType =  new TypeToken<HashSet<Integer>>(){}.getType();

    @RequestMapping("/like/{blogId}")
    @ResponseBody
    public String likeBlog(@PathVariable("blogId") int blogId,
                           @CookieValue(value = "likesSet",required = false)String likesSetStr,
                           HttpServletResponse response) {


        Set<Integer> likesSet = gson.fromJson(likesSetStr, hashSetType);

        if (likesSet == null)
            likesSet = new HashSet<Integer>();

        if (likesSet.contains(blogId))
            return "only once";

        blogService.likeBlog(blogId);

        likesSet.add(blogId);
        Cookie cookie = new Cookie("likesSet", gson.toJson(likesSet));
        cookie.setMaxAge(300000);
        response.addCookie(cookie);

        return "success";
    }

}
