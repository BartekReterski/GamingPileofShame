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
import android.widget.TextView;
import android.widget.Toast;

import com.gamesdatabase.gamerspileofshame.R;
import com.gamesdatabase.gamerspileofshame.activities.DetailsGame;
import com.gamesdatabase.gamerspileofshame.model.ResultFinished;
import com.squareup.picasso.Picasso;
import java.util.List;

import es.dmoral.toasty.Toasty;
import io.realm.Realm;
import io.realm.RealmResults;

public class FinishedGamesAdapter extends RecyclerView.Adapter<FinishedGamesAdapter.FinishedGamesAdapterViewHolder> {

    private Context mCtx;
    private List<ResultFinished> finishedGamesList;


    Realm realm;


    public FinishedGamesAdapter(Context mCtx, List<ResultFinished> finishedGamesList) {
        this.mCtx = mCtx;
        this.finishedGamesList = finishedGamesList;
    }

    @Override
    public FinishedGamesAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.details_items_finished, null);

        Realm.init(mCtx);
        realm= Realm.getDefaultInstance();


        return new FinishedGamesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FinishedGamesAdapterViewHolder holder, final int position) {

        final ResultFinished result = finishedGamesList.get(position);


        holder.gameTitle.setText(result.getTitle());
        holder.gameScore.setText(String.valueOf(result.getScore()));
        holder.gameDateRelease.setText(result.getReleaseDate());
        holder.gameAlsoAvaiable.setText(result.getAlsoAvailableOn());
        holder.gameDescription.setText(result.getDescription());
        holder.gamePublisher.setText(result.getPublisher());
        holder.gameDeveloper.setText(result.getDeveloper());
        holder.gameGenre.setText(result.getGenre());
        holder.gameRating.setText(result.getRating());
        Picasso.get().load(result.getImage()).into(holder.gameCover);
        if (holder.gameCover.getDrawable() == null) {
            holder.gameCover.setImageResource(R.drawable.noimage);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {

            //deklaracja reklamy pełonoerkanowej




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


     holder.moreOption.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             PopupMenu popup = new PopupMenu(mCtx, holder.moreOption);

             popup.inflate(R.menu.card_popup_menu_for_pile_of_victories);
             popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                 @Override
                 public boolean onMenuItemClick(MenuItem item) {
                     switch (item.getItemId()) {

                         case R.id.delete_game:
                             AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
                             builder.setTitle("Delete game")
                                     .setMessage("Are you sure you want to delete this game from you pile of victories?")
                                     .setCancelable(false)
                                     .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                         @Override
                                         public void onClick(DialogInterface dialog, int which) {
                                             Toasty.normal(mCtx,"Deleted",Toast.LENGTH_SHORT).show();

                                             RealmResults<ResultFinished> resultFinisheds = realm.where(ResultFinished.class).equalTo("title",holder.gameTitle.getText().toString()).findAll();

                                             realm.beginTransaction();

                                             resultFinisheds.deleteAllFromRealm();

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

                         default:
                             return false;
                     }
                 }
             });

             popup.show();
         }
     });



    }






    @Override
    public int getItemCount() {
        return finishedGamesList.size();
    }



    class FinishedGamesAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView gameTitle, gameScore, gameDateRelease, gamePublisher, gameDeveloper,gameGenre, gameRating, gameDescription,gameAlsoAvaiable;
        ImageView gameCover, moreOption;
        CheckBox checkBox;


        public FinishedGamesAdapterViewHolder(View itemView) {
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
            moreOption=itemView.findViewById(R.id.more_option);


        }
    }

    public void onDestroy() {
        realm.close();

    }

}