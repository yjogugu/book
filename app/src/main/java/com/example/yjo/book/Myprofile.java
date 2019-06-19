package com.example.yjo.book;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Myprofile extends AppCompatActivity {
    private Picasso picasso;
    //private LruCache picassoLruCache;
    ImageView my_image;
    Button save;
    EditText Img_title;
    private static final int IMG_REQUEST = 777;
    private Bitmap bitmap;
    String email,string;
    String url,image1;
    private ArrayList<String>myimage;
    String resut = "true";
    String myname;
    TextView textView;
    int a=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://13.209.17.127").build();
        Service service = restAdapter.create(Service.class);

        textView = (TextView)findViewById(R.id.my_user_name);
        my_image = (ImageView) findViewById(R.id.imageView);
        save = (Button) findViewById(R.id.myprofile_save);
        Img_title = (EditText) findViewById(R.id.editText3);

        Intent intent = getIntent();
        email = intent.getStringExtra("name");
        myname = intent.getStringExtra("myname");
        //Toast.makeText(this, email, Toast.LENGTH_SHORT).show();
        string = "http://13.209.17.127/imageupload/uploads/"+email+".jpg";

        textView.setText(myname);
        service.profile_my_image(
                email,
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


                                picasso.with(Myprofile.this)
                                        .load(url)
                                        .centerCrop()
                                        .resize(400,400)
                                        .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                                        .placeholder(R.drawable.baseline_account_circle_black_18dp)
                                        .transform(new CircleTransform())
                                        .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                                        .into(my_image);

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


        my_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upoadImage();
                image();
                //Toast.makeText(Myprofile.this, email, Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(Myprofile.this,main_view.class);
                intent1.putExtra("name2",email);
                my_image.setImageBitmap(null);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                finish();
            }
        });
    }


    private void upoadImage(){
        final String Image = imageToString();
        String Title = Img_title.getText().toString();
        Service2 apiInterface = ApiClient.getRetrofit().create(Service2.class);
        Call<ImageClass> call = apiInterface.uploadImage(email,Image);
        call.enqueue(new Callback<ImageClass>() {
            @Override
            public void onResponse(Call<ImageClass> call, Response<ImageClass> response) {
                ImageClass imageClass = response.body();
                Toast.makeText(Myprofile.this, "server:"+imageClass.getResponse(), Toast.LENGTH_SHORT).show();
                Img_title.setText("");
            }

            @Override
            public void onFailure(Call<ImageClass> call, Throwable t) {
                return;
            }
        });
    }
    private void selectImage(){
        Intent intent = new Intent();
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(intent,IMG_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMG_REQUEST && resultCode==RESULT_OK && data!=null){
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                Glide.with(Myprofile.this).load(bitmap).apply(new RequestOptions()).centerCrop().circleCrop().into(my_image);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private String imageToString(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte,Base64.DEFAULT);
    }

    public void image() {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://13.209.17.127").build();
        Service service = restAdapter.create(Service.class);
        string = "http://13.209.17.127/imageupload/uploads/"+email+".jpg";
        service.image_change(
                email,resut,
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


}
