package com.example.yjo.book;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

/*public class Comment_adapter extends RecyclerView.Adapter<Comment_adapter.ViewHoder> {
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
        viewHoder.textView.setText(mdata.get(i).getComment());
        viewHoder.coment_name.setText(mdata.get(i).getComment_name());
        Glide.with(viewHoder.itemView.getContext())
                .load(mdata.get(i).getComment_image())
                .apply(new RequestOptions().centerCrop().circleCrop())
                .skipMemoryCache (true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .thumbnail(0.5f)
                .into(viewHoder.coment_profile);
        viewHoder.coment_id.setText(mdata.get(i).getComent_id());

    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public  class  ViewHoder extends RecyclerView.ViewHolder{
        TextView textView,coment_name;
        TextView coment_answer,coment_id;
        ImageView coment_profile;

        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.comment_post);
            coment_name = (TextView) itemView.findViewById(R.id.coment_name);
            coment_profile = (ImageView) itemView.findViewById(R.id.coment_profile);
            coment_answer = (TextView) itemView.findViewById(R.id.coment_answer);
            coment_id = (TextView) itemView.findViewById(R.id.coment_id);

            coment_answer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(),Comcomment.class);
                    Context context = v.getContext();
                    intent.putExtra("id",coment_id.getText().toString());
                    intent.putExtra("post",textView.getText().toString());
                    intent.putExtra("name",coment_name.getText().toString());
                    context.startActivity(intent);
                }
            });
        }
    }




}*/
