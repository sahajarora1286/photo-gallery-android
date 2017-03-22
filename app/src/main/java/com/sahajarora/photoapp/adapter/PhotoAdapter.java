package com.sahajarora.photoapp.adapter;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sahajarora.photoapp.MainActivity;
import com.sahajarora.photoapp.PhotosActivity;
import com.sahajarora.photoapp.R;
import com.sahajarora.photoapp.Utils;
import com.sahajarora.photoapp.model.Photo;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by sahajarora1286 on 17-02-2017.
 *
 * Adapter class to facilitate photo ListView creation.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    Context context;
    ArrayList<Photo> listPhotos;
    private static LayoutInflater inflater = null;

    public PhotoAdapter(PhotosActivity photosActivity,
                                    ArrayList<Photo> listPhotos){
        this.context = photosActivity;
        this.listPhotos = listPhotos;
        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public PhotoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_photo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotoAdapter.ViewHolder holder, final int position) {
        holder.txtTitle.setText(listPhotos.get(position).getTitle());

        Picasso.with(context).load(listPhotos.get(position).getThumbnailUrl()).into(holder.imgPhoto);

        holder.imgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_VIEW);

                intent.setDataAndType(Uri.parse(listPhotos.get(position).getUrl()) ,"image/*");

                try {
                    context.startActivity(intent);

                } catch (ActivityNotFoundException e){
                    e.printStackTrace();
                    AlertDialog.Builder builder = new AlertDialog.Builder(context)
                            .setTitle("Alert")
                            .setPositiveButton("Ok", null)
                            .setMessage("Could not find any application on your phone that can open the image.");
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }

            }
        });

        holder.txtTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Toast.makeText(context, Uri.parse(
                        listPhotos.get(position).getUrl()).toString(), Toast.LENGTH_LONG).show();
                System.out.println(Uri.parse(listPhotos.get(position).getUrl()).toString());
                intent.setDataAndType(Uri.parse(listPhotos.get(position).getUrl()) ,"image/*");
                try {
                    context.startActivity(intent);

                } catch (ActivityNotFoundException e){
                    e.printStackTrace();
                    AlertDialog.Builder builder = new AlertDialog.Builder(context)
                            .setTitle("Alert")
                            .setPositiveButton("Okay", null)
                            .setMessage("Could not find any application on your phone that can open the image.");
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }


        });
    }



    @Override
    public int getItemCount() {
        return listPhotos.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgPhoto;
        TextView txtTitle;

        public ViewHolder(View view) {
            super(view);

            txtTitle = (TextView) view.findViewById(R.id.txtTitle);
            imgPhoto = (ImageView) view.findViewById(R.id.imgPhoto);


        }
    }


}
