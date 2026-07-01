package io.github.yingzhuo.claude.utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.time.Duration;

/**
 * 线程休眠工具类
 *
 * @author 应卓
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SleepUtils {

	/**
	 * 休眠指定时长
	 *
	 * @param duration 休眠时长，不能为 {@code null}
	 */
	public static void sleep(Duration duration) {
		Assert.notNull(duration, "duration must not be null");
		try {
			Thread.sleep(duration.toMillis());
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new RuntimeException("Thread was interrupted during sleep", e);
		}
	}

	/**
	 * 休眠指定毫秒数
	 *
	 * @param millis 休眠毫秒数，小于等于 0 时不休眠
	 */
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
