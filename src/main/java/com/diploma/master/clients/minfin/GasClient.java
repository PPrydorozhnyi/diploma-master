package com.diploma.master.clients.minfin;

import com.diploma.master.models.entities.PriceMap;
import com.diploma.master.models.enums.PriceType;
import com.diploma.master.models.exceptions.ResourceNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GasClient {

  @Value("${minfin.gas}")
  private String gasURL;

  public PriceMap getGasPricesForRegions() {

    Document doc;

    try {
      doc = Jsoup.connect(gasURL).get();
    } catch (IOException e) {
      log.error("Cannot get data from minfin service", e);
      throw new ResourceNotFoundException("Gas info from minfin was not found");
    }

    Map<String, Float> map = doc.getElementsByClass("grid")
        .first().children().get(1)
        .children().stream()
        .skip(2)
        .collect(
            Collectors.toUnmodifiableMap(
                element -> element.children().get(1).text(),
                element -> {
                  String delivery = element.children().get(3).text();
                  return Float.parseFloat(element.children().get(2).text().replace(',', '.'))
                      + (delivery.isBlank() ? 0f : Float.parseFloat(delivery.replace(',', '.')));
                },
                (existing, replacement) -> existing > replacement ? existing : replacement
            )
        );

    return new PriceMap(PriceType.GAS, map);
  }
}
