package com.example.yjo.book;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class chat_adapter extends RecyclerView.Adapter<chat_adapter.ViewHoder>{

        ArrayList<chat_item> mdata;
        String myid;  //login_iD
        static String youid;
        public chat_adapter(ArrayList<chat_item> mydata,String id){
            mdata = new ArrayList<>();
            mdata=mydata;
            this.myid= id;
        }

        @NonNull
        @Override
        public ViewHoder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chat_item,viewGroup,false);
            ViewHoder vh = new ViewHoder(view);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHoder viewHoder, final int i) {
            ViewHoder messageViewHoder = ((ViewHoder)viewHoder);
            if(mdata.get(i).text.equals(myid)){
                //messageViewHoder.textView1.setTextSize(25);
                messageViewHoder.textView3.setBackgroundResource(R.drawable.bule);
                messageViewHoder.textView1.setVisibility(View.GONE);
                messageViewHoder.linearLayout_main.setGravity(Gravity.RIGHT);
                messageViewHoder.textView1.setText(mdata.get(i).getText());
                messageViewHoder.textView3.setText(mdata.get(i).getId());
            }
            else{
                messageViewHoder.textView3.setBackgroundResource(R.drawable.yellow);
                messageViewHoder.textView1.setTextSize(15);
                messageViewHoder.linearLayout_main.setGravity(Gravity.LEFT);
                messageViewHoder.textView1.setText(mdata.get(i).getText());
                messageViewHoder.textView3.setText(mdata.get(i).getId());
                youid = viewHoder.textView1.getText().toString();
            }

        }

        @Override
        public int getItemCount() {
            return mdata.size();
        }


        public class ViewHoder extends RecyclerView.ViewHolder {
            TextView textView1,textView3;
            LinearLayout linearLayout_main;
            public ViewHoder(@NonNull View itemView) {
                super(itemView);
                textView1 = (TextView) itemView.findViewById(R.id.youid);
                textView3 = (TextView) itemView.findViewById(R.id.myid);
                linearLayout_main = (LinearLayout)itemView.findViewById(R.id.messageItmem_linearlayout_main);

            }

        }



}
