package dev.fneira.pruebabci.controller;

import dev.fneira.pruebabci.constant.Importance;
import dev.fneira.pruebabci.constant.Urgency;
import dev.fneira.pruebabci.dto.GenericResponseDto;
import dev.fneira.pruebabci.dto.OfferDto;
import dev.fneira.pruebabci.dto.RequestOfertaRequestDto;
import dev.fneira.pruebabci.service.offers.OfferService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador para las ofertas
 */
@RestController
@RequestMapping("ofertas")
@RequiredArgsConstructor
public class OffersController {

  /**
   * Servicio para las ofertas
   */
  private final OfferService offerService;

  /**
   * Obtener todas las ofertas para un cliente
   * @param importance importancia
   * @param urgent urgencia
   * @return Lista de ofertas
   */
  @Operation(summary = "Obtener todas las ofertas para un cliente")
  @GetMapping
  public List<OfferDto> getAllClientOffers(
      @RequestParam(name = "importancia", required = false) Importance importance,
      @RequestParam(name = "urgencia", required = false) Urgency urgent) {
    return offerService.getOffers(importance, urgent);
  }

  /**
   * Obtener ofertas por categoria
   * @param importance importancia
   * @param urgent urgencia
   * @param category categoria
   * @return Lista de ofertas
   */
  @Operation(summary = "Obtener ofertas por categoria")
  @GetMapping("/{category}")
  public List<OfferDto> getOffersByCategory(
      @RequestParam(name = "importancia", required = false) Importance importance,
      @RequestParam(name = "urgencia", required = false) Urgency urgent,
      @PathVariable("category") String category) {
    return offerService.getOffers(importance, urgent, category);
  }

    /**
     * Tomar una oferta
     * @param requestOfertaRequestDto Datos de la solicitud
     * @return Datos de la respuesta
     */
  @Operation(summary = "Tomar una oferta")
  @PostMapping
  public GenericResponseDto requestOffer(
      @RequestBody RequestOfertaRequestDto requestOfertaRequestDto) {
    return offerService.takeOffer(requestOfertaRequestDto);
  }
}
