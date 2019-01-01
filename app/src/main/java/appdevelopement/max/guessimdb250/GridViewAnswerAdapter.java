package appdevelopement.max.guessimdb250;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import java.util.Arrays;

/**
 * Created by reale on 3/9/2017.
 */

public class GridViewAnswerAdapter extends BaseAdapter {

    public interface OnAnswerCorrectCallback {
        void answerCorrectCallback(String result);
    }

    private String title;
    private char[] answerCharacter;
    private Context context;
    OnAnswerCorrectCallback listener;


    public GridViewAnswerAdapter(char[] answerCharacter, Context context) {
        this.answerCharacter = answerCharacter;
        this.context = context;
    }

    public GridViewAnswerAdapter(char[] answerCharacter, Context context, String title, OnAnswerCorrectCallback listener) {
        this.answerCharacter = answerCharacter;
        this.context = context;
        this.title = title;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return answerCharacter.length;
    }

    @Override
    public Object getItem(int position) {
        return answerCharacter[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       Button button;
        if(convertView == null) {

            //Create new button
            button = new Button(context);
            button.setLayoutParams(new GridView.LayoutParams(50,85));

            Log.d("SEEANSWER", "getView: " + Arrays.toString(answerCharacter));
            Log.d("WHATISTITLE", "getView: " + title);
            //String next at
            button.setPadding(0,2,0,2);
            button.setBackgroundColor(Color.BLUE);
            button.setTextColor(Color.YELLOW);
            if (answerCharacter[position]==' ') {
                button.setVisibility(View.INVISIBLE);
            }
            button.setText(String.valueOf(answerCharacter[position]));

            Log.d("CHECKLENGHT", "getView: " + Common.user_submit_answer);
            if (title.length()==Common.user_submit_answer.length) {

                checkAnswer();
            }


        } else
            button=(Button)convertView;
        return button;
    }

    public void checkAnswer() {
        String result="";
        for(int i=0;i< Common.user_submit_answer.length;i++) {
            result += String.valueOf(Common.user_submit_answer[i]);
        }

        if(result.equals(title.toLowerCase()))
        {
           listener.answerCorrectCallback(result);
        }
    }
}
