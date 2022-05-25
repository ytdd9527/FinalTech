package io.taraxacum.common.annotation;

import java.lang.annotation.*;

/**
 * An annotation to notify programmer it's translatable.
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.PACKAGE, ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.LOCAL_VARIABLE})
public @interface Translate {

}
