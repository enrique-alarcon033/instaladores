package com.yonusa.instaladores_plus.ui.device_control.view.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yonusa.instaladores_plus.R;
import com.yonusa.instaladores_plus.ui.device_control.models.response.Historial;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Logs
 */
public class eventsDateAdapter extends RecyclerView.Adapter<eventsDateAdapter.eventsDateViewHolder> {
    private List<Historial> eventItemsList;

    public static class eventsDateViewHolder extends RecyclerView.ViewHolder{

        public androidx.cardview.widget.CardView cvEvents;
        public TextView eventDescription, eventOwner, eventDate;

        public eventsDateViewHolder(@NonNull View itemView) {
            super(itemView);

            cvEvents = itemView.findViewById(R.id.cv_events);
            eventDescription = itemView.findViewById(R.id.tv_event);
            eventOwner = itemView.findViewById(R.id.tv_event_owner);
            eventDate = itemView.findViewById(R.id.tv_event_date);
        }
    }

    public eventsDateAdapter(List<Historial> eventItemsList){
        this.eventItemsList = eventItemsList;
    }

    @NonNull
    @Override
    public eventsDateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_items, parent, false);
        eventsDateViewHolder eventVH = new eventsDateViewHolder(v);
        return eventVH;
    }

    @Override
    public void onBindViewHolder(@NonNull eventsDateViewHolder holder, int position) {
        Historial currentItem = eventItemsList.get(position);
        //TODO set time to user date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        Date d = null;
        try {
            d = sdf.parse(currentItem.getFechaRegistroDato());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedTime = output.format(d);

        holder.cvEvents.setBackgroundResource(R.drawable.cell_background_white);
        holder.eventDescription.setText(currentItem.getTextoMostrar());
        holder.eventOwner.setText(currentItem.getNombre().concat(" ").concat(currentItem.getApellidos()));
        holder.eventDate.setText(formattedTime);
    }

    @Override
    public int getItemCount() {
        return eventItemsList.size();
    }
}
