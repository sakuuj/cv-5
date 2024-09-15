package by.clevertec.mapper;

import by.clevertec.TestCakeBuilder;
import by.clevertec.dto.CakeRequest;
import by.clevertec.dto.CakeResponse;
import by.clevertec.entity.CakeEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CakeMapperImplTest {

    private final CakeMapperImpl cakeMapper = new CakeMapperImpl();

    @Test
    void shouldMapToEntity_WhenWithoutId() {

        // given
        TestCakeBuilder testCakeBuilder = TestCakeBuilder.newInstance()
                .withId(null);

        CakeRequest request = testCakeBuilder.buildRequest();
        CakeEntity expected = testCakeBuilder.build();

        // when
        CakeEntity actual = cakeMapper.toEntity(request);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldMapToEntity_WhenWithId() {

        // given
        TestCakeBuilder testCakeBuilder = TestCakeBuilder.newInstance();

        CakeRequest request = testCakeBuilder.buildRequest();
        CakeEntity expected = testCakeBuilder.build();

        // when
        CakeEntity actual = cakeMapper.toEntity(request, expected.getId());

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldMapToResponse() {

        // given
        TestCakeBuilder testCakeBuilder = TestCakeBuilder.newInstance();

        CakeEntity entity = testCakeBuilder.build();
        CakeResponse expected = testCakeBuilder.buildResponse();

        // when
        CakeResponse actual = cakeMapper.toResponse(entity);

        // then
        assertThat(actual).isEqualTo(expected);
    }
}