package edu.skku.cs.pa3;

import android.app.Activity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

public interface Contract {
    interface ContractForView{

    }

    interface ContractForModel{
        String getId();
        String getSid();
        Activity getActivity();

        void setSid(String s);
    }

    interface ContractForPresenter{
        void onLoginButtonTouched(EditText editText, EditText editText1);
        void onRegisterButtonTouched();

        void onEnrollButtonTouched(EditText editText, EditText editText1);

        void initial(TextView[] textViews);
        void onImageTouched(ConstraintLayout constraintLayout, ImageView imageView, TextView[] textViews);
        void onStartButtonTouched();

        void game_init(ConstraintLayout constraintLayout);
        void onExitButtonTouched(Integer time);

    }
}
