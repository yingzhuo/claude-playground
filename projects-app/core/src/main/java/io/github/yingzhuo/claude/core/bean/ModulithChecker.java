package io.github.yingzhuo.claude.core.bean;

import io.github.yingzhuo.claude.core.ApplicationBoot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnProperty(prefix = "claude-playground", name = "check-modules", havingValue = "true", matchIfMissing = true)
public class ModulithChecker implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) {
        ApplicationModules.of(ApplicationBoot.class).verify();
    }

}
