package by.clevertec.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record CakeResponse(UUID id, String name, BigDecimal price) {
}
