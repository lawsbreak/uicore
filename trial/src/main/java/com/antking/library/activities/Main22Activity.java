package com.antking.library.activities;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;

import com.antking.library.R;

public class Main22Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main22);

        final Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupWindow popupWindow = new PopupWindow(300, 200);
                popupWindow.setContentView(View.inflate(Main22Activity.this, R.layout.layout_popup,null));
                popupWindow.setBackgroundDrawable(new ColorDrawable(0xffff00));

                popupWindow.showAtLocation(button
                        , Gravity.TOP
                        , 0
                        , button.getTop() - button.getHeight() - 16);
            }
        });
    }
}
