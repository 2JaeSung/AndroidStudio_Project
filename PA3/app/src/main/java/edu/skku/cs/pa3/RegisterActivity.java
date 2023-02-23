package edu.skku.cs.pa3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity implements Contract.ContractForView {
    private Presenter presenter;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText editText = findViewById(R.id.editTextTextPersonName);
        EditText editText1 = findViewById(R.id.editTextTextPersonName2);
        btn = findViewById(R.id.button2);

        presenter = new Presenter(this, new Model("name", "0", RegisterActivity.this));
        btn.setOnClickListener(view -> {presenter.onEnrollButtonTouched(editText, editText1);});
    }
}