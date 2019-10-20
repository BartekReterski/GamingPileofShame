package com.gamesdatabase.gamerspileofshame.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gamesdatabase.gamerspileofshame.R;
import com.gamesdatabase.gamerspileofshame.adapter.FinishedGamesAdapter;
import com.gamesdatabase.gamerspileofshame.model.ResultFinished;

import io.realm.Realm;
import io.realm.RealmResults;

public class FinishedGames extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView pileofVictoriesTextView;
    private ImageView pilofVictoriesImageView;

    Realm realm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished_games);
        getSupportActionBar().setTitle("Pile of Victories");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        pileofVictoriesTextView=findViewById(R.id.noPileOfVictories);
        pilofVictoriesImageView=findViewById(R.id.gameFinished);

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

// inicjacja bazy danych
        Realm.init(this);
        realm = Realm.getDefaultInstance();

        viewRecord();

    }


    // wyswietlenie bazy danych Realm
    public void viewRecord(){
        RealmResults<ResultFinished> finisheds = realm.where(ResultFinished.class).findAll();

        for(ResultFinished resultFinished : finisheds){

            FinishedGamesAdapter finishedGamesAdapter = new FinishedGamesAdapter(this,finisheds);
            recyclerView.setAdapter(finishedGamesAdapter);
        }

//jesli pusta lista to pokaz obraz
        if(finisheds.isEmpty()){
            pileofVictoriesTextView.setVisibility(View.VISIBLE);
            pilofVictoriesImageView.setVisibility(View.VISIBLE);


        }

    }

    @Override
    protected void onDestroy() {
        realm.close();
        super.onDestroy();
    }






}


