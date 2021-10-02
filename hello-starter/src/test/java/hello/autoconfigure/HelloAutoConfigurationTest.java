package hello.autoconfigure;

import hello.ConsoleHelloService;
import hello.HelloService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(OutputCaptureExtension.class)
class HelloAutoConfigurationTest {

    private ConfigurableApplicationContext context;

    @AfterEach
    public void closeContext() {
        if (this.context != null) {
            this.context.close();
        }
    }

    @Test
    void defaultServiceIsAutoConfigured(final CapturedOutput capturedOutput) {
        this.load(EmptyConfiguration.class, "hello.prefix=Test", "hello.suffix=*");
        final HelloService service = this.context.getBean(HelloService.class);
        service.sayHello("World");
        assertTrue(capturedOutput.getOut().contains("Test World*"));
    }

    @Test
    void defaultServiceBacksOff(final CapturedOutput capturedOutput) {
        this.load(UserConfiguration.class);
        final HelloService bean = this.context.getBean(HelloService.class);
        bean.sayHello("Works");
        assertTrue(capturedOutput.getOut().contains("Mine Works*"));
    }

    private void load(final Class<?> config, final String... environment) {
        final AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext();
        ctx.register(config);
        TestPropertyValues.of(environment).applyTo(ctx);
        ctx.refresh();
        this.context = ctx;
    }

    @Configuration
    @ImportAutoConfiguration(HelloAutoConfiguration.class)
    static class EmptyConfiguration {

    }

    @Configuration
    @Import(EmptyConfiguration.class)
    static class UserConfiguration {

        @Bean
        public HelloService myHelloService() {
            return new ConsoleHelloService("Mine", "*");
        }
    }

}