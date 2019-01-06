package appdevelopement.max.guessimdb250;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
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
    private int tries;
    private Context context;
    OnAnswerCorrectCallback listener;
    boolean hasReturnedCallBack = false;


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
            button.setLayoutParams(new GridView.LayoutParams(70,80));

            //String next at
            button.setPadding(0,2,0,2);
            //button.setBackgroundColor(Color.parseColor("#724a4d"));
            button.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0x724a4d));
            button.setBackgroundResource(R.drawable.less_rounded_corner);
            button.setTextColor((Color.WHITE));
            if (answerCharacter[position]==' ') {
                button.setVisibility(View.INVISIBLE);
            }
            button.setText(String.valueOf(answerCharacter[position]));

            checkAnswer();


        } else

            button=(Button)convertView;
        return button;
    }

    public void checkAnswer() {

        StringBuilder sb = new StringBuilder();
        for(int i=0;i< Common.user_submit_answer.length;i++) {
            sb.append(String.valueOf(Common.user_submit_answer[i]));

            if (sb.toString().equals((title.toLowerCase())) && !hasReturnedCallBack) {
                hasReturnedCallBack = true;
                listener.answerCorrectCallback(sb.toString());
            }
        }

    }



}
