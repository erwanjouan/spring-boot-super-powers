package hello.autoconfigure;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Conditional(OnValidHelloPrefixCondition.class)
public @interface ConditionalOnValidHelloPrefix {
}
