package com.yonusa.instaladores_plus.ui.device_control.view.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.yonusa.instaladores_plus.R;
import com.yonusa.instaladores_plus.ui.device_control.models.response.Controls;

import java.util.ArrayList;

public class DeviceControlAdapter extends RecyclerView.Adapter<DeviceControlAdapter.DeviceControlViewHolder> {
    private ArrayList<Controls> dataControl;
    private onDataControlListener listener;
    private int heigthItem;
    private boolean status;

    public interface onDataControlListener {
        void onDataControlClick(int position, boolean isLong);
    }

    public DeviceControlAdapter(ArrayList<Controls> datacontrol, boolean status, onDataControlListener listener, int heigthSize) {
        this.dataControl = datacontrol;
        this.status = status;
        this.listener = listener;
        this.heigthItem = heigthSize;
    }

    public static class DeviceControlViewHolder extends RecyclerView.ViewHolder {
        public ImageButton ibControl;
        public ImageView ivControl;
        public TextView tvControl;
        public CardView cardView;

        public DeviceControlViewHolder(@NonNull View itemView, onDataControlListener listener, int heigthZise) {
            super(itemView);

            itemView.getLayoutParams().height = heigthZise;

            cardView = itemView.findViewById(R.id.control_card_view);
            ibControl = itemView.findViewById(R.id.ib_control);
            ivControl = itemView.findViewById(R.id.iv_control);
            tvControl = itemView.findViewById(R.id.tv_control);

            ibControl.setOnClickListener(v -> {
                        goToControl(listener, this.getAdapterPosition(), false);
                    }
            );
            ibControl.setOnLongClickListener(v -> {
                goToControl(listener, this.getAdapterPosition(), true);
                return true;
            });
        }

        public static void goToControl(onDataControlListener listener, int position, boolean isLongPress) {
            if (listener != null) {
                if (position != RecyclerView.NO_POSITION) {
                    Log.isLoggable("Position------>", position);
                    listener.onDataControlClick(position, isLongPress);
                }
            }
        }
    }

    @NonNull
    @Override
    public DeviceControlViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.decive_control_item, parent, false);
        DeviceControlViewHolder deviceControlViewHolder = new DeviceControlViewHolder(v, this.listener, this.heigthItem);
        return deviceControlViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceControlViewHolder holder, int position) {
        Controls control = dataControl.get(position);

        holder.cardView.setBackgroundResource(R.drawable.item_control_shape);

        switch (control.getControlId()) {
            case 1: //fence
                if (control.getEstadoPermiso() && status) {
                    if (control.getEstadoControl()) {
                        holder.ivControl.setImageResource(R.drawable.fence2_on);
                    } else {
                        holder.ivControl.setImageResource(R.drawable.fence2_off);
                    }
                } else {
                    holder.ivControl.setImageResource(R.drawable.fence_disable);
                    holder.ibControl.setClickable(false);
                    holder.ibControl.setLongClickable(false);
                }

                holder.tvControl.setText(control.getAliasControl());

                break;
            case 2: //panic
                if (control.getEstadoPermiso() && status) {
                    if (control.getEstadoControl()) {
                        holder.ivControl.setImageResource(R.drawable.panico_on);
                    } else {
                        holder.ivControl.setImageResource(R.drawable.panico_off);
                    }
                } else {
                    holder.ivControl.setImageResource(R.drawable.panic_disable);
                    holder.ibControl.setClickable(false);
                    holder.ibControl.setLongClickable(false);
                }

                holder.tvControl.setText(control.getAliasControl());
                break;
            case 3: //door
                if (control.getEstadoPermiso() && status) {
                    if (control.getEstadoControl()) {
                        holder.ivControl.setImageResource(R.drawable.puerta_open);
                    } else {
                        holder.ivControl.setImageResource(R.drawable.puerta_close);

                    }
                } else {
                    holder.ivControl.setImageResource(R.drawable.door_disable);
                    holder.ibControl.setClickable(false);
                    holder.ibControl.setLongClickable(false);
                }

                holder.tvControl.setText(control.getAliasControl());
                break;
            case 4: //lights
                if (control.getEstadoPermiso() && status) {
                    if (control.getEstadoControl()) {
                        holder.ivControl.setImageResource(R.drawable.lights_on);
                    } else {
                        holder.ivControl.setImageResource(R.drawable.lights_off);
                    }
                } else {
                    holder.ivControl.setImageResource(R.drawable.lights_disable);
                    holder.ibControl.setClickable(false);
                    holder.ibControl.setLongClickable(false);
                }

                holder.tvControl.setText(control.getAliasControl());
                break;
            case 5: //aux1
            case 6: //aux2
                if (control.getEstadoPermiso() && status) {
                    if (control.getEstadoControl()) {
                        holder.ivControl.setImageResource(R.drawable.aux_on);
                    } else {
                        holder.ivControl.setImageResource(R.drawable.aux_off);
                    }
                } else {
                    holder.ivControl.setImageResource(R.drawable.aux_disable);
                    holder.ibControl.setClickable(false);
                    holder.ibControl.setLongClickable(false);
                }

                holder.tvControl.setText(control.getAliasControl());
                break;

            case 7: //panel
                if (control.getEstadoPermiso() && status) {
                    if (control.getEstadoControl()) {
                        holder.ivControl.setImageResource(R.drawable.panel_on);
                    } else {
                        holder.ivControl.setImageResource(R.drawable.panel_off);
                    }
                } else {
                    holder.ivControl.setImageResource(R.drawable.panel_disable);
                    holder.ibControl.setClickable(false);
                    holder.ibControl.setLongClickable(false);
                }

                holder.tvControl.setText(control.getAliasControl());
                break;
            case 8: //zone
                if (control.getEstadoPermiso() && status) {
                    if (control.getEstadoControl()) {
                        holder.ivControl.setImageResource(R.drawable.zone_on);
                    } else {
                        holder.ivControl.setImageResource(R.drawable.zone_off);
                    }
                } else {
                    holder.ivControl.setImageResource(R.drawable.zone_disable);
                    holder.ibControl.setClickable(false);
                    holder.ibControl.setLongClickable(false);
                }

                holder.tvControl.setText(control.getAliasControl());
                break;
        }

    }

    @Override
    public int getItemCount() {
        return dataControl.size();
    }

}