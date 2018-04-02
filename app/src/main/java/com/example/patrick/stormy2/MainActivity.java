package com.example.patrick.stormy2;

        import android.content.Context;
        import android.net.ConnectivityManager;
        import android.net.Network;
        import android.net.NetworkInfo;
        import android.os.TokenWatcher;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.widget.Toast;

        import java.io.IOException;

        import okhttp3.Call;
        import okhttp3.Callback;
        import okhttp3.OkHttpClient;
        import okhttp3.Request;
        import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String apiKey = "89f39ad8c11fa010586381e6207d9d55";
        double latitude = 37.8267;
        double longitude = -122.4233;
        String forcastUrl = "https://api.darksky.net/forecast/" + apiKey + "/" + latitude + "," + longitude;


        if(isNetworkAvailable()) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(forcastUrl)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        if (response.isSuccessful()) {
                            Log.v(TAG, response.body().string());
                        } else {
                            alertUserAboutError();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Exception caught", e);
                    }


                }

            });
        } else{
            Toast.makeText(this, R.string.Network_unavailable, Toast.LENGTH_LONG).show();
        }

        Log.d(TAG, "Main UI code is running");
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if(networkInfo != null && networkInfo.isConnected()){
            isAvailable = true;
        }

        return isAvailable;
    }

    private void alertUserAboutError() {

        AlertDialogFragment dialog =  new AlertDialogFragment();
        dialog.show(getFragmentManager(), "Error dialog");
    }
}

// Working with JSON

