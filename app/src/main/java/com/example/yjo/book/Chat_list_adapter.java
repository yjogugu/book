package com.example.yjo.book;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class Chat_list_adapter  extends RecyclerView.Adapter<Chat_list_adapter.ViewHoder> {
        String user_id;
        ArrayList<chat_list_item> mdata;
        String mylogin;
        ArrayList<String> post_chat;
        String image_url="http://13.209.17.127/imageupload/uploads/"+user_id+".jpg";
        public Chat_list_adapter(ArrayList<chat_list_item> mydata ,String login , ArrayList<String> post ){
            mdata=mydata;
            mylogin = login;
            post_chat = post;
            //user_id = user;
        }

        @NonNull
        @Override
        public ViewHoder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chat_list_item,viewGroup,false);
            ViewHoder vh = new ViewHoder(view);
            return vh;
        }


        @Override
        public void onBindViewHolder(@NonNull final ViewHoder viewHoder, final int i) {
            viewHoder.chat_list_user_id.setText(mdata.get(i).getChat_list_id());
            viewHoder.chat_list_user_post.setText(mdata.get(i).getChat_list_post());
            user_id = viewHoder.chat_list_user_id.getText().toString();
            Glide.with(viewHoder.itemView.getContext())
                    .load("http://13.209.17.127/imageupload/uploads/"+user_id+".jpg")
                    .apply(new RequestOptions().centerCrop().circleCrop())
                    .skipMemoryCache (true)
                    .error(R.drawable.baseline_account_circle_black_18dp)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(viewHoder.chat_list_profile_image);
            Log.e("dddd",user_id+"");

        }

        @Override
        public int getItemCount() {
            return mdata.size();
        }


        public class ViewHoder extends RecyclerView.ViewHolder {
            TextView chat_list_user_id , chat_list_user_post;
            LinearLayout linearLayout;
            ImageView chat_list_profile_image;
            public ViewHoder(@NonNull View itemView) {
                super(itemView);
                chat_list_user_id = (TextView) itemView.findViewById(R.id.chat_list_friend);
                chat_list_user_post = (TextView) itemView.findViewById(R.id.chat_list_post);
                linearLayout = (LinearLayout) itemView.findViewById(R.id.chat_list_linear);
                chat_list_profile_image = (ImageView) itemView.findViewById(R.id.chat_list_item_image);

                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext() , chat.class);
                        Context context = v.getContext();
                        intent.putExtra("login",mylogin);
                        intent.putExtra("arry",post_chat);
                        intent.putExtra("user",chat_list_user_id.getText().toString());
                        context.startActivity(intent);
                        post_chat.clear();
                    }
                });

            }

        }




}

