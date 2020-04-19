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

public class NewTCActivity extends AppCompatActivity {
    Button btnAddTC, btnRetourTC;
    EditText txtLibelle, txtAdrMAC;
    Spinner spinnerProprio;
    int proprio;

    ArrayList<UserEntity> userEntityArrayList;
    UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_tc);


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

            TeleCEntity teleCEntity = new TeleCEntity(0,libelle, adrMAC,proprio);
            long idTC = teleCDAO.ajouter(teleCEntity);
            Log.i("TC DAO","Ajouter invoked, new idTC created = "+idTC);

            if(idTC > 0) { // ok?
                Toast t = Toast.makeText(this, "Télécommande ajouté avec succès!", Toast.LENGTH_LONG);
                t.show();

                Log.i("ADD TC","ADDING is successfull");

            } else {
                Toast t = Toast.makeText(this, "Erreur d'ajout de la nouvelle Télécommande!", Toast.LENGTH_LONG);
                t.show();
                Log.i("ADD TC","ADDING  failed");
            }
        }

    }

    private void setupListeners() {
        btnAddTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkData();
            }
        });

        btnRetourTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewTCActivity.this, GestionAccesActivity.class);
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

        btnAddTC = (Button) findViewById(R.id.btnAddTC);
        btnRetourTC = (Button) findViewById(R.id.btnRetourTC);
        txtLibelle = (EditText) findViewById(R.id.txtLibelle);
        txtAdrMAC = (EditText) findViewById(R.id.txtAdrMAC);
        spinnerProprio = (Spinner) findViewById(R.id.spinnerProprio);


    }
}
