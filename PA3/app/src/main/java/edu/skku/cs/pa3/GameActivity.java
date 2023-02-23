package edu.skku.cs.pa3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

public class GameActivity extends AppCompatActivity implements Contract.ContractForView {
    private String name;
    public String sid;
    private Presenter presenter;
    private ConstraintLayout constraintLayout;

    public long start = System.currentTimeMillis();
    public long end = System.currentTimeMillis();

    GameView gameView;
    TextView textView;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameView = findViewById(R.id.gameView);
        textView = findViewById(R.id.textView3);
        btn = findViewById(R.id.button);
        constraintLayout = findViewById(R.id.cons);

        Intent intent = getIntent();
        name = intent.getStringExtra(Presenter.EXT_NAME);
        sid = intent.getStringExtra(Presenter.EXT_SID);
        gameView.map = sid;
        ShowTimeMethod();

        presenter = new Presenter(this, new Model(name, sid, GameActivity.this));
        presenter.game_init(constraintLayout);
        btn.setOnClickListener(view -> {presenter.onExitButtonTouched((int)(end-start)/1000);});


    }

    public void ShowTimeMethod(){
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                end = System.currentTimeMillis();
                textView.setText(Integer.toString((int)(end-start)/1000));
            }
        };

        Runnable task = new Runnable() {
            @Override
            public void run() {
                while(!gameView.isDead){
                    try{
                        Thread.sleep(1000);
                    }catch(InterruptedException e){}
                    handler.sendEmptyMessage(1);
                }
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }
}