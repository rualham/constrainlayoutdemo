package com.android.sharedemo.retrofit;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GithubApi {
    @GET("search/repositories?sort=stars")
    suspend fun searchRepos(
            @Query("q")query: String,
            @Query("page")page: Int,
            @Query("per_page")itemsPerPage: Int
    ): RepoSearchResponse
}
