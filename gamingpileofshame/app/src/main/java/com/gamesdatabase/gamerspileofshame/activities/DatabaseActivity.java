package com.gamesdatabase.gamerspileofshame.activities;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gamesdatabase.gamerspileofshame.R;
import com.gamesdatabase.gamerspileofshame.adapter.FavoriteAdapter;
import com.gamesdatabase.gamerspileofshame.model.ResultFavorite;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class DatabaseActivity extends AppCompatActivity {


    Realm realm;
    private RecyclerView recyclerView;
    private TextView pilofshameTextView;
    private ImageView pilofshameImageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        getSupportActionBar().setTitle("Pile of Shame");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        pilofshameTextView=findViewById(R.id.noPileOfShame);
        pilofshameImageView=findViewById(R.id.gameOver);

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));




// inicjacja bazy danych
        Realm.init(this);
        realm = Realm.getDefaultInstance();

        viewRecord();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.database_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){


            case R.id.pile_of_victory:

                Intent intent = new Intent(this,FinishedGames.class);
                startActivity(intent);

                return true;

// dodanie sortowań danych w recylcerview

            case R.id.ascending:

                RealmResults<ResultFavorite> resultFavoritesAscending = realm.where(ResultFavorite.class).sort("title", Sort.ASCENDING).findAll();

                for(ResultFavorite resultFavorite: resultFavoritesAscending){

                    FavoriteAdapter favoriteAdapterAscending = new FavoriteAdapter(this,resultFavoritesAscending);
                    recyclerView.setAdapter(favoriteAdapterAscending);
                }
                return true;



            case R.id.descending:

                RealmResults<ResultFavorite> resultFavoritesDescending= realm.where(ResultFavorite.class).sort("title",Sort.DESCENDING).findAll();

                for(ResultFavorite resultFavorite: resultFavoritesDescending){

                    FavoriteAdapter favoriteAdapterDescending= new FavoriteAdapter(this, resultFavoritesDescending);
                    recyclerView.setAdapter(favoriteAdapterDescending);
                }

                return true;

            case R.id.metascore:

                RealmResults<ResultFavorite> resultFavoritesMetaScore= realm.where(ResultFavorite.class).sort("score", Sort.DESCENDING).findAll();

                for(ResultFavorite resultFavorite: resultFavoritesMetaScore){

                    FavoriteAdapter favoriteAdapterMetaScore= new FavoriteAdapter(this,resultFavoritesMetaScore);
                    recyclerView.setAdapter(favoriteAdapterMetaScore);
                }


                return true;


            case R.id.yourrating:

                RealmResults<ResultFavorite> resultFavoritesYourRating = realm.where(ResultFavorite.class).sort("hyperanking",Sort.DESCENDING).findAll();

                for(ResultFavorite resultFavorite : resultFavoritesYourRating){

                    FavoriteAdapter favoriteAdapterYourRating = new FavoriteAdapter(this,resultFavoritesYourRating);
                    recyclerView.setAdapter(favoriteAdapterYourRating);
                }


        }

        return super.onOptionsItemSelected(item);
    }

// wyswietlenie bazy danych Realm
    public void viewRecord(){
        RealmResults<ResultFavorite> results = realm.where(ResultFavorite.class).findAll();

        for(ResultFavorite resultFavorite : results){

            FavoriteAdapter favoriteAdapter = new FavoriteAdapter(this,results);
            recyclerView.setAdapter(favoriteAdapter);
        }

//jesli pusta lista to pokaz obraz
        if(results.isEmpty()){
            pilofshameTextView.setVisibility(View.VISIBLE);
            pilofshameImageView.setVisibility(View.VISIBLE);


        }

    }

    @Override
    protected void onDestroy() {
        realm.close();
        super.onDestroy();
    }


    @Override
    public void onBackPressed() {
      Intent intent = new Intent(this,MainActivity.class);
      startActivity(intent);
    }



}
