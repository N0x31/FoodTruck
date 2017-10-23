package fr.wcs.foodtruck;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class AdminAccueil extends AppCompatActivity {

    private Button mDeco;
    private FirebaseAuth mAuth;
    private int mBackButtonCount = 0;
    private Button buttonSend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_accueil);

        ImageView menu = (ImageView) findViewById(R.id.menu);
        ImageView lieu = (ImageView) findViewById(R.id.lieu);
        ImageView event = (ImageView) findViewById(R.id.event);
        ImageView contact = (ImageView) findViewById(R.id.contact);
        mDeco = (Button) findViewById(R.id.deconnect);

        mAuth = FirebaseAuth.getInstance();

        final Intent disconnect = new Intent(AdminAccueil.this, MainActivity.class);
        mDeco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(disconnect);
            }
        });


        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminAccueil.this, AdminListeMenuDuJour.class);
                startActivity(intent);
            }
        });

        lieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminAccueil.this, ListLocalisationAdmin.class);
                startActivity(intent);
            }
        });

        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminAccueil.this, AdminEvent.class);
                startActivity(intent);
            }
        });

        /*contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminAccueil.this, ContactPrivatisation.class);
                startActivity(intent);
            }
        });*/

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminAccueil.this, ChoixReserveContactAdmin.class);
                startActivity(intent);
            }
        });

        // Start Service
        Intent serviceIntent = new Intent(this,NotificationServiceCommande.class);
        startService(serviceIntent);

        Intent serviceIntent2 = new Intent(this,NotificationServiceContact.class);
        startService(serviceIntent2);

        // // TODO: 23/10/17 ajout shardpreference

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("admin", true);
        editor.commit();

    }
    @Override
    protected void onResume() {
        super.onResume();
        mBackButtonCount = 0;
    }

    @Override
    public void onBackPressed() {
        if(mBackButtonCount > 0) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "On va se calmer", Toast.LENGTH_SHORT).show();
        }
    }

}
