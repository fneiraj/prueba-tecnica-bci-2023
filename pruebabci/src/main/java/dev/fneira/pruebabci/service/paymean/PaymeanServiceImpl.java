package dev.fneira.pruebabci.service.paymean;

import dev.fneira.pruebabci.client.paymeans.PaymeansClient;
import dev.fneira.pruebabci.client.paymeans.PaymeansClientResponse;
import dev.fneira.pruebabci.exception.GenericException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/** Implementación de servicio de medios de pago */
@Service
@AllArgsConstructor
public class PaymeanServiceImpl implements PaymeanService {

  /** Cliente de medios de pago */
  private final PaymeansClient paymeansClient;

  /**
   * Obtiene un medio de pago por id
   *
   * @param paymeanId
   * @return
   */
  @Override
  public PaymeansClientResponse.Paymean getPaymentMeans(final int paymeanId) {
    final PaymeansClientResponse paymeansClientResponse =
        paymeansClient.getPaymeansByEmail(
            SecurityContextHolder.getContext().getAuthentication().getName());

    final Optional<PaymeansClientResponse.Paymean> paymeanFinded =
        paymeansClientResponse.getPaymeans().stream()
            .filter(paymean -> paymean.getId() == paymeanId)
            .findFirst();

    if (paymeanFinded.isEmpty()) {
      throw new GenericException(String.format("El medio de pago n° %s no existe", paymeanId));
    }

    return paymeanFinded.get();
  }
}
