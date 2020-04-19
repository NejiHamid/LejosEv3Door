package app.gate.door;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ChoiceActivity extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    Button btnEditProfil, btnGererAcces, btnAccesPortail, btnDeco;
    int idUser, role ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);

        Intent intent =getIntent();

        idUser = intent.getIntExtra("idUser",'0');
        role = intent.getIntExtra("role",'0');

        btnAccesPortail = (Button) findViewById(R.id.btnAccesPortail);
        btnGererAcces = (Button) findViewById(R.id.btnGestionAcces);
        btnEditProfil= (Button) findViewById(R.id.btnEditProfil);
        btnDeco= (Button) findViewById(R.id.btnDeconnexion);

        if(role == 0) {
            btnGererAcces.setVisibility(View.GONE);
        }else {
            btnGererAcces.setVisibility(View.VISIBLE);
        }

        setupListeners();
    }

    private void setupListeners() {

        btnGererAcces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChoiceActivity.this, GestionAccesActivity.class);
                startActivity(intent);
            }
        });
        // }

        btnEditProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChoiceActivity.this, ProfilActivity.class);
                intent.putExtra("idUser",idUser);
                intent.putExtra("role",role);
                startActivity(intent);
            }
        });

        btnAccesPortail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChoiceActivity.this, AccesPortailActivity.class);
                intent.putExtra("idUser",idUser);
                intent.putExtra("role",role);
                startActivity(intent);
            }
        });

        btnDeco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChoiceActivity.this, ConnectActivity.class);
                startActivity(intent);
            }
        });
    }

}
