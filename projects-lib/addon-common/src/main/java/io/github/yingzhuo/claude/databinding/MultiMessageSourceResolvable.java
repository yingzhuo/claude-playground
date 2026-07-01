package io.github.yingzhuo.claude.databinding;

import org.springframework.context.MessageSourceResolvable;

import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@FunctionalInterface
public interface MultiMessageSourceResolvable extends Iterable<MessageSourceResolvable> {

	@Override
	public Iterator<MessageSourceResolvable> iterator();

	public default Stream<MessageSourceResolvable> stream() {
		return stream(false);
	}

	public default Stream<MessageSourceResolvable> stream(boolean parallel) {
		return StreamSupport.stream(spliterator(), parallel);
	}

}
