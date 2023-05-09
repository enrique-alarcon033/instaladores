package com.yonusa.instaladores_plus.ui.cercas.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.yonusa.instaladores_plus.R;
import com.yonusa.instaladores_plus.ui.cercas.Asignar_Cerca;
import com.yonusa.instaladores_plus.ui.cercas.modelo.cercas_model_completo;

import java.util.ArrayList;


/**
 * Created by javiexpo on 26/7/16.
 */
public class cercas_adapter_completo extends RecyclerView.Adapter<cercas_adapter_completo.ViewHolder>  {
    private Context mContextCol;
    private ArrayList<cercas_model_completo> mDatasetcol;
    String codigo;



    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
            TextView rol,aliasCerca,cercaId,MAC,modelo,estadoBateria,estadoConexionCorriente,
                estadoConexionAlSistema,estadoAlarma,texto_asignado;
        ImageView on,off;
        Context context;
        LinearLayout vista;

        CardView tarjeta;
        public Transition transition;

        ViewHolder(View v) {
            super(v);
            context = v.getContext();
           // contenido = (TextView) v.findViewById(R.id.Nombre_Categoria);
           // iduser =(TextView) v.findViewById(R.id.Virtual_Categoria_id);
            rol = (TextView) v.findViewById(R.id.rol_completo);
            aliasCerca = (TextView) v.findViewById(R.id.alias_completo);
            cercaId=(TextView)v.findViewById(R.id.cerca_id_completo);
            MAC = (TextView) v.findViewById(R.id.mac_completo);
            modelo=(TextView) v.findViewById(R.id.modelo_completo);
            estadoBateria=(TextView) v.findViewById(R.id.bateria_completo);
            estadoConexionCorriente = (TextView) v.findViewById(R.id.corriente_completo);
            estadoConexionAlSistema = (TextView) v.findViewById(R.id.sistema_completo);
            estadoAlarma = (TextView) v.findViewById(R.id.alarma_completo);
           vista = (LinearLayout) v.findViewById(R.id.layout_tarjeta);
            tarjeta =(CardView) v.findViewById(R.id.tarjeta_vista);
            on = (ImageView)v.findViewById(R.id.ok_on);
            off = (ImageView)v.findViewById(R.id.ok_off);

            texto_asignado= (TextView) v.findViewById(R.id.tv_asignar_correo);


        }

        void setOnClickListener()    {
            tarjeta.setOnClickListener(this);
        }



      @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tarjeta_vista:
                 Intent i = new Intent(context, Asignar_Cerca.class);
                    i.putExtra("rol", rol.getText());
                    i.putExtra("cerca_id", cercaId.getText());
                    i.putExtra("mac", MAC.getText());

                 //  i.putExtra("cardLastFour", cardLastFour.getText());
                    context.startActivity(i);
                    break;
            }
        }
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public cercas_adapter_completo(Context contextcol, ArrayList<cercas_model_completo> myDatasetco) {
        mDatasetcol = myDatasetco;
        mContextCol= contextcol;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cercas_item_completo, parent, false);
        // set the view's size, margins, paddings and layout parameters

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
//////////
        /////////////
        //////////////


     //   Glide.with(mContextCol).load(mDatasetcol.get(position).getImageUrlco()).into(holder.mImgtViewco);
        holder.rol.setText(mDatasetcol.get(position).getRol());
        holder.aliasCerca.setText(mDatasetcol.get(position).getAliasCerca());
    holder.cercaId.setText(mDatasetcol.get(position).getCercaId());
    holder.MAC.setText(mDatasetcol.get(position).getMAC());
        holder.modelo.setText(mDatasetcol.get(position).getModelo());
        holder.estadoBateria.setText(mDatasetcol.get(position).getEstadoBateria());
        holder.estadoConexionCorriente.setText(mDatasetcol.get(position).getEstadoConexionCorriente());
        holder.estadoConexionAlSistema.setText(mDatasetcol.get(position).getEstadoConexionAlSistema());
        holder.estadoAlarma.setText(mDatasetcol.get(position).getEstadoAlarma());

        if (holder.rol.getText().equals("3")){
            holder.on.setVisibility(View.VISIBLE);
            holder.texto_asignado.setText("Cerca Asignada");
            holder.texto_asignado.setTextColor(R.color.negro);
        }

        holder.setOnClickListener();
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDatasetcol.size();
    }


}
