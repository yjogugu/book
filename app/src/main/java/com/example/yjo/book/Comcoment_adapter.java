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

/*public class Comcoment_adapter extends RecyclerView.Adapter<Comcoment_adapter.ViewHoder>{
    ArrayList<Comment_list_item> mdata;

    public Comcoment_adapter(ArrayList<Comment_list_item>mydata){
        mdata=mydata;

    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comment_comment_item,viewGroup,false);
        ViewHoder vh = new ViewHoder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder viewHoder, int i) {
        viewHoder.coment_coment_textView.setText(mdata.get(i).getComment());
        viewHoder.coment_coment_name.setText(mdata.get(i).getComment_name());
        Glide.with(viewHoder.itemView.getContext())
                .load(mdata.get(i).getComment_image())
                .apply(new RequestOptions().centerCrop().circleCrop())
                .skipMemoryCache (true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .thumbnail(0.5f)
                .into(viewHoder.coment_coment_profile);
        viewHoder.comcoment_num.setText(mdata.get(i).getComent_no());
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        TextView coment_coment_textView,coment_coment_name,comcoment_num;
        TextView coment_coment_answer;
        ImageView coment_coment_profile;
        TextView coment_coment_update , coment_coment_delete;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            coment_coment_textView = (TextView)itemView.findViewById(R.id.comment_coment_post);
            coment_coment_name = (TextView) itemView.findViewById(R.id.coment_coment_name);
            coment_coment_profile = (ImageView) itemView.findViewById(R.id.coment_coment_profile2);
            coment_coment_answer = (TextView) itemView.findViewById(R.id.coment_coment_answer);
            coment_coment_update = (TextView) itemView.findViewById(R.id.comcoment_update);
            coment_coment_delete = (TextView) itemView.findViewById(R.id.comcoment_delete);
            comcoment_num = (TextView) itemView.findViewById(R.id.comcoment_num);

            coment_coment_update .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext() , Comcoment_update.class);
                    Context context = v.getContext();
                    intent.putExtra("post",coment_coment_textView.getText().toString());
                    intent.putExtra("num",comcoment_num.getText().toString());
                    context.startActivity(intent);
                }
            });
        }
    }
}*/
