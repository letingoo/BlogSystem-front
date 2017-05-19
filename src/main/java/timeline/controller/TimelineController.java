package timeline.controller;

import blog.entity.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import timeline.service.TimelineViewService;
import user.entity.User;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by BASA on 2017/5/14.
 */

@Controller
public class TimelineController {

    @Autowired
    private TimelineViewService timelineViewService;


    @RequestMapping(method = RequestMethod.GET, value = "/timeline")
    public ModelAndView getTimeLine(int pageNo, HttpSession session) {


        User user = (User) session.getAttribute("user");
        List<Blog> blogs =  timelineViewService.getTimeline(user.getUserName(), pageNo);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("timeLine");
        modelAndView.addObject("timeLines", blogs);

        return modelAndView;
    }


}
