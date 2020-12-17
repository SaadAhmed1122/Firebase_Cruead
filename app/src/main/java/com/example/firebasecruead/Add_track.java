package com.example.firebasecruead;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Add_track extends AppCompatActivity {
    TextView TextViewArtistName;
    EditText editTextTrackName;
    SeekBar seekBarrting;
    DatabaseReference databasetracks;
Button buttonAddtrack;
    ListView listViewTrack;

    List<Track> tracks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_track);

        TextViewArtistName = (TextView) findViewById(R.id.textTrackName);
        editTextTrackName = (EditText)  findViewById(R.id.editTextTrackName);
        seekBarrting = (SeekBar) findViewById(R.id.seekBar);
        buttonAddtrack =(Button) findViewById(R.id.Add_track);
        listViewTrack = findViewById(R.id.listViewArtist_track);

        Intent intent=getIntent();
        tracks = new ArrayList<>();
        String id = intent.getStringExtra(Show_artist.ARTIST_ID);
        String name= intent.getStringExtra(Show_artist.ARTIST_NAME);
        TextViewArtistName.setText(name);
        databasetracks = FirebaseDatabase.getInstance().getReference("tracks").child(id);

        buttonAddtrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savetrack();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        databasetracks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                tracks.clear();

                for(DataSnapshot tracksnap: dataSnapshot.getChildren()){
                    Track track = tracksnap.getValue(Track.class);
                    tracks.add(track);

                }
                TrackList trackListadaptor = new TrackList(Add_track.this,tracks);
                listViewTrack.setAdapter(trackListadaptor);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void savetrack() {
        String trackName = editTextTrackName.getText().toString().trim();
        int rating= seekBarrting.getProgress();
        if(!TextUtils.isEmpty(trackName)){
            String id = databasetracks.push().getKey();
            Track track = new Track(id, trackName,rating);
            databasetracks.child(id).setValue(track);
            Toast.makeText(this, "Track Enter Suuccess fully", Toast.LENGTH_SHORT).show();

        }
        else {
            Toast.makeText(this, "Plz Enter the track", Toast.LENGTH_SHORT).show();
        }
    }
}
