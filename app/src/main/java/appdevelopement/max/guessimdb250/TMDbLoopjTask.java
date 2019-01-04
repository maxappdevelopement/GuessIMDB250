package appdevelopement.max.guessimdb250;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class TMDbLoopjTask {

    public interface OnLoopjCompleted {
        void taskPlotCompleted(String results);
        void taskImageCompleted(String results);
    }

    private static final String TAG = "TMDbLoopjTask";

    AsyncHttpClient asyncHttpClient;
    RequestParams requestParams;
    Context context;
    OnLoopjCompleted loopjListener;

    //String BASE_URL = "http://www.omdbapi.com/?apikey=ca9c6abf&";
    String BASE_URL = "https://api.themoviedb.org/3/search/movie?api_key=41ffcaf4121b4732b86dda66c01f6afb&query=";

    String jsonPlot;
    String jsonImage;

    public TMDbLoopjTask(Context context, OnLoopjCompleted listener) {
        asyncHttpClient = new AsyncHttpClient();
        requestParams = new RequestParams();
        this.context = context;
        this.loopjListener = listener;
    }

    public void executeLoopjCall(String queryTerm) {
        queryTerm = handleExeption(queryTerm);
        Log.d(TAG, "executeLoopjCall: " + queryTerm);
        requestParams.put("s", queryTerm);
        asyncHttpClient.get(BASE_URL + queryTerm, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    jsonPlot = response.getJSONArray("results").getJSONObject(0).getString("overview");
                    jsonImage = response.getJSONArray("results").getJSONObject(0).getString("poster_path");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                loopjListener.taskPlotCompleted(jsonPlot);
                loopjListener.taskImageCompleted(convertPosterPath(jsonImage));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e(TAG, "onFailure: " + errorResponse);
            }
        });
    }

    public String convertPosterPath(String url) {
        return  "https://image.tmdb.org/t/p/w500" + url;
    }

    public String handleExeption(String queryTerm) {
        if (queryTerm.equals("Taare Zameen Par")) {
            return "Like Stars on Earth";
        }
        return queryTerm;
    }
}

