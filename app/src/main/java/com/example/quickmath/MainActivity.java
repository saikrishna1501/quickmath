package com.example.quickmath;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private int pickOption,correctCount,totalCount;
    private boolean counterRunning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hideAllExceptGo();
    }
    public void hideView(View view) {
        view.setVisibility(View.INVISIBLE);
    }
    public void showView(View view) {
        view.setVisibility(View.VISIBLE);
    }

    public void hideGo() {
        hideView(findViewById(R.id.goButton));
    }
    public  void showGo() {
        showView(findViewById(R.id.goButton));
    }
    public void hideAllExceptGo() {
        hideView(findViewById(R.id.time));
        hideView(findViewById(R.id.question));
        hideView(findViewById(R.id.score));
        hideView(findViewById(R.id.option1));
        hideView(findViewById(R.id.option2));
        hideView(findViewById(R.id.option3));
        hideView(findViewById(R.id.option4));
        hideView(findViewById(R.id.result));
        hideView(findViewById(R.id.tryAgain));
        showGo();
    }
    public void afterGoPressed() {
        hideGo();
        showView(findViewById(R.id.time));
        showView(findViewById(R.id.question));
        showView(findViewById(R.id.score));
        showView(findViewById(R.id.option1));
        showView(findViewById(R.id.option2));
        showView(findViewById(R.id.option3));
        showView(findViewById(R.id.option4));
        hideView(findViewById(R.id.result));
        hideView(findViewById(R.id.tryAgain));
    }
    public void goClicked(View view) {
        afterGoPressed();
        countDown();
        correctCount = 0;
        totalCount = 0;
        int sum = generateQuestion();
        pickOption = generateOptions(sum);
    }

    public void countDown() {
                counterRunning = true;
                final TextView time = (TextView) findViewById(R.id.time);
                final Button tryAgain = (Button) findViewById(R.id.tryAgain);
                final TextView result = (TextView) findViewById(R.id.result);
                final TextView score = (TextView) findViewById(R.id.score);
                int secondsLeft = 30;

                time.setText(secondsLeft+"");
                CountDownTimer countDownTimer = new CountDownTimer(30000+30,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        time.setText(millisUntilFinished/1000 + "");
                    }

                    @Override
                    public void onFinish() {
                        tryAgain.setVisibility(View.VISIBLE);
                        result.setText("Your score: " + score.getText());
                        result.setVisibility(View.VISIBLE);
                        counterRunning = false;
                        disableOnClickForOptions();

                    }
                }.start();
    }

    public int generateQuestion() {
        Random r =  new Random();
        int num1 = r.nextInt(100);
        int num2 = r.nextInt(100);
        int sum = num1 + num2;
        TextView question = (TextView) findViewById(R.id.question);
        question.setText(num1 + "+" + num2);
        return sum;
    }
    public int generateOptions(int sum) {
        Random r = new Random();
        int pickOption = r.nextInt(4) + 1;
        Button option1 = (Button) findViewById(R.id.option1);
        Button option2 = (Button) findViewById(R.id.option2);
        Button option3 = (Button) findViewById(R.id.option3);
        Button option4 = (Button) findViewById(R.id.option4);
        Button correctPick = (Button) findViewById(R.id.option3);

        switch (pickOption) {
            case 1 :
                correctPick = option1;
                option2.setText(r.nextInt(199)+"");
                option3.setText(r.nextInt(199)+"");
                option4.setText(r.nextInt(199)+"");
                break;
            case 2 :
                correctPick = option2;
                option1.setText(r.nextInt(199)+"");
                option3.setText(r.nextInt(199)+"");
                option4.setText(r.nextInt(199)+"");
                break;
            case 3 :
                correctPick = option3;
                option1.setText(r.nextInt(199)+"");
                option2.setText(r.nextInt(199)+"");
                option4.setText(r.nextInt(199)+"");
                break;
            case 4:
                correctPick = option4;
                option1.setText(r.nextInt(199)+"");
                option2.setText(r.nextInt(199)+"");
                option3.setText(r.nextInt(199)+"");
                break;
        }

        correctPick.setText(sum+"");
        return pickOption;
    }

    public void optionSelected(View view) {
        String tag = view.getTag().toString();
        Boolean correctAnswer = false;
        switch (tag) {
            case "option1":
                if(pickOption == 1) {
                    correctAnswer = true;
                }
                break;
            case "option2":
                if(pickOption == 2) {
                    correctAnswer = true;
                }
                break;
            case "option3":
                if(pickOption == 3) {
                    correctAnswer = true;
                }
                break;
            case "option4":
                if(pickOption == 4) {
                    correctAnswer = true;
                }
                break;
        }
        TextView result = (TextView) findViewById(R.id.result);
        result.setVisibility(View.VISIBLE);
        if(correctAnswer) {
            correctCount++;
            result.setText("Correct!");
        }
        else {
            result.setText("Wrong!");
        }
        totalCount++;
        TextView score = (TextView) findViewById(R.id.score);
        score.setText(correctCount + "/" + totalCount);
        if(counterRunning) {
            int sum = generateQuestion();
            pickOption = generateOptions(sum);
        }
    }
    public void disableOnClickForOptions() {
        Button option[] = new Button[5];
        option[1] = (Button) findViewById(R.id.option1);
        option[2] = (Button) findViewById(R.id.option2);
        option[3] = (Button) findViewById(R.id.option3);
        option[4] = (Button) findViewById(R.id.option4);
        for(int i = 1;i < 5;i++) {
            option[i].setOnClickListener(null);
        }
    }
    public void enableOnClickForOptions() {
        Button option[] = new Button[5];
        option[1] = (Button) findViewById(R.id.option1);
        option[2] = (Button) findViewById(R.id.option2);
        option[3] = (Button) findViewById(R.id.option3);
        option[4] = (Button) findViewById(R.id.option4);
        for(int i = 1;i < 5;i++) {
            option[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    optionSelected(v);
                }
            });
        }
    }
    public void resetDefaults() {
        TextView score = (TextView)findViewById(R.id.score);
        score.setText("0/0");
        TextView time = (TextView)findViewById(R.id.time);
        time.setText("30");
    }
    public void tryAgainClicked(View view) {
        resetDefaults();
        Button go = (Button) findViewById(R.id.goButton);
        go.callOnClick();
        enableOnClickForOptions();
    }
}
