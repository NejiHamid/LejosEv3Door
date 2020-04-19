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

public class EditUserActivity extends AppCompatActivity {
    Button btnEditProfil, btnRetour, btnSupp;
    EditText txtNom, txtPrenom, txtLogin, txtPwd;
    int idUser, role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        Intent intent = getIntent();
        idUser = intent.getIntExtra("idUser",0);
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


            UserEntity userEntity = new UserEntity(idUser,nom, prenom,login,pwd,role);
            int res = userDAO.modifier(userEntity);
            Log.i("USER DAO","Modifer invoked, res = "+res);

            if(res==1) { // ok?
                Toast t = Toast.makeText(this, "Profil édité avec succès!", Toast.LENGTH_LONG);
                t.show();

                Log.i("Edit SUCESS","Edition successfull");

            } else {
                Toast t = Toast.makeText(this, "Erreur d'édtion du profil!", Toast.LENGTH_LONG);
                t.show();
                Log.i("Edit failed","Edition error occured");
            }
        }

    }

    private void setupListeners() {
        btnEditProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("EDIT PROFIL","Edition listner invoked");
                checkData();

            }
        });

        btnSupp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("SUPP USER","SUPP listner invoked");
                checkDeletion();

            }
        });

        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditUserActivity.this, GestionAccesActivity.class);
                startActivity(intent);
            }
        });
    }
    private void setupUI() {

        btnEditProfil = (Button) findViewById(R.id.btnEditerU);
        btnSupp = (Button) findViewById(R.id.btnSupp);
        btnRetour = (Button) findViewById(R.id.btnRetourE);
        txtNom = (EditText) findViewById(R.id.txtNomE);
        txtPrenom = (EditText) findViewById(R.id.txtPrenomE);
        txtLogin = (EditText) findViewById(R.id.txtLoginE);
        txtPwd = (EditText) findViewById(R.id.txtPasswordE);


        //Valeur initiales

        UserDAO userDAO = new UserDAO(this);

        UserEntity userEntity = userDAO.selectionner(idUser);

        String nom = userEntity.getNom();
        String prenom = userEntity.getPrenom();
        String login = userEntity.getLogin();
        String pwd = userEntity.getPassword();
        role = userEntity.getRole();

        txtNom.setText(nom);
        txtPrenom.setText(prenom);
        txtLogin.setText(login);
        txtPwd.setText(pwd);


    }

    private void checkDeletion() {
        UserDAO userDAO = new UserDAO(this);

        int res = userDAO.supprimer(idUser);
        Log.i("USER DAO","Modifer invoked, res = "+res);

        if(res==1) { // ok?
            Toast t = Toast.makeText(this, "Profil supprimé avec succès!", Toast.LENGTH_LONG);
            t.show();

            Log.i("SUPP","Suppression is successfull");

        } else {
            Toast t = Toast.makeText(this, "Erreur de suppression du profil!", Toast.LENGTH_LONG);
            t.show();
            Log.i("SUPP","Suppression error occured");
        }
    }
}
