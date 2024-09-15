package by.clevertec.service;

import by.clevertec.TestCakeBuilder;
import by.clevertec.dto.CakeRequest;
import by.clevertec.dto.CakeResponse;
import by.clevertec.entity.CakeEntity;
import by.clevertec.mapper.CakeMapper;
import by.clevertec.repository.CakeRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CakeServiceImplTest {

    @Mock
    private CakeMapper cakeMapper;

    @Mock
    private CakeRepo cakeRepo;

    @InjectMocks
    private CakeServiceImpl cakeService;


    @Test
    void shouldFindAll() {
        // given
        final TestCakeBuilder firstCakeBuilder = TestCakeBuilder.newInstance();
        final TestCakeBuilder secondCakeBuilder = TestCakeBuilder.newInstance()
                .withId(UUID.fromString("19c8caab-340d-4722-9414-8933d5da5dd8"))
                .withName("CAKE TWO")
                .withPrice(BigDecimal.valueOf(333, 2));

        CakeResponse firstExpectedResponse = firstCakeBuilder.buildResponse();
        CakeResponse secondExpectedResponse = secondCakeBuilder.buildResponse();
        List<CakeResponse> expected = List.of(firstExpectedResponse, secondExpectedResponse);

        CakeEntity firstCakeEntity = firstCakeBuilder.build();
        CakeEntity secondCakeEntity = secondCakeBuilder.build();
        List<CakeEntity> entitiesFromRepo = List.of(firstCakeEntity, secondCakeEntity);

        when(cakeRepo.findAll()).thenReturn(entitiesFromRepo);

        when(cakeMapper.toResponse(firstCakeEntity)).thenReturn(firstExpectedResponse);
        when(cakeMapper.toResponse(secondCakeEntity)).thenReturn(secondExpectedResponse);

        // when
        List<CakeResponse> actual = cakeService.findAll();

        // then
        assertThat(actual).isEqualTo(expected);
    }
    @Test
    void shouldFindById() {
        // given
        final TestCakeBuilder testCakeBuilder = TestCakeBuilder.newInstance();

        UUID idToFindBy = testCakeBuilder.getId();
        CakeEntity cakeToFind = testCakeBuilder.build();
        CakeResponse expected = testCakeBuilder.buildResponse();

        when(cakeRepo.findById(idToFindBy)).thenReturn(Optional.of(cakeToFind));
        when(cakeMapper.toResponse(cakeToFind)).thenReturn(expected);

        // when
        Optional<CakeResponse> actual = cakeService.findById(idToFindBy);

        // then
        assertThat(actual).contains(expected);
    }

    @Test
    void shouldReturnEmptyOptional_whenNotFound() {
        // given
        final TestCakeBuilder testCakeBuilder = TestCakeBuilder.newInstance();

        UUID idToFindBy = testCakeBuilder.getId();

        when(cakeRepo.findById(idToFindBy)).thenReturn(Optional.empty());

        // when
        Optional<CakeResponse> actual = cakeService.findById(idToFindBy);

        // then
        verifyNoInteractions(cakeMapper);

        assertThat(actual).isEmpty();
    }

    @Test
    void shouldDeleteById() {
        // given
        final TestCakeBuilder testCakeBuilder = TestCakeBuilder.newInstance();

        UUID idToDeleteBy = testCakeBuilder.getId();

        // when
        cakeService.deleteById(idToDeleteBy);

        // then
        verifyNoInteractions(cakeMapper);
        verify(cakeRepo).deleteById(idToDeleteBy);
    }

    @Test
    void shouldUpdate() {
        // given
        final TestCakeBuilder testCakeBuilder = TestCakeBuilder.newInstance();

        UUID id = testCakeBuilder.getId();
        CakeRequest updateRequest = testCakeBuilder.buildRequest();
        CakeEntity cakeEntity = testCakeBuilder.build();
        CakeEntity updatedEntity = testCakeBuilder
                .withPrice(BigDecimal.TEN)
                .withName("NEW NAME")
                .build();
        CakeResponse expected = testCakeBuilder.buildResponse();

        when(cakeMapper.toEntity(updateRequest, id)).thenReturn(cakeEntity);
        when(cakeRepo.update(cakeEntity)).thenReturn(updatedEntity);
        when(cakeMapper.toResponse(updatedEntity)).thenReturn(expected);

        // when
        CakeResponse actual = cakeService.update(id, updateRequest);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldCreate() {
        // given
        final TestCakeBuilder testCakeBuilder = TestCakeBuilder.newInstance();

        CakeRequest createRequest = testCakeBuilder.buildRequest();
        CakeEntity cakeEntity = testCakeBuilder.build();
        CakeEntity createdEntity = testCakeBuilder
                .withPrice(BigDecimal.TEN)
                .withName("NEW NAME")
                .build();
        CakeResponse expected = testCakeBuilder.buildResponse();

        when(cakeMapper.toEntity(createRequest)).thenReturn(cakeEntity);
        when(cakeRepo.create(cakeEntity)).thenReturn(createdEntity);
        when(cakeMapper.toResponse(createdEntity)).thenReturn(expected);

        // when
        CakeResponse actual = cakeService.create(createRequest);

        // then
        assertThat(actual).isEqualTo(expected);
    }

}