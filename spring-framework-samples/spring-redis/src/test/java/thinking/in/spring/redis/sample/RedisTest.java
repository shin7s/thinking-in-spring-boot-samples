package thinking.in.spring.redis.sample;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.print.attribute.standard.DateTimeAtCompleted;
import javax.xml.ws.Response;

import org.assertj.core.api.Assertions;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.format.datetime.standard.TemporalAccessorParser;

import com.google.common.collect.Lists;

import io.lettuce.core.LettuceFutures;
import io.lettuce.core.Range;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.RedisURI;
import io.lettuce.core.SetArgs;
import io.lettuce.core.TransactionResult;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.codec.RedisCodec;
import io.lettuce.core.codec.StringCodec;
import io.lettuce.core.dynamic.RedisCommandFactory;
import io.lettuce.core.output.StatusOutput;
import io.lettuce.core.protocol.CommandArgs;
import io.lettuce.core.protocol.CommandType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RedisTest {
	
//	@Test
//	public void testSetGet() throws Exception {
//	    RedisURI redisUri = RedisURI.builder()                    // <1> 创建单机连接的连接信息
//	            .withHost("localhost")
//	            .withPort(6379)
//	            .withTimeout(Duration.of(10, ChronoUnit.SECONDS))
//	            .build();
//	    RedisClient redisClient = RedisClient.create(redisUri);   // <2> 创建客户端
//	    StatefulRedisConnection<String, String> connection = redisClient.connect();     // <3> 创建线程安全的连接
//	    RedisCommands<String, String> redisCommands = connection.sync();                // <4> 创建同步命令
//	    SetArgs setArgs = SetArgs.Builder.nx().ex(5);
//	    String result = redisCommands.set("name", "throwable", setArgs);
//	    Assertions.assertThat(result).isEqualToIgnoringCase("OK");
//	    result = redisCommands.get("name");
//	    Assertions.assertThat(result).isEqualTo("throwable");
//	    // ... 其他操作
//	    connection.close();   // <5> 关闭连接
//	    redisClient.shutdown();  // <6> 关闭客户端
//	}
	
	private static StatefulRedisConnection<String, String> CONNECTION;
	private static RedisClient CLIENT;
	private static RedisCommands<String, String> COMMAND;
	private static RedisAsyncCommands<String, String> ASYNC_COMMAND;
	
	@BeforeClass
	public static void beforeClass() {
	    RedisURI redisUri = RedisURI.builder()
	            .withHost("localhost")
	            .withPort(6379)
	            .withTimeout(Duration.of(10, ChronoUnit.SECONDS))
	            .build();
	    CLIENT = RedisClient.create(redisUri);
	    CONNECTION = CLIENT.connect();
	    COMMAND = CONNECTION.sync();
	    ASYNC_COMMAND = CONNECTION.async();
	}

	@AfterClass
	public static void afterClass() throws Exception {
	    CONNECTION.close();
	    CLIENT.shutdown();
	}
	
	@Test
	public void testSyncPing() throws Exception {
	   String pong = COMMAND.ping();
	   Assertions.assertThat(pong).isEqualToIgnoringCase("PONG");
	}

	@Test
	public void testSyncSetAndGet() throws Exception {
	    SetArgs setArgs = SetArgs.Builder.nx().ex(5);
	    COMMAND.set("name", "throwable", setArgs);
	    String value = COMMAND.get("name");
	    log.info("Get value: {}", value);
	}
	
	@Test
	public void testSyncMulti() throws Exception {
	    COMMAND.multi();
	    COMMAND.setex("name-1", 2, "throwable");
	    COMMAND.setex("name-2", 2, "doge");
	    TransactionResult result = COMMAND.exec();
	    int index = 0;
	    for (Object r : result) {
	        log.info("Result-{}:{}", index, r);
	        index++;
	    }
	}
	
	// 自定义实现PING方法
	@Test
	public void testCustomPing() throws Exception {
	    RedisCodec<String, String> codec = StringCodec.UTF8;
	    String result = COMMAND.dispatch(CommandType.PING, new StatusOutput<>(codec));
	    log.info("PING:{}", result);
	}
	// PING:PONG
	
	// 自定义实现Set方法
	@Test
	public void testCustomSet() throws Exception {
	    RedisCodec<String, String> codec = StringCodec.UTF8;
	    COMMAND.dispatch(CommandType.SETEX, new StatusOutput<>(codec),
	            new CommandArgs<>(codec).addKey("name").add(5).addValue("throwable"));
	    String result = COMMAND.get("name");
	    log.info("Get value:{}", result);
	}

	
	@Test
	public void testBloom() throws Exception {
//	    RedisCodec<String, String> codec = StringCodec.UTF8;
	    RedisCommandFactory commandFactory = new RedisCommandFactory(CONNECTION);
	    BloomFilterCommand commands = commandFactory.getCommands(BloomFilterCommand.class);
	    COMMAND.del("codehole");
	    commands.bfReserve("codehole", "0.01", "100000");
	    int count = 0;
	    for (int i = 0; i < 100000; i++) {
			String key = "user" + i;
			commands.bfAdd("codehole", key);
			boolean ret = commands.bfExists("codehole", "user" + i + 1) > 0;
			if (ret) {
//				log.error(key);
				count++;
			}
		}
	    log.info("" + count);
	}
	
	@Test
	public void testRateLimit() throws Exception {
		int period = 5;
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					try {
						log.info(Thread.currentThread() + ":" + isActionAllowed("user0", "put", period, 5));
						TimeUnit.MILLISECONDS.sleep(1600);
					} catch (InterruptedException | ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
		
		for (int i = 0; i < 20; i++) {
			log.info("" + isActionAllowed("user0", "put", period, 5));
			TimeUnit.MILLISECONDS.sleep(1400);
		}

		
	}
	
	private boolean isActionAllowed(String userId, String actionKey, int period, int maxCount) throws InterruptedException, ExecutionException {
		
		String key = String.format("hist:%s:%s", userId, actionKey);
		ASYNC_COMMAND.setAutoFlushCommands(false);
		long nowTs = System.currentTimeMillis();
		List<RedisFuture<?>> redisFutures = Lists.newArrayList();
		
		redisFutures.add(ASYNC_COMMAND.zadd(key, nowTs * 1.0, "" + nowTs));
		redisFutures.add(ASYNC_COMMAND.zremrangebyscore(key, Range.create(0,  nowTs - period * 1000)));
		redisFutures.add(ASYNC_COMMAND.zcard(key));
//		redisFutures.add(ASYNC_COMMAND.expire(key, period + 1));
		ASYNC_COMMAND.flushCommands();
		boolean result = LettuceFutures.awaitAll(10,  TimeUnit.SECONDS, redisFutures.toArray(new RedisFuture[redisFutures.size()]));
		Long count = (Long)redisFutures.get(2).get();
		
		log.info("count:" + count + ":" + LocalDateTime.now());
		return count <= maxCount;
	}
}
