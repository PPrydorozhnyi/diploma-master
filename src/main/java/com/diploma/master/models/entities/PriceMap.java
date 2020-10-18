package com.diploma.master.models.entities;

import com.diploma.master.models.enums.PriceType;
import java.util.Map;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document
@CompoundIndex(name = "email_age", def = "{'type' : 1, 'createdDate': 1}")
@NoArgsConstructor
public class PriceMap {

  @Id
  @Field("_id")
  private String id;

  private PriceType type;

  private Map<String, Float> regionPriceMap;

  private String createdDate;

  public PriceMap(PriceType type, Map<String, Float> regionPriceMap) {
    this.type = type;
    this.regionPriceMap = regionPriceMap;
  }

}
