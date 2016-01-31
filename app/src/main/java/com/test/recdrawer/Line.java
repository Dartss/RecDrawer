package com.test.recdrawer;

public class Line {
    float startX;
    float startY;
    float endX;
    float endY;
    float width;

    public Line(float x1, float y1, float x2, float y2, float width) {
        startX = x1;
        startY = y1;
        endX = x2;
        endY = y2;
        this.width = width;
    }

    public String toString() {
        return "(" + startX + ", " + startY + ")" + " - " + "(" + endX + ", " + endY + ")";
    }
}
