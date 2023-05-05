package com.demo.activities;

import com.demo.dto.TransactionRequest;
import com.demo.dto.ActivityResult;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface MainActivities {
    @ActivityMethod
    ActivityResult getData(TransactionRequest dto);

}
