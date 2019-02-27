package com.example.yash.music;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;

public class MusicActivity extends AppCompatActivity {

    Button btn_pause,btn_next,btn_prev;
    SeekBar seekBar;
    TextView songname;

    static MediaPlayer mediaPlayer;
    Thread updateseekbar;
    int position;
    String sname;
    ArrayList<File> mysongs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_pause=findViewById(R.id.play);
        btn_next=findViewById(R.id.next);
        btn_prev=findViewById(R.id.previous);
        seekBar=findViewById(R.id.seekbar);
        songname=findViewById(R.id.song_name);

        updateseekbar=new Thread(){

            @Override
            public void run() {
                int duration=mediaPlayer.getDuration();
                int current=0;

                while(current<duration)
                {
                    try {
                        sleep(500);
                        current=mediaPlayer.getCurrentPosition();
                        seekBar.setProgress(current);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        };

        if(mediaPlayer!=null)
        {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        Intent i=getIntent();
        Bundle bundle=i.getExtras();
        assert bundle != null;
        mysongs=(ArrayList) bundle.getParcelableArrayList("songs");
        sname=mysongs.get(position).getName().toString();

        final String song=i.getStringExtra("songname");

        songname.setText(song);
        songname.setSelected(true);

        position=bundle.getInt("pos",0);

        Uri u=Uri.parse(mysongs.get(position).toString());

        mediaPlayer=MediaPlayer.create(getApplicationContext(),u);
        mediaPlayer.start();
        seekBar.setMax(mediaPlayer.getDuration());
        updateseekbar.start();
        seekBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary),PorterDuff.Mode.MULTIPLY);
        seekBar.getThumb().setColorFilter(getResources().getColor(R.color.colorPrimary),PorterDuff.Mode.SRC_IN);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });

        btn_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekBar.setMax(mediaPlayer.getDuration());
                if(mediaPlayer.isPlaying())
                {
                    btn_pause.setBackgroundResource(R.drawable.ic_play_arrow_black_24dp);
                    mediaPlayer.pause();
                }
                else
                {
                    btn_pause.setBackgroundResource(R.drawable.ic_pause);
                    mediaPlayer.start();
                }
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();

                if(position<mysongs.size()-1)
                {
                    position=position+1;
                }
                else
                {
                    position=0;
                }

                Uri u=Uri.parse(mysongs.get(position).toString());
                mediaPlayer=MediaPlayer.create(getApplicationContext(),u);

                sname=mysongs.get(position).getName().toString();
                songname.setText(sname);
                mediaPlayer.start();

            }
        });

        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();

                if(position>0)
                {
                    position=position-1;
                }
                else
                {
                    position=mysongs.size()-1;
                }

                Uri u=Uri.parse(mysongs.get(position).toString());
                mediaPlayer=MediaPlayer.create(getApplicationContext(),u);
                sname=mysongs.get(position).getName().toString();
                songname.setText(sname);
                mediaPlayer.start();
            }
        });
    }
}
