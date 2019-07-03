package itoken.funtl.itoken.service.redis.service;

/**
 * @Author: BernardLowe
 * @Description:
 * @Date: Created in 9:54 2019/6/18
 * @Modified By: BernardLowe
 */
public interface RedisService {
    public void set(String key, Object value, long seconds);
    public Object get(String key);
}
