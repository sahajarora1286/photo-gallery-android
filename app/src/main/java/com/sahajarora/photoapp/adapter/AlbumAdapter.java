package com.sahajarora.photoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sahajarora.photoapp.MainActivity;
import com.sahajarora.photoapp.R;
import com.sahajarora.photoapp.model.Album;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sahajarora1286 on 17-02-2017.
 *
 * Adapter class to facilitate photo ListView creation.
 */

public class AlbumAdapter extends BaseAdapter{
    Context context;
    ArrayList<Album> listAlbums;
    private static LayoutInflater inflater = null;

    public AlbumAdapter(MainActivity mainActivity, ArrayList<Album> albums){
        this.context = mainActivity;
        this.listAlbums = albums;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listAlbums.size();
    }

    public class Holder{
        TextView txtTitle;
        TextView txtUserId;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.list_item_album, null);

        holder.txtTitle = (TextView) rowView.findViewById(R.id.txtTitle);
        holder.txtUserId = (TextView) rowView.findViewById(R.id.txtUserId);

        holder.txtTitle.setText(listAlbums.get(i).getTitle());
        holder.txtUserId.setText(listAlbums.get(i).getUserId());

        return rowView;
    }
}
