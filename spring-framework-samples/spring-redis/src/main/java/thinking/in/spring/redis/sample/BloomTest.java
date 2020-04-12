package thinking.in.spring.redis.sample;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.SetArgs;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.codec.RedisCodec;
import io.lettuce.core.codec.StringCodec;
import io.lettuce.core.output.StatusOutput;
import io.lettuce.core.protocol.Command;
import io.lettuce.core.protocol.CommandArgs;
import io.lettuce.core.protocol.CommandType;

public class BloomTest {
	public static void main(String[] args) {
		
		RedisURI uri = RedisURI.builder().withHost("localhost").withPort(6379).build();
		RedisClient client = RedisClient.create(uri);
		
		StatefulRedisConnection<String, String> connection = client.connect();
		RedisCommands<String, String> redisCommands = connection.sync();
		
		SetArgs setArgs = SetArgs.Builder.nx().ex(5);
		String result = redisCommands.set("name", "throwable", setArgs);
		System.out.println(result);
		
		result = redisCommands.get("name");
		System.out.println(result);
		
		String ping = redisCommands.ping();
		System.out.println(ping);
		
		
//		for (int i = 0; i < 2; i++) {
//			redisCommands.sadd("codehole", "user" + i);
//			System.out.println(redisCommands.exists("codehole"));
//		}
		RedisCodec<String, String> codec = StringCodec.UTF8;
//		redisCommands.dispatch("BF.ADD", new StatusOutput<String, String>(codec), 
//				new CommandArgs<>(codec).addKey("name").add(5).addValue("throwable"));

		
//		for (int i = 0; i < 10000; i++) {
//			
//		}
		
		connection.close();
		client.shutdown();
	}
}
