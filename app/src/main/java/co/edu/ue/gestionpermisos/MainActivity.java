package co.edu.ue.gestionpermisos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 100;
    private Button btnCheckPermissions;
    //BOTONES DE RESQUEST
    private Button btnFinger;
    private Button btnInternet;
    private Button btnCamera;
    private Button btnSMS;
    private Button btnCall;
    private Button btnMicrophone;
    private Button btnContacts;
    private TextView tvCamera;
    private TextView tvBiometric;
    private TextView tvInternet;
    private TextView tvResponse;
    private TextView tvSMS;
    private TextView tvCall;
    private TextView tvMicrophone;
    private TextView tvContactos;
    String habilitado = " Enabled";
    String desabilitado = " Disabled";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Llamada enlace de datos
        begin();
        //Enlace de botones a metodos
        btnCheckPermissions.setOnClickListener(this::voidCheckPermissions);
        btnCamera.setOnClickListener(this::RequestCamera);
        btnFinger.setOnClickListener(this::RequestFinger);
        btnInternet.setOnClickListener(this::RequestInternet);
        btnSMS.setOnClickListener(this::RequestSMS);
        btnCall.setOnClickListener(this::RequestCall);
        btnMicrophone.setOnClickListener(this::RequestMicrophone);
        btnContacts.setOnClickListener(this::RequestContacts);
    }

    private void RequestSMS(View view) {
        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.SEND_SMS}, REQUEST_CODE);
        }
    }

    private void RequestCall(View view) {
        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ANSWER_PHONE_CALLS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ANSWER_PHONE_CALLS}, REQUEST_CODE);
        }
    }

    private void RequestMicrophone(View view) {
        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_CODE);
        }
    }

    private void RequestContacts(View view) {
        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_CONTACTS}, REQUEST_CODE);
        }
    }

    //LOS PERMISOS DEBEN SER INDIVIDUAL
    //Solicitud Camara
    private void RequestCamera(View view) {
        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA}, REQUEST_CODE);
        }
    }
    //Solicitud Finger
    private void RequestFinger(View view) {
        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.USE_BIOMETRIC) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.USE_BIOMETRIC}, REQUEST_CODE);
        }
    }
    //Solicitud Internet
    private void RequestInternet(View view) {
        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.INTERNET}, REQUEST_CODE);
        }
    }

    //7.Gestion de respuesta del usuario respecto a la solicitud de permiso
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        tvResponse.setText(" " + grantResults[0]);
        if(requestCode == REQUEST_CODE){ //Si respondio
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                new AlertDialog.Builder(this)
                        .setTitle("Permissions")
                        .setMessage("You denied permissions. Allow all permission at Setting -> Permissions")
                        .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                dialogInterface.dismiss();
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", getPackageName(), null));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }).setNegativeButton("exit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                dialogInterface.dismiss();
                                finish();
                            }
                        }).create().show();
            }
        }
    }

    //5. Verificacion permisos
    private void voidCheckPermissions(View view) {
        //Si hay permiso el me arroja un 0, sino hay me arroja un -1
        int statusCamera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int statusInternet = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        int statusBiometric = ContextCompat.checkSelfPermission(this, Manifest.permission.USE_BIOMETRIC);
        int statusSMS = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        int statusCall = ContextCompat.checkSelfPermission(this, Manifest.permission.ANSWER_PHONE_CALLS);
        int statusMicrophone = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        int statusContacts = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS);

        if(statusCamera == 0){tvCamera.setText("Status Camera:" + habilitado);}
        else {tvCamera.setText("Status Camera:" + desabilitado);}

        if(statusSMS == 0){tvSMS.setText("Status SMS:" + habilitado);}
        else{tvSMS.setText("Status SMS:" + desabilitado);}

        if(statusCall == 0){tvCall.setText("Status Answer Calls:" + habilitado);}
        else{tvCall.setText("Status Answer Calls:" + desabilitado);}

        if(statusMicrophone == 0){tvMicrophone.setText("Status Microphone:" + habilitado);}
        else{tvMicrophone.setText("Status Microphone:" + desabilitado);}

        if(statusContacts == 0){tvContactos.setText("Status Contacts:" + habilitado);}
        else{tvContactos.setText("Status Contacts:" + desabilitado);}

        if(statusInternet == 0){tvInternet.setText("Status Internet:" + habilitado);}
        else{tvInternet.setText("Status Internet:" + desabilitado);}

        if(statusBiometric == 0){tvBiometric.setText("Status Biometric:" + habilitado);}
        else{tvBiometric.setText("Status Biometric:" + desabilitado);}

        btnCamera.setEnabled(true);
        btnFinger.setEnabled(true);
        btnInternet.setEnabled(true);
        btnSMS.setEnabled(true);
        btnCall.setEnabled(true);
        btnMicrophone.setEnabled(true);
        btnContacts.setEnabled(true);
    }

    private void begin(){
        this.btnCheckPermissions = findViewById(R.id.btnCheckPermission);
        //Botones Request
        this.btnFinger = findViewById(R.id.btnFingerPint);
        this.btnCamera = findViewById(R.id.btnCamera);
        this.btnInternet = findViewById(R.id.btnInternet);
        this.btnSMS = findViewById(R.id.btnSMS);
        this.btnCall = findViewById(R.id.btnCall);
        this.btnMicrophone = findViewById(R.id.btnMicrophone);
        this.btnContacts = findViewById(R.id.btnContacts);
        //TextViews
        this.tvBiometric = findViewById(R.id.tvDactilar);
        this.tvCamera = findViewById(R.id.tvCamera);
        this.tvInternet = findViewById(R.id.tvInternet);
        this.tvSMS = findViewById(R.id.tvSMS);
        this.tvCall = findViewById(R.id.tvCall);
        this.tvMicrophone = findViewById(R.id.tvMicrophone);
        this.tvContactos = findViewById(R.id.tvContactos);

        // Desactivar los botones
        btnCamera.setEnabled(false);
        btnFinger.setEnabled(false);
        btnInternet.setEnabled(false);
        btnSMS.setEnabled(false);
        btnCall.setEnabled(false);
        btnMicrophone.setEnabled(false);
        btnContacts.setEnabled(false);

    }
}
