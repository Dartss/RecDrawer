package com.test.recdrawer;


import android.util.Log;

import java.util.ArrayList;

public class CalculatingCordinates {

    private static ArrayList<Line> lines = new ArrayList<>();
    private float displayWidth;
    private float displayHeight;
    private float ratioValue;

    public CalculatingCordinates(float displayWidth, float displayHeight,
                                 regulatedParameters parameters) {
        ratioValue = parameters.ratio / 10;
        this.displayHeight = displayHeight;
        this.displayWidth = displayWidth;
        lines.removeAll(lines);
        makeTree(parameters.depth, 0.5f, 0, (float) Math.PI / 2, parameters.size / 10);
    }

    private void makeTree(int n, float x, float y, float a, float branchRadius){

        if (n == 0) return;

        float bendAngle = (float) Math.toRadians(340);
        float branchAngle = (float) Math.toRadians(37);
        float lineWidth = (float) (Math.pow(n, 1.3));

        float cx = (x + (float)Math.cos(a) * branchRadius);
        float cy = (y + (float)Math.sin(a) * branchRadius);

        lines.add(new Line(x * displayWidth,
                           displayHeight - y * displayWidth,
                           cx * displayWidth,
                           displayHeight - cy * displayWidth,
                           lineWidth));

        makeTree(n - 1, cx, cy, a + bendAngle - branchAngle, branchRadius * ratioValue);
        makeTree(n - 1, cx, cy, a + bendAngle + branchAngle, branchRadius * ratioValue);
        makeTree(n - 1, cx, cy, a + bendAngle, branchRadius * (1 - ratioValue));
    }

    public ArrayList<Line> getLines() {
        return lines;
    }

}
