package util;

import com.sun.prism.j2d.J2DPipeline;
import redis.clients.jedis.Jedis;

/**
 * Created by zmc on 2017/5/22.
 */
public class JedisUtil {


    public static Jedis jedis = new Jedis("114.215.159.226", 6379);

    static {
        jedis.auth("letingoo");
    }

    private JedisUtil(){

    }



}
