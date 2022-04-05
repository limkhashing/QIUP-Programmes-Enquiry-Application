package com.qiup.programmeenquiry;

import com.qiup.POJO.RulePojo;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetRuleAPI {
    @GET("/v0/b/main-stack-194212.appspot.com/o/AllProgramme.json?alt=media&token=your_token")
    Call<RulePojo> getRule();
}
