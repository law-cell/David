package com.murex.retail.client.builder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.murex.retail.client.feign.InventoryFeignClient;
import feign.Feign;
import feign.Logger.Level;
import feign.httpclient.ApacheHttpClient;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;

public class InventoryFeignClientBuilder {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .enable(SerializationFeature.INDENT_OUTPUT);

    private InventoryFeignClientBuilder() {
    }

    public static InventoryFeignClient buildFeignClient(String url, Class<InventoryFeignClient> feignClientClass) {
        return Feign.builder()
                .client(new ApacheHttpClient())
                .encoder(new JacksonEncoder(OBJECT_MAPPER))
                .decoder(new ResponseEntityDecoder(new JacksonDecoder(OBJECT_MAPPER)))
                .logLevel(getLoggerLevel())
                .target(feignClientClass, url);
    }

    private static Level getLoggerLevel() {
        return Level.BASIC;
    }
}
