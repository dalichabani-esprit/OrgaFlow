package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Radarchart {

    @FXML
    private Canvas radarCanvas;
    private double[] data;
    private String[] axisNames = {"age", "Compétences", "coordonnées", "Interêts", "qualité du cv"};

    public void setRadarCanvas(Canvas radarCanvas) {
        this.radarCanvas = radarCanvas;
    }

    public void setData(double[] data) {
        this.data = data;
    }

    public void initialize() {
        if (data != null) {
            drawRadarChart();
        }
    }

    private void drawRadarChart() {
        if (radarCanvas == null || data == null) {
            return;
        }
        GraphicsContext gc = radarCanvas.getGraphicsContext2D();
        double width = radarCanvas.getWidth();
        double height = radarCanvas.getHeight();
        double centerX = width / 2;
        double centerY = height / 2;
        double radius = Math.min(centerX, centerY) * 0.8;

        int numAxes = data.length;

        gc.setStroke(Color.GRAY);
        gc.setFont(Font.font(12));

        for (int i = 0; i < numAxes; i++) {
            double angle = 2 * Math.PI * i / numAxes;
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);
            gc.strokeLine(centerX, centerY, x, y);

            double textX = centerX + (radius + 10) * Math.cos(angle);
            double textY = centerY + (radius + 10) * Math.sin(angle);
            gc.fillText(axisNames[i], textX, textY);
        }

        gc.setStroke(Color.GREEN);
        gc.setFill(Color.GREEN.deriveColor(1, 1, 1, 0.3));
        double[] xPoints = new double[numAxes];
        double[] yPoints = new double[numAxes];
        for (int i = 0; i < numAxes; i++) {
            double angle = 2 * Math.PI * i / numAxes;
            double value = data[i] * radius;
            xPoints[i] = centerX + value * Math.cos(angle);
            yPoints[i] = centerY + value * Math.sin(angle);
        }
        gc.fillPolygon(xPoints, yPoints, numAxes);
        gc.strokePolygon(xPoints, yPoints, numAxes);
    }
}