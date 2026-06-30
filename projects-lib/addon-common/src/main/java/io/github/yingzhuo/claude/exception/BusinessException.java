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

package io.github.yingzhuo.claude.exception;

/**
 * 业务异常。当 Service 层检测到业务规则冲突时抛出，
 * 由 {@code ControllerExceptionHandlers} 统一转换为对应 HTTP 响应。
 *
 * @author 应卓
 */
public class BusinessException extends RuntimeException {

    /**
     * 创建业务异常
     *
     * @param message 异常描述（将作为响应消息返回给客户端）
     */
    public BusinessException(String message) {
        super(message);
    }
}
