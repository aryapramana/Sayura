package com.example.aryap.sayura;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.aryap.sayura.Common.Common;
import com.example.aryap.sayura.Model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignInActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference table_users;

    private MaterialEditText editTextPhone;
    private MaterialEditText editTextPassword;

    private Button btnSignInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // initialise the Firebase
        database = FirebaseDatabase.getInstance();
        table_users = database.getReference("Users");

        editTextPhone = findViewById(R.id.editTextPhone);
        editTextPassword = findViewById(R.id.editTextPassword);

        btnSignInUser = findViewById(R.id.btnSignInUser);

        btnSignInUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create a progress dialog
                final ProgressDialog progressDialog = new ProgressDialog(SignInActivity.this);
                progressDialog.setMessage("Please wait...");
                // end of create a progress dialog

                progressDialog.show();

                table_users.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // check if user is exist or not
                        if(dataSnapshot.child(editTextPhone.getText().toString()).exists()) {
                            // get user information from field
                            progressDialog.dismiss();

                            Users user = dataSnapshot.child(editTextPhone.getText().toString()).getValue(Users.class);
                            user.setPhone(editTextPhone.getText().toString()); // temporary set phone data
                            if (user.getPassword().equals(editTextPassword.getText().toString())) {
                                Intent HomeIntent = new Intent(SignInActivity.this, HomeActivity.class);
                                Common.current_user = user;
                                startActivity(HomeIntent);
                                finish();
                                Toast.makeText(SignInActivity.this, "Signed In!", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(SignInActivity.this, "Phone or Password incorrect!", Toast.LENGTH_LONG).show();
                            }
                        } else  {
                            progressDialog.dismiss();
                           Toast.makeText(SignInActivity.this, "User doesn't exist!", Toast.LENGTH_LONG).show();

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
