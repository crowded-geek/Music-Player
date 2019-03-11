package com.example.yash.music;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yash.music.Model.Song;

import java.util.ArrayList;

public class SongAdapter extends BaseAdapter {

    Context context;
    ArrayList<Song> songs;


    public SongAdapter(Context context,ArrayList<Song> songs) {
        this.context = context;
        this.songs = songs;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.custom_list, null);
        ((TextView)v.findViewById(R.id.song_name)).setText(songs.get(position).getSongName().replace(".mp3","").replace(".wav",""));
        ((TextView)v.findViewById(R.id.artist_name)).setText(songs.get(position).getSongArtist());
        return  v;
    }

    @Override
    public int getCount() {
        return songs.size();
    }

    @Override
    public Song getItem(int i) {
        return songs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
}