package com.yonusa.instaladores_plus.ui.homeScreen.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yonusa.instaladores_plus.R;
import com.yonusa.instaladores_plus.ui.homeScreen.models.response.Cerca;
import com.yonusa.instaladores_plus.utilities.catalogs.Constants;

import java.util.ArrayList;

public class Adapter_HomeScreen_Items extends RecyclerView.Adapter<Adapter_HomeScreen_Items.HomeScreenItemHolder> {

    private ArrayList<Cerca> mDevicesList;
    private OnItemClickListener mListener;
    private onItemLongPress listenerLongPress;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface onItemLongPress {
        void onLongPressItem(int position);
    }

    public static class HomeScreenItemHolder extends RecyclerView.ViewHolder {

        public androidx.cardview.widget.CardView cell_card;
        public ImageView icn_device;
        public ImageView icn_status;

        public TextView tv_deviceName;
        public TextView tv_deviceModel;
        public TextView tv_deviceStatus;

        public Context context;

        public HomeScreenItemHolder(@NonNull View itemView, OnItemClickListener listener, onItemLongPress listenerLongPress) {
            super(itemView);


            cell_card = itemView.findViewById(R.id.cell_card);

            icn_device = itemView.findViewById(R.id.device_icon);
            icn_status = itemView.findViewById(R.id.icn_status);

            tv_deviceName = itemView.findViewById(R.id.tv_device_name);
            tv_deviceModel = itemView.findViewById(R.id.tv_model_id);
            tv_deviceStatus = itemView.findViewById(R.id.tv_status);

            itemView.setOnClickListener(v -> goToControl(listener, this.getAdapterPosition()));

            itemView.setOnLongClickListener(v -> {
                goToLongPress(listenerLongPress, this.getAdapterPosition());
                return true;
            });

        }

        public static void goToControl(OnItemClickListener listener, int position) {
            if (listener != null) {
                if (position != RecyclerView.NO_POSITION) {
                    Log.isLoggable("Position------>", position);
                    listener.onItemClick(position);
                }
            }
        }

        public static void goToLongPress(onItemLongPress listener, int position) {
            if (listener != null) {
                if (position != RecyclerView.NO_POSITION) {
                    Log.isLoggable("Position------>", position);
                    listener.onLongPressItem(position);
                }
            }
        }
    }


    public Adapter_HomeScreen_Items(ArrayList<Cerca> devicesList, onItemLongPress listenerLongPress) {
        this.mDevicesList = devicesList;
        this.listenerLongPress = listenerLongPress;
    }

    @NonNull
    @Override
    public HomeScreenItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_homescreen, parent, false);
        HomeScreenItemHolder homeScreenItemHolder = new HomeScreenItemHolder(v, mListener, listenerLongPress);
        return homeScreenItemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeScreenItemHolder holder, int position) {
        Cerca cerca = mDevicesList.get(position);

        String currentDeviceModel = cerca.getModelo();

        holder.cell_card.setBackgroundResource(R.drawable.cell_background_disable);
        holder.tv_deviceName.setText(cerca.getAliasCerca());


        switch (currentDeviceModel) {
            case (Constants.LIFE_FI_MODEL):
                holder.tv_deviceModel.setText(R.string.life_fi_model);
                holder.icn_device.setImageResource(R.drawable.icn_btnvida);
                break;
            case (Constants.WI_FI_MODEL_02):
                holder.tv_deviceModel.setText(R.string.wi_fi_model);
                holder.icn_device.setImageResource(R.drawable.ico_fence_disable);
                break;
            case (Constants.WI_FI_MODEL_03):
                holder.tv_deviceModel.setText(R.string.wi_fi_model);
                holder.icn_device.setImageResource(R.drawable.ico_fence_disable);
                break;
            case (Constants.MONITOR_MODEL):
                holder.tv_deviceModel.setText(R.string.monitor_model);
                holder.icn_device.setImageResource(R.drawable.icon_panel);
                break;
        }

        int currentRol = cerca.getRol();

        switch (currentRol) {
            case 1:
                holder.tv_deviceName.setText(cerca.getAliasCerca());
                break;
            case 2:
                String guestTo = "Invitado a - " + cerca.getAliasCerca();
                holder.tv_deviceName.setText(guestTo);
                break;
        }

        Boolean deviceConnected = cerca.getEstadoConexionAlSistema();
        Boolean deviceToPowerSupply = cerca.getEstadoConexionCorriente();
        Integer deviceBatteryStatus = cerca.getEstadoBateria();

        if (deviceConnected) {
            //Device connected

            holder.cell_card.setBackgroundResource(R.drawable.cell_background_enable);

            switch (currentDeviceModel) {
                case (Constants.LIFE_FI_MODEL):
                    holder.icn_device.setImageResource(R.drawable.icn_btnvida);
                    break;
                case (Constants.WI_FI_MODEL_02):
                    holder.icn_device.setImageResource(R.drawable.ico_fence_enable);
                    break;
                case (Constants.WI_FI_MODEL_03):
                    holder.icn_device.setImageResource(R.drawable.ico_fence_enable);
                    break;
                case (Constants.MONITOR_MODEL):
                    holder.icn_device.setImageResource(R.drawable.icon_panel);
                    break;
            }

            if (deviceToPowerSupply) {
                //Device powered by AC

                holder.cell_card.setBackgroundResource(R.drawable.cell_background_enable);
                holder.icn_status.setImageResource(R.drawable.ico_energyon);

                switch (currentDeviceModel) {
                    case (Constants.LIFE_FI_MODEL):
                        holder.tv_deviceModel.setText(R.string.life_fi_model);
                        break;
                    case (Constants.WI_FI_MODEL_02):
                        holder.tv_deviceModel.setText(R.string.wi_fi_model);
                        break;
                    case (Constants.WI_FI_MODEL_03):
                        holder.tv_deviceModel.setText(R.string.wi_fi_model);
                        break;
                    case (Constants.MONITOR_MODEL):
                        holder.tv_deviceModel.setText(R.string.monitor_model);
                        break;
                }

                switch (deviceBatteryStatus) {
                    //Battery status
                    case 1:
                        holder.tv_deviceStatus.setText(R.string.charging_battery);
                        break;
                    case 2:
                        holder.tv_deviceStatus.setText(R.string.device_connected);
                        break;
                }

            } else {
                //Device connected but working with battery
               // holder.cell_card.setBackgroundResource(R.drawable.cell_background_low_battery);
/** codigo para mostrar estatus de bateria color rojo la tarjeta y cambio de iconos
                switch (currentDeviceModel) {
                    case (Constants.LIFE_FI_MODEL):
                        holder.tv_deviceModel.setText(R.string.life_fi_model);
                        holder.icn_device.setImageResource(R.drawable.icn_btnvida);
                        break;
                    case (Constants.WI_FI_MODEL_02):
                        holder.icn_device.setImageResource(R.drawable.ico_fence_red);
                        holder.tv_deviceModel.setText(R.string.wi_fi_model);
                        break;
                    case (Constants.WI_FI_MODEL_03):
                        holder.icn_device.setImageResource(R.drawable.ico_fence_red);
                        holder.tv_deviceModel.setText(R.string.wi_fi_model);
                        break;
                    case (Constants.MONITOR_MODEL):
                        holder.tv_deviceModel.setText(R.string.monitor_model);
                        holder.icn_device.setImageResource(R.drawable.icon_panel);
                        break;
                }

                switch (deviceBatteryStatus) {
                    //Battery status
                    case 1:
                        holder.icn_status.setImageResource(R.drawable.ico_lowbatt);
                        holder.tv_deviceStatus.setText(R.string.low_battery);
                        break;
                    case 2:
                        holder.icn_status.setImageResource(R.drawable.ico_fullbatt);
                        holder.tv_deviceStatus.setText(R.string.using_battery);
                        break;
                }
*/

            }

        } else {

            //Device disconnected
            holder.tv_deviceStatus.setText(R.string.device_disconnected);

        }


    }

    @Override
    public int getItemCount() {
        return mDevicesList.size();
    }
}
