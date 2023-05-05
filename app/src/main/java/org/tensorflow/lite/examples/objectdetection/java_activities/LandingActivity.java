package org.tensorflow.lite.examples.objectdetection.java_activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.tensorflow.lite.examples.objectdetection.kotlin_activities.MainActivity;
import org.tensorflow.lite.examples.objectdetection.R;


public class LandingActivity extends AppCompatActivity {

    Button scan, type, object_detection, playground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        scan = findViewById(R.id.scan);
        type = findViewById(R.id.type);
        object_detection = findViewById(R.id.objectDetection);
        playground = findViewById(R.id.playground);
        scan.setOnClickListener(v -> startActivity(new Intent(LandingActivity.this, ScanActivity.class)));
        type.setOnClickListener(v -> startActivity(new Intent(LandingActivity.this, TypeActivity.class)));
        object_detection.setOnClickListener(v -> startActivity(new Intent(LandingActivity.this, MainActivity.class)));
        playground.setOnClickListener(v -> startActivity(new Intent(LandingActivity.this, PlaygroundActivity.class)));
    }
}