package hello.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("hello")
public class HelloProperties {

    /**
     * Prefix of the welcome message. A space is added before the prefix and
     * the actual message.
     */
    private String prefix;

    /**
     * Suffix of the welcome message.
     */
    private String suffix = "!";

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(final String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public void setSuffix(final String suffix) {
        this.suffix = suffix;
    }

}
