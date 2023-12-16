package dev.fneira.pruebabci.client.offers;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "offer", url = "${mock-server.url}")
public interface OfferClient {

  @RequestMapping(
      method = RequestMethod.POST,
      value = "/offers/{email}?importance={importance}&urgent={urgent}",
      consumes = "application/json")
  OfferClientResponse getOffersByEmail(
      @PathVariable("email") final String email,
      @PathVariable(value = "importance") final String importance,
      @PathVariable(value = "urgent") final String urgent);

  @RequestMapping(
      method = RequestMethod.GET,
      value = "/request-offer/{offer_id}/{email}",
      consumes = "application/json")
  RequestOfferClientResponse requestOfferId(
      @PathVariable("offer_id") final String offerId, @PathVariable("email") final String email);

  @RequestMapping(method = RequestMethod.POST, value = "/take-offer", consumes = "application/json")
  TakeOfferClientResponse takeOffer(final TakeOfferClientRequest request);
}
