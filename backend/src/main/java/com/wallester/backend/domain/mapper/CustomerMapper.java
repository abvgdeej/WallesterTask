package com.wallester.backend.domain.mapper;

import com.wallester.backend.domain.dto.CustomerDto;
import com.wallester.backend.persist.entity.CustomerEntity;
import org.mapstruct.Mapper;

/**
 * Mapper for {@link CustomerEntity} and {@link CustomerEntity}
 */
@Mapper
public abstract class CustomerMapper {
    public abstract CustomerDto mapToDto(CustomerEntity source);

    public abstract CustomerEntity mapToEntity(CustomerDto source);
}
