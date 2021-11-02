package com.mutawalli.uts.scanit;

import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileOutputStream;

public class Generate extends AppCompatActivity {
    //Initialize Variable
    EditText edit_input;
    Button btn_gene, btn_save;
    ImageView img_output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate);

        edit_input = findViewById(R.id.edt_teks);
        btn_gene = findViewById(R.id.btn_generate);
        btn_save = findViewById(R.id.btn_save);
        img_output = findViewById(R.id.output);

        ActivityCompat.requestPermissions(com.mutawalli.uts.scanit.Generate.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        ActivityCompat.requestPermissions(com.mutawalli.uts.scanit.Generate.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        btn_gene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get value from EditText
                String sText = edit_input.getText().toString().trim();
                //Initialize multiformat writer
                MultiFormatWriter writer = new MultiFormatWriter();
                try {
                    //Initialize bit matrix
                    BitMatrix matrix = writer.encode(sText, BarcodeFormat.QR_CODE, 350, 350);
                    //Initialize barcode encoder
                    BarcodeEncoder encoder = new BarcodeEncoder();
                    //Initialize bitmap
                    Bitmap bitmap = encoder.createBitmap(matrix);
                    //Set bitmap on image view
                    img_output.setImageBitmap(bitmap);
                    //Initialize input manager
                    InputMethodManager manager = (InputMethodManager) getSystemService(
                            Context.INPUT_METHOD_SERVICE
                    );
                    //Hide soft keyboard
                    manager.hideSoftInputFromWindow(edit_input.getApplicationWindowToken()
                            , 0);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToGallery();
            }
        });
    }

    private void saveToGallery() {
        if (img_output.getDrawable() != null) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) img_output.getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();

            FileOutputStream outputStream = null;
            File file = Environment.getExternalStorageDirectory();
            File dir = new File(file.getAbsolutePath() + "/Download");
            dir.mkdirs();

            String filename = String.format("%d.png", System.currentTimeMillis());
            File outFile = new File(dir, filename);
            try {
                outputStream = new FileOutputStream(outFile);
                Toast.makeText(com.mutawalli.uts.scanit.Generate.this, "Gambar Berhasil Disimpan!", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            try {
                outputStream.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(com.mutawalli.uts.scanit.Generate.this, "Code QR Belum Di Generate!", Toast.LENGTH_LONG).show();
        }
    }
}