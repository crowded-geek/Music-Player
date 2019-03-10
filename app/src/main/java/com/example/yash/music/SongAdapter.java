package com.example.yash.music;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.yash.music.Model.Song;

import java.util.ArrayList;

public class SongAdapter extends ArrayAdapter<Song> {

    Context context;
    ArrayList<Song> songs;


    public SongAdapter(Context context, int resource,ArrayList<Song> songs) {
        super(context, resource);
        this.context = context;
        this.songs = songs;
    }

    public SongAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.custom_list, null);
        ((TextView)v.findViewById(R.id.song_name)).setText(songs.get(position).getSongName());
        ((TextView)v.findViewById(R.id.artist_name)).setText(songs.get(position).getSongArtist());
        return  v;
    }
}
