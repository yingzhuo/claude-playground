package io.github.yingzhuo.claude.databinding;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.validation.ObjectError;

import java.util.*;

@Deprecated(forRemoval = true)
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
