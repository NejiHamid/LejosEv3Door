package app.gate.door;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PortailActivity extends AppCompatActivity {
    TextView textViewPortail;
    Button btnDeco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portail);

        textViewPortail = (TextView) findViewById(R.id.txtVPortail);
        btnDeco = (Button) findViewById(R.id.btnDecoPortail);

        String msg = "Bienvenue sur le menu d'accès du Portail";
        textViewPortail.setText(msg);

        setupListeners();
    }

    private void setupListeners() {
        btnDeco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PortailActivity.this, ConnectActivity.class);
                startActivity(intent);
            }
        });
    }
}
