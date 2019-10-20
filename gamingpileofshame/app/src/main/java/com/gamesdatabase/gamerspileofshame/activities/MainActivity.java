package com.gamesdatabase.gamerspileofshame.activities;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.gamesdatabase.gamerspileofshame.R;
import com.gamesdatabase.gamerspileofshame.client.GamesClient;
import com.gamesdatabase.gamerspileofshame.inteface.GamesInterface;
import com.gamesdatabase.gamerspileofshame.model.Example;
import com.gamesdatabase.gamerspileofshame.modelSuggestion.AutoSuggestion;
import com.gamesdatabase.gamerspileofshame.modelSuggestion.Result;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import dmax.dialog.SpotsDialog;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;



public class MainActivity extends AppCompatActivity {

    private String url;
    private AutoCompleteTextView gameTitle;
    private Button searchGame;

    private ImageView searchSugestions;


    private String contentTitle;
    private String contentDate;
    private String contentPublisher;
    private String contentDeveloper;
    private String contentAlsoAvailable;
    private String contentGenre;
    private String contentDescription;
    private String contentRating;
    private String contentScore;
    private String contentSuggestionPlatform;
    private String contentSuggestionTitle;
    private AdView adView;


    // boolean do sprawdzanie stanu połączenia z internetem
    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();

        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        gameTitle = findViewById(R.id.autoCompleteTextView);
        gameTitle.addTextChangedListener(textWatcher);
        gameTitle.setOnItemClickListener(onItemClickListener);
        searchGame = findViewById(R.id.buttonSearch);
        searchSugestions = findViewById(R.id.imageViewSearch);
        searchSugestions.setEnabled(false);


        ButtonLogic();


