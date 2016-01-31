package com.test.recdrawer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

class DrawView extends SurfaceView implements SurfaceHolder.Callback  {
    private Paint paint = new Paint();
    private regulatedParameters parameters;
    private DrawThread drawThread;
    private float displayWidth;
    private float displayHeight;

    public DrawView(Context context, float displayWidth,
                    float displayHeight, regulatedParameters parameters) {
        super(context);
        getHolder().addCallback(this);
        this.parameters = parameters;
        this.displayHeight = displayHeight;
        this.displayWidth = displayWidth;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread = new DrawThread(getHolder());
        drawThread.isDaemon();
        drawThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        drawThread.setRunning(false);
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    public DrawThread getDrawThread() {
        return this.drawThread;
    }

    class DrawThread extends Thread {
        private boolean running = false;
        private SurfaceHolder surfaceHolder;
        private boolean isInterrupted = false;

        public DrawThread(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
        }

        public void setRunning(boolean running) {
            this.running = running;
        }

        public  boolean isRunning(){ return  this.running; }

        public void pauseDrawing() {
            setRunning(false);
        }

        public void stopDrawing() { isInterrupted = true;
        }

        public synchronized void resumeDrawing() {
            setRunning(true);
            notify();
        }

        @Override
        public void run() {
            setRunning(true);
            ArrayList<Line> drawenLines = new ArrayList<>();
            CalculatingCordinates calc =
                    new CalculatingCordinates(displayWidth, displayHeight, parameters);
            ArrayList<Line> lines = calc.getLines();
            paint.setColor(Color.BLACK);
            Canvas canvas;
            canvas = null;
            int i = 0;
            int limit = lines.size();

            for ( ; i < limit && !isInterrupted; ) {
                Line currentLine = lines.get(i);
                Log.d("Lines are drawen", currentLine.toString());
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    canvas = surfaceHolder.lockCanvas();
                    if (canvas == null)
                        continue;
                    canvas.drawColor(Color.GRAY);
                    for (Line drawen : drawenLines) {
                        paint.setStrokeWidth(drawen.width);
                        canvas.drawLine(drawen.startX,
                                drawen.startY,
                                drawen.endX,
                                drawen.endY,
                                paint);
                    }
                    paint.setStrokeWidth(currentLine.width);
                    canvas.drawLine(currentLine.startX,
                            currentLine.startY,
                            currentLine.endX,
                            currentLine.endY,
                            paint);
                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    drawenLines.add(currentLine);
                    i++;
                }

                synchronized(this) {
                    while(!running) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}