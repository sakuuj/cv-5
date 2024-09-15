package by.clevertec.repository;

import by.clevertec.entity.CakeEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CakeRepo {

    List<CakeEntity> findAll();

    Optional<CakeEntity> findById(UUID id);

    CakeEntity create(CakeEntity cake);

    CakeEntity update(CakeEntity cake);

    void deleteById(UUID id);
}
