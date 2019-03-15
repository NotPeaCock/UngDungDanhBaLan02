package com.trungse.ungdungdanhbalan02;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.trungse.ungdungdanhbalan02.adapter.ContactAdapter;
import com.trungse.ungdungdanhbalan02.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Contact> arrayContact;
    private EditText edtName;
    private EditText edtNumber;
    private RadioButton rbtnMale;
    private RadioButton rbtnFemale;
    private Button btnAddContact;
    private ListView lvContact;
    private ContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setwidget();

        arrayContact = new ArrayList<>();
        adapter = new ContactAdapter(this,R.layout.item_contact,arrayContact);
        lvContact.setAdapter(adapter);
        CheckAndRequestPermissions();
        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDialogConfirm(position);
            }
        });
    }

    public void CheckAndRequestPermissions(){
        String[] permissions = new String[]{
                Manifest.permission.CALL_PHONE,
                Manifest.permission.SEND_SMS
        };

        List<String> listPermissionsNeeded = new ArrayList<>();
        for(String permission:permissions){
            if(ContextCompat.checkSelfPermission(this,permission)!= PackageManager.PERMISSION_GRANTED){
                listPermissionsNeeded.add(permission);
            }
        }

        if(!listPermissionsNeeded.isEmpty()){
            ActivityCompat.requestPermissions(this,listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),1);
        }

    }

    public void setwidget(){
        edtName = findViewById(R.id.edt_name);
        edtNumber = findViewById(R.id.edt_number);
        rbtnFemale = findViewById(R.id.rbtn_female);
        rbtnMale = findViewById(R.id.rbtn_male);
        btnAddContact = findViewById(R.id.btn_add_contact);
        lvContact  = findViewById(R.id.lv_contact);
    }

    public void addContact(View view){
        if(view.getId()==R.id.btn_add_contact){
            String name = edtName.getText().toString().trim();
            String number = edtNumber.getText().toString().trim();
            boolean isMale=true;
            if(rbtnMale.isChecked()){
                isMale=true;
            }else{
                isMale=false;
            }
            if(TextUtils.isEmpty(name) || TextUtils.isEmpty(number)){
                Toast.makeText(this, "Please Input Name And Name!", Toast.LENGTH_SHORT).show();
            }else{
                Contact contact =new Contact(isMale,name,number);
                arrayContact.add(contact);
            }
            adapter.notifyDataSetChanged();
        }
    }

    public void showDialogConfirm(final int position){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);

        Button btnCall = dialog.findViewById(R.id.btn_call);
        Button btnSendMessage = dialog.findViewById(R.id.btn_send_message);

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentCall(position);
            }
        });

        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentSendMessage(position);
            }
        });

        dialog.show();

    }

    private void intentSendMessage(int position) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("sms" + arrayContact.get(position).getmNumber()));
        startActivity(intent);

    }

    private void intentCall(int position) {

        Intent intent =new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel" + arrayContact.get(position).getmNumber()));
        startActivity(intent);

    }

}