        //deklaracja reklamy typu Banner
        adView= findViewById(R.id.adView);
        MobileAds.initialize(this,"ca-app-pub-4988561860946861~3720656968");
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);





    }


    //pobranie danych na temat gier(Szczegolow)

    private void DownloadGames() {
//inicjaca alert dialogu (podaczas ładowania)

        final AlertDialog alertDialog = new SpotsDialog.Builder()
                .setContext(MainActivity.this)
                .setTheme(R.style.CustomDialog)
                .build();
        alertDialog.setMessage("Loading Data... Please wait...");
        alertDialog.setCancelable(false);
        alertDialog.show();

        Retrofit retrofit = GamesClient.getRetrofitClient();

        GamesInterface gamesInterface = retrofit.create(GamesInterface.class);

        Call call = gamesInterface.getGamesbyName(contentSuggestionTitle, contentSuggestionPlatform);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                if (response.isSuccessful()) {
                    alertDialog.dismiss();

                    if (response.body() != null) {

                        Example example = (Example) response.body();


                        // tytul
                        contentTitle = (example.getResult().getTitle());
                        // data
                        contentDate = (example.getResult().getReleaseDate());

                        if (contentDate.equals("")) {
                            contentDate = ("No data");
                        }

                        // wydawca
                        StringBuilder allPublisher = new StringBuilder();
                        List<String> genrePublisher = example.getResult().getPublisher();
                        if (genrePublisher != null) {
                            for (int i = 0; i < genrePublisher.size(); i++) {
                                allPublisher.append(genrePublisher.get(i));
                                if (i < genrePublisher.size() - 1) allPublisher.append("\n");


                                contentPublisher = (allPublisher.toString());
                            }

//
                        } else {
                            contentPublisher = ("No data");
                        }

                        // deweloper
                        contentDeveloper = (example.getResult().getDeveloper());

                        if (contentDeveloper.equals("")) {
                            contentDeveloper = ("No data");
                        }

                        // opis
                        contentDescription = (example.getResult().getDescription());

                        if (contentDescription.equals("")) {
                            contentDescription = ("No data");
                        }

                        //rating
                        contentRating = (example.getResult().getRating());

                        if (contentRating.equals("")) {
                            contentRating = ("No data");
                        }

                        //punkty
                        contentScore = (String.valueOf(example.getResult().getScore()));

                        if (contentScore.equals("")) {
                            contentScore = ("No data");
                        }


                        //okładka
                        url = (example.getResult().getImage());


                        // gatunki gry
                        StringBuilder allGenre = new StringBuilder();
                        List<String> genreList = example.getResult().getGenre();
                        if (genreList != null) {
                            for (int i = 0; i < genreList.size(); i++) {
                                allGenre.append(genreList.get(i));
                                if (i < genreList.size() - 1) allGenre.append("\n");

                                // usuniecie duplikatow w liście
                                Set<String> genreLisWithoutDuplicates = new LinkedHashSet<String>(genreList);
                                genreList.clear();
                                genreList.addAll(genreLisWithoutDuplicates);

                                contentGenre = (allGenre.toString());


                            }

//
                        } else {
                            contentGenre = ("No data");
                        }

                        // dostępna na
                        StringBuilder allAlsoAvailable = new StringBuilder();
                        List<Object> alsoAvailableList = example.getResult().getAlsoAvailableOn();
                        if (alsoAvailableList != null) {
                            for (int i = 0; i < alsoAvailableList.size(); i++) {
                                allAlsoAvailable.append(alsoAvailableList.get(i));
                                if (i < alsoAvailableList.size() - 1) allAlsoAvailable.append("\n");

                                contentAlsoAvailable = (allAlsoAvailable.toString());

                            }
                        }
                    } else {
                        contentAlsoAvailable = ("No data");
                    }


                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

                alertDialog.dismiss();
                Toasty.warning(getBaseContext(), "Server Error- Couldn't load data, Please try again", Toast.LENGTH_LONG).show();
                gameTitle.setText("");
                searchGame.setEnabled(false);


            }
        });


    }


    //pobranie listy sugestii
    public void DownloadSuggestion() {

        final AlertDialog alertDialog = new SpotsDialog.Builder()
                .setContext(MainActivity.this)
                .setTheme(R.style.CustomDialog)
                .build();
        alertDialog.setMessage("Loading suggestion list");
        alertDialog.setCancelable(false);
        alertDialog.show();


        Retrofit retrofit = GamesClient.getRetrofitClient();

        final GamesInterface gamesInterface = retrofit.create(GamesInterface.class);

        Call call = gamesInterface.getGamesSuggestions(gameTitle.getText().toString());

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    alertDialog.dismiss();
                    if (response.body() != null) {

                        AutoSuggestion autoSuggestion = (AutoSuggestion) response.body();

                        StringBuilder allSuggestions = new StringBuilder();
                        List<Result> suggestionResult = autoSuggestion.getResult();
                        if (suggestionResult != null) {
                            for (int i = 0; i < suggestionResult.size(); i++) {
                                allSuggestions.append(suggestionResult.get(i).getTitle()).append(" | ");
                                allSuggestions.append(suggestionResult.get(i).getPlatform()).append(",");
                                if (i < suggestionResult.size() - 1) allSuggestions.append("\n");

                            }


                            String sb = allSuggestions.toString();

                            //podzielenie stringa poprzez ostatni znak ","
                            String[] array = sb.split(",(?!.*,)");


                            ArrayAdapter<String> adapteo = new ArrayAdapter<String>(getBaseContext(),
                                    android.R.layout.simple_spinner_dropdown_item, array);
//

                            gameTitle.setThreshold(1);
                            //pokazanie całości listy sugestii
                            gameTitle.setDropDownWidth(getResources().getDisplayMetrics().widthPixels);
//

                            gameTitle.setAdapter(adapteo);
                            gameTitle.showDropDown();


                        } else {
                            Toasty.warning(getBaseContext(), "No suggestions for this game", Toast.LENGTH_LONG).show();
                        }

                    }

                }


            }


            @Override
            public void onFailure(Call call, Throwable t) {
                alertDialog.dismiss();
                Toasty.warning(getBaseContext(), "Server Error- Couldn't load data, Please try again", Toast.LENGTH_LONG).show();
                gameTitle.setText("");
                searchGame.setEnabled(false);
            }

        });

    }

    //ucięcie stringa z tytulu gry
    public void SubString() {
        String platform = gameTitle.getText().toString();
        contentSuggestionPlatform = (platform.substring(platform.lastIndexOf('|') + 2));

        String title = gameTitle.getText().toString();
        contentSuggestionTitle = (title.substring(0, title.lastIndexOf('|') - 1));


        if (contentSuggestionPlatform.equals("PC")) {
            contentSuggestionPlatform = ("pc");
        }

        if (contentSuggestionPlatform.equals("PS4")) {
            contentSuggestionPlatform = ("playstation-4");
        }
        if (contentSuggestionPlatform.equals("PS3")) {
            contentSuggestionPlatform = ("playstation-3");
        }
        if (contentSuggestionPlatform.equals("XONE")) {
            contentSuggestionPlatform = ("xbox-one");
        }
        if (contentSuggestionPlatform.equals("X360")) {
            contentSuggestionPlatform = ("xbox-360");
        }
        if (contentSuggestionPlatform.equals("Switch")) {
            contentSuggestionPlatform = ("switch");
        }
        if (contentSuggestionPlatform.equals("GBA")) {
            contentSuggestionPlatform = ("game-boy-advance");
        }
        if (contentSuggestionPlatform.equals("N64")) {
            contentSuggestionPlatform = ("nintendo-64");
        }
        if (contentSuggestionPlatform.equals("GC")) {
            contentSuggestionPlatform = ("gamecube");
        }
        if (contentSuggestionPlatform.equals("iOS")) {
            contentSuggestionPlatform = ("ios");
        }
        if (contentSuggestionPlatform.equals("DC")) {
            contentSuggestionPlatform = ("dreamcast");
        }
        if (contentSuggestionPlatform.equals("PSP")) {
            contentSuggestionPlatform = ("psp");
        }
        if (contentSuggestionPlatform.equals("PS")) {
            contentSuggestionPlatform = ("playstation");
        }
        if (contentSuggestionPlatform.equals("PS2")) {
            contentSuggestionPlatform = ("playstation-2");
        }
        if (contentSuggestionPlatform.equals("3DS")) {
            contentSuggestionPlatform = ("3ds");
        }
        if (contentSuggestionPlatform.equals("DS")) {
            contentSuggestionPlatform = ("ds");
        }
        if (contentSuggestionPlatform.equals("VITA")) {
            contentSuggestionPlatform = ("playstation-vita");
        }
        if (contentSuggestionPlatform.equals("WII")) {
            contentSuggestionPlatform = ("wii");
        }
        if (contentSuggestionPlatform.equals("WIIU")) {
            contentSuggestionPlatform = ("wii-u");
        }
        if (contentSuggestionPlatform.equals("XBOX")) {
            contentSuggestionPlatform = ("xbox");
        }


    }


    private void ButtonLogic() {

        searchGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PassData();
                gameTitle.setText("");

            }
        });

        searchSugestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InternetConnectionCheck();
                DownloadSuggestion();
                hideKeyboard();


            }
        });


    }

    // obostrzenie zeby nie bylo pustego autocomplete gdy pole wyszukiwania jest np puste
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            String checkText = gameTitle.getText().toString();
            searchGame.setEnabled(checkText.contains(" | "));
            searchSugestions.setEnabled(!checkText.isEmpty());




        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    private void PassData() {

        Intent intent = new Intent(this, DetailsActivity.class);

        Bundle bundle = new Bundle();

        bundle.putString("gameTitle", contentTitle);
        bundle.putString("gameReleaseData", contentDate);
        bundle.putString("gameDescription", contentDescription);
        bundle.putString("gameDeveloper", contentDeveloper);
        bundle.putString("gamePublisher", contentPublisher);
        bundle.putString("gameGenre", contentGenre);
        bundle.putString("gameScore", contentScore);
        bundle.putString("gameAlsoAvailable", contentAlsoAvailable);
        bundle.putString("gameRating", contentRating);
        bundle.putString("gameCover", url);



        intent.putExtras(bundle);

        startActivity(intent);




    }

    //Logika odpowiedzialna za wybranie i zaladowanie danych juz z austosugestionstextview
    private AdapterView.OnItemClickListener onItemClickListener =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    SubString();
                    DownloadGames();
                    Toasty.normal(MainActivity.this,
                            "Selected:"
                                    + adapterView.getItemAtPosition(i)
                            , Toast.LENGTH_SHORT).show();
                }
            };


    //schowanie klwiatury po wyszukiwaniu sugestii

    private void hideKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    // wlasciwe sprawdzanie połączeniz internetem wraz z komentarzami

    public void InternetConnectionCheck() {
        if (haveNetworkConnection()) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            AlertDialog alertDialog = alertDialogBuilder.create();
//            alertDialog.show();

        } else {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("No internet connection");
            alertDialogBuilder.setMessage("Please check your internet connection");
            alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    //Toast.makeText(MainActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.pile_of_shame) {
            Intent intent = new Intent(getBaseContext(), DatabaseActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }


}





