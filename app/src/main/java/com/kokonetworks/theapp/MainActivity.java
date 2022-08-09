package com.kokonetworks.theapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private Field field;
    private TextView tvLevel;
    private TextView tvScore;

    private Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        field = findViewById(R.id.field);
        tvLevel = findViewById(R.id.tvLevel);
        btnStart = findViewById(R.id.btnStart);
        tvScore = findViewById(R.id.tvScore);

        setEventListeners();
    }

    void setEventListeners(){
        btnStart.setOnClickListener(view -> {
            btnStart.setVisibility(View.GONE);
            tvScore.setVisibility(View.GONE);
            field.startGame();
            startGameTimer();
        });
        field.setListener(listener);
    }

    public void startGameTimer() {
        new CountDownTimer(140000, 1000) {
            public void onTick(long millisUntilFinished) {
                // Used for formatting digit to be in 2 digits only
                NumberFormat f = new DecimalFormat("00");
                long hour = (millisUntilFinished / 3600000) % 24;
                long min = (millisUntilFinished / 60000) % 60;
                long sec = (millisUntilFinished / 1000) % 60;
                tvTimer.setText(f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));
            }

            // When the task is over it will print 00:00:00 there
            public void onFinish() {
                tvTimer.setText("00:00:00");
            }

        }.start();
    }
    private final Field.Listener listener = new Field.Listener() {

        @Override
        public void onGameEnded(int score) {
                btnStart.setVisibility(View.VISIBLE);
                tvScore.setVisibility(View.GONE);
                tvScore.setText(String.format(getString(R.string.your_score), score));
        }

        @Override
        public void onLevelChange(int level) {
            tvLevel.setText(String.format(getString(R.string.level), level));
        }

        @Override
        public void onScoreUpdate(int score) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvScore.setText(String.format(getString(R.string.your_score), score));
                }
            });
        }
    };
}