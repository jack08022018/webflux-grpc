package com.demo.activities;

import com.demo.dto.ActivityResult;
import com.demo.dto.TransactionRequest;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface MainActivities {
    @ActivityMethod
    ActivityResult getData(TransactionRequest dto);

    @ActivityMethod
    ActivityResult getDataNonBlocking();

}
