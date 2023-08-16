package com.example.raghavateam;

import static com.example.raghavateam.sharespref.phoneNo;
import static com.example.raghavateam.sharespref.userName;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.WindowDecorActionBar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class character_page extends AppCompatActivity implements select_char{

    RecyclerView r;

    HashMap<String,String> select_data=new HashMap<>();
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_page);


        initializeViews();

        ActionBar actionbar= getSupportActionBar();
        actionbar.setTitle("COTE CAST");
        actionbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.ActionColor)));

        ArrayList<String> names=new ArrayList<>();
        names.add("Ayanokoji");
        names.add("Arisu");
        names.add("Horikita");
        names.add("Ryuen");
        names.add("Hashimoto");
        names.add("Ken Sudo");
        names.add("Koenji");
        names.add("Manabu");
        names.add("Ichinose");
        names.add("Nagumo");

        ArrayList<Integer> imgs=new ArrayList<Integer>();
        imgs.add(R.drawable.char_0);
        imgs.add(R.drawable.char_1);
        imgs.add(R.drawable.char_2);
        imgs.add(R.drawable.char_3);
        imgs.add(R.drawable.char_4);
        imgs.add(R.drawable.char_5);
        imgs.add(R.drawable.char_6);
        imgs.add(R.drawable.char_7);
        imgs.add(R.drawable.char_8);
        imgs.add(R.drawable.char_9);

        ArrayList<character_model> al=new ArrayList<>();

        character_model c0 = new character_model(names.get(0),imgs.get(0));
        character_model c1 = new character_model(names.get(1),imgs.get(1));
        character_model c2 = new character_model(names.get(2),imgs.get(2));
        character_model c3 = new character_model(names.get(3),imgs.get(3));
        character_model c4 = new character_model(names.get(4),imgs.get(4));
        character_model c5 = new character_model(names.get(5),imgs.get(5));
        character_model c6 = new character_model(names.get(6),imgs.get(6));
        character_model c7 = new character_model(names.get(7),imgs.get(7));
        character_model c8 = new character_model(names.get(8),imgs.get(8));
        character_model c9 = new character_model(names.get(9),imgs.get(9));

        al.add(c0);
        al.add(c1);
        al.add(c2);
        al.add(c3);
        al.add(c4);
        al.add(c5);
        al.add(c6);
        al.add(c7);
        al.add(c8);
        al.add(c9);



          // for vertical view
//        LinearLayoutManager lm=new LinearLayoutManager(character_page.this);
//        r.setLayoutManager(lm);

        // for grid view
        GridLayoutManager glm =new GridLayoutManager(character_page.this,2,LinearLayoutManager.VERTICAL,false);
        r.setLayoutManager(glm);

        auth=FirebaseAuth.getInstance();

        RecyclerViewAdapter obj =new RecyclerViewAdapter(character_page.this,al,this);
        r.setAdapter(obj);

    }
    public void initializeViews(){
        r=findViewById(R.id.rv);
    }

    @Override
    public void onCardSelected(character_model cm) {
        String user = sharespref.getString(character_page.this,userName);
//        String ph= sharespref.getString(character_page.this,phoneNo);
        Toast.makeText(this, cm.getName() + " is Selected !!", Toast.LENGTH_SHORT).show();
           FirebaseUser User= auth.getCurrentUser();
            DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("User_Data").child(User.getUid());

            dbref.child("fav_char").setValue(cm.getName());
//            dbref.child("Phone_no").setValue(ph);
    }

    @Override
    public void onCardSelected(model_friend cm) {

    }
}