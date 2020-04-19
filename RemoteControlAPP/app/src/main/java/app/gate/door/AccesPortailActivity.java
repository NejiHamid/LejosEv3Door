package app.gate.door;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import app.gate.door.models.TeleCEntity;
import app.gate.door.models.dao.TeleCDAO;

public class AccesPortailActivity extends AppCompatActivity {
    Button btnConnect, btnRetour;
    EditText txtAdrMAC;
    int idUser, role;
    private  BleuthoothConnection btComm = new BleuthoothConnection();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acces_portail);

        Intent intent = getIntent();
        idUser = intent.getIntExtra("idUser",0);
        role = intent.getIntExtra("role",0);

        setupUI();
        setupListeners();
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    void checkData() {

        boolean isValid = true;

        if ( isEmpty(txtAdrMAC)) {
            txtAdrMAC.setError("Champ obligatoire!");
            isValid = false;
        }

        if (isValid) {
            Log.i("Accès Portail","Connexion Bluetooth OK , adrMAC = "+txtAdrMAC.getText().toString());
            Intent intent = new Intent(AccesPortailActivity.this, RCActivity.class);
            intent.putExtra("idUser",idUser);
            intent.putExtra("role",role);
            intent.putExtra("macAdd",txtAdrMAC.getText().toString());
            startActivity(intent);

        } else {
            Toast t = Toast.makeText(this, "Erreur de connexion avec l'EV3! adrMAC = "+txtAdrMAC.getText().toString(), Toast.LENGTH_LONG);
            t.show();
            Log.i("Accès Portail","Connexion Bluetooth failedadrMAC = "+txtAdrMAC.getText().toString());
        }


    }

    private void setupListeners() {
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkData();
            }
        });


        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccesPortailActivity.this, ChoiceActivity.class);
                startActivity(intent);
            }
        });

    }
    private void setupUI() {

        btnConnect = (Button) findViewById(R.id.btnConnectPortail);
        btnRetour = (Button) findViewById(R.id.btnRetourAP);
        txtAdrMAC = (EditText) findViewById(R.id.txtAdresseMAC);

        //Valeur initiales
        TeleCDAO teleCDAO = new TeleCDAO(this);

        TeleCEntity teleCEntity = teleCDAO.getLoggedUserTC(idUser);

        String adrMAC = teleCEntity.getAdrMAC();

        txtAdrMAC.setText(adrMAC);
    }
}
