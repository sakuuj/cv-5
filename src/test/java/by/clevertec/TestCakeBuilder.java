package by.clevertec;

import by.clevertec.dto.CakeRequest;
import by.clevertec.dto.CakeResponse;
import by.clevertec.entity.CakeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

import java.math.BigDecimal;
import java.util.UUID;

@With
@Getter
@NoArgsConstructor(staticName = "newInstance")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TestCakeBuilder {

    private UUID id = UUID.fromString("ecc4ac5d-f033-408c-ac17-c47ab3feb26d");
    private String name = "uber cake";
    private BigDecimal price = BigDecimal.valueOf(1225, 2);

    public CakeEntity build() {

        return CakeEntity.builder()
                .id(id)
                .name(name)
                .price(price)
                .build();
    }

    public CakeRequest buildRequest() {

        return CakeRequest.builder()
                .name(name)
                .price(price)
                .build();
    }

    public CakeResponse buildResponse() {

        return CakeResponse.builder()
                .id(id)
                .name(name)
                .price(price)
                .build();
    }

}
