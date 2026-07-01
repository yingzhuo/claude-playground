package io.github.yingzhuo.claude.utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.time.Duration;

/**
 * 线程休眠工具类
 *
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SleepUtils {

	public static void sleep(Duration duration) {
		Assert.notNull(duration, "duration must not be null");
		try {
			Thread.sleep(duration.toMillis());
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new RuntimeException("Thread was interrupted during sleep", e);
		}
	}

	public static void sleepInMillis(long millis) {
		if (millis > 0) {
			try {
				Thread.sleep(millis);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				throw new RuntimeException("Thread was interrupted during sleep", e);
			}
		}
	}

}
