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

package io.github.yingzhuo.claude.security.jwt;

import com.auth0.jwt.algorithms.Algorithm;
import io.github.yingzhuo.claude.utility.PemUtils;
import lombok.Builder;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * @author 应卓
 * @see AlgorithmProvider
 */
@Builder
public class RSA256AlgorithmProvider implements AlgorithmProvider, InitializingBean {

	private String pemLocation;
	private @Nullable String keyPassword;

	@Override
	public Algorithm get() {
		return Algorithm.RSA256(
			PemUtils.getPublicKeyFromCertificate(pemLocation),
			PemUtils.getPrivateKey(pemLocation, keyPassword)
		);
	}

	@Override
	public void afterPropertiesSet() {
		Assert.notNull(pemLocation, "cert pem location is required");
	}

}
