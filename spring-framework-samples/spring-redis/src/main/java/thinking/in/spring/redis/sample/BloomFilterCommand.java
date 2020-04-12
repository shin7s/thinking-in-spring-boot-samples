package thinking.in.spring.redis.sample;

import java.util.List;

import io.lettuce.core.Value;
import io.lettuce.core.dynamic.Commands;
import io.lettuce.core.dynamic.annotation.Command;
import io.lettuce.core.dynamic.annotation.CommandNaming;
import io.lettuce.core.dynamic.annotation.Param;
import io.lettuce.core.output.IntegerOutput;
import io.lettuce.core.output.ValueOutput;

public interface BloomFilterCommand extends Commands {
	
	@Command("BF.ADD :key :value ")
	Long bfAdd(@Param("key") String key, @Param("value") String value);
	
	@Command("BF.EXISTS :key :value")
	Long bfExists(@Param("key") String key, @Param("value") String value);

	@Command("BF.RESERVE :key :error_rate :initial_size")
	Long bfReserve(@Param("key") String key, @Param("error_rate") String error_rate, @Param("initial_size") String initial_size);
	
    /**
     * 方法名作为命令
     */
    @CommandNaming(strategy = CommandNaming.Strategy.METHOD_NAME)
    String mSet(String key1, String value1, String key2, String value2);
}
