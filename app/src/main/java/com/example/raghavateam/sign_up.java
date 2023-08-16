package com.example.raghavateam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class sign_up extends AppCompatActivity {
    Button signup_butt;
    RadioGroup Rg;
    EditText FName,LastName,Email,PhoneNumber,Pass,u;
    Spinner s;
    private FirebaseAuth auth;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signup_butt=findViewById(R.id.sub_signup);
        FName=findViewById(R.id.fname);
        LastName=findViewById(R.id.lname);
        Email=findViewById(R.id.mail);
        PhoneNumber=findViewById(R.id.numb);
        Pass=findViewById(R.id.pass);
        u=findViewById(R.id.user);
        Rg=findViewById(R.id.rg);
        s=findViewById(R.id.spin);
        auth=FirebaseAuth.getInstance();

        String[] countries={"India","Russia","Japan","Nepal","USA","Germany","Canada","Dubai"};

        ArrayAdapter<String> add =new ArrayAdapter<>(sign_up.this,R.layout.activity_spin_c,countries);
        s.setAdapter(add);
        final String[] myc = new String[1];
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                myc[0] =countries[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        signup_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fn=FName.getText().toString();
                String ln=LastName.getText().toString();
                String nu=PhoneNumber.getText().toString();
                String mail=Email.getText().toString();
                String sec=Pass.getText().toString();
                String un=u.getText().toString();

                if(fn.equals("Enter First name")){
                    FName.setError("");
                }
                else if(ln.equals("")){
                    LastName.setError("Enter Last name");
                }
                else if(mail.equals("")){
                    Email.setError(" Enter Email");
                }
                else if(nu.equals("") || nu.length()!=10){
                    PhoneNumber.setError("Enter phone number");
                }
                else if(sec.length()<6){
                    Pass.setError(" Password must be at least 6 characters ");
                }
                else{
                    int gen=Rg.getCheckedRadioButtonId();
                    RadioButton rb=findViewById(gen);
                    String GEN=rb.getText().toString();
                    String EM=Email.getText().toString();

                   String Pa = Pass.getText().toString();

                   auth.createUserWithEmailAndPassword(EM,Pa).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if(task.isSuccessful()){
                               FirebaseUser user = auth.getCurrentUser();
                                ref= FirebaseDatabase.getInstance().getReference("User_Data").child(user.getUid());

                               if(user!=null){
                                   HashMap<String,Object> hashMap=new HashMap<>();
                                   hashMap.put("username",un);
                                   hashMap.put("email",EM);
                                   hashMap.put("id",user.getUid());

                                   String def="default";
                                   hashMap.put("imgURL",def);

                                   ref.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                       @Override
                                       public void onComplete(@NonNull Task<Void> task) {
                                           Toast.makeText(sign_up.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();

                                           Intent i=new Intent(sign_up.this,MainActivity.class);
                                           startActivity(i);
                                       }
                                   });
                               }
                           }
                           else{
                               Toast.makeText(sign_up.this,"User Alredy registered !!",Toast.LENGTH_SHORT).show();
                           }
                       }
                   });
                   sharespref.saveString(sign_up.this,sharespref.PassWord,Pa);

                    sharespref.saveSignUp(sign_up.this,un,mail,nu,GEN,myc[0],sec);


                }

            }
        });
    }
}