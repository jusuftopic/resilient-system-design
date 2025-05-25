package org.example.authservice.mapper;

import org.example.authservice.dto.RefreshTokenDTO;
import org.example.authservice.entity.RefreshToken;
import org.mapstruct.Mapper;

@Mapper(uses = {UserMapper.class})
public interface RefreshTokenMapper
{

    RefreshTokenDTO map(RefreshToken refreshToken);

}
