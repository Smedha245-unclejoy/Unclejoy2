package com.example.medhasingh.unclejoy2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.animation.Animator;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


public class Welcome_logo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_logo);
        final View thumbImageView = findViewById(R.id.logo2);

        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.welcome_animation);
        thumbImageView.setAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                finish();
                startActivity(new Intent(getApplicationContext(),GetStarted.class));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

        });
    }
}






