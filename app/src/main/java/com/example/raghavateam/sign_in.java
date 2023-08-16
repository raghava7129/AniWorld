package com.example.raghavateam;

import static com.example.raghavateam.sharespref.PassWord;
import static com.example.raghavateam.sharespref.userName;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class sign_in extends AppCompatActivity {
    Button sub;
    EditText name,password,e;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        sub=findViewById(R.id.Sub);
        name=findViewById(R.id.user);
        password=findViewById(R.id.password_toggle);
        e=findViewById(R.id.Email);

        auth=FirebaseAuth.getInstance();

        String PASSword = sharespref.getString(sign_in.this,PassWord);
        String uN = sharespref.getString(sign_in.this,userName);

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name=name.getText().toString();
                String Password=password.getText().toString();
                String eml=e.getText().toString();
                if(Name.equals("")){
                    name.setError("Enter user name");
                }
                else if(Password.equals("")){
                    password.setError("Enter Password");
                }
                else if(eml.equals("")){
                    e.setError("Enter E mail");
                }
                else{
                    loginMe(eml,Password);
                }
            }
        });
    }

    private void loginMe(String em,String p){
        auth.signInWithEmailAndPassword(em,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    sharespref.set_is_loggedIn(sign_in.this,true);
                    Intent i=new Intent(sign_in.this,Home_page.class);
                    startActivity(i);
                    finish();
                }
                else{
                    Toast.makeText(sign_in.this, "invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}