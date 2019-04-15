package com.example.yjo.book;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class post_adapter extends RecyclerView.Adapter<post_adapter.ViewHolder> {
    ArrayList<post_list_item> mDataset;
    Context context;
    String id;
    RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://13.209.17.127").build();
    Service service = restAdapter.create(Service.class);
    private boolean a;
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView,textView_email,textView_id;
        ImageView imageView,imageView2,imageView3,comment_button;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            comment_button = (ImageView) itemView.findViewById(R.id.comment_button);
            textView = (TextView) itemView.findViewById(R.id.item_name_content);
            textView_email=(TextView) itemView.findViewById(R.id.item_content);
            imageView = (ImageView) itemView.findViewById(R.id.good);
            imageView2 = (ImageView) itemView.findViewById(R.id.good2);
            imageView3 = (ImageView) itemView.findViewById(R.id.profile_my);
            textView_id = (TextView) itemView.findViewById(R.id.text_id_item);

            comment_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(),Comment.class);
                    Context context = v.getContext();
                    intent.putExtra("name",textView_id.getText().toString());
                    context.startActivity(intent);

                }
            });

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    a=true;
                    if(a){
                        imageView2.setVisibility(View.VISIBLE);
                        imageView.setVisibility(View.GONE);
                    }
                }
            });
            imageView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    a=false;
                    if(!a){
                        imageView2.setVisibility(View.GONE);
                        imageView.setVisibility(View.VISIBLE);
                    }
                }
            });

        }
    }
    public post_adapter(ArrayList<post_list_item> myDataset){
        mDataset=myDataset;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.posts_item,viewGroup,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Glide.with(viewHolder.itemView.getContext())
                .load(mDataset.get(i).getImages())
                .apply(new RequestOptions().centerCrop().circleCrop())
                .skipMemoryCache (true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .thumbnail(0.5f)
                .into(viewHolder.imageView3);
        viewHolder.textView.setText(mDataset.get(i).getPosts());
        viewHolder.textView_email.setText(mDataset.get(i).getName());
        viewHolder.textView_id.setText(mDataset.get(i).getId());


    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }






}
