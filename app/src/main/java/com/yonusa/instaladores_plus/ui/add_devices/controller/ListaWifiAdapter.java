package com.yonusa.instaladores_plus.ui.add_devices.controller;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yonusa.instaladores_plus.R;
import com.yonusa.instaladores_plus.ui.add_devices.models.ListaWifi;

import java.util.List;

public class ListaWifiAdapter extends RecyclerView.Adapter<ListaWifiAdapter.MyViewHolder> {

    private Context mContext;
    private List<ListaWifi> wifiList;
    private Intent myIntent;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txtNombreWifi, txtPotencia, txtMac;
        public ImageView imgPotencia;
        private String mItem;

        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            txtNombreWifi = view.findViewById(R.id.txtNombreWifi);
            txtPotencia = view.findViewById(R.id.txtPotencia);
            txtMac = view.findViewById(R.id.txtMac);
            imgPotencia = view.findViewById(R.id.imgPotencia);

            txtNombreWifi.setOnClickListener(this);
            imgPotencia.setOnClickListener(this);

        }

        public void setItem(String item) {
            mItem = item;
        }

        @Override
        public void onClick(View view) {
            //Util.Display("onClick " + getPosition() + " " + mItem);
            selectedItem(mItem);

        }
    }

    public ListaWifiAdapter(Context mContext, List<ListaWifi> wifiList, Intent myIntent) {
        this.mContext = mContext;
        this.wifiList = wifiList;
        this.myIntent = myIntent;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_lista_wifi, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        ListaWifi wifi = wifiList.get(position);

        holder.txtNombreWifi.setText(wifi.getName());
        holder.setItem((String) holder.txtNombreWifi.getText());

        Glide.with(mContext).load(wifi.getThumbnail()).into(holder.imgPotencia);

    }


    private void selectedItem(String mItem) {
        myIntent.putExtra("SSID", mItem); //Optional parameters
        mContext.startActivity(myIntent);

    }

    @Override
    public int getItemCount() {
        return wifiList.size();
    }
}
