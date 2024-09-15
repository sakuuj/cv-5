package by.clevertec.repository;

import by.clevertec.TestCakeBuilder;
import by.clevertec.dto.CakeResponse;
import by.clevertec.entity.CakeEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class CakeRepoImplTest {

    @Test
    void shouldFindAll() {
        // given
        final TestCakeBuilder firstCakeBuilder = TestCakeBuilder.newInstance();
        final TestCakeBuilder secondCakeBuilder = TestCakeBuilder.newInstance()
                .withId(UUID.fromString("19c8caab-340d-4722-9414-8933d5da5dd8"))
                .withName("CAKE TWO")
                .withPrice(BigDecimal.valueOf(333, 2));

        CakeEntity firstCakeEntity = firstCakeBuilder.build();
        CakeEntity secondCakeEntity = secondCakeBuilder.build();
        List<CakeEntity> expected = List.of(firstCakeEntity, secondCakeEntity);

        CakeRepoImpl cakeRepoImpl = new CakeRepoImpl(
                new HashMap<>(
                        Map.of(
                                firstCakeEntity.getId(), firstCakeEntity,
                                secondCakeEntity.getId(), secondCakeEntity
                        )
                )
        );

        // when
        List<CakeEntity> actual = cakeRepoImpl.findAll();

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldFindById() {
        // given
        TestCakeBuilder testCakeBuilder = TestCakeBuilder.newInstance();
        CakeEntity expected = testCakeBuilder.build();

        CakeRepoImpl cakeRepoImpl = new CakeRepoImpl(
                new HashMap<>(
                        Map.of(
                                expected.getId(), expected
                        )
                )
        );

        // when
        Optional<CakeEntity> actualOptional = cakeRepoImpl.findById(expected.getId());

        // then
        assertThat(actualOptional).contains(expected);
    }

    @Test
    void shouldCreate() {
        // given
        TestCakeBuilder testCakeBuilder = TestCakeBuilder.newInstance();
        CakeEntity entityToCreate = testCakeBuilder
                .withId(null)
                .build();

        CakeRepoImpl cakeRepoImpl = new CakeRepoImpl(new HashMap<>());

        // when
        cakeRepoImpl.create(entityToCreate);
        Optional<CakeEntity> actualOptional = cakeRepoImpl.findById(entityToCreate.getId());

        // then
        assertThat(actualOptional).isPresent();

        CakeEntity actual = actualOptional.get();
        assertThat(actual.getId()).isNotNull();
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(entityToCreate);
    }

    @Test
    void shouldDeleteById() {
        // given
        TestCakeBuilder testCakeBuilder = TestCakeBuilder.newInstance();
        CakeEntity expected = testCakeBuilder.build();

        CakeRepoImpl cakeRepoImpl = new CakeRepoImpl(
                new HashMap<>(
                        Map.of(
                                expected.getId(), expected
                        )
                )
        );

        // when
        cakeRepoImpl.deleteById(expected.getId());
        Optional<CakeEntity> actualOptional = cakeRepoImpl.findById(expected.getId());

        // then
        assertThat(actualOptional).isEmpty();
    }

    @Test
    void shouldUpdate() {
        // given
        TestCakeBuilder testCakeBuilder = TestCakeBuilder.newInstance();
        CakeEntity entityToUpdate = testCakeBuilder.build();
        CakeRepoImpl cakeRepoImpl = new CakeRepoImpl(
                new HashMap<>(
                        Map.of(
                                entityToUpdate.getId(), entityToUpdate
                        )
                )
        );

        CakeEntity updateRequest = testCakeBuilder
                .withName("NEW NAME")
                .withPrice(testCakeBuilder.getPrice().add(BigDecimal.TEN))
                .build();

        // when
        cakeRepoImpl.update(updateRequest);
        Optional<CakeEntity> actualOptional = cakeRepoImpl.findById(entityToUpdate.getId());

        // then
        assertThat(actualOptional).contains(updateRequest);
    }

}