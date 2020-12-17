package com.example.firebasecruead;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class TrackList extends ArrayAdapter<Track> {
    private Activity content;
   List<Track> tracks;

    public TrackList(Activity content,List<Track> tracks){
        super(content,R.layout.track_list_layout,tracks);
        this.content = content;
        this.tracks = tracks;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = content.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.track_list_layout,null,true);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewtrackName);
        TextView textViewrating = (TextView) listViewItem.findViewById(R.id.textViewrating);

        Track track = tracks.get(position);

        textViewName.setText(track.getTrackname());
        textViewrating.setText(String.valueOf(track.getTrackrating()));

        return listViewItem;


}}
