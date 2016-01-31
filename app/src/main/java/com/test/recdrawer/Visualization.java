package com.test.recdrawer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class Visualization extends Activity implements View.OnClickListener {

    private DrawView viewOfRecursion;
    private Button btnStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        regulatedParameters params = intent.getParcelableExtra("Parameters");

        Display display = this.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        float displayWidth = size.x;
        float displayHeight = size.y;

        viewOfRecursion = new DrawView(this, displayWidth, displayHeight, params);

        addView();
    }

    private void addView() {
        FrameLayout visualSrcreenFL = new FrameLayout(this);

        LinearLayout visualScreenLL = new LinearLayout(this);
        visualScreenLL.setOrientation(LinearLayout.VERTICAL);

        btnStop = new Button(this);
        btnStop.setWidth(300);
        btnStop.setText("Stop");
        btnStop.setOnClickListener(this);

        visualScreenLL.addView(btnStop);
        visualScreenLL.addView(viewOfRecursion);

        visualSrcreenFL.addView(visualScreenLL);

        setContentView(visualSrcreenFL);
    }


    @Override
    public void onClick(View v) {
        if (viewOfRecursion.getDrawThread().isRunning() ) {
            viewOfRecursion.getDrawThread().pauseDrawing();
            btnStop.setText("Resume");
        } else {
            viewOfRecursion.getDrawThread().resumeDrawing();
            btnStop.setText("Stop");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MYLOG", "Was paused");

        viewOfRecursion.getDrawThread().stopDrawing();
    }

}
