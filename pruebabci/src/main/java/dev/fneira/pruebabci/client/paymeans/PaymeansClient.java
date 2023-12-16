package dev.fneira.pruebabci.client.paymeans;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "paymeans", url = "${mock-server.url}")
public interface PaymeansClient {

  @RequestMapping(
      method = RequestMethod.GET,
      value = "/paymeans/{email}",
      consumes = "application/json")
  PaymeansClientResponse getPaymeansByEmail(@PathVariable("email") final String email);
}
