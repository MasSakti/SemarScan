package com.mutawalli.semar;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.mutawalli.semar.R;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList qrc_id, qrc_name;

    CustomAdapter(Context context, ArrayList qrc_id, ArrayList qrc_name) {
        this.context = context;
        this.qrc_id = qrc_id;
        this.qrc_name = qrc_name;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.qrc_id_txt.setText(String.valueOf(qrc_id.get(position)));
        holder.qrc_name_txt.setText(String.valueOf(qrc_name.get(position)));
    }

    @Override
    public int getItemCount() {
        return qrc_id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView qrc_id_txt, qrc_name_txt;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            qrc_id_txt = itemView.findViewById(R.id.qrc_id_txt);
            qrc_name_txt = itemView.findViewById(R.id.qrc_name_txt);
        }

    }

}
