package com.sahajarora.photoapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.sahajarora.photoapp.adapter.AlbumAdapter;
import com.sahajarora.photoapp.adapter.PhotoAdapter;
import com.sahajarora.photoapp.model.Album;
import com.sahajarora.photoapp.model.Photo;
import com.sahajarora.photoapp.rest.RestClient;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/* Author: Sahaj Arora
 * Albums Activity that fetches albums from JsonPlaceHolder API and displays them on the screen.
 */
public class MainActivity extends AppCompatActivity {

    ListView lvAlbums;
    private ArrayList<Album> listAlbums;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Albums");
        setSupportActionBar(toolbar);



        listAlbums = new ArrayList<>();
        lvAlbums = (ListView) findViewById(R.id.listAlbums);

        lvAlbums.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Album album = listAlbums.get(i);

                startActivity(new Intent(MainActivity.this, PhotosActivity.class).putExtra("albumId", album.getId()));
            }
        });

        fetchData();
    }

    private void fetchData(){


        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        pDialog.setCancelable(false);

        //Fetch photos from JsonPlaceHolder using RetroFit
        RestClient.get().getAlbums(new Callback<ArrayList<Album>>() {
            @Override
            public void success(ArrayList<Album> albums, Response response) {

                listAlbums = albums;

                lvAlbums.setAdapter(new AlbumAdapter(MainActivity.this, listAlbums));
                if (pDialog.isShowing()) pDialog.dismiss();

            }

            @Override
            public void failure(RetrofitError error) {
                if (pDialog.isShowing()) pDialog.dismiss();
                //Toast.makeText(MainActivity.this, "Could not fetch data from JsonPlaceHolderApi. Please check your internet connection.", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
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
