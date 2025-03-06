package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Radarchart {

    @FXML
    private Canvas radarCanvas;

    public void initialize() {
        drawRadarChart();
    }

    private void drawRadarChart() {
        GraphicsContext gc = radarCanvas.getGraphicsContext2D();
        double width = radarCanvas.getWidth();
        double height = radarCanvas.getHeight();
        double centerX = width / 2;
        double centerY = height / 2;
        double radius = Math.min(centerX, centerY) * 0.8;

        int numAxes = 5;
        double[] data = {0.8, 0.6, 0.9, 0.7, 0.5};
        String[] axisNames = {"age", "Compétences", "coordonnées", "Interêts", "qualité du cv"}; // Noms des axes

        gc.setStroke(Color.GRAY);
        gc.setFont(Font.font(12)); // Définir une police pour le texte

        for (int i = 0; i < numAxes; i++) {
            double angle = 2 * Math.PI * i / numAxes;
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);
            gc.strokeLine(centerX, centerY, x, y);

            // Ajouter le nom de l'axe
            double textX = centerX + (radius + 10) * Math.cos(angle); // Ajuster la position du texte
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
