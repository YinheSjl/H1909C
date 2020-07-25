package cn.jiyun;

import java.util.List;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Demo01 {

	@Test
	public void jedis() {
		// 链接Redis
		Jedis jedis = new Jedis("127.0.0.1", 6379);

		// 取得是string类型的数据
		String string = jedis.get("a");

		System.out.println(string);

		jedis.set("a", "你好呀！");

		jedis.del("a");

//		jedis.lpush("list1", strings)
		
		jedis.close();
		add();
	}

	public void add(String... names) {

	}

	@Test
	public void jedispool() {
		// 获取连接池配置对象，设置配置项
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		// 设置最大连接数
		jedisPoolConfig.setMaxTotal(30);
		// 设置最大空闲连接数
		jedisPoolConfig.setMaxIdle(10);
		// 获取连接池对象
		JedisPool jedisPool = new JedisPool(jedisPoolConfig, "127.0.0.1", 6379);

		Jedis jedis = jedisPool.getResource();
		
		List<String> lrange = jedis.lrange("list1", 0, 10);
		
		System.out.println(lrange);
		
		jedis.close();
	}

}
