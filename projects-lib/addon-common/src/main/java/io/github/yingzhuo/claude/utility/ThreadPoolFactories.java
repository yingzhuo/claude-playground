/*
 * Copyright 2026-2026 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
