package com.gamesdatabase.gamerspileofshame.activities;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gamesdatabase.gamerspileofshame.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class DetailsGame extends AppCompatActivity {

    private TextView gameTitle;
    private ExpandableTextView gameDescription;
    private TextView gameReleaseDate;
    private TextView gameDeveloper;
    private TextView gamePublisher;
    private TextView gameGenre;
    private TextView gameAlsoAvailable;
    private TextView gameRating;
    private ImageView gameImage;
    private ImageView gameRatingImage;
    private TextView gameScore;
    private String image;
    private AdView adView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_game);
        getSupportActionBar().setTitle("Details");


        gameTitle = findViewById(R.id.gameTitle);
        gameDescription =
                findViewById(R.id.expand_text_view);
        gameReleaseDate = findViewById(R.id.gameDateRelease);
        gameDeveloper = findViewById(R.id.gameDeveloper);
        gamePublisher = findViewById(R.id.gamePublisher);
        gameGenre = findViewById(R.id.gameGenre);
        gameAlsoAvailable = findViewById(R.id.gameAlsoAvailable);
        gameRating = findViewById(R.id.gameRating);
        gameImage = findViewById(R.id.gameCover);
        gameScore = findViewById(R.id.gameDate);
        gameRatingImage = findViewById(R.id.gameRatingImage);
        adView=findViewById(R.id.adView);


        //deklaracja reklamy typu Banner
        adView= findViewById(R.id.adView);
        MobileAds.initialize(this,"ca-app-pub-4988561860946861~3720656968");
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);




        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            String title = bundle.getString("gameTitle");
            String description = bundle.getString("gameDescription");
            String releaseData = bundle.getString("gameDataRelease");
            String developer = bundle.getString("gameDeveloper");
            String publisher = bundle.getString("gamePublisher");
            String genre = bundle.getString("gameGenre");
            String score = bundle.getString("gameScore");
            String alsoavailable = bundle.getString("gameAlsoAvailable");
            String rating = bundle.getString("gameRating");
            image = bundle.getString("gameCover");



            gameTitle.setText(title);
            gameDescription.setText(description);
            gameReleaseDate.setText(releaseData);
            gameDeveloper.setText(developer);
            gamePublisher.setText(publisher);
            gameGenre.setText(genre);
            gameScore.setText(score);
            gameAlsoAvailable.setText(alsoavailable);
            gameRating.setText(rating);


            //wczytanie okladki
            Picasso.get().load(image).into(gameImage);


            if (gameImage.getDrawable() == null) {
                gameImage.setImageResource(R.drawable.noimage);
            }


        }

        RatingImages();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.about_app_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.about_application) {
            Intent intent = new Intent(getBaseContext(), AboutAppActivity.class);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    public void RatingImages() {

        if (gameRating.getText().toString().equals("M")) {
            gameRatingImage.setImageResource(R.drawable.mature);
            gameRating.setVisibility(View.INVISIBLE);

        }


        if (gameRating.getText().toString().equals("A")) {
            gameRatingImage.setImageResource(R.drawable.adults);
            gameRating.setVisibility(View.INVISIBLE);

        }

        if (gameRating.getText().toString().equals("E")) {
            gameRatingImage.setImageResource(R.drawable.everyone);
            gameRating.setVisibility(View.INVISIBLE);

        }

        if (gameRating.getText().toString().equals("E10+")) {
            gameRatingImage.setImageResource(R.drawable.everyone10);
            gameRating.setVisibility(View.INVISIBLE);

        }

        if (gameRating.getText().toString().equals("RP")) {
            gameRatingImage.setImageResource(R.drawable.ratingpendency);
            gameRating.setVisibility(View.INVISIBLE);

        }

        if (gameRating.getText().toString().equals("T")) {
            gameRatingImage.setImageResource(R.drawable.teen);
            gameRating.setVisibility(View.INVISIBLE);

        }

        if (gameRating.getText().toString().equals("No data")) {
            gameRatingImage.setVisibility(View.INVISIBLE);
            gameRating.setVisibility(View.VISIBLE);

        }


        if (Objects.requireNonNull(gameDescription.getText()).toString().equals("No data")) {

            gameDescription.setGravity(View.TEXT_ALIGNMENT_CENTER);
        }

    }


}
