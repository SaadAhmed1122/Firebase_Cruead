package com.example.firebasecruead;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    Button btnAdd;
    Spinner spinnerGeners;
    DatabaseReference databaseArtists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        spinnerGeners = findViewById(R.id.spinner2);
        btnAdd= findViewById(R.id.button);
        databaseArtists = FirebaseDatabase.getInstance().getReference("artists");

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addArtist();
            }
        });

    }



    private void addArtist(){
        String name = editText.getText().toString().trim();
        String genre = spinnerGeners.getSelectedItem().toString();

        if(!TextUtils.isEmpty(name)){
            String id = databaseArtists.push().getKey();
            Artist artist = new Artist(id,name,genre);
            databaseArtists.child(id).setValue(artist);

            Toast.makeText(this, "Artist Added Succeessfully", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "You Should Enter a Name", Toast.LENGTH_SHORT).show();
        }

    }

    public void showdata(View view) {
        Intent ii = new Intent(MainActivity.this,Show_artist.class);
        startActivity(ii);
    }
}
