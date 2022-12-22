package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static TextView txt_score, txt_best_score, txt_score_over;
    public static Button txt_restart;
    public static ImageView txt_F_B, btn_play;
    public static ImageView btn_trophy;
    public static ImageView btn_setting;
    public static ImageView btn_skinChange;
    public static RelativeLayout rl_over;
    public static RelativeLayout rl_start;
    public static MediaPlayer media;
    public static ImageView btn_bird1, btn_bird2, btn_bird3;
    Switch mediaOff, musicOff;
    int bird_a, bird_b;
    private GameView gv;
    Dialog dialog, dialogSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Constaint.SCREEN_HEIGHT = dm.heightPixels;
        Constaint.SCREEN_WIDTH = dm.widthPixels;
        setContentView(R.layout.activity_main);

        //Get views id
        txt_restart = findViewById(R.id.txt_restart);
        txt_score = findViewById(R.id.txt_score);
        txt_F_B = findViewById(R.id.txt_F_bird);
        txt_best_score = findViewById(R.id.txt_best_score);
        txt_score_over = findViewById(R.id.txt_score_over);
        rl_over = findViewById(R.id.rl_over);
        rl_start = findViewById(R.id.rl_start);
        btn_setting = findViewById(R.id.btn_Setting);
        btn_play = findViewById(R.id.btn_play);
        btn_skinChange = findViewById(R.id.btn_skinChange);
        btn_trophy = findViewById(R.id.btn_trophy);
        gv = findViewById(R.id.gv);

        //music
        media = MediaPlayer.create(this, R.raw.sillychipsong);
        media.setLooping(true);
        media.start();

        //set default bird
        bird_a = R.drawable.bird_new1;
        bird_b = R.drawable.bird_new2;

        gv.intBird(bird_a, bird_b);

        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogSetting.show();
            }
        });

        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gv.setStart(true);
                rl_start.setVisibility((View.INVISIBLE));
                txt_score.setVisibility(View.VISIBLE);
                txt_F_B.setVisibility(View.INVISIBLE);
            }
        });

        txt_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_F_B.setVisibility(View.VISIBLE);
                rl_over.setVisibility(View.INVISIBLE);
                rl_start.setVisibility(View.VISIBLE);
                gv.setStart(false);
                gv.intBird(bird_a, bird_b);
                gv.reset();
            }
        });

        btn_skinChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
        showDialog();
        showDialogSetting();

        btn_trophy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    protected void onResume() {
        super.onResume();
        media.start();
    }

    protected void onPause() {
        super.onPause();
        media.pause();
    }

    public void showDialog() {
        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.skindialog);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.bg_dialog));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);

        btn_bird1 = dialog.findViewById(R.id.bird1);
        btn_bird2 = dialog.findViewById(R.id.bird2);
        btn_bird3 = dialog.findViewById(R.id.bird3);

        btn_bird1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bird_a = R.drawable.bird1;
                bird_b = R.drawable.bird2;
                gv.intBird(bird_a, bird_b);
                dialog.dismiss();
            }
        });

        btn_bird2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bird_a = R.drawable.bird_new1;
                bird_b = R.drawable.bird_new2;
                gv.intBird(bird_a, bird_b);
                dialog.dismiss();
            }
        });

        btn_bird3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bird_a = R.drawable.twitterlogo;
                bird_b = R.drawable.twitterlogo;
                gv.intBird(bird_a, bird_b);
                dialog.dismiss();
            }
        });
    }

    public void showDialogSetting() {
        dialogSetting = new Dialog(MainActivity.this);
        dialogSetting.setContentView(R.layout.setting_dia_log);
        dialogSetting.getWindow().setBackgroundDrawable(getDrawable(R.drawable.bg_dialog));
        dialogSetting.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogSetting.setCancelable(true);

        mediaOff = dialogSetting.findViewById(R.id.turnOff_media);
        musicOff = dialogSetting.findViewById(R.id.turnOff_music);

        mediaOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b == true){
                    GameView.Loadsound = false;
                }else{
                    GameView.Loadsound = true;
                }
            }
        });

        musicOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b == true){
                    media.pause();
                }else{
                    media.start();
                }
            }
        });

    }


    @Override
    public void onBackPressed() {
        if(dialog.isShowing() || dialogSetting.isShowing()) {
            dialog.dismiss();
            dialogSetting.dismiss();
        } else {
            super.onBackPressed();
        }
    }
}