package appdevelopement.max.guessimdb250;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class OMDbLoopjTask {

    private static final String TAG = "OMDbLoopjTask";

    public interface OnLoopjCompleted {
        void taskDirectorCompleted(String results);
        void taskActorsCompleted(String results);
        void taskYearCompleted(String results);
        void taskRatingCompleted(String results);
    }

    AsyncHttpClient asyncHttpClient;
    RequestParams requestParams;
    Context context;
    OnLoopjCompleted loopjListener;


    String BASE_URL = "http://www.omdbapi.com/?apikey=ca9c6abf&t=";
    String jsonDirector, jsonActors, jsonYear, jsonRating;

    public OMDbLoopjTask(Context context, OnLoopjCompleted listener) {
        asyncHttpClient = new AsyncHttpClient();
        requestParams = new RequestParams();
        this.context = context;
        this.loopjListener = listener;
    }

    public void executeLoopjCall(String queryTerm) {
        requestParams.put("s", queryTerm);
        asyncHttpClient.get(BASE_URL + queryTerm, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    jsonDirector = response.getString("Director");
                    jsonActors = response.getString("Actors");
                    jsonYear = response.getString("Year");
                    jsonRating = response.getString("imdbRating");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                loopjListener.taskDirectorCompleted(jsonDirector);
                loopjListener.taskActorsCompleted(jsonActors);
                loopjListener.taskYearCompleted(jsonYear);
                loopjListener.taskRatingCompleted(jsonRating);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e(TAG, "onFailure: " + errorResponse);
            }
        });
    }
}
