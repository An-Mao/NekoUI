package dev.anye.mc.nekoui.render;

import dev.anye.core.math._MathCDT;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public final class UniformCircleDistribution {
	private UniformCircleDistribution(){}
	//
	public static List<Point2D.Double> distributePoints(double circleRadius, int pointNumber) {
		return distributePoints(circleRadius, pointNumber, 0);
	}

	public static List<Point2D.Double> distributePoints(double circleRadius, int pointNumber, double rotationAngle) {
		ArrayList<Point2D.Double> points = new ArrayList<>();
		for (int i = 0; i < pointNumber; i++) {
			double angle = rotationAngle + _MathCDT.TWICE_PI * i / pointNumber;
			double x = circleRadius * Math.cos(angle);
			double y = circleRadius * Math.sin(angle);
			points.add(new Point2D.Double(x, y));
		}
		return points;
	}
}
