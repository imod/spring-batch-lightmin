package org.tuxdevelop.spring.batch.lightmin.server.annotation;

import org.springframework.context.annotation.Import;
import org.tuxdevelop.spring.batch.lightmin.server.configuration.LightminServerCoreConfiguration;

import java.lang.annotation.*;

/**
 * @author Marcel Becker
 * @version 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(value = {LightminServerCoreConfiguration.class})
public @interface EnableLightminServerCore {
}
