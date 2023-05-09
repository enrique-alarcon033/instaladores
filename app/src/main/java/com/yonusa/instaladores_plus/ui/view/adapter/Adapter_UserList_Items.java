package com.yonusa.instaladores_plus.ui.view.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yonusa.instaladores_plus.R;
import com.yonusa.instaladores_plus.ui.view.models.response.Usuario;

import java.util.ArrayList;

public class Adapter_UserList_Items extends RecyclerView.Adapter<Adapter_UserList_Items.ViewHolder> {

    private ArrayList<Usuario> mUserList;
    private OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public androidx.cardview.widget.CardView cell_user_card;
        public ImageView mImageView;
        public TextView mGuestUserName;
        public TextView mGuestUserEmail;


        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            cell_user_card = itemView.findViewById(R.id.cell_user_card);

            mImageView = itemView.findViewById(R.id.iv_user_list_profile_icn);
            mGuestUserName = itemView.findViewById(R.id.tv_list_user_name);
            mGuestUserEmail = itemView.findViewById(R.id.tv_guest_email);

            itemView.setOnClickListener(v -> goToPermissions(listener, this.getAdapterPosition()));

        }

        public static void goToPermissions(OnItemClickListener listener, int position){

            if (listener != null) {
                if (position != RecyclerView.NO_POSITION) {
                    Log.isLoggable("Position------>", position);
                    listener.onItemClick(position);
                }
            }

        }

    }

    public Adapter_UserList_Items(ArrayList<Usuario> userList){
        mUserList = userList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(v, mListener);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Usuario usuario = mUserList.get(position);
        holder.cell_user_card.setBackgroundResource(R.drawable.cell_background_disable);
        holder.mGuestUserName.setText(usuario.getAliasUsuario());
        holder.mGuestUserEmail.setText(usuario.getEmail());

    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

}
