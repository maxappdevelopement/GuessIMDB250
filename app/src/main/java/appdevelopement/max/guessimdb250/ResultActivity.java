package appdevelopement.max.guessimdb250;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.Arrays;
import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class ResultActivity extends AppCompatActivity implements TMDbLoopjTask.OnLoopjCompleted {

    ImageView image;
    ImageView nextQuestionBtn;
    TextView displayTitle, displayDirector, displayActors, displayYear, displayRating;
    String title, poster, director, actors, year, rating, ranking;
    Typeface mTypeface;

    final int radius = 12;
    final int margin = 12;
    final Transformation transformation = new RoundedCornersTransformation(radius, margin);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        image = findViewById(R.id.poster);
        displayDirector = findViewById(R.id.director);
        displayActors = findViewById(R.id.actors);
        displayYear = findViewById(R.id.year);
        displayRating = findViewById(R.id.rating);
        nextQuestionBtn = findViewById(R.id.nextQuestion);

        title = getIntent().getExtras().getString("title");
        poster = getIntent().getExtras().getString("poster");
        director = getIntent().getExtras().getString("director");
        actors = getIntent().getExtras().getString("actors");
        year = getIntent().getExtras().getString("year");
        rating = getIntent().getExtras().getString("rating");
        ranking = getIntent().getExtras().getString("ranking");

        Picasso.get().load(poster).
                transform(transformation).
                into(image);

        mTypeface = Typeface.createFromAsset(getAssets(), "fonts/CormorantGaramond-Bold.ttf");
        displayDirector.setTypeface(mTypeface);
        displayActors.setTypeface(mTypeface);
        displayYear.setTypeface(mTypeface);
        displayRating.setTypeface(mTypeface);

        displayDirector.setText("Director: " + director);
        displayActors.setText("Stars: " + displayActors(actors));
        displayYear.setText("Released: " + year);
        displayRating.setText("IMDB Rating: " + rating + " (Ranking: " + ranking + ")");

        nextQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(mainActivity);
            }
        });

    }

    public String displayActors(String actors) {
        String displayActors = "";
        List<String> actorList = Arrays.asList(actors.split("\\s*,\\s*"));
        if (actorList.size() >= 2) {
            displayActors = " " + actorList.get(0) + ", " + actorList.get(1) + ", " + actorList.get(2);
        } else {
            for (String s: actorList) {
                displayActors += actorList;
            }
        }
        return displayActors;
    }


    @Override
    public void taskPlotCompleted(String results) {
        Log.d("THEANSWER", "taskPlotCompleted: " + results);
    }

    @Override
    public void taskImageCompleted(String results) {

        String toPutInPicasso = results;
        Log.d("GETURL", "taskImageCompleted: ");
    }

}
