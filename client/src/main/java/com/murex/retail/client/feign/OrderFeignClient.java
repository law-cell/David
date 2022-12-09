package com.murex.retail.client.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

@FeignClient
@Headers("content-type: application/json")
public interface OrderFeignClient {
    @RequestLine("POST /build")
    Response buildOrder(List<String> idList);

    @RequestLine("GET /{id}")
    Response fetchOrder(@Param(value = "id") String id);
}
