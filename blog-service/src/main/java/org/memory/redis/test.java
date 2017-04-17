package org.memory.redis;

import redis.clients.jedis.Jedis;

/**
 * @author 高灶顺
 * @date 2017/2/25
 */
public class test {
    public static void main(String[] args){
        Jedis jedis = new Jedis("localhost");
        jedis.set("foo", "bar");
        String value = jedis.get("foo");
        System.out.println(value);
    }
}
