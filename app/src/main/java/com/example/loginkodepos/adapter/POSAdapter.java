package com.example.loginkodepos.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginkodepos.R;
import com.example.loginkodepos.model.PosModel;

import java.util.List;

public class POSAdapter extends RecyclerView.Adapter<POSAdapter.POSViewHolder> {
    private Context context;
    private List<PosModel> list;
    private Dialog dialog;

    public interface Dialog{
        void onClick(int pos);
    }

    public void setDialog(Dialog dialog) { this.dialog = dialog; }

    public POSAdapter(Context context, List<PosModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public POSViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowkodepos,parent,false);
        return new POSViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull POSViewHolder holder, int position) {
        holder.KotKab.setTextSize(20);
        holder.KotKab.setTextColor(Color.BLACK);
        holder.KotKab.setText(list.get(position).getKabupaten());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class POSViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView KotKab;

        public POSViewHolder(@NonNull View itemView)
        {
            super(itemView);

            cardView = itemView.findViewById(R.id.card);
            KotKab = itemView.findViewById(R.id.KotKab);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(dialog !=null)
                    {
                        dialog.onClick(getLayoutPosition());
                    }
                }
            });
        }
    }
}
