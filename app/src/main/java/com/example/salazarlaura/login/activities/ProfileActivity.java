package com.example.salazarlaura.login.activities;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.salazarlaura.login.R;
import com.example.salazarlaura.login.helper.Constants;
import com.example.salazarlaura.login.models.User;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {
    private TextView tvUserName;
    private TextView tvNickName;
    private TextView tvMail;
    private User user;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvUserName = findViewById(R.id.tvUserName);
        tvNickName = findViewById(R.id.tvNickName);
        tvMail = findViewById(R.id.tvMail);
        imageView = findViewById(R.id.ivImage);

        user = (User) getIntent().getSerializableExtra(Constants.USER);

        tvUserName.setText(user.getName());
        tvNickName.setText(user.getUsername());
        tvMail.setText(user.getEmail());

        Picasso.get().load(user.getPhoto()).into(imageView);

    }
}
