package com.sahajarora.photoapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.sahajarora.photoapp.adapter.PhotoAdapter;
import com.sahajarora.photoapp.model.Photo;
import com.sahajarora.photoapp.rest.RestClient;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


/* Author: Sahaj Arora
 * Photos Activity that fetches photos from JsonPlaceHolder API and displays them on the screen.
 */
public class PhotosActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    private ArrayList<Photo> listPhotos;
    String albumId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Photos");
        setSupportActionBar(toolbar);



        albumId = getIntent().getStringExtra("albumId");
        recyclerView = (RecyclerView)findViewById(R.id.imageGallery);
        //recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),3);
        recyclerView.setLayoutManager(layoutManager);
        listPhotos = new ArrayList<>();
        fetchData();
    }

    private void fetchData(){
        //Fetch photos from JsonPlaceHolder
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        pDialog.setCancelable(false);

        //Fetch photos with albumId = this.albumId from JsonPlaceHolder API using RetroFit
        RestClient.get().getPhotos(albumId, new Callback<ArrayList<Photo>>() {
            @Override
            public void success(ArrayList<Photo> photos, Response response) {

                listPhotos = photos;

                recyclerView.setAdapter(new PhotoAdapter(PhotosActivity.this, listPhotos));

                if (pDialog.isShowing()) pDialog.dismiss();
            }

            @Override
            public void failure(RetrofitError error) {

                if (pDialog.isShowing()) pDialog.dismiss();
                //Toast.makeText(PhotosActivity.this, "Could not fetch data from JsonPlaceHolderApi", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(PhotosActivity.this)
                        .setTitle("Alert")
                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                fetchData();
                            }
                        })
                        .setMessage("Could not fetch data from JsonPlaceHolderApi. Please check your internet connection and tap Retry.");
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
