package com.goribmanush.uniassist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


public class Splash_Screen extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);


        /** STARTING OF ANIMATION TEAM LOGO */

        ImageView ivFirstLogo = (ImageView) findViewById(R.id.iv_anim_1);
        Animation an1 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.alpha_1);
        an1.setStartTime(Animation.START_ON_FIRST_FRAME);
        ivFirstLogo.startAnimation(an1);

        ImageView ivFirstText = (ImageView) findViewById(R.id.iv_anim_2);
        Animation an2 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.alpha_2);
        an2.setStartTime(0);
        ivFirstText.startAnimation(an2);

        ImageView ivSecondLogo = (ImageView) findViewById(R.id.iv_anim_3);
        Animation an3 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.alpha_3);
        an3.setStartTime(0);
        ivSecondLogo.startAnimation(an3);

        ImageView ivSecondText = (ImageView) findViewById(R.id.iv_anim_4);
        Animation an4 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.alpha_4);
        an4.setStartTime(0);
        ivSecondText.startAnimation(an4);

        ImageView ivFinalDelay = (ImageView) findViewById(R.id.iv_anim_delay);
        Animation an5 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.final_delay);
        an5.setStartTime(0);
        ivFinalDelay.startAnimation(an5);


        final Intent i = new Intent(this,MainActivity.class);


        an5.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                finish();
                startActivity(i);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });




    }




}
