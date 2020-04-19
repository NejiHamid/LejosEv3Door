package app.gate.door;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import app.gate.door.adapters.UserAdapter;
import app.gate.door.models.TeleCEntity;
import app.gate.door.models.UserEntity;
import app.gate.door.models.dao.TeleCDAO;
import app.gate.door.models.dao.UserDAO;

public class EditTCActivity extends AppCompatActivity {
    Button btnEditTC, btnRetourTC, btnSuppTC;
    EditText txtLibelle, txtAdrMAC;
    Spinner spinnerProprio;
    int proprio, idTC, position;

    ArrayList<UserEntity> userEntityArrayList;
    UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tc);

        Intent intent = getIntent();
        idTC = intent.getIntExtra("idTC",0);
        position = intent.getIntExtra("position",0);

        setupUI();
        loadDataInSpinner();
        setupListeners();
    }
    
    private void loadDataInSpinner() {
        UserDAO userDAO = new UserDAO(this);

        userEntityArrayList = new ArrayList<>();
        userEntityArrayList = userDAO.getAllUsers();
        userAdapter = new UserAdapter(this, userEntityArrayList);
        spinnerProprio.setAdapter(userAdapter);
        userAdapter.notifyDataSetChanged();
        spinnerProprio.setSelection(position);
    }
    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    void checkData() {

        boolean isValid = true;
        if (isEmpty(txtLibelle) ) {
            txtLibelle.setError("Champ obligatoire!");
            isValid = false;
        }
        if ( isEmpty(txtAdrMAC)) {
            txtAdrMAC.setError("Champ obligatoire!");
            isValid = false;
        }
        if ( proprio == 0) {
            isValid = false;
        }



        if (isValid) {
            //ajout en base de données ok

            TeleCDAO teleCDAO = new TeleCDAO(this);


            String libelle = txtLibelle.getText().toString();
            String adrMAC = txtAdrMAC.getText().toString();

            TeleCEntity teleCEntity = new TeleCEntity(idTC,libelle, adrMAC,proprio);
            long idTC = teleCDAO.modifier(teleCEntity);
            Log.i("TC DAO","Modifier invoked, new idTC created = "+idTC);

            if(idTC > 0) { // ok?
                Toast t = Toast.makeText(this, "Télécommande ajouté avec succès!", Toast.LENGTH_LONG);
                t.show();

                Log.i("UPADATE TC","UPADATING is successfull");

            } else {
                Toast t = Toast.makeText(this, "Erreur de modification de la nouvelle Télécommande!", Toast.LENGTH_LONG);
                t.show();
                Log.i("ADD TC","UPADATING  failed");
            }
        }

    }

    private void setupListeners() {
        btnEditTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkData();
            }
        });

        btnSuppTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("SUPP USER","SUPP listner invoked");
                checkDeletion();

            }
        });

        btnRetourTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditTCActivity.this, GestionAccesActivity.class);
                startActivity(intent);
            }
        });

        spinnerProprio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                proprio = userEntityArrayList.get(i).getIdUser();
                Log.i("ADD TC","Item  selected proprio= "+proprio);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                proprio = 0;
                Log.i("ADD TC","Item  selected proprio= "+proprio);
            }
        });
    }
    private void setupUI() {

        btnEditTC = (Button) findViewById(R.id.btnEditerTC);
        btnRetourTC = (Button) findViewById(R.id.btnRetourTC);
        btnSuppTC = (Button) findViewById(R.id.btnSuppTC);

        txtLibelle = (EditText) findViewById(R.id.txtLibelleE);
        txtAdrMAC = (EditText) findViewById(R.id.txtAdrMACE);
        spinnerProprio = (Spinner) findViewById(R.id.spinnerProprio);

        //Valeur initiales
        TeleCDAO teleCDAO = new TeleCDAO(this);

        TeleCEntity teleCEntity = teleCDAO.getTCById(idTC);

        String libelle = teleCEntity.getLibelle();
        String adrMAC = teleCEntity.getAdrMAC();
        proprio = teleCEntity.getProprietaire();


        txtLibelle.setText(libelle);
        txtAdrMAC.setText(adrMAC);



    }

    private void checkDeletion() {
        TeleCDAO teleCDAO = new TeleCDAO(this);

        int res = teleCDAO.supprimer(idTC);
        Log.i("TC DAO","Supprimer invoked, res = "+res);

        if(res==1) { // ok?
            Toast t = Toast.makeText(this, "TC supprimée avec succès!", Toast.LENGTH_LONG);
            t.show();

            Log.i("SUPP","Suppression is successfull");

        } else {
            Toast t = Toast.makeText(this, "Erreur de suppression de la TC!", Toast.LENGTH_LONG);
            t.show();
            Log.i("SUPP","Suppression error occured");
        }
    }
}
