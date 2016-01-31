package com.test.recdrawer;

import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements SeekBar.OnSeekBarChangeListener {

    TextView tvRatioSeek, tvSizeSeek, tvRecDepth;
    SeekBar ratioSeekBar, sizeSeekBar, recDepthSeekBar;
    private float ratioValue, treeSize;
    private int recDepth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvRatioSeek = (TextView) findViewById(R.id.tvRatioSeek);
        tvSizeSeek = (TextView) findViewById(R.id.tvSizeSeek);
        tvRecDepth = (TextView) findViewById(R.id.tvRecurDepth);

        recDepthSeekBar = (SeekBar) findViewById(R.id.recurDepthSeekBar);
        recDepthSeekBar.setOnSeekBarChangeListener(this);
        recDepth = 3;

        ratioSeekBar = (SeekBar) findViewById(R.id.ratioSeekBar);
        ratioSeekBar.setOnSeekBarChangeListener(this);
        ratioValue = 5;

        sizeSeekBar = (SeekBar) findViewById(R.id.sizeSeekBar);
        sizeSeekBar.setOnSeekBarChangeListener(this);
        treeSize = 3;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "add");
        menu.add(0, 2, 0, "edit");
        menu.add(0, 3, 3, "delete");
        menu.add(1, 4, 1, "copy");
        menu.add(1, 5, 2, "paste");
        menu.add(1, 6, 4, "exit");

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void startVisualization(View v) {
        Intent intent = new Intent(this, Visualization.class);
        regulatedParameters parameters = new regulatedParameters(ratioValue, treeSize, recDepth);
        intent.putExtra("Parameters", parameters);
        startActivity(intent);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
                                  boolean fromUser) { }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) { }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        switch (seekBar.getId()) {
            case R.id.ratioSeekBar:
                ratioValue = seekBar.getProgress();
                tvRatioSeek.setText("Ratio = " + String.format("%d", (int)ratioValue));
                break;
            case R.id.sizeSeekBar:
                treeSize = seekBar.getProgress();
                tvSizeSeek.setText("Size = " + String.format("%d",(int)treeSize));
                break;
            case R.id.recurDepthSeekBar:
                recDepth = seekBar.getProgress() + 3;
                tvRecDepth.setText("Recursion depth = " + recDepth);
        }

    }
}
