package by.clevertec.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CakeRequest(String name, BigDecimal price) {
}
