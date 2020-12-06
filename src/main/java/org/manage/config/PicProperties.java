package org.manage.config;


/**
 * @author: Diego
 * @date: 2020/8/1 10:35
 * @Des:
 */
//@Configuration
//@ConfigurationProperties(prefix = "pic", ignoreUnknownFields = false)
//@PropertySource({"classpath:pic.properties"})
//@Component
public class PicProperties {
    private String a;
    private String b;

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }
}
