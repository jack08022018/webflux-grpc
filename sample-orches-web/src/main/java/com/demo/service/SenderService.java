package com.demo.service;

import com.demo.dto.ActivityRequest;
import com.demo.dto.ActivityResult;

public interface SenderService {
    ActivityResult blocking(ActivityRequest dto) throws Exception;
}
