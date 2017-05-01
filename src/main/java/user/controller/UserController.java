package user.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import user.entity.User;
import user.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by BASA on 2017/5/1.
 */

@Controller
@RequestMapping("/user")
public class UserController {

    private Gson gson = new Gson();


    @Autowired
    private UserService userService;





    @RequestMapping("/toAddUser")
    public String toAddUser() {

        return "register";
    }


    @RequestMapping("/toUpdateUser")
    public String toUpdateUser() {

        return "updateUserData";
    }


    /**
     * 添加用户
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/addUser")
    public String addUser(User user) {

        userService.addUser(user);
        return "hello";
    }



    @RequestMapping("/toLogin")
    public String toLogin() {

        return "login";
    }



    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public String doLogin(HttpServletRequest request, HttpServletResponse response) {

        String userName = request.getParameter("userName");
        String password = request.getParameter("password");

        User user = userService.doLogin(userName, password);

        if ( null == user ) {
            return "fail";
        }

        else {
            request.getSession().setAttribute("user", user);
            Cookie cookie = new Cookie("user", gson.toJson(user));
            cookie.setMaxAge(6000);
            response.addCookie(cookie);

            return "success";
        }
    }


    // 存放头像的文件路径
    @Value("${PATH}")
    private String avatarPath;

    @Value("${AVATAR_PREFIX}")
    private String avatarPrefix;


    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/avatar")
    public String uploadAvatar(@RequestParam(value = "avatar", required = false)MultipartFile avatar,
                               HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user == null)
            return "not login";


        // 给图片文件打上时间戳
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String dateStr = sdf.format(new Date());

        // 获取图片文件后缀名
        String originalName = avatar.getOriginalFilename();
        String type = originalName.substring( originalName.lastIndexOf('.') );


        File targetFile = new File(avatarPath, dateStr + type);

        try {
            avatar.transferTo(targetFile);
        } catch (IOException e) {
            e.printStackTrace();
        }


        String avatarPath = avatarPrefix + dateStr;
        user.setAvatar(avatarPath);
        userService.updateUser(user);

        return "success";
    }
}
