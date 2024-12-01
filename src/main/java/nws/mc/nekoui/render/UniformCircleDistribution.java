package nws.mc.nekoui.render;

import nws.dev.core.math._Math;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class UniformCircleDistribution extends _Math {
    //
    public static ArrayList<Point2D.Double> distributePoints(double circleRadius , int pointNumber) {
        return distributePoints(circleRadius,pointNumber,0);
    }
    public static ArrayList<Point2D.Double> distributePoints(double circleRadius , int pointNumber,double rotationAngle) {
        ArrayList<Point2D.Double> points = new ArrayList<>();
        for (int i = 0; i < pointNumber; i++) {
            double angle = rotationAngle + TWICE_PI * i / pointNumber;
            double x = circleRadius * Math.cos(angle);
            double y = circleRadius * Math.sin(angle);
            points.add(new Point2D.Double(x, y));
        }
        return points;
    }
}
