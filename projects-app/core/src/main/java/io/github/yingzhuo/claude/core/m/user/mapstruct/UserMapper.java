package io.github.yingzhuo.claude.core.m.user.mapstruct;

import io.github.yingzhuo.claude.core.m.user.controller.dto.RegisterRequestDTO;
import io.github.yingzhuo.claude.core.m.user.controller.dto.UpdateProfileRequestDTO;
import io.github.yingzhuo.claude.model.user.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "password", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "cancelledAt", ignore = true)
	@Mapping(target = "roles", ignore = true)
	@Mapping(target = "enabled", ignore = true)
	User toEntity(RegisterRequestDTO dto);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "username", ignore = true)
	@Mapping(target = "password", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "cancelledAt", ignore = true)
	@Mapping(target = "roles", ignore = true)
	@Mapping(target = "enabled", ignore = true)
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void applyProfileUpdate(@MappingTarget User user, UpdateProfileRequestDTO dto);
}
