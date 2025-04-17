package com.ecommerce.platform.user.mapper;

import java.util.List;

public interface GenericMapper<D,E> {

    D toDto(E entity);

    E toEntity(D dto);

    List<D> toDtoList(List<E> entities);

    List<E> toEntityList(List<D> dtos);
}
