package io.github.yingzhuo.claude.utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MyBatisUtils {

    public static String escapeLike(String s) {
        Assert.notNull(s, "s is null");
        return s.replace("/", "//")
                .replace("%", "/%")
                .replace("_", "/_");
    }

}
