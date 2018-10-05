package com.example.aryap.sayura;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.aryap.sayura.Model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference table_users;

    private MaterialEditText editTextNameUp;
    private MaterialEditText editTextPhoneUp;
    private MaterialEditText editTextPasswordUp;

    private Button btnSignUpUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // initialise the Firebase
        database = FirebaseDatabase.getInstance();
        table_users = database.getReference("Users");

        editTextNameUp = findViewById(R.id.editTextNameUp);
        editTextPhoneUp = findViewById(R.id.editTextPhoneUp);
        editTextPasswordUp = findViewById(R.id.editTextPasswordUp);

        btnSignUpUser = findViewById(R.id.btnSignUpUser);

        btnSignUpUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this);
                progressDialog.setMessage("Please wait...");
                progressDialog.show();

                table_users.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // check if already have an account
                        if(dataSnapshot.child(editTextPhoneUp.getText().toString()).exists())   {
                            progressDialog.dismiss();
                            Toast.makeText(SignUpActivity.this, "Phone number already registered!", Toast.LENGTH_LONG).show();

                        } else {
                            progressDialog.dismiss();
                            Users newUser = new Users(editTextNameUp.getText().toString(), editTextPasswordUp.getText().toString());
                            table_users.child(editTextPhoneUp.getText().toString()).setValue(newUser);
                            Toast.makeText(SignUpActivity.this, "Sign up successful!", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
