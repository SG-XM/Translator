package com.example.translater;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private Button button;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
   

///////////////////////////////////////////////翻译按钮点击事件/////////////////////////////////////
        Button button = findViewById(R.id.main_button);
        button.setTypeface(Typeface.createFromAsset(getAssets(), "font/Futura LT Bold.ttf"));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Toast.makeText(MainActivity.this, "dddd", Toast.LENGTH_SHORT).show();
                String word=editText.getText().toString();

                if(!word.matches("[a-zA-Z]+")){
                    return;
                }
                Retrofit retrofit = new Retrofit
                        .Builder()
                        .baseUrl("http://www.iciba.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                TranslateAPI translateAPI = retrofit.create(TranslateAPI.class);


                translateAPI.getTranslation(word).enqueue(new Callback<TranslationResult>() {
                //translateAPI.getTranslation().enqueue(new Callback<TranslationResult>() {
                @Override
                    public void onResponse(Call<TranslationResult> call, Response<TranslationResult> response) {

                        if(response.isSuccessful()){
                            //Toast.makeText(MainActivity.this, "yyy", Toast.LENGTH_SHORT).show();
                            TranslationResult result = response.body();

                            String translation = result.getBaesInfo().getSymbols().get(0).getParts().get(0).getMeans().get(0);

                            editText.setText(translation);

                        }else {
                            //Toast.makeText(MainActivity.this, "shibai", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<TranslationResult> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "查询失败/(ㄒoㄒ)/~~", Toast.LENGTH_SHORT).show();
                        System.out.println("////////////////////////////////////");
                        t.printStackTrace();
                        System.out.println("////////////////////////////////////");
                    }
                });
            }
        });
///////////////////////////////////////////翻译按钮点击事件/////////////////////////////////////

//////////////////////////////////////////设置edittext获取焦点后清空内容/////////////////////////////////////
        editText = findViewById(R.id.edittext);
        editText.setTypeface(Typeface.createFromAsset(getAssets(), "font/Futura LT Bold.ttf"));
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == true) {
                    String str = editText.getText().toString();
                    if (editText.getText().toString().equals("Type here")) {
                        editText.setText("");
                        editText.setTextColor(getResources().getColor(R.color.colorText));

                    }

                }
            }
        });
//////////////////////////////////////////设置edittext获取焦点后清空内容/////////////////////////////////////


    }
}