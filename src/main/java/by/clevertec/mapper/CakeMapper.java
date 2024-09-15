package by.clevertec.mapper;

import by.clevertec.dto.CakeRequest;
import by.clevertec.dto.CakeResponse;
import by.clevertec.entity.CakeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CakeMapper {

    CakeResponse toResponse(CakeEntity entity);

    @Mapping(target = "id", ignore = true)
    CakeEntity toEntity(CakeRequest request);

    @Mapping(target = "id", source = "entityId")
    CakeEntity toEntity(CakeRequest request, UUID entityId);
}
