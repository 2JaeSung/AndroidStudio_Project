package edu.skku.cs.pa3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.media.audiofx.DynamicsProcessing;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class StageActivity extends AppCompatActivity implements Contract.ContractForView{
    private Presenter presenter;
    private Button btn;
    private String name;
    private TextView textView;
    private ImageView imageView;
    private ConstraintLayout constraintLayout;
    private TextView[] textViews = new TextView[7];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage);

        Intent intent = getIntent();
        name = intent.getStringExtra(Presenter.EXT_NAME);

        presenter = new Presenter(this, new Model(name, "0", StageActivity.this));

        constraintLayout = findViewById(R.id.constraint);
        constraintLayout.setBackgroundColor(Color.BLUE);

        textView = findViewById(R.id.textView);
        textView.setText("Hello! " + name);

        textViews[0] = findViewById(R.id.textView10);
        textViews[1] = findViewById(R.id.textView11);
        textViews[2] = findViewById(R.id.textView12);
        textViews[3] = findViewById(R.id.textView13);
        textViews[4] = findViewById(R.id.textView14);
        textViews[5] = findViewById(R.id.textView15);
        textViews[6] = findViewById(R.id.textView16);

        presenter.initial(textViews);

        imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(view -> {presenter.onImageTouched(constraintLayout, imageView, textViews);});

        btn = findViewById(R.id.start);
        btn.setOnClickListener(view -> {presenter.onStartButtonTouched();});

    }
}