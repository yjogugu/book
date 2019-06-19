package com.example.yjo.book;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/*public class recycleritem_adapter extends RecyclerView.Adapter<recycleritem_adapter.ViewHolder>{

    ArrayList <friend_recycleritem> mDataset;
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView,textView_email;
        Button friend_add_button;
        Context context;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.itemNameTv);
            textView_email=(TextView) itemView.findViewById(R.id.item_email);
            friend_add_button =(Button) itemView.findViewById(R.id.friend_add_button);

            friend_add_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), textView_email.getText().toString(), Toast.LENGTH_SHORT).show();
                    Log.e("qdwq",textView_email.getText().toString());
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(),profile.class);
                    Context context = v.getContext();
                    intent.putExtra("name",textView.getText().toString());
                    context.startActivity(intent);
                }
            });

        }
    }

    public recycleritem_adapter(ArrayList<friend_recycleritem> myDataset){
        mDataset=myDataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(mDataset.get(position).getName());
        holder.textView_email.setText(mDataset.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}*/