package by.clevertec.service;

import by.clevertec.dto.CakeRequest;
import by.clevertec.dto.CakeResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CakeService {

    List<CakeResponse> findAll();

    Optional<CakeResponse> findById(UUID id);

    CakeResponse create(CakeRequest request);

    CakeResponse update(UUID id, CakeRequest request);

    void deleteById(UUID id);
}
