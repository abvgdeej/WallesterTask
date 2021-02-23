import com.wallester.backend.domain.dto.CustomerDto;
import com.wallester.backend.domain.mapper.CustomerMapper;
import com.wallester.backend.persist.entity.CustomerEntity;
import com.wallester.backend.utils.Gender;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.sql.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Test cases for {@link CustomerMapper}
 */
@Slf4j
public class MapperTests {
    private static final long DATE = 631152000000L;
    private final CustomerMapper mapper = Mappers.getMapper(CustomerMapper.class);

    @Test
    public void verify_thatAfterMappingDtoEqualsEntity() {
        //arrange
        Date date = new Date(DATE);
        CustomerEntity entity = new CustomerEntity();
        entity.setId(1);
        entity.setFirstName("Ivan");
        entity.setLastName("Ivanov");
        entity.setBirthDate(date);
        entity.setGender(Gender.MALE);
        entity.setEmail("ivan.ivanov.@gmail.com");
        entity.setAddress("Moscow, Tverskaya st, 1-1");

        //act
        CustomerDto dto = mapper.mapToDto(entity);

        //assert
        log.info("DTO: {}", dto);
        assertEquals("First name is not equals", entity.getFirstName(), dto.getFirstName());
        assertEquals("Last name is not equals", entity.getLastName(), dto.getLastName());
        assertEquals("Birth date is not equals", entity.getBirthDate(), dto.getBirthDate());
        assertEquals("Gender is not equals", entity.getGender().getValue(), dto.getGender().getValue());
        assertEquals("Email is not equals", entity.getEmail(), dto.getEmail());
        assertEquals("Address is not equals", entity.getAddress(), dto.getAddress());
    }

    @Test
    public void verify_thatAfterMappingEntityEqualsDto() {
        //arrange
        Date date = new Date(DATE);
        CustomerDto dto = new CustomerDto();
        dto.setFirstName("Ivan");
        dto.setLastName("Ivanov");
        dto.setBirthDate(date);
        dto.setGender(Gender.MALE);
        dto.setEmail("ivan.ivanov.@gmail.com");
        dto.setAddress("Moscow, Tverskaya st, 1-1");

        //act
        CustomerEntity entity = mapper.mapToEntity(dto);

        //assert
        assertNull("Id must be null", entity.getId());
        assertEquals("First name is not equals", dto.getFirstName(), entity.getFirstName());
        assertEquals("Last name is not equals", dto.getLastName(), entity.getLastName());
        assertEquals("Birth date is not equals", dto.getBirthDate(), entity.getBirthDate());
        assertEquals("Gender is not equals", dto.getGender().getValue(), entity.getGender().getValue());
        assertEquals("Email is not equals", dto.getEmail(), entity.getEmail());
        assertEquals("Address is not equals", dto.getAddress(), entity.getAddress());
    }
}
