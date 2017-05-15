package com.fiuady.android.domotics;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fiuady.android.domotics.db.Inventory;
import com.fiuady.android.domotics.db.InventoryHelper;
import com.fiuady.android.domotics.db.Tables.Users;

import java.util.List;
public class LoginActivity extends AppCompatActivity {

    private Inventory inventory;

    private AutoCompleteTextView txtUserName;
    private EditText txtPassword;
    private boolean Access = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        inventory=new Inventory (getApplicationContext());
        InventoryHelper.backupDatabaseFile(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtUserName = (AutoCompleteTextView) findViewById(R.id.txt_username);
        txtPassword = (EditText) findViewById(R.id.txt_password);

        Button btnLogin = (Button) findViewById(R.id.btn_login);
        Button btnSignIn = (Button) findViewById(R.id.btn_SignIn);
        btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = txtUserName.getText().toString();
                final String password = txtPassword.getText().toString();
                List<Users> users = inventory.getAllUsers();

                for(Users u : users){
                    if(username.equals(u.getUserName()) && password.equals(u.getPassword())){
                        Access = true;
                    }
                }
                if(Access){
                    Intent i = new Intent(LoginActivity.this, DeviceList.class);
                    startActivity(i);
                }else {
                    Toast.makeText(LoginActivity.this, "Usuario y/o contraseña no coinciden", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnSignIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(LoginActivity.this, SignInActivity.class);
                startActivity(j);

            }
        });


    }

}


