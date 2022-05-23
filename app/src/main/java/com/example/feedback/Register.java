package com.example.feedback;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

class users{
    private String name,mail;
    public users(String mail,String name){
        this.mail = mail;
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}

public class Register extends AppCompatActivity {
    EditText email,pass,name,pass2;
    Button b1,b2;
    FirebaseAuth fauth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = findViewById(R.id.Remail);
        pass = findViewById(R.id.Rpassword);
        pass2 = findViewById(R.id.Rpassword2);
        name = findViewById(R.id.Rname);
        b1 = findViewById(R.id.Rbutton);
        fauth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        //fstore = FirebaseFirestore.getInstance();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = email.getText().toString();
                String password1 = pass.getText().toString();
                String password2 = pass2.getText().toString();
                String name1 = name.getText().toString();
                fauth.createUserWithEmailAndPassword(mail,password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this,"success",Toast.LENGTH_LONG).show();
                            uid = fauth.getCurrentUser().getUid();

                            databaseReference = firebaseDatabase.getReference("Users").child(name1);
                            users use=new users(mail,name1);
                            databaseReference.setValue(use);
                            Intent intent = new Intent(Register.this, Login.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(Register.this,"not success",Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });
    }
}