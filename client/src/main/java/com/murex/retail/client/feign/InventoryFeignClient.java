package com.murex.retail.client.feign;

import com.murex.retail.model.component.ComputerComponent;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

@FeignClient(value = "inventory-service")
@Headers("content-type: application/json")
public interface InventoryFeignClient {
    @RequestLine("GET /")
    List<ComputerComponent> fetchAll();

    @RequestLine("GET /operations/compute_average_price")
    Double computeAveragePrice();

    @RequestLine("GET /quantity")
    int countItems();

    @RequestLine("GET /fetch/{name}")
    ComputerComponent fetchComponentByName(@Param(value = "name") String name);
}
