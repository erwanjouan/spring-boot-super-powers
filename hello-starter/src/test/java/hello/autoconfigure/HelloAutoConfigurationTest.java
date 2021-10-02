package hello.autoconfigure;

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
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(OutputCaptureExtension.class)
public class HelloAutoConfigurationTest {

    private ConfigurableApplicationContext context;

    @AfterEach
    public void closeContext() {
        if (this.context != null) {
            this.context.close();
        }
    }

    @Test
    public void defaultServiceIsAutoConfigured(CapturedOutput capturedOutput) {
        load(EmptyConfiguration.class);
        HelloService service = this.context.getBean(HelloService.class);
        service.sayHello("World");
        assertTrue(capturedOutput.getOut().contains("World"));
    }

    private void load(Class<?> config, String... environment) {
        AnnotationConfigApplicationContext ctx =
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


    }

}