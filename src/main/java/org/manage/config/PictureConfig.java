package org.manage.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author: Diego
 * @date: 2020/9/16 15:42
 * @Des:
 */

@Component
public class PictureConfig {
    @Value("${spring.mvc.static-path-pattern}")
    private String path;

    @Value("${spring.resources.static-locations}")
    private String location;

    public String getPath() {
        return path;
    }

    public String getLocation() {
        return location;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
