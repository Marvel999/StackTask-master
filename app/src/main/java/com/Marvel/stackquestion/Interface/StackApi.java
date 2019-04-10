package com.Marvel.stackquestion.Interface;

import com.Marvel.stackquestion.model.TagResponse;
import com.Marvel.stackquestion.model.QuestionItemData;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StackApi {

    @GET("oauth/dialog")
    Call<ResponseBody>stackAuthCall(@Query("client_id") int client_id, @Query("redirect_uri ") String redirect_uri);

    @GET("2.2/tags")
    Call<TagResponse>stackTagCall(@Query("page")int page, @Query("order") String order, @Query("sort") String popular,
                                  @Query("site")String site);

    @GET("2.2/questions")
    Call<QuestionItemData>stackQuestionCall(@Query("page")int page, @Query("order") String order, @Query("sort") String sort
                                            ,@Query("tagged") String tag,@Query("site")String site, @Query("filter") String filter);
}
