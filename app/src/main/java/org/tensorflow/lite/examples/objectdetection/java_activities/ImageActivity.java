package org.tensorflow.lite.examples.objectdetection.java_activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import org.tensorflow.lite.examples.objectdetection.R;
import org.tensorflow.lite.examples.objectdetection.classes.LoadingDialog;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImageActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageAdapter adapter;
    private List<String> imageUrls;
    LoadingDialog loadingDialog;
    String host_link = "https://64ea-34-91-214-119.ngrok-free.app/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        loadingDialog = new LoadingDialog(this, "image_loading");

        loadingDialog.show();


        String word = getIntent().getStringExtra("word").trim();
        word = word.substring(0, 1).toUpperCase() + word.substring(1);

        TextView textView = findViewById(R.id.title);
        textView.setText(word);
        try {
            ApiTask apiTask = new ApiTask();
            apiTask.execute(word);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "There's some error loading images...", Toast.LENGTH_SHORT).show();
            throw new RuntimeException(e);
        }

    }

    private void showImages() {

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ImageAdapter();
        recyclerView.setAdapter(adapter);

        String image_url0 = "https://firebasestorage.googleapis.com/v0/b/text-to-image-c4dd6.appspot.com/o/image0.png?alt=media&token=0f326abb-16ee-460b-8b43-85e9dc554909";
        String image_url1 = "https://firebasestorage.googleapis.com/v0/b/text-to-image-c4dd6.appspot.com/o/image1.png?alt=media&token=0f326abb-16ee-460b-8b43-85e9dc554909";
        String image_url2 = "https://firebasestorage.googleapis.com/v0/b/text-to-image-c4dd6.appspot.com/o/image2.png?alt=media&token=0f326abb-16ee-460b-8b43-85e9dc554909";
        String image_url3 = "https://firebasestorage.googleapis.com/v0/b/text-to-image-c4dd6.appspot.com/o/image3.png?alt=media&token=0f326abb-16ee-460b-8b43-85e9dc554909";

        imageUrls = Arrays.asList(
                image_url0,
                image_url1,
                image_url2,
                image_url3
        );

        for (String url : imageUrls) {
            loadImage(url);
        }
    }

    private boolean generateImages(String word) throws Exception {

        String apiEndpoint = host_link;
        String promptText = word;

        // Encode the prompt text as a query parameter
        String promptParam = "prompt_text=" + URLEncoder.encode(promptText, "UTF-8");

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
    }

    private void loadImage(String url) {
        RequestQueue queue = Volley.newRequestQueue(this);
        ImageRequest request = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                adapter.addImage(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("MainActivity", "Error loading image: " + error.getMessage());
            }
        });
        queue.add(request);
    }

    private class ImageAdapter extends RecyclerView.Adapter<ImageViewHolder> {

        private List<Bitmap> images;

        public ImageAdapter() {
            images = new ArrayList<>();
        }

        public void addImage(Bitmap image) {
            images.add(image);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
            return new ImageViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
            holder.imageView.setImageBitmap(images.get(position));
        }

        @Override
        public int getItemCount() {
            return images.size();
        }
    }

    private static class ImageViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
        }
    }

    private class ApiTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            try {
                String apiEndpoint = host_link;
                String promptText = params[0];
                String count = "4";
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
                loadingDialog.cancel();
            } else {
                Toast.makeText(getApplicationContext(), "Error in getting images. \nPlease try again...", Toast.LENGTH_SHORT).show();
                loadingDialog.cancel();
                finish();
            }
        }
    }

}
