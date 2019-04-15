package com.example.yjo.book;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Comment_adapter extends RecyclerView.Adapter<Comment_adapter.ViewHoder> {
    ArrayList<Comment_list_item> mdata;

    public Comment_adapter (ArrayList<Comment_list_item> mydata){
        mdata = mydata;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comment_item,viewGroup,false);
        ViewHoder vh = new ViewHoder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder viewHoder, int i) {
        viewHoder.textView.setText(mdata.get(i).getComment_name());
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public  class  ViewHoder extends RecyclerView.ViewHolder{
        TextView textView;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            textView = (itemView.findViewById(R.id.comment_post));

        }
    }
}
