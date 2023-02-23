package edu.skku.cs.pa3;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Presenter implements Contract.ContractForPresenter{

    private Contract.ContractForView view;
    private Contract.ContractForModel model;
    public static final String EXT_NAME = "Name";
    public static final String EXT_SID = "0";

    public Presenter(Contract.ContractForView view, Contract.ContractForModel model){
        this.view = view;
        this.model = model;
    }

    //////////MainActivity//////////

    @Override
    public void onLoginButtonTouched(EditText editText, EditText editText1){
        String id = editText.getText().toString();
        String pass = editText1.getText().toString();
        String u = "https://90jh18vf43.execute-api.ap-northeast-2.amazonaws.com/dev/login";

        OkHttpClient client = new OkHttpClient();
        Model data = new Model();
        data.setName(id);
        data.setPasswd(pass);
        Gson gson = new Gson();
        String json = gson.toJson(data, Model.class);

        HttpUrl.Builder urlBuilder = HttpUrl.parse(u).newBuilder();
        String url = urlBuilder.build().toString();

        Request req = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MediaType.parse("application/json"),json))
                .build();

        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String myResponse = response.body().string();
                Gson gson = new GsonBuilder().create();
                final Model data1 = gson.fromJson(myResponse, Model.class);

                model.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(data1.getSuccess()){
                            Toast.makeText(model.getActivity().getApplicationContext(), "Login Success!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(model.getActivity(), StageActivity.class);
                            intent.putExtra(EXT_NAME, id);
                            model.getActivity().startActivity(intent);
                        }
                        else{
                            Toast.makeText(model.getActivity().getApplicationContext(), "Entered ID is Wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });


    }

    @Override
    public void onRegisterButtonTouched(){
        Intent intent = new Intent(model.getActivity(), RegisterActivity.class);
        model.getActivity().startActivity(intent);
        model.getActivity().finish();
    }


    //////////RegisterActivity//////////

    @Override
    public void onEnrollButtonTouched(EditText editText, EditText editText1){
        String id = editText.getText().toString();
        String pass = editText1.getText().toString();
        String u = "https://90jh18vf43.execute-api.ap-northeast-2.amazonaws.com/dev/adduser";

        OkHttpClient client = new OkHttpClient();
        Model data = new Model();
        data.setName(id);
        data.setPasswd(pass);
        Gson gson = new Gson();
        String json = gson.toJson(data, Model.class);

        HttpUrl.Builder urlBuilder = HttpUrl.parse(u).newBuilder();
        String url = urlBuilder.build().toString();

        Request req = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MediaType.parse("application/json"),json))
                .build();

        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String myResponse = response.body().string();
                Gson gson = new GsonBuilder().create();
                final Model data1 = gson.fromJson(myResponse, Model.class);

                model.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(data1.getSuccess()){
                            Toast.makeText(model.getActivity().getApplicationContext(), "Register Success!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(model.getActivity(), MainActivity.class);
                            model.getActivity().startActivity(intent);
                            model.getActivity().finish();
                        }
                        else{
                            Toast.makeText(model.getActivity().getApplicationContext(), "Entered ID is already exists!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });


    }



    //////////StageActivity//////////

    @Override
    public void initial(TextView[] textViews){
        String u = "https://90jh18vf43.execute-api.ap-northeast-2.amazonaws.com/dev/get/map1";

        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(u).newBuilder();
        String url = urlBuilder.build().toString();

        Request req = new Request.Builder().url(url).build();

        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String myResponse = response.body().string();
                Gson gson = new GsonBuilder().create();
                final Model data = gson.fromJson(myResponse, Model.class);

                model.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        textViews[6].setText("SKY (EASY)");
                        textViews[0].setText(data.getRank1_name());
                        textViews[1].setText(data.getRank2_name());
                        textViews[2].setText(data.getRank3_name());

                        textViews[3].setText(Integer.toString(data.getRank1_map1()));
                        textViews[4].setText(Integer.toString(data.getRank2_map1()));
                        textViews[5].setText(Integer.toString(data.getRank3_map1()));

                    }
                });

            }
        });
    }

    @Override
    public void onImageTouched(ConstraintLayout constraintLayout, ImageView imageView, TextView[] textViews){
        if(model.getSid().equals("0")){
            model.setSid("1");
            constraintLayout.setBackgroundColor(Color.BLACK);
            imageView.setImageResource(R.drawable.space);


            String u = "https://90jh18vf43.execute-api.ap-northeast-2.amazonaws.com/dev/get/map2";

            OkHttpClient client = new OkHttpClient();

            HttpUrl.Builder urlBuilder = HttpUrl.parse(u).newBuilder();
            String url = urlBuilder.build().toString();

            Request req = new Request.Builder().url(url).build();

            client.newCall(req).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    final String myResponse = response.body().string();
                    Gson gson = new GsonBuilder().create();
                    final Model data = gson.fromJson(myResponse, Model.class);

                    model.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            textViews[6].setText("SPACE (HARD)");
                            textViews[0].setText(data.getRank1_name());
                            textViews[1].setText(data.getRank2_name());
                            textViews[2].setText(data.getRank3_name());

                            textViews[3].setText(Integer.toString(data.getRank1_map2()));
                            textViews[4].setText(Integer.toString(data.getRank2_map2()));
                            textViews[5].setText(Integer.toString(data.getRank3_map2()));

                        }
                    });

                }
            });






        }
        else{
            model.setSid("0");
            constraintLayout.setBackgroundColor(Color.BLUE);
            imageView.setImageResource(R.drawable.sky);


            String u = "https://90jh18vf43.execute-api.ap-northeast-2.amazonaws.com/dev/get/map1";

            OkHttpClient client = new OkHttpClient();

            HttpUrl.Builder urlBuilder = HttpUrl.parse(u).newBuilder();
            String url = urlBuilder.build().toString();

            Request req = new Request.Builder().url(url).build();

            client.newCall(req).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    final String myResponse = response.body().string();
                    Gson gson = new GsonBuilder().create();
                    final Model data = gson.fromJson(myResponse, Model.class);

                    model.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            textViews[6].setText("SKY (EASY)");
                            textViews[0].setText(data.getRank1_name());
                            textViews[1].setText(data.getRank2_name());
                            textViews[2].setText(data.getRank3_name());

                            textViews[3].setText(Integer.toString(data.getRank1_map1()));
                            textViews[4].setText(Integer.toString(data.getRank2_map1()));
                            textViews[5].setText(Integer.toString(data.getRank3_map1()));

                        }
                    });

                }
            });





        }
    }

    @Override
    public void onStartButtonTouched(){
        Intent intent = new Intent(model.getActivity(), GameActivity.class);
        intent.putExtra(EXT_NAME, model.getId());
        intent.putExtra(EXT_SID, model.getSid());
        model.getActivity().startActivity(intent);
        model.getActivity().finish();

    }



    //////////GameActivity//////////

    @Override
    public void game_init(ConstraintLayout constraintLayout){
        if(model.getSid().equals("0")){
            constraintLayout.setBackgroundColor(Color.BLUE);
        }
        else{
            constraintLayout.setBackgroundColor(Color.BLACK);
        }
    }

    @Override
    public void onExitButtonTouched(Integer time){
        if(model.getSid().equals("0")){
            String u = "https://90jh18vf43.execute-api.ap-northeast-2.amazonaws.com/dev/map1";

            OkHttpClient client = new OkHttpClient();
            Model data = new Model();
            data.setName(model.getId());
            data.setMap_1(time);
            Gson gson = new Gson();
            String json = gson.toJson(data, Model.class);

            HttpUrl.Builder urlBuilder = HttpUrl.parse(u).newBuilder();
            String url = urlBuilder.build().toString();

            Request req = new Request.Builder()
                    .url(url)
                    .post(RequestBody.create(MediaType.parse("application/json"),json))
                    .build();

            client.newCall(req).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    final String myResponse = response.body().string();
                    Gson gson = new GsonBuilder().create();
                    final Model data1 = gson.fromJson(myResponse, Model.class);

                    model.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(data1.getSuccess()){
                                Intent intent = new Intent(model.getActivity(), StageActivity.class);
                                intent.putExtra(EXT_NAME, model.getId());
                                model.getActivity().startActivity(intent);
                                model.getActivity().finish();

                            }

                        }
                    });

                }
            });

        }
        else{
            String u = "https://90jh18vf43.execute-api.ap-northeast-2.amazonaws.com/dev/map2";

            OkHttpClient client = new OkHttpClient();
            Model data = new Model();
            data.setName(model.getId());
            data.setMap_2(time);
            Gson gson = new Gson();
            String json = gson.toJson(data, Model.class);

            HttpUrl.Builder urlBuilder = HttpUrl.parse(u).newBuilder();
            String url = urlBuilder.build().toString();

            Request req = new Request.Builder()
                    .url(url)
                    .post(RequestBody.create(MediaType.parse("application/json"),json))
                    .build();

            client.newCall(req).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    final String myResponse = response.body().string();
                    Gson gson = new GsonBuilder().create();
                    final Model data1 = gson.fromJson(myResponse, Model.class);

                    model.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(data1.getSuccess()){
                                Intent intent = new Intent(model.getActivity(), StageActivity.class);
                                intent.putExtra(EXT_NAME, model.getId());
                                model.getActivity().startActivity(intent);
                                model.getActivity().finish();
                            }

                        }
                    });

                }
            });

        }


    }



}
