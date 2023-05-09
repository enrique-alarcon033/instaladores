package com.yonusa.instaladores_plus.ui.view.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yonusa.instaladores_plus.R;
import com.yonusa.instaladores_plus.ui.view.models.response.Controle;

import java.util.ArrayList;

public class Adapter_UserPermissions extends RecyclerView.Adapter<Adapter_UserPermissions.ViewHolder> {


    private ArrayList<Controle> mControlsList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){ mListener = listener;}


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public androidx.cardview.widget.CardView cell_control_permission;
        public ImageView mControlIcon;
        public TextView mControlAlias;
        public Integer mControlId;
        public Boolean mPermissionState;
        public Switch mSwitchPermission;


        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            cell_control_permission = itemView.findViewById(R.id.cell_control_permission);

            mControlIcon = itemView.findViewById(R.id.iv_control_icon);
            mControlAlias = itemView.findViewById(R.id.tv_control_name);
            mSwitchPermission = itemView.findViewById(R.id.sw_permission);

            mControlIcon.setOnClickListener(v -> updateUserPermission(listener, this.getAdapterPosition()));
            mSwitchPermission.setOnClickListener(v -> updateUserPermission(listener, this.getAdapterPosition()));
            mControlAlias.setOnClickListener(v -> updateUserPermission(listener, this.getAdapterPosition()));
            cell_control_permission.setOnClickListener(v -> updateUserPermission(listener, this.getAdapterPosition()));


        }

        public static void updateUserPermission(OnItemClickListener listener, int position){

            if (listener != null) {
                if (position != RecyclerView.NO_POSITION) {
                    Log.isLoggable("Position------>", position);
                    listener.onItemClick(position);
                }
            }

        }

    }
    public Adapter_UserPermissions(ArrayList<Controle> userList){
        mControlsList = userList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_control_permissions, parent, false);
        ViewHolder viewHolder = new ViewHolder(v, mListener);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Controle controls = mControlsList.get(position);
        holder.cell_control_permission.setBackgroundResource(R.drawable.cell_background_disable);
        holder.mControlAlias.setText(controls.getAliasControl());
        holder.mControlId = controls.getControlId();

        holder.mPermissionState = controls.getEstadoPermiso();
        holder.mSwitchPermission.setChecked(holder.mPermissionState);

        switch (holder.mControlId){
            case 1:
                holder.mControlIcon.setImageResource(R.drawable.fence_on);
                break;
            case 2:
                holder.mControlIcon.setImageResource(R.drawable.panic_on);
                break;
            case 3:
                holder.mControlIcon.setImageResource(R.drawable.door_closed);
                break;
            case 4:
                holder.mControlIcon.setImageResource(R.drawable.lights_on);
                break;
            case 5:
                holder.mControlIcon.setImageResource(R.drawable.aux_on);
                break;
            case 6:
                holder.mControlIcon.setImageResource(R.drawable.aux_on);
                break;
            case 7:
                holder.mControlIcon.setImageResource(R.drawable.panel_on);
                break;
            case 8:
                holder.mControlIcon.setImageResource(R.drawable.zone_on);
                break;

        }
    }

    @Override
    public int getItemCount() {
        return mControlsList.size();
    }
}
