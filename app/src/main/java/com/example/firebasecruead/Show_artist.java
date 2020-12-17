package com.example.firebasecruead;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Show_artist extends AppCompatActivity {

    public static final String ARTIST_NAME = "AritstName";
    public static final String ARTIST_ID = "Aritstid";



    ListView showaritstlist;
    DatabaseReference databaseArtists;
    List<Artist> artistList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_artist);

        artistList = new ArrayList<>();
        showaritstlist = findViewById(R.id.listViewArtist_track);
        databaseArtists = FirebaseDatabase.getInstance().getReference("artists");

        showaritstlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Artist artist = artistList.get(position);

                Intent ia = new Intent(getApplicationContext(),Add_track.class);
                ia.putExtra(ARTIST_ID, artist.getArtistId());
                ia.putExtra(ARTIST_NAME, artist.getArtistName());
                startActivity(ia);

            }
        });

        showaritstlist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Artist artist = artistList.get(position);
                showUpdtaedialog(artist.getArtistId(),artist.getArtistName());

                return false;
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseArtists.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                artistList.clear();

                for(DataSnapshot artistdatasnap: dataSnapshot.getChildren()){
                    Artist artist = artistdatasnap.getValue(Artist.class);
                    artistList.add(artist);

                }
                ArtistList adaptor = new ArtistList(Show_artist.this,artistList);
                showaritstlist.setAdapter(adaptor);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    private void showUpdtaedialog(final String artistId, String artistName){
        AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogview =inflater.inflate(R.layout.update_dailog,null);
        dialogbuilder.setView(dialogview);

        final EditText editText = (EditText) dialogview.findViewById(R.id.editText2);
        final Button button = (Button) dialogview.findViewById(R.id.button3);
        final Spinner spinner = (Spinner) dialogview.findViewById(R.id.spinner12);
        final Button delete_btn = (Button) dialogview.findViewById(R.id.button_delete);

        dialogbuilder.setTitle("Updating Aritst:\n"+ artistName);

        final AlertDialog alertDialog = dialogbuilder.create();
        alertDialog.show();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String name= editText.getText().toString().trim();
            String genre = spinner.getSelectedItem().toString();
            if(TextUtils.isEmpty(name)){
             editText.setError("Name Required");
             return;
            }
            updateAritist(artistId,name,genre);
            alertDialog.dismiss();
            }
        });
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteArtist(artistId);
            }
        });

        }

    private void deleteArtist(String artistId) {
    DatabaseReference drAritist=  FirebaseDatabase.getInstance().getReference("artists").child(artistId);
    DatabaseReference drTracks= FirebaseDatabase.getInstance().getReference("tracks").child(artistId);
    drAritist.removeValue();
    drTracks.removeValue();

        Toast.makeText(this, "Artist Deleted Successfully", Toast.LENGTH_SHORT).show();
    }

    private boolean updateAritist(String id, String name, String genre){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("artists").child(id);
        Artist artist = new Artist(id,name, genre);
        databaseReference.setValue(artist);
        Toast.makeText(this, "Artist Update Successfully", Toast.LENGTH_SHORT).show();
        return true;
    }
}
