package com.gamesdatabase.gamerspileofshame.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;

import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gamesdatabase.gamerspileofshame.R;
import com.gamesdatabase.gamerspileofshame.activities.DetailsGame;
import com.gamesdatabase.gamerspileofshame.activities.FinishedGames;
import com.gamesdatabase.gamerspileofshame.model.ResultFavorite;
import com.gamesdatabase.gamerspileofshame.model.ResultFinished;
import com.squareup.picasso.Picasso;
import java.util.List;

import es.dmoral.toasty.Toasty;
import io.realm.Realm;
import io.realm.RealmResults;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private Context mCtx;
    Realm realm;
    private List<ResultFavorite> resultList;
    private String image;




    public FavoriteAdapter(Context mCtx, List<ResultFavorite> resultList) {
        this.mCtx = mCtx;
        this.resultList = resultList;
    }

    @Override
    public FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.details_items, null);



        Realm.init(mCtx);
        realm= Realm.getDefaultInstance();


        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FavoriteViewHolder holder, final int position) {

        final ResultFavorite result = resultList.get(position);


        holder.gameTitle.setText(result.getTitle());
        holder.gameScore.setText(String.valueOf(result.getScore()));
        holder.gameDateRelease.setText(result.getReleaseDate());
        holder.gameAlsoAvaiable.setText(result.getAlsoAvailableOn());
        holder.gameDescription.setText(result.getDescription());
        holder.gamePublisher.setText(result.getPublisher());
        holder.gameDeveloper.setText(result.getDeveloper());
        holder.gameGenre.setText(result.getGenre());
        holder.gameRating.setText(result.getRating());
        holder.communicateRatingTextView.setText(String.valueOf(result.getHyperanking()));
        holder.helperImage.setText(result.getImage());
        holder.comunicateInfo.setText(result.getHypecomunicate());
        Picasso.get().load(result.getImage()).into(holder.gameCover);

        if (holder.gameCover.getDrawable() == null) {
            holder.gameCover.setImageResource(R.drawable.noimage);
        }








        // logika odpowiedzialna za działanie checkboxa o skonczeniu gry  wraz z alertdialogiem

       holder.checkBox.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
               builder.setTitle("Finish game")
                       .setMessage("Did you really finish this game?")
                       .setCancelable(false)
                       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {

// dodanie danych na temat zaliczonych gier
                               realm.beginTransaction();

                               ResultFinished object = realm.where(ResultFinished.class)
                                       .equalTo("title",holder.gameTitle.getText().toString())
                                       .findFirst();
                               if(object == null){
                                   ResultFinished resultFinished = realm.createObject(ResultFinished.class);
                                   resultFinished.setImage(holder.helperImage.getText().toString());
                                   resultFinished.setTitle(holder.gameTitle.getText().toString());
                                   resultFinished.setReleaseDate(holder.gameDateRelease.getText().toString());
                                   resultFinished.setScore(Integer.parseInt(holder.gameScore.getText().toString()));
                                   resultFinished.setAlsoAvailableOn(holder.gameAlsoAvaiable.getText().toString());
                                   resultFinished.setDescription(holder.gameDescription.getText().toString());
                                   resultFinished.setPublisher(holder.gamePublisher.getText().toString());
                                   resultFinished.setDeveloper(holder.gameDeveloper.getText().toString());
                                   resultFinished.setGenre(holder.gameGenre.getText().toString());
                                   resultFinished.setRating(holder.gameRating.getText().toString());


                               } else {

                               }

                               realm.commitTransaction();


                               RealmResults<ResultFavorite> resultFavorites = realm.where(ResultFavorite.class).equalTo("title",holder.gameTitle.getText().toString()).findAll();

                               realm.beginTransaction();

                               resultFavorites.deleteAllFromRealm();

                               realm.commitTransaction();

                               notifyDataSetChanged();

                               Toasty.success(mCtx,"This game has been added to the pile of victories",Toast.LENGTH_LONG).show();

                               holder.checkBox.setChecked(false);

                               Intent intent = new Intent(mCtx, FinishedGames.class);
                               mCtx.startActivity(intent);


                           }
                       })
                       .setNegativeButton("No", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               holder.checkBox.setChecked(false);
                           }
                       });

               AlertDialog dialog  = builder.create();
               dialog.show();


           }
       });


       //logika popupmenu w ktorej zadeklarowane sa opcje usuniecia rekordu z Realm oraz ocenianienia gry

       holder.moreOption.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               PopupMenu popup = new PopupMenu(mCtx, holder.moreOption);

               popup.inflate(R.menu.card_popup_menu);
               popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                   @Override
                   public boolean onMenuItemClick(MenuItem item) {
                       switch (item.getItemId()) {

                           case R.id.delete_game:
                               AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
                               builder.setTitle("Delete game")
                                       .setMessage("Are you sure you want to delete this game from you pile of shame?")
                                       .setCancelable(false)
                                       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                           @Override
                                           public void onClick(DialogInterface dialog, int which) {
                                               Toasty.normal(mCtx,"Deleted",Toast.LENGTH_SHORT).show();

                                               RealmResults<ResultFavorite> resultFavorites = realm.where(ResultFavorite.class).equalTo("title",holder.gameTitle.getText().toString()).findAll();

                                               realm.beginTransaction();

                                               resultFavorites.deleteAllFromRealm();

                                               realm.commitTransaction();

                                               notifyDataSetChanged();


                                           }
                                       })
                                       .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                           @Override
                                           public void onClick(DialogInterface dialog, int which) {

                                           }
                                       });

                               AlertDialog dialog  = builder.create();
                               dialog.show();

                               return true;


                           case R.id.rate_game:

                               // dodanie logiki alert dialogu wraz z Ratingbarem

                               AlertDialog.Builder builder1 = new AlertDialog.Builder(mCtx);
                               LayoutInflater inflater = (LayoutInflater) mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                               builder1.setTitle("Set your hype ranking for this game:");
                               View dialogLayout = inflater.inflate(R.layout.alert_dialog_with_ratingbar, null);
                               final RatingBar ratingBar = dialogLayout.findViewById(R.id.ratingBar);
                               builder1.setView(dialogLayout);
                               builder1.setPositiveButton("Set", new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialogInterface, int i) {

                                       // zmienna przechowujaca rating

                                       float ratingValue= ratingBar.getRating();

                                       if(ratingValue==1.0){

                                           Toasty.info(mCtx, "Interested for now", Toast.LENGTH_SHORT, true).show();
                                           holder.comunicateInfo.setText("Interested for now");



                                       }

                                       if(ratingValue==2.0){

                                           Toasty.info(mCtx, "Maybe I'll play", Toast.LENGTH_SHORT, true).show();
                                           holder.comunicateInfo.setText("Maybe I'll play");

                                       }

                                       if(ratingValue==3.0){

                                           Toasty.info(mCtx, "I'll play someday", Toast.LENGTH_SHORT, true).show();
                                           holder.comunicateInfo.setText(" I'll play someday");

                                       }

                                       if(ratingValue==4.0){

                                           Toasty.info(mCtx, "I'll definitely play", Toast.LENGTH_SHORT, true).show();
                                           holder.comunicateInfo.setText("I'll definitely play");

                                       }

                                       if(ratingValue==5.0){

                                           Toasty.info(mCtx, "MUST PLAY!", Toast.LENGTH_SHORT, true).show();
                                           holder.comunicateInfo.setText("MUST PLAY!");

                                       }


                                       // dodanie nowej oceny rankingowej do bazy danych
                                       RealmResults<ResultFavorite> resultFavorites = realm.where(ResultFavorite.class).equalTo("title",holder.gameTitle.getText().toString()).findAll();

                                       realm.beginTransaction();

                                       for(ResultFavorite student : resultFavorites){
                                           result.setHyperanking(ratingValue);
                                           result.setHypecomunicate(holder.comunicateInfo.getText().toString());

                                       }

                                       realm.commitTransaction();


                                   }
                               })
                                       .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                           @Override
                                           public void onClick(DialogInterface dialog, int which) {

                                           }
                                       });
                               builder1.show();


                               return true;

                           default:
                               return false;
                       }
                   }
               });

               popup.show();


           }




       });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx, DetailsGame.class);
                Bundle bundle = new Bundle();

                bundle.putString("gameTitle",result.getTitle());
                bundle.putString("gameScore",String.valueOf(result.getScore()));
                bundle.putString("gameDataRelease",result.getReleaseDate());
                bundle.putString("gameAlsoAvailable",result.getAlsoAvailableOn());
                bundle.putString("gameDescription",result.getDescription());
                bundle.putString("gamePublisher",result.getPublisher());
                bundle.putString("gameDeveloper",result.getDeveloper());
                bundle.putString("gameGenre",result.getGenre());
                bundle.putString("gameRating",result.getRating());
                bundle.putString("gameCover",result.getImage());

                intent.putExtras(bundle);

                mCtx.startActivity(intent);
            }
        });

    }



    @Override
    public int getItemCount() {
        return resultList.size();
    }



    class FavoriteViewHolder extends RecyclerView.ViewHolder {

        TextView gameTitle, gameScore, gameDateRelease, gamePublisher, gameDeveloper,gameGenre, gameRating, gameDescription,gameAlsoAvaiable, communicateRatingTextView , comunicateInfo, helperImage;
        ImageView gameCover, moreOption;
        CheckBox checkBox;


        public FavoriteViewHolder(View itemView) {
            super(itemView);

            gameTitle=itemView.findViewById(R.id.gameTitle);
            gameScore=itemView.findViewById(R.id.gameScore);
            gameDateRelease=itemView.findViewById(R.id.gameDate);
            gamePublisher=itemView.findViewById(R.id.gamePublisher);
            gameDeveloper=itemView.findViewById(R.id.gameDeveloper);
            gameGenre=itemView.findViewById(R.id.gameGenre);
            gameRating=itemView.findViewById(R.id.gameRating);
            gameDescription=itemView.findViewById(R.id.gameDescription);
            gameAlsoAvaiable=itemView.findViewById(R.id.gameAlsoAvailable);

            gameCover=itemView.findViewById(R.id.gameCover);
            checkBox=itemView.findViewById(R.id.checkBox);
            communicateRatingTextView=itemView.findViewById(R.id.comunicateRatingTextView);
            comunicateInfo=itemView.findViewById(R.id.comunicateInfo);
            helperImage=itemView.findViewById(R.id.helperImage);
            moreOption=itemView.findViewById(R.id.more_option);


        }
    }


    public void onDestroy() {
        realm.close();

    }
}