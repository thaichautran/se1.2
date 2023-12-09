package com.memorise.memorise_backend.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary getCloudinary(){
        Map<String, String> config = new HashMap();
        config.put("cloud_name", "dn0sqkeva");
        config.put("api_key", "177233919879689");
        config.put("api_secret", "XH4yBC2Z2jLJxZAkoOYxgG9Yr3s");
//        config.put("secure", true);
        return new Cloudinary(config);
    }
}



