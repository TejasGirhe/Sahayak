package org.tensorflow.lite.examples.objectdetection.java_activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import org.tensorflow.lite.examples.objectdetection.R;
import org.tensorflow.lite.examples.objectdetection.classes.Objects;
import org.tensorflow.lite.examples.objectdetection.classes.Thesaurus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ScanActivity extends AppCompatActivity {

    SurfaceView surfaceView;
    TextView text;
    CameraSource cameraSource;
    FloatingActionButton ArButton;
    String API_KEY = "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";
    Thesaurus.GetSynonymsTask task;
    ImageView Synonyms;
    private static final int PERMISSION = 100;
    private static final String TAG = ScanActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

//        java.util.Objects.requireNonNull(getSupportActionBar()).hide();

        surfaceView = findViewById(R.id.camera);
        text = findViewById(R.id.text);
        Synonyms = findViewById(R.id.SynonymsButton);
        startCam();
    }


    private void startCam() {
        final TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();

        if (!textRecognizer.isOperational()) {
            Toast.makeText(this, "Not Working", Toast.LENGTH_SHORT).show();
        } else {
            cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK).setRequestedPreviewSize(800, 600)
                    .setAutoFocusEnabled(true).setRequestedFps(10f).build();

            surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(@NonNull SurfaceHolder holder) {
                    try {
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(ScanActivity.this, new String[]{android.Manifest.permission.CAMERA}, PERMISSION);
                            return;
                        }
                        cameraSource.start(surfaceView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

                }

                @Override
                public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
                    cameraSource.stop();
                }
            });

            textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                @Override
                public void release() {

                }

                @Override
                public void receiveDetections(@NonNull Detector.Detections<TextBlock> detections) {
                    final SparseArray<TextBlock> items = detections.getDetectedItems();
                    if (items.size() != 0) {
                        final StringBuilder[] stringBuilder = {new StringBuilder()};
                        text.post(new Runnable() {
                            @Override
                            public void run() {
                                stringBuilder[0] = new StringBuilder();
                                for (int i = 0; i < items.size(); i++) {
                                    stringBuilder[0].append(items.valueAt(i).getValue()).append(" ");
                                }
                                String recognisedText = stringBuilder[0].toString();
                                text.setText(recognisedText);
                                Synonyms.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(ScanActivity.this, InfoActivity.class);
                                        String str = text.getText().toString();
                                        str = str.replace("\n", " ");
                                        intent.putExtra("recognisedText", str);
                                        startActivity(intent);
                                    }
                                });
                                ArrayList<String> objects = null;

                                try {
                                    objects = Objects(text.getText().toString());
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }

                            }
                        });

                    }
                }
            });
        }
    }

    private ArrayList<String> Objects(String recognisedText) throws IOException {
        List<String> commonWords = new Objects(getApplicationContext()).readLine();
        ArrayList<String> words = new ArrayList<>();
        String[] arr = recognisedText.split(" ");
        for (String s : arr) {
            if (commonWords.contains(s.toLowerCase(Locale.ROOT))) {
                words.add(s);
            }
        }
        return words;
    }
}