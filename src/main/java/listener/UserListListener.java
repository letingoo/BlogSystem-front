package listener;


import user.entity.User;
import util.JedisUtil;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by BASA on 2017/5/19.
 */
public class UserListListener implements HttpSessionListener, HttpSessionAttributeListener {





    private static final String ONLINE_USER = "online_user";


    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        //System.out.println("created");
    }


    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

        User user = (User) httpSessionEvent.getSession().getAttribute("user");

        if (user != null)
            JedisUtil.jedis.zrem(ONLINE_USER, user.getUserName());
    }


    @Override
    public void attributeAdded(HttpSessionBindingEvent httpSessionBindingEvent) {
        if ( httpSessionBindingEvent.getName().equals("user") ) {
            //JedisUtil.jedis.sadd(ONLINE_USER, httpSessionBindingEvent.getValue().toString());
            String userName = ((User) httpSessionBindingEvent.getValue()).getUserName();
            JedisUtil.jedis.sadd(ONLINE_USER, userName);
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent httpSessionBindingEvent) {

    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent httpSessionBindingEvent) {

    }
}
