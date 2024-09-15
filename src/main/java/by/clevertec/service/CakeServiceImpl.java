package by.clevertec.service;

import by.clevertec.dto.CakeRequest;
import by.clevertec.dto.CakeResponse;
import by.clevertec.entity.CakeEntity;
import by.clevertec.mapper.CakeMapper;
import by.clevertec.repository.CakeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CakeServiceImpl implements CakeService {

    private final CakeRepo cakeRepo;
    private final CakeMapper cakeMapper;

    @Override
    public List<CakeResponse> findAll() {

        return cakeRepo.findAll().stream()
                .map(cakeMapper::toResponse)
                .toList();
    }

    @Override
    public Optional<CakeResponse> findById(UUID id) {

        return cakeRepo.findById(id).map(cakeMapper::toResponse);
    }

    @Override
    public CakeResponse create(CakeRequest request) {

        CakeEntity entityToCreate = cakeMapper.toEntity(request);
        CakeEntity createdEntity = cakeRepo.create(entityToCreate);
        return cakeMapper.toResponse(createdEntity);
    }

    @Override
    public CakeResponse update(UUID id, CakeRequest request) {

        CakeEntity entityToUpdate = cakeMapper.toEntity(request, id);
        CakeEntity updatedEntity = cakeRepo.update(entityToUpdate);
        return cakeMapper.toResponse(updatedEntity);
    }

    @Override
    public void deleteById(UUID id) {

        cakeRepo.deleteById(id);
    }
}
