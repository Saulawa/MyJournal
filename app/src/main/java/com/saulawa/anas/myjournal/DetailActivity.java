package com.saulawa.anas.myjournal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    TextView titletxt, detailtxt, datetxt;
    ImageView emoji;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        titletxt = findViewById(R.id.entrytitledisplay);
        detailtxt = findViewById(R.id.detailtext);
        emoji = findViewById(R.id.moodemoji);
        datetxt = findViewById(R.id.datediplay);

        Intent i = getIntent();
        String title = i.getStringExtra("title");
        String desctiption = i.getStringExtra("detail");
        byte[] b = i.getByteArrayExtra("image");
        String date = i.getStringExtra("date");

        titletxt.setText(title);
        detailtxt.setText(desctiption);
        emoji.setImageBitmap(converttobitma(b));
       datetxt.setText(date);

    }

    private Bitmap converttobitma(byte[] b) {
        return BitmapFactory.decodeByteArray(b, 0, b.length);
    }
}
