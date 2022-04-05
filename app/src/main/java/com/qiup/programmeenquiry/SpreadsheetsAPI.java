package com.qiup.programmeenquiry;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SpreadsheetsAPI {

    @POST("1FAIpQLSc-YHeXIpLuul0aNmOwhk7JuBQfc0RGN9hP-N3wl1UZ9yAIHw/formResponse")
    @FormUrlEncoded
    Call<Void> postToSpreadsheet(
            @Field("entry.1397369254") String Name,
            @Field("entry.502184134") String IC,
            @Field("entry.636103052") String schoolName,
            @Field("entry.725125565") String qualificationLevel,
            @Field("entry.517907311") String contactNumber,
            @Field("entry.794801132") String Email,
            @Field("entry.1548031110") String interestedProgramme,
            @Field("entry.1144024313") String eligibleProgramme,
            @Field("entry.798377098") String notEligibleProgramme,
            @Field("entry.1825730903") String Remark
    );
}
