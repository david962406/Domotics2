package com.fiuady.android.domotics;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.fiuady.android.domotics.db.Inventory;

public class SignInActivity extends android.app.Fragment {
    private Inventory inventory;
    private EditText txtFirstName;
    private EditText txtLastName;
    private EditText txtUserName;
    private EditText txtPassword;
    private EditText txtPassword2;
    private ImageButton btnSave;
    private ImageButton btnCancel;
    public SignInActivity(){

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_sign_in, container, false);
        super.onCreate(savedInstanceState);
        final AccessActivity activity = (AccessActivity)getActivity();
        inventory=new Inventory (activity.getApplicationContext());

        txtFirstName = (EditText) view.findViewById(R.id.txt_NewFirstName);
        txtLastName = (EditText) view.findViewById(R.id.txt_NewLastName);
        txtUserName = (EditText) view.findViewById(R.id.txt_NewUserName);
        txtPassword = (EditText) view.findViewById(R.id.txt_NewPassword);
        txtPassword2 = (EditText) view.findViewById(R.id.txt_NewPassword2);

        btnSave = (ImageButton) view.findViewById(R.id.btn_SaveNewUser);
        btnCancel = (ImageButton) view.findViewById(R.id.btn_CancelUser);

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
                    Toast.makeText(activity.getApplicationContext(), "Faltan campos por resolver", Toast.LENGTH_SHORT).show();
                }else{
                    if(Password.equals(Password2)){
                        inventory.newUser(inventory.getLastUserId(), FN, LN, UN, Password);
                        Toast.makeText(activity.getApplicationContext(), "Cuanta Agregada", Toast.LENGTH_SHORT).show();
                        //finish();
                    }else {
                        Toast.makeText(activity.getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
            }
        });
        return view;
    }
}

