package comment.controller;

import comment.entity.Comment;
import comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import user.entity.User;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by BASA on 2017/5/1.
 */

@Controller
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService service;



    /**
     * 根据blogId获取post列表
     * @param blogId
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/comments/{blogId}")
    public List<Comment> comments(@PathVariable("blogId") int blogId) {

        return service.getComments(blogId);
    }


    /**
     * 添加一条post
     * @param blogId
     * @param comment
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/comment/{blogId}")
    public String comment(HttpSession session, @PathVariable("blogId")int blogId, Comment comment) {

        if ( session.getAttribute("user") == null )
            return "fail";

        /*
         * 设置发帖人和发帖时间
         */
        User user = (User) session.getAttribute("user");
        comment.setUserName( user.getUserName() );
        comment.setTime( new Timestamp((new Date()).getTime()) );
        comment.setBlogId(blogId);

        service.addComment( comment );

        return "success";

    }

}
