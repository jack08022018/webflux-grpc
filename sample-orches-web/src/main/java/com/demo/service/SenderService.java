package com.demo.service;

import com.demo.dto.TransactionRequest;
import com.demo.dto.ActivityResult;

public interface SenderService {
    ActivityResult blocking(TransactionRequest dto) throws Exception;
}
