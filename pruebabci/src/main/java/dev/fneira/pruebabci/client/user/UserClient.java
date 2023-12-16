package dev.fneira.pruebabci.client.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "user", url = "${mock-server.url}")
public interface UserClient {

  @RequestMapping(method = RequestMethod.POST, value = "/sign-in", consumes = "application/json")
  UserClientResponse signIn(final UserClientRequest request);

  @RequestMapping(
      method = RequestMethod.GET,
      value = "/get-user-by-email/{email}",
      consumes = "application/json")
  UserClientResponse getUserByEmail(@PathVariable("email") final String email);
}
