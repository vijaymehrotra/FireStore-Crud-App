package com.example.firestorecrud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    EditText title,disc;
    Button save,show;

    String uid,uTitle , uDisc;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = findViewById(R.id.title);
        disc = findViewById(R.id.disc);
        save = findViewById(R.id.save);
        show = findViewById(R.id.show);

        db =  FirebaseFirestore.getInstance();
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            save.setText("Update");
            uTitle = bundle.getString("uTitle");
            uid = bundle.getString("uid");
            uDisc = bundle.getString("uDisc");

            title.setText(uTitle);
            disc.setText(uDisc);
        }
        else{
            save.setText("Save");
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titles = title.getText().toString();
                String mdisc = disc.getText().toString();

                Bundle bundle1 = getIntent().getExtras();
                if(bundle1 != null){
                    String id = uid;
                    updateToFireStore(id ,titles,mdisc);
                }else{
                    String id = UUID.randomUUID().toString();
                    saveToFireStore(id , titles , mdisc);
                }

            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this , com.example.firestorecrud.show.class));
            }
        });
    }

    private void updateToFireStore(String id,String title , String disc){
        db.collection("Documents").document(id).update("title",title , "disc",disc).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                    Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Error :"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void saveToFireStore(String id , String title ,String disc){
        if(!title.isEmpty() && !disc.isEmpty()){
            Map<String, Object> map = new HashMap<>();
            map.put("id" , id);
            map.put("title" , title);
            map.put("disc" , disc);

            db.collection("Documents").document(id).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(MainActivity.this, "Data Saved", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "Failesd", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            Toast.makeText(this, "Enpty Fields not allowed", Toast.LENGTH_SHORT).show();
        }
    }
}