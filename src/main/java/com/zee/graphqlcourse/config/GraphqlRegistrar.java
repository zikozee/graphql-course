package com.zee.graphqlcourse.config;

import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.context.annotation.Configuration;
import java.util.UUID;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 23 Nov, 2024
 */

//@Configuration
public class GraphqlRegistrar implements RuntimeHintsRegistrar {
    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
        hints.reflection().registerType(UUID[].class);
    }
}
