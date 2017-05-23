package com.fiuady.android.domotics.db;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.fiuady.android.domotics.AccessActivity;
import com.fiuady.android.domotics.R;

public class NewProfile extends android.app.Fragment{
    EditText textNameProfile;
    ImageButton btnRoom1;
    ImageButton btnRoom2;
    ImageButton btnSwimPool;
    ImageButton btnMainRoom;
    ImageButton btnSave;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.new_profile, container, false);
        final AccessActivity activity = (AccessActivity)getActivity();
        textNameProfile = (EditText)view.findViewById(R.id.textNewProfile);
        btnRoom1 = (ImageButton)view.findViewById(R.id.btnEditRoom1);
        btnRoom1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(container.getContext());
                View view1 = inflater.inflate(R.layout.dialog_edit_room_1, container, false);

                builder.setView(view1);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        btnRoom2 = (ImageButton)view.findViewById(R.id.btnEditRoom2);
        btnSwimPool = (ImageButton)view.findViewById(R.id.btnEditSwimPool);
        btnMainRoom = (ImageButton)view.findViewById(R.id.btnEditMainRoom);
        btnSave = (ImageButton)view.findViewById(R.id.btnSave);

        return view;
    }
}
