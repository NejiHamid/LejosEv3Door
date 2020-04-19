package app.gate.door;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import app.gate.door.models.UserEntity;
import app.gate.door.models.dao.UserDAO;

public class ConnectActivity extends AppCompatActivity {

    private EditText txtLogin, txtPassword;
    private Button btnConnect;
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnConnect = (Button) findViewById(R.id.btnConnect);
        txtLogin= (EditText) findViewById(R.id.txtLoginC);
        txtPassword= (EditText) findViewById(R.id.txtPasswordC);

        userDAO = new UserDAO(this);

        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = txtLogin.getText().toString().trim();
                String pwd = txtPassword.getText().toString().trim();

                //verification si le user loggué existe dans la base
                UserEntity userEntity = userDAO.getLoggedUser(login,pwd);

                if(userEntity != null) { //login ok
                    Intent intent = new Intent(ConnectActivity.this, ChoiceActivity.class);
                    intent.putExtra("idUser",userEntity.getIdUser());
                    intent.putExtra("role",userEntity.getRole());

                    startActivity(intent);

                }else {
                    Toast.makeText(ConnectActivity.this, "Login ou mot de passe incorrect", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


}
