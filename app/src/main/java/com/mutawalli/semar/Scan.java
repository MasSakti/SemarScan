package com.mutawalli.semar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.mutawalli.semar.R;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class Scan extends AppCompatActivity {
    private static final int REQUEST_ONE = 1;
    private static final int REQUEST_TWO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
    }

    public void btnscan(View view) {
        //Intent integrator
        IntentIntegrator intentIntegrator = new IntentIntegrator(
                Scan.this
        );
        //Prompt Text
        intentIntegrator.setPrompt("Gunakan Tombol Volume Atas Untuk Menghidupkan Flash");
        //Beep
        intentIntegrator.setBeepEnabled(true);
        //Locked Orientation
        intentIntegrator.setOrientationLocked(true);
        //Capture Activity
        intentIntegrator.setCaptureActivity(Capture.class);
        //Initiate Scan
        intentIntegrator.initiateScan();
    }

    public void btnbar(View view) {
        //Intent integrator
        IntentIntegrator intentIntegrator = new IntentIntegrator(
                Scan.this
        );
        //Prompt Text
        intentIntegrator.setPrompt("Gunakan Tombol Volume Atas Untuk Menghidupkan Flash");
        //Beep
        intentIntegrator.setBeepEnabled(true);
        //Locked Orientation
        intentIntegrator.setOrientationLocked(true);
        //Capture Activity
        intentIntegrator.setCaptureActivity(CaptureBar.class);
        //Initiate Scan
        intentIntegrator.initiateScan();
    }

    public void btngallery(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, REQUEST_TWO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != REQUEST_TWO) {
            //Initialize Result
            IntentResult intentResult = IntentIntegrator.parseActivityResult(
                    requestCode, resultCode, data
            );
            //Condition
            if (intentResult.getContents() != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        Scan.this
                );
                builder.setTitle("Hasil Scan");
                if (intentResult.getContents().startsWith("https://goo.gl/maps/")) {
                    builder.setMessage("Data QR \n" + intentResult.getContents() + "\n\n Tipe : Google Maps" + "\n\n" + "Telusuri Koordinat Maps Tersebut ?");
                } else if (intentResult.getContents().startsWith("mailto")) {
                    builder.setMessage("Data QR \n" + intentResult.getContents() + "\n\n Tipe : Email" + "\n\n" + "Kirim Email ke Kontak Tersebut ?");
                } else if (intentResult.getContents().startsWith("tel")) {
                    builder.setMessage("Data QR \n" + intentResult.getContents() + "\n\n Tipe : Nomor Telephone" + "\n\n" + "Hubungi Nomor Tersebut ?");
                } else if (intentResult.getContents().startsWith("https://")) {
                    builder.setMessage("Data QR \n" + intentResult.getContents() + "\n\n Tipe : Web" + "\n\n" + "Kunjungi Alamat URL Tersebut ?");
                } else if (intentResult.getContents().startsWith("8")) {
                    builder.setMessage("Data Barcode \n" + intentResult.getContents() + "\n\n Tipe : Produk" + "\n\n" + "Cari Produk Tersebut ?");
                } else {
                    builder.setMessage("Data QR \n" + intentResult.getContents() + "\n\n Tipe : Kode/Nomor/Teks" + "\n\n" + "Sebarkan Kode/Nomer/Teks Tersebut ?");
                }
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (intentResult.getContents().startsWith("https://goo.gl/maps/")) {
                            Intent go = new Intent(Intent.ACTION_VIEW, Uri.parse(intentResult.getContents()));
                            startActivity(go);
                        } else if (intentResult.getContents().startsWith("mailto")) {
                            Intent go = new Intent(Intent.ACTION_SENDTO);
                            go.setType("text/plain");
                            go.putExtra(Intent.EXTRA_SUBJECT, "Subject of email");
                            go.putExtra(Intent.EXTRA_TEXT, "Body of email");
                            go.setData(Uri.parse(intentResult.getContents().toLowerCase()));
                            go.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(go);
                        } else if (intentResult.getContents().startsWith("tel")) {
                            Intent go = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + intentResult.getContents()));
                            startActivity(go);
                        } else if (intentResult.getContents().startsWith("https://")) {
                            Intent go = new Intent(Intent.ACTION_VIEW, Uri.parse(intentResult.getContents()));
                            startActivity(go);
                        } else if (intentResult.getContents().startsWith("8")) {
                            Intent go = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=produk+" + intentResult.getContents()));
                            startActivity(go);
                        } else {
                            Intent sendIntent = new Intent();
                            sendIntent.setAction(Intent.ACTION_SEND);
                            sendIntent.putExtra(Intent.EXTRA_TEXT, intentResult.getContents());
                            sendIntent.setType("text/plain");
                            startActivity(sendIntent);
                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Hasil Scan Kosong!", Toast.LENGTH_SHORT)
                        .show();
            }
        } else if (requestCode == REQUEST_TWO) {
            if (resultCode == RESULT_OK) {
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    try {
                        Bitmap bMap = selectedImage;
                        String contents = null;

                        int[] intArray = new int[bMap.getWidth() * bMap.getHeight()];
                        bMap.getPixels(intArray, 0, bMap.getWidth(), 0, 0, bMap.getWidth(), bMap.getHeight());

                        LuminanceSource source = new RGBLuminanceSource(bMap.getWidth(), bMap.getHeight(), intArray);
                        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

                        Reader reader = new MultiFormatReader();
                        Result result = reader.decode(bitmap);
                        contents = result.getText();
                        //Toast.makeText(getApplicationContext(), contents, Toast.LENGTH_LONG).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(
                                Scan.this
                        );
                        builder.setTitle("Hasil Scan");
                        if (contents.startsWith("https://goo.gl/maps/")) {
                            builder.setMessage("Data QR \n" + contents + "\n\n Tipe : Google Maps" + "\n\n" + "Telusuri Koordinat Maps Tersebut ?");
                        } else if (contents.startsWith("mailto")) {
                            builder.setMessage("Data QR \n" + contents + "\n\n Tipe : Email" + "\n\n" + "Kirim Email ke Kontak Tersebut ?");
                        } else if (contents.startsWith("tel")) {
                            builder.setMessage("Data QR \n" + contents + "\n\n Tipe : Nomor Telephone" + "\n\n" + "Hubungi Nomor Tersebut ?");
                        } else if (contents.startsWith("https://")) {
                            builder.setMessage("Data QR \n" + contents + "\n\n Tipe : Web" + "\n\n" + "Kunjungi Alamat URL Tersebut ?");
                        } else {
                            builder.setMessage("Data QR \n" + contents + "\n\n Tipe : Kode/Nomor" + "\n\n" + "Sebarkan Kode/Nomer Tersebut ?");
                        }
                        String finalContents = contents;
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (finalContents.startsWith("https://goo.gl/maps/")) {
                                    Intent go = new Intent(Intent.ACTION_VIEW, Uri.parse(finalContents));
                                    startActivity(go);
                                } else if (finalContents.startsWith("mailto")) {
                                    Intent go = new Intent(Intent.ACTION_SENDTO);
                                    go.setType("text/plain");
                                    go.putExtra(Intent.EXTRA_SUBJECT, "Subject of email");
                                    go.putExtra(Intent.EXTRA_TEXT, "Body of email");
                                    go.setData(Uri.parse(finalContents.toLowerCase()));
                                    go.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(go);
                                } else if (finalContents.startsWith("tel")) {
                                    Intent go = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + finalContents));
                                    startActivity(go);
                                } else if (finalContents.startsWith("https://")) {
                                    Intent go = new Intent(Intent.ACTION_VIEW, Uri.parse(finalContents));
                                    startActivity(go);
                                } else {
                                    Intent sendIntent = new Intent();
                                    sendIntent.setAction(Intent.ACTION_SEND);
                                    sendIntent.putExtra(Intent.EXTRA_TEXT, finalContents);
                                    sendIntent.setType("text/plain");
                                    startActivity(sendIntent);
                                }
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //  image_view.setImageBitmap(selectedImage);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(Scan.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(Scan.this, "Gambar Belum Dipilih!", Toast.LENGTH_LONG).show();
            }
        }

    }
}