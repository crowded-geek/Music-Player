package com.example.yash.music;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.yash.music.Model.Song;
import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;
import java.util.ArrayList;

import javax.xml.transform.Result;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    SongAdapter songAdapter;
    ArrayList<Song> songs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        songs = new ArrayList<>();
        listView = findViewById(R.id.listView);
        songAdapter = new SongAdapter(MainActivity.this, songs);
        listView.setAdapter(songAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, MusicActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("songs", songs);
                i.putExtras(b);
                i.putExtra("pos", position);
                startActivity(i);
            }
        });
        checkUserPermission();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finish();
                break;
            case R.id.about:
                startActivity(new Intent(MainActivity.this,AboutActivity.class));

        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 123 && resultCode==RESULT_OK){
            loadSongs();
        }
    }

    private void checkUserPermission(){
        if(Build.VERSION.SDK_INT>=24){
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},123);
                return;
            } else {
                loadSongs();
            }
        }
    }

    private void loadSongs(){
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC+"!=0";
        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                    System.out.println(name);
                    String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));

                    Song s = new Song(name,artist,url);
                    songs.add(s);
                    songAdapter.notifyDataSetChanged();
                }while (cursor.moveToNext());
            }

            cursor.close();

        }
    }
}