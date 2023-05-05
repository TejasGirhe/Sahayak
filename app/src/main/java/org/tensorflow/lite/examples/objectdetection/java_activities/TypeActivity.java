package org.tensorflow.lite.examples.objectdetection.java_activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.tensorflow.lite.examples.objectdetection.R;

public class TypeActivity extends AppCompatActivity {

    EditText editText;
    Button process;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);

        editText = findViewById(R.id.typed_text);
        process = findViewById(R.id.process);

        try{
            String str = getIntent().getStringExtra("text").trim();
            editText.setText(str);
        }catch (Exception e){
            System.out.println(e);
        }
        process.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString().trim().replace("\n", " ");
                Intent intent = new Intent(TypeActivity.this, InfoActivity.class);
                intent.putExtra("recognisedText", text);
                startActivity(intent);
            }
        });
    }
}