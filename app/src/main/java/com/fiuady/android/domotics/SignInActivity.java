package com.fiuady.android.domotics;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fiuady.android.domotics.db.Inventory;

public class SignInActivity extends AppCompatActivity {
    private Inventory inventory;
    private EditText txtFirstName;
    private EditText txtLastName;
    private EditText txtUserName;
    private EditText txtPassword;
    private EditText txtPassword2;
    private Button btnSave;
    private Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        inventory=new Inventory (getApplicationContext());

        txtFirstName = (EditText) findViewById(R.id.txt_NewFirstName);
        txtLastName = (EditText) findViewById(R.id.txt_NewLastName);
        txtUserName = (EditText) findViewById(R.id.txt_NewUserName);
        txtPassword = (EditText) findViewById(R.id.txt_NewPassword);
        txtPassword2 = (EditText) findViewById(R.id.txt_NewPassword2);

        btnSave = (Button) findViewById(R.id.btn_SaveNewUser);
        btnCancel = (Button) findViewById(R.id.btn_CancelUser);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String FN = txtFirstName.getText().toString();
                String LN = txtLastName.getText().toString();
                String UN = txtUserName.getText().toString();
                String Password = txtPassword.getText().toString();
                String Password2 = txtPassword2.getText().toString();

                if(txtFirstName.getText().toString().isEmpty() || txtLastName.getText().toString().isEmpty()
                        || txtUserName.getText().toString().isEmpty() || txtPassword.getText().toString().isEmpty()
                        || txtPassword2.getText().toString().isEmpty()  ){
                    Toast.makeText(SignInActivity.this, "Faltan campos por resolver", Toast.LENGTH_SHORT).show();
                }else{
                    if(Password.equals(Password2)){
                        inventory.newUser(inventory.getLastUserId(), FN, LN, UN, Password);
                        Toast.makeText(SignInActivity.this, "Cuanta Agregada", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(SignInActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}

