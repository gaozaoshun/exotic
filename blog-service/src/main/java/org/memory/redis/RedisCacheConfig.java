package org.memory.redis;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

import java.lang.reflect.Method;

/**
 * Redis内存服务器配置  【Java配置版】
 *
 * @author GZS
 */

@Configuration
@EnableCaching
public class RedisCacheConfig extends CachingConfigurerSupport {
	/**
	 * Redis连接池
	 * @return
	 */
	@Bean
	public JedisPoolConfig jedisPoolConfig(){
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxIdle(300);
		jedisPoolConfig.setMaxTotal(300);
		jedisPoolConfig.setMaxWaitMillis(3000);
		jedisPoolConfig.setTestOnBorrow(true);
		return jedisPoolConfig;
	}
	/**
	 * 连接工厂
	 * @return
	 */
	@Bean
	public RedisConnectionFactory redisConnectionFactory(JedisPoolConfig jc){
		JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory(jc);  
        // Defaults  
        redisConnectionFactory.setHostName("127.0.0.1");  
        redisConnectionFactory.setPort(6379);  
        return redisConnectionFactory;  
	}
	/**
	 * 交互模板
	 * @param cf
	 * @return
	 */
	@Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory cf) {  
		//存储类型
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();  
        redisTemplate.setConnectionFactory(cf);  
        return redisTemplate;  
    }
	
	/**
	 * 内存管理
	 * @param redisTemplate
	 * @return
	 */
    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {  
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);  
        //设置缓存过期时间【单位：秒】
        cacheManager.setDefaultExpiration(3000); 
        return cacheManager;  
    }  
    /**
     * 自定义Key生成策略
     * @return
     */
    @Bean
    public KeyGenerator customKeyGenerator() {  
    	/**
    	 * key -- 类名 +方法名+（参数类型+参数值）多个
    	 * 不会导致 同一方法缓存XXX-keys 下的key相同 而导致覆盖缓存
    	 */
        return new KeyGenerator() {
			/**
			 *
			 * @param o
			 * @param method
			 * @param objects 方法参数
			 * @return
			 */
            @Override  
            public Object generate(Object o, Method method, Object... objects) {  
                StringBuilder sb = new StringBuilder();  
                sb.append(o.getClass().getName());
				sb.append(method.getName());
                for (Object obj : objects) {  
                    sb.append(obj.toString());  
                }  
                return sb.toString();  
            }
        };  
    }

}
