package com.diploma.master.client.minfin;

import com.diploma.master.model.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class GasClient {

    @Value("${minfin.gas}")
    private String gasURL;

    public Map<String, Float> getGasPricesForRegions() {

        Document doc;

        try {
            doc = Jsoup.connect(gasURL).get();
        } catch (IOException e) {
            log.error("Cannot get data from minfin service", e);
            throw new ResourceNotFoundException("Gas info from minfin was not found");
        }

        return doc.getElementsByClass("grid")
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
    }
}
