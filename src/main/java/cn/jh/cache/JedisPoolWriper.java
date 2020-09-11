package cn.jh.cache;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Program: o2o
 * @ClassName: JedisPoolWriper
 * @Author: JH
 * @Date: 2020-08-10 14:40
 * @Description: JedisPoolWriper
 */
public class JedisPoolWriper {
    /*reids连接池对象*/
    private JedisPool jedisPool;

    public JedisPoolWriper(final JedisPoolConfig poolConfig,final String host,final int port) {
        try {
            jedisPool=new JedisPool(poolConfig,host,port);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /*连接池get set 方法*/
    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }
}
