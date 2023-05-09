package com.yonusa.instaladores_plus.ui.cercas.adapter;

import android.content.Context;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.yonusa.instaladores_plus.R;
import com.yonusa.instaladores_plus.ui.cercas.modelo.cercas_model;

import java.util.ArrayList;


/**
 * Created by javiexpo on 26/7/16.
 */
public class cercas_adapter extends RecyclerView.Adapter<cercas_adapter.ViewHolder>  {
    private Context mContextCol;
    private ArrayList<cercas_model> mDatasetcol;
    String codigo;



    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
            TextView usuario_id,cerca_id,rol_id,alias_cerca,alias_usuario,nombre_cerca,email;
        Context context;
        LinearLayout vista;

        CardView tarjeta;
        public Transition transition;

        ViewHolder(View v) {
            super(v);
            context = v.getContext();
           // contenido = (TextView) v.findViewById(R.id.Nombre_Categoria);
           // iduser =(TextView) v.findViewById(R.id.Virtual_Categoria_id);
            usuario_id = (TextView) v.findViewById(R.id.usuario_id_cerca);
            cerca_id = (TextView) v.findViewById(R.id.cerca_id_cerca);
            rol_id=(TextView)v.findViewById(R.id.rold_id_cerca);
            alias_cerca = (TextView) v.findViewById(R.id.alias_cerca);
            alias_usuario=(TextView) v.findViewById(R.id.alias_usuario_cerca);
            nombre_cerca=(TextView) v.findViewById(R.id.nombre_cerca);
            email = (TextView) v.findViewById(R.id.correo_cerca);
           vista = (LinearLayout) v.findViewById(R.id.layout_tarjeta);
            tarjeta =(CardView) v.findViewById(R.id.tarjeta_vista);




        }

        void setOnClickListener()    {
            tarjeta.setOnClickListener(this);
        }



      @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tarjeta_vista:
               /*     Intent i = new Intent(context, Informacion_tarjeta.class);
                    i.putExtra("cardId", cardId.getText());
                    i.putExtra("cardExpMont", cardExpMont.getText());
                    i.putExtra("cardExpYear", cardExpYear.getText());
                    i.putExtra("cardName", cardName.getText());
                    i.putExtra("cardLastFour", cardLastFour.getText());
                    i.putExtra("cardBrand", cardBrand.getText());
                    i.putExtra("predeterminada", defual.getText());*/
                 //  i.putExtra("cardLastFour", cardLastFour.getText());
                  //  context.startActivity(i);
                    break;
            }
        }
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public cercas_adapter(Context contextcol, ArrayList<cercas_model> myDatasetco) {
        mDatasetcol = myDatasetco;
        mContextCol= contextcol;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cercas_item, parent, false);
        // set the view's size, margins, paddings and layout parameters

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
//////////
        /////////////
        //////////////


     //   Glide.with(mContextCol).load(mDatasetcol.get(position).getImageUrlco()).into(holder.mImgtViewco);
        holder.usuario_id.setText(mDatasetcol.get(position).getUsuarioId());
        holder.cerca_id.setText(mDatasetcol.get(position).getCercaId());
    holder.rol_id.setText(mDatasetcol.get(position).getRolId());
    holder.alias_cerca.setText(mDatasetcol.get(position).getAliasCerca());
        holder.alias_usuario.setText(mDatasetcol.get(position).getAliasUsuario());
        holder.nombre_cerca.setText(mDatasetcol.get(position).getNombre());
        holder.email.setText(mDatasetcol.get(position).getEmail());




        holder.setOnClickListener();
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDatasetcol.size();
    }


}
