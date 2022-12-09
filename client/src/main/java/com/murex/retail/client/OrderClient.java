package com.murex.retail.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.murex.retail.client.feign.OrderFeignClient;
import com.murex.retail.model.order.Order;
import feign.Response;
import org.springframework.http.HttpStatus;

import java.util.List;

import static com.murex.retail.client.builder.OrderFeignClientBuilder.buildFeignClient;

public class OrderClient {
    OrderFeignClient orderFeignClient;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .enable(SerializationFeature.INDENT_OUTPUT);

    public OrderClient() {
        this.orderFeignClient = buildFeignClient("http://localhost:8037/api/v1/orders", OrderFeignClient.class);
    }

    public String buildOrder(List<String> componentIds) {
        Response response = this.orderFeignClient.buildOrder(componentIds);
        if (response.status() == HttpStatus.OK.value()) {
            return response.body().toString();
        }
        throw new IllegalStateException("Bad HTTP status - " + response.status());
    }

    public Order fetchOrder(String orderId) {
        Response response = this.orderFeignClient.fetchOrder(orderId);
        if (response.status() == HttpStatus.OK.value()) {
            return extract(response.body(), Order.class);
        }
        throw new IllegalStateException("Bad HTTP status - " + response.status());
    }

    private <T> T extract(Response.Body body, Class<T> type) {
        try {
            return OBJECT_MAPPER.readValue(body.asInputStream(), type);
        } catch (Exception e) {
            throw new IllegalStateException("Unable to read object from response body", e);
        }
    }
}
