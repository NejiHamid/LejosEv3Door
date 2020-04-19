package app.gate.door;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import app.gate.door.adapters.TCAdapter;
import app.gate.door.adapters.UserAdapter;
import app.gate.door.models.TeleCEntity;
import app.gate.door.models.UserEntity;
import app.gate.door.models.dao.TeleCDAO;
import app.gate.door.models.dao.UserDAO;

public class GestionAccesActivity extends AppCompatActivity {
    Button btnNewUser, btnNewTC, btnRetour;
    ListView listeUsers = null, listeTC = null;
    ArrayList<UserEntity> userEntityArrayList;
    ArrayList<TeleCEntity> teleCEntityArrayList;
    UserAdapter userAdapter;
    TCAdapter tcAdapter;
    int role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_acces);

        listeUsers = (ListView) findViewById(R.id.listUsers);
        listeTC = (ListView) findViewById(R.id.listTC);
        userEntityArrayList = new ArrayList<>();
        teleCEntityArrayList = new ArrayList<>();

        Intent intent =getIntent();

        role = intent.getIntExtra("role",'0');

        btnNewUser = (Button) findViewById(R.id.btnNewUser);
        btnNewTC = (Button) findViewById(R.id.btnNewTC);
        btnRetour = (Button) findViewById(R.id.btnRetourAccess);

        loadDataInListView();

        setupListeners();
    }

    private void loadDataInListView() {
        UserDAO userDAO = new UserDAO(this);
        TeleCDAO teleCDAO = new TeleCDAO(this);

        userEntityArrayList = userDAO.getOrdinaryUsers();
        userAdapter = new UserAdapter(this, userEntityArrayList);
        listeUsers.setAdapter(userAdapter);
        userAdapter.notifyDataSetChanged();

        teleCEntityArrayList = teleCDAO.getAllTC();
        tcAdapter = new TCAdapter(this, teleCEntityArrayList);
        listeTC.setAdapter(tcAdapter);
        tcAdapter.notifyDataSetChanged();
    }

    private void setupListeners() {
        btnNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GestionAccesActivity.this, NewUserActivity.class);
                startActivity(intent);

            }
        });

        btnNewTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GestionAccesActivity.this, NewTCActivity.class);
                startActivity(intent);
            }
        });

        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GestionAccesActivity.this, ChoiceActivity.class);
                intent.putExtra("role",role);
                startActivity(intent);
            }
        });


        listeUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                UserEntity userEntity = (UserEntity) adapterView.getItemAtPosition(position);
                if(userEntity.getIdUser()>0) {
                    Intent intent = new Intent(GestionAccesActivity.this, EditUserActivity.class);
                    intent.putExtra("idUser", userEntity.getIdUser());
                    startActivity(intent);
                }else {
                    Toast.makeText(GestionAccesActivity.this, "Erreur de selection de l'item", Toast.LENGTH_LONG).show();
                }



            }
        });

        listeTC.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                TeleCEntity teleCEntity = (TeleCEntity) adapterView.getItemAtPosition(position);
                if(teleCEntity.getIdTC()>0) {
                    Intent intent = new Intent(GestionAccesActivity.this, EditTCActivity.class);
                    intent.putExtra("idTC", teleCEntity.getIdTC());
                    intent.putExtra("position", position);
                    startActivity(intent);
                }else {
                    Toast.makeText(GestionAccesActivity.this, "Erreur de selection de l'item", Toast.LENGTH_LONG).show();
                }



            }
        });
    }
}
