package follow.controller;

import follow.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import user.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by BASA on 2017/5/1.
 */

@Controller
public class FollowController {

    @Autowired
    private FollowService followService;


    /**
     * 去关注
     * @return
     */
    @RequestMapping(value = "/follow", method = RequestMethod.POST)
    @ResponseBody
    public String follow(HttpServletRequest request, String followee) {

        String follower = ((User) request.getSession().getAttribute("user")).getUserName();

        followService.followSomeone(follower, followee);

        return "success";
    }




    @RequestMapping(value = "/follow", method = RequestMethod.GET)
    @ResponseBody
    public List<String> follow(String follower) {

        List<String> followeeList = followService.getFollowee(follower);
        return followeeList;

    }
}
