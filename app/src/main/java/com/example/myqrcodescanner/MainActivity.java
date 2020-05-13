package com.example.myqrcodescanner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    String[] QR_CODES = {"blue fish", "Shark", "Yellow Fish", "Status_4"};
    private final int[] BUTTON_ID = {
            (R.id.btn_status_1),
            (R.id.btn_status_2),
            (R.id.btn_status_3),
            (R.id.btn_status_4)
    };

    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.view_result);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PackageManager.PERMISSION_GRANTED);

        for(int i = 0; i < QR_CODES.length; i++)  {
            Button button = (Button)findViewById(BUTTON_ID[i]);
            button.setText(QR_CODES[i]);
            button.setBackgroundColor(Color.RED);
        }



    }

    public void scanButton(View view)  {
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(intentResult != null)  {
            if( intentResult.getContents() == null)  {
                textView.setText("Cancelled");
            }  else  {
                checkForStatus(intentResult);
                textView.setText(intentResult.getContents());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    void checkForStatus(IntentResult intentResult)  {
        String results = intentResult.getContents();
        for(int i = 0; i < QR_CODES.length; i++)  {
            Log.d("myTag", results);
            if( QR_CODES[i].equals(results) )  {
                Button button = (Button)findViewById(BUTTON_ID[i]);
                button.setBackgroundColor(Color.GREEN);

            }
        }
    }


}
