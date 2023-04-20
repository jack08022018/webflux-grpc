package com.demo.service;

import com.demo.dto.RequestDto;
import com.demo.dto.UserData;
import com.demo.dto.UserDto;

public interface SenderService {
    UserData getInfo(RequestDto<UserDto> dto) throws Exception;
}
