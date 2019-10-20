package com.gamesdatabase.gamerspileofshame.inteface;

import com.gamesdatabase.gamerspileofshame.model.Example;
import com.gamesdatabase.gamerspileofshame.modelSuggestion.AutoSuggestion;
import com.gamesdatabase.gamerspileofshame.modelSuggestion.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GamesInterface {

        @GET("/rest/games/{gameName}")
        Call<Example> getGamesbyName(@Path("gameName") String gameName, @Query("platform") String gamePlatform);

        @GET("/rest/games")
        Call<AutoSuggestion> getGamesSuggestions(@Query("title") String gameSuggestions);

    }

