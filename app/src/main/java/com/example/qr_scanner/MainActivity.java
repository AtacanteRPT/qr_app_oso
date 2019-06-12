package com.example.qr_scanner;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{
    ZXingScannerView zs;
    EditText edt3;

    Button b3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        zs = new ZXingScannerView(this);
        setContentView(R.layout.activity_main);

        onPause();
        edt3=(EditText)findViewById(R.id.editText3);

        b3=(Button)findViewById(R.id.button3);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[] { Manifest.permission.CAMERA}, 0);
                    Toast.makeText(getApplicationContext(),
                            "Butuh akses ke kamera", Toast.LENGTH_SHORT)
                            .show();
                }else{
                    setContentView(zs);
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        zs.setResultHandler(this);
        zs.startCamera();
    }
    @Override
    public void onPause() {
        super.onPause();
        zs.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Hasil");
        builder.setMessage(result.getText());
        AlertDialog alert1 = builder.create();
        alert1.show();
        edt3.setText(result.getText());
        zs.resumeCameraPreview(this);
    }
}