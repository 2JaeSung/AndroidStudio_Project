package edu.skku.cs.pa3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements Contract.ContractForView{
    private Presenter presenter;
    private Button btn;
    private Button btn1;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editText = findViewById(R.id.userID);
        EditText editText1 = findViewById(R.id.userPasswd);
        btn = findViewById(R.id.login);
        btn1 = findViewById(R.id.register);
        name = editText.getText().toString();

        presenter = new Presenter(this, new Model(name, "0", MainActivity.this));
        btn.setOnClickListener(view -> {presenter.onLoginButtonTouched(editText, editText1);});
        btn1.setOnClickListener(view -> {presenter.onRegisterButtonTouched();});

    }


}