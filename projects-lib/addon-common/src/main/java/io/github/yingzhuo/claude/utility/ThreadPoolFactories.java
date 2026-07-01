package io.github.yingzhuo.claude.utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.time.Duration;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ThreadPoolFactories {

	public static ExecutorService newPool(int corePoolSize, int maximumPoolSize, Duration keepAliveTime, int queueCapacity) {
		Assert.isTrue(maximumPoolSize > 0, "maximumPoolSize must be positive");
		Assert.isTrue(corePoolSize > 0, "corePoolSize must be positive");
		Assert.isTrue(queueCapacity > 0, "queueCapacity must be positive");
		Assert.notNull(keepAliveTime, "keepAliveTime must not be null");
		return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime.toNanos(), TimeUnit.NANOSECONDS, new ArrayBlockingQueue<>(queueCapacity));
	}

}
