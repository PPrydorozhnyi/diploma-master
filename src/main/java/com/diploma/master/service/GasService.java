package com.diploma.master.service;

import com.diploma.master.clients.minfin.GasClient;
import com.diploma.master.models.entities.PriceMap;
import com.diploma.master.models.enums.PriceType;
import com.diploma.master.repositories.PriceMapRepository;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

@Service
public class GasService {

  private final PriceMapRepository priceMapRepository;
  private final GasClient gasClient;

  public GasService(PriceMapRepository priceMapRepository,
                    GasClient gasClient) {
    this.priceMapRepository = priceMapRepository;
    this.gasClient = gasClient;
  }

  public float getGasPrice(String region) {

    final LocalDateTime now = LocalDateTime.now();

    final String currentMonthAndYear = now.getMonthValue() + "-" + now.getYear();

    final PriceMap priceMap =
        priceMapRepository.findByTypeAndCreatedDate(PriceType.GAS, currentMonthAndYear)
            .orElseGet(() -> {
              final PriceMap gasPricesForRegions = gasClient.getGasPricesForRegions();
              gasPricesForRegions.setCreatedDate(currentMonthAndYear);
              priceMapRepository.save(gasPricesForRegions);
              return gasPricesForRegions;
            });

    return priceMap.getRegionPriceMap().get(region);

  }

}
