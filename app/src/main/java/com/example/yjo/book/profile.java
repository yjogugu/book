package com.example.yjo.book;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.signature.ObjectKey;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit.RestAdapter;
import retrofit.RetrofitError;

public class profile extends AppCompatActivity {

    TextView textView,profile_id;
    ImageView profile;
    Button button;
    String url;
    String image1;
    Context context;
    String ch = "true";
    String resut="false";
    Bitmap bitmap;
    String string_profile_name , string_profile_loginid , string_profile_id;
    private RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://13.209.17.127").build();
    private Service service = restAdapter.create(Service.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textView = (TextView) findViewById(R.id.profile_name);
        profile = (ImageView) findViewById(R.id.profile_image);
        profile_id = (TextView) findViewById(R.id.profile_id);

        //((main_view)main_view.mcontext).onResume();





        Intent intent = getIntent();


        string_profile_name = intent.getExtras().getString("name");
        string_profile_id = intent.getStringExtra("prfile_id");
        string_profile_loginid = intent.getStringExtra("id");
        textView.setText(string_profile_name);
        profile_id.setText(string_profile_id);


        service.profile_my_image(
                string_profile_id,
                new retrofit.Callback<retrofit.client.Response>() {
                    @Override
                    public void success(retrofit.client.Response response, retrofit.client.Response response2) {
                        BufferedReader reader = null;

                        try {
                            reader = new BufferedReader(new InputStreamReader(response.getBody().in()));
                            image1 = reader.readLine();

                            try{
                                JSONObject jsonObject = new JSONObject(image1); // json 사용방법
                                JSONArray jsonArray = jsonObject.getJSONArray("result");

                                for(int i=0; i<jsonArray.length(); i++){
                                    JSONObject subjsonObject = jsonArray.getJSONObject(i);

                                    image1 = subjsonObject.getString("image");
                                    url = "http://13.209.17.127/imageupload/"+image1;
                                }
                                Picasso picasso = null;

                                picasso.with(com.example.yjo.book.profile.this)
                                        .load(url)
                                        .centerCrop()
                                        .resize(400,400)
                                        .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                                        //.placeholder(R.drawable.baseline_person_outline_black_18dp)
                                        .transform(new CircleTransform())
                                        .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                                        //.transform(PicassoTransformations.resizeTransformation)
                                        .into(profile);


                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                }
        );

    }

    public class CircleTransform implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());

            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                source.recycle();
            }

            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(squaredBitmap,
                    BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);

            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);

            squaredBitmap.recycle();
            return bitmap;
        }

        @Override
        public String key() {
            return "circle";
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        service.image_change(
                string_profile_id,resut,
                new retrofit.Callback<retrofit.client.Response>() {
                    @Override
                    public void success(retrofit.client.Response response, retrofit.client.Response response2) {
                        BufferedReader reader = null;
                        String a;
                        try {
                            reader = new BufferedReader(new InputStreamReader(response.getBody().in()));
                            a = reader.readLine();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                }
        );
        /*Intent intent = new Intent(com.example.yjo.book.profile.this , main_view.class);
        intent.putExtra("name2",string_profile_loginid);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();*/
    }




}
