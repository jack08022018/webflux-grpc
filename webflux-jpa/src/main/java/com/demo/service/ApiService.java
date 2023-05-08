package com.demo.service;

import com.demo.dto.RequestDto;
import com.demo.dto.UserData;
import com.demo.dto.UserDto;
import reactor.core.publisher.Mono;

public interface ApiService {
    UserData getInfo(RequestDto<UserDto> dto) throws Exception;
}
