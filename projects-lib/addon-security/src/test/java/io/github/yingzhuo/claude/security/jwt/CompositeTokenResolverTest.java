package io.github.yingzhuo.claude.security.jwt;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompositeTokenResolverTest {

    @Mock
    private TokenResolver delegateA;

    @Mock
    private TokenResolver delegateB;

    @Mock
    private WebRequest request;

    // ---- resolve ----

    @Test
    void should_return_null_when_all_delegates_return_null() {
        var resolver = new CompositeTokenResolver(List.of(delegateA, delegateB));
        var token = resolver.resolve(request);

        assertThat(token).isNull();
    }

    @Test
    void should_return_token_from_first_delegate_when_it_returns_non_null() {
        when(delegateA.resolve(request)).thenReturn("token-abc");

        var resolver = new CompositeTokenResolver(List.of(delegateA, delegateB));
        var token = resolver.resolve(request);

        assertThat(token).isEqualTo("token-abc");
        verifyNoInteractions(delegateB);
    }

    @Test
    void should_return_token_from_second_delegate_when_first_returns_null() {
        when(delegateA.resolve(request)).thenReturn(null);
        when(delegateB.resolve(request)).thenReturn("token-xyz");

        var resolver = new CompositeTokenResolver(List.of(delegateA, delegateB));
        var token = resolver.resolve(request);

        assertThat(token).isEqualTo("token-xyz");
    }

    @Test
    void should_short_circuit_when_first_delegate_returns_token() {
        when(delegateA.resolve(request)).thenReturn("token-123");

        var resolver = new CompositeTokenResolver(List.of(delegateA, delegateB));
        resolver.resolve(request);

        verify(delegateA).resolve(request);
        verifyNoInteractions(delegateB);
    }

    // ---- of() factory ----

    @Test
    void of_should_return_noop_resolver_when_no_args() {
        var resolver = CompositeTokenResolver.of();

        var token = resolver.resolve(request);
        assertThat(token).isNull();
    }

    @Test
    void of_should_return_same_instance_when_single_arg() {
        var resolver = CompositeTokenResolver.of(delegateA);

        assertThat(resolver).isSameAs(delegateA);
    }

    @Test
    void of_should_wrap_in_composite_when_multiple_args() {
        when(delegateA.resolve(request)).thenReturn("token-first");

        var resolver = CompositeTokenResolver.of(delegateA, delegateB);
        var token = resolver.resolve(request);

        assertThat(token).isEqualTo("token-first");
        assertThat(resolver).isInstanceOf(CompositeTokenResolver.class);
    }

    // ---- constructor validation ----

    @Test
    void should_throw_when_delegates_list_is_empty() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new CompositeTokenResolver(List.of()));
    }

    @Test
    void should_throw_when_delegates_list_contains_null() {
        // List.of() rejects null elements before Assert validation
        assertThatNullPointerException()
                .isThrownBy(() -> new CompositeTokenResolver(List.of(delegateA, null)));
    }

    @Test
    void should_throw_when_delegates_varargs_contains_null() {
        // List.of() rejects null elements before Assert validation
        assertThatNullPointerException()
                .isThrownBy(() -> new CompositeTokenResolver(delegateA, null));
    }

    // ---- immutability ----

    @Test
    void should_not_be_affected_by_external_list_modification_after_construction() {
        var mutableList = new java.util.ArrayList<>(List.of(delegateA));
        var resolver = new CompositeTokenResolver(mutableList);

        mutableList.clear();

        var request = org.mockito.Mockito.mock(WebRequest.class);
        when(delegateA.resolve(request)).thenReturn("token");

        assertThat(resolver.resolve(request)).isEqualTo("token");
    }

}
