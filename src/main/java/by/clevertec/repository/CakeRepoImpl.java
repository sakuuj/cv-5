package by.clevertec.repository;

import by.clevertec.entity.CakeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class CakeRepoImpl implements CakeRepo {

    private final Map<UUID, CakeEntity> dataStorage;

    @Override
    public List<CakeEntity> findAll() {
        return new ArrayList<>(dataStorage.values());
    }

    @Override
    public Optional<CakeEntity> findById(UUID id) {

        return Optional.ofNullable(dataStorage.get(id));
    }

    @Override
    public CakeEntity create(CakeEntity cake) {

        UUID id = UUID.randomUUID();
        cake.setId(id);
        dataStorage.put(id, cake);

        return cake;
    }

    @Override
    public CakeEntity update(CakeEntity cake) {
        return dataStorage.compute(cake.getId(), (k, v) ->
        {
            if (v == null) {
                throw new IllegalStateException("Entity to update is not found");
            }

            return cake;
        });
    }

    @Override
    public void deleteById(UUID id) {

        dataStorage.remove(id);
    }
}
