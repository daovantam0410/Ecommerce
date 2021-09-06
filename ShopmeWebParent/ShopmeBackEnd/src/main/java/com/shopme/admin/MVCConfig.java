package com.shopme.admin;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MVCConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String dirName = "user-photos";
        //Lấy ra ảnh của user
        Path userPhotosDir = Paths.get(dirName);
        //lấy ra đường dẫn của ảnh
        String userPhotosPath = userPhotosDir.toFile().getAbsolutePath();
        registry.addResourceHandler("/" + dirName + "/**")
                .addResourceLocations("file:/" + userPhotosPath + "/");

    }
}
