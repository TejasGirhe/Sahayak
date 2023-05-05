package org.tensorflow.lite.examples.objectdetection.java_activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.tensorflow.lite.examples.objectdetection.R;
import org.tensorflow.lite.examples.objectdetection.adapters.ImageAdapter;
import org.tensorflow.lite.examples.objectdetection.classes.LoadingDialog;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

public class PlaygroundActivity extends AppCompatActivity {
    EditText prompt;
    Button generate;
    ImageView imageView;
    String host_link = "https://64ea-34-91-214-119.ngrok-free.app/";
    private List<String> imageUrls;
    LoadingDialog loadingDialog;
    private ImageAdapter adapter;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playground);


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Generating Image...");
        progressDialog.setCanceledOnTouchOutside(false);
        prompt= findViewById(R.id.prompt);
        generate = findViewById(R.id.get_image);
        imageView = findViewById(R.id.image);

        generate.setOnClickListener(v -> {
            progressDialog.show();
            imageView.setImageDrawable(null);
            String prompt_str = prompt.getText().toString();
            try {
                ApiTask apiTask = new ApiTask();
                apiTask.execute(prompt_str);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "There's some error loading images...", Toast.LENGTH_SHORT).show();
                throw new RuntimeException(e);
            }
        });


    }
    private void showImages() {

        String image_url0 = "https://firebasestorage.googleapis.com/v0/b/text-to-image-c4dd6.appspot.com/o/image0.png?alt=media&token=0f326abb-16ee-460b-8b43-85e9dc554909";

        Picasso.get().load(image_url0).into(imageView);

    }

    private class ApiTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            try {
                String apiEndpoint = host_link;
                String promptText = params[0];
                String count = "1";
                // Encode the prompt text as a query parameter
                String promptParam = "prompt_text=" + URLEncoder.encode(promptText, "UTF-8") + "&count=" + URLEncoder.encode(count, "UTF-8");

                // Construct the API URL
                URL url = new URL(apiEndpoint + "?" + promptParam);

                // Create a connection to the API
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json");

                // Read the response from the API
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Print the response from the API
                System.out.println(response.toString());
                return response.toString().contains("Success");

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                showImages();
                progressDialog.dismiss();
//                loadingDialog.cancel();
            } else {
                Toast.makeText(getApplicationContext(), "Error in getting images. \nPlease try again...", Toast.LENGTH_SHORT).show();
//                loadingDialog.cancel();
                progressDialog.dismiss();
                finish();
            }
        }
    }

}
