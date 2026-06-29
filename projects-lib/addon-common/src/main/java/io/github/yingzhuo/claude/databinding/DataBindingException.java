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

package io.github.yingzhuo.claude.databinding;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.validation.ObjectError;

import java.util.*;

@RequiredArgsConstructor
public class DataBindingException extends IllegalArgumentException implements MultiMessageSourceResolvable {

	private final List<ObjectError> errors;

	@Override
	public Iterator<MessageSourceResolvable> iterator() {
		return errors.stream()
			.map(e -> (MessageSourceResolvable) e)
			.iterator();
	}

	public List<ObjectError> getErrors() {
		return Collections.unmodifiableList(errors);
	}

	public List<String> getMessages(MessageSource messageSource) {
		return getMessages(messageSource, Locale.getDefault());
	}

	public List<String> getMessages(MessageSource messageSource, Locale locale) {
		return getMessages(messageSource, locale, null);
	}

	public List<String> getMessages(MessageSource messageSource, Locale locale, @Nullable Comparator<String> ordering) {
		return stream()
			.map(msr -> messageSource.getMessage(msr, locale))
			.sorted(ordering != null ? ordering : Comparator.naturalOrder())
			.toList();
	}

}
