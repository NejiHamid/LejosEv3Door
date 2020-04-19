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

import app.gate.door.models.UserEntity;
import app.gate.door.models.dao.UserDAO;

public class NewUserActivity extends AppCompatActivity {
    Button btnAddUser, btnRetourUser;
    EditText txtNom, txtPrenom, txtLogin, txtPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        setupUI();
        setupListeners();
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    void checkData() {

        boolean isValid = true;
        if (isEmpty(txtNom) ) {
            txtNom.setError("Champ obligatoire!");
            isValid = false;
        }
        if ( isEmpty(txtPrenom)) {
            txtPrenom.setError("Champ obligatoire!");
            isValid = false;
        }
        if (isEmpty(txtLogin)) {
            txtLogin.setError("Champ obligatoire!");
            isValid = false;
        }
        if ( isEmpty(txtPwd) ) {
            txtPwd.setError("Champ obligatoire!");
            isValid = false;
        }else {
            if (txtPwd.getText().toString().length() < 4) {
                txtPwd.setError("Le mot de passe doit au moins être de 4 caratcères!");
                isValid = false;
            }
        }


        if (isValid) {
            //ajout en base de données ok

            UserDAO userDAO = new UserDAO(this);


            String nom = txtNom.getText().toString();
            String prenom = txtPrenom.getText().toString();
            String login = txtLogin.getText().toString();
            String pwd = txtPwd.getText().toString();



            UserEntity userEntity = new UserEntity(0,nom, prenom,login,pwd,0);
            long idUser = userDAO.ajouter(userEntity);
            Log.i("USER DAO","Ajouter invoked, new idUser created = "+idUser);

            if(idUser > 0) { // ok?
                Toast t = Toast.makeText(this, "Utilisateur ajouté avec succès!", Toast.LENGTH_LONG);
                t.show();

                Log.i("ADD USER","ADDING is successfull");

            } else {
                Toast t = Toast.makeText(this, "Erreur d'ajout du nouvel utilisateur!", Toast.LENGTH_LONG);
                t.show();
                Log.i("ADD USER","ADDING  failed");
            }
        }

    }

    private void setupListeners() {
        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkData();
            }
        });

        btnRetourUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewUserActivity.this, GestionAccesActivity.class);
                startActivity(intent);
            }
        });
    }
    private void setupUI() {

        btnAddUser = (Button) findViewById(R.id.btnAddUser);
        btnRetourUser = (Button) findViewById(R.id.btnRetourUser);
        txtNom = (EditText) findViewById(R.id.txtNomN);
        txtPrenom = (EditText) findViewById(R.id.txtPrenomN);
        txtLogin = (EditText) findViewById(R.id.txtLoginN);
        txtPwd = (EditText) findViewById(R.id.txtPasswordN);

    }
}
