package blog.controller;

import blog.entity.Blog;
import blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import user.entity.User;
import util.PageParam;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by BASA on 2017/5/1.
 */

@Controller
public class BlogController {

    @Autowired
    private BlogService service;


    @RequestMapping("/toAddBlog")
    public String toAddBlog(HttpSession session) {

        if ( session.getAttribute("user") == null )
            return "redirect:/user/toLogin";
        else
            return "addBlog";
    }


    /**
     * 增加Blog
     * @param session
     * @param blog
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/blog")
    public String addBlog(HttpSession session, Blog blog) {

        if ( session.getAttribute("user") == null )
            return "fail";

        //blog.setUser( (User) session.getAttribute("user") );
        blog.setTime(  new Timestamp((new Date()).getTime())  );
        blog.setUserName( ((User) session.getAttribute("user")).getUserName() );
        service.addBlog(blog);

        return "success";
    }




    private static int PAGE_SIZE = 10;


    /**
     * 获取博客列表
     * @param pageNo
     * @return
     */
    @RequestMapping("/blogs-{pageNo}")
    public ModelAndView getBlogs(@PathVariable int pageNo) {

        ModelAndView modelAndView = new ModelAndView();
        PageParam pageParam = new PageParam(pageNo, PAGE_SIZE);
        modelAndView.addObject( "blogs", service.getBlogs(pageParam) );
        modelAndView.setViewName("blogs");

        return modelAndView;
    }



    /**
     *
     * @param blogId
     * @return
     */
    @RequestMapping("/blog/{blogId}")
    public ModelAndView getBlogDetail(@PathVariable int blogId) {

        ModelAndView modelAndView = new ModelAndView();

        Blog blog = service.getBlogDetail(blogId);
        modelAndView.addObject( "blog", service.getBlogDetail(blogId) );
        modelAndView.setViewName("blogDetail");

        return modelAndView;
    }



    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE, value = "/blog/{blogId}")
    public String deleteBlog(HttpSession session, @PathVariable int blogId) {

        if (session.getAttribute("user") == null)
            return "fail";

        User user = (User) session.getAttribute("user");

        return service.deleteBlog(blogId, user.getUserName());

    }






    /**
     * 查询一个用户所发的Blog
     * @param userName
     * @param pageNo
     * @return
     */
    @RequestMapping("/u/{userName}")
    public ModelAndView getUserBlogs(@PathVariable("userName") String userName,
                                     int pageNo) {

        ModelAndView modelAndView = new ModelAndView();

        PageParam pageParam = new PageParam(pageNo, PAGE_SIZE);
        modelAndView.addObject("blogs", service.getBlogsByUser(pageParam, userName));
        modelAndView.setViewName("blogs");

        return modelAndView;
    }

}
