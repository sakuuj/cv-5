package by.clevertec.entity;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class CakeEntity {

    private UUID id;
    private String name;
    private BigDecimal price;
}
