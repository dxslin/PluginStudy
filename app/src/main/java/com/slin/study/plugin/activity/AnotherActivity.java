package com.slin.study.plugin.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.slin.study.plugin.databinding.ActivityMainBinding;

/**
 * author: slin
 * <p>
 * date: 2021/12/13
 * <p>
 * description:
 */
public class AnotherActivity extends AppCompatActivity {

    private final static String TAG = AnotherActivity.class.getSimpleName();

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "OnClick: "+view);
            }
        });


        binding.tvLambda.setVisibility(View.VISIBLE);
        binding.tvLambda.setOnClickListener(view -> Log.d(TAG, "OnClick: "+view));

    }

    public void setOnClickListener(View.OnClickListener listener){
        binding.tvHello.setOnClickListener(listener);

    }
}
