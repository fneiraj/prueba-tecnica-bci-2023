package dev.fneira.pruebabci.service.paymean;

import dev.fneira.pruebabci.client.paymeans.PaymeansClientResponse;

public interface PaymeanService {

    PaymeansClientResponse.Paymean getPaymentMeans(final int paymeanId);

}
