package com.radar.ui.javafx;

import com.radar.model.Coordinate;
import com.radar.model.Interceptor;
import com.radar.model.Target;
import com.radar.simulation.SimulationEngine;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class RadarPane extends Pane {

    private static final int SIM_WIDTH = 40;
    private static final int SIM_HEIGHT = 20;
    private static final int SIM_RADAR_X = 20;
    private static final int SIM_RADAR_Y = 18;
    private static final int PADDING = 30;

    private final Canvas canvas;
    private final GraphicsContext gc;
    private SimulationEngine engine;
    private double sweepAngle = 0.0;

    public RadarPane() {
        canvas = new Canvas(600, 500);
        gc = canvas.getGraphicsContext2D();
        getChildren().add(canvas);

        widthProperty().addListener((obs, oldVal, newVal) -> resize());
        heightProperty().addListener((obs, oldVal, newVal) -> resize());
    }

    public void setEngine(SimulationEngine engine) {
        this.engine = engine;
    }

    public void resize() {
        canvas.setWidth(getWidth());
        canvas.setHeight(getHeight());
        draw();
    }

    public void update() {
        sweepAngle = (sweepAngle + 0.08) % (2 * Math.PI);
        draw();
    }

    private void draw() {
        if (engine == null) return;

        double w = canvas.getWidth();
        double h = canvas.getHeight();

        gc.clearRect(0, 0, w, h);

        double xScale = (w - 2 * PADDING) / SIM_WIDTH;
        double yScale = (h - 2 * PADDING) / SIM_HEIGHT;

        double radarPaneX = PADDING + SIM_RADAR_X * xScale;
        double radarPaneY = PADDING + SIM_RADAR_Y * yScale;

        gc.setFill(Color.rgb(10, 20, 10));
        gc.fillRect(0, 0, w, h);

        gc.setStroke(Color.rgb(0, 80, 0));
        gc.setLineWidth(1);

        for (int i = 0; i <= SIM_WIDTH; i++) {
            double x = PADDING + i * xScale;
            gc.strokeLine(x, PADDING, x, h - PADDING);
        }

        for (int i = 0; i <= SIM_HEIGHT; i++) {
            double y = PADDING + i * yScale;
            gc.strokeLine(PADDING, y, w - PADDING, y);
        }

        gc.setStroke(Color.rgb(0, 120, 0));
        gc.setLineWidth(1.5);

        for (int r = 5; r <= Math.max(SIM_WIDTH, SIM_HEIGHT); r += 5) {
            double rx = r * xScale;
            double ry = r * yScale;
            gc.strokeOval(
                    radarPaneX - rx,
                    radarPaneY - ry,
                    rx * 2,
                    ry * 2
            );
        }

        gc.setStroke(Color.rgb(0, 255, 0));
        gc.setLineWidth(2);
        gc.strokeLine(
                radarPaneX,
                radarPaneY,
                radarPaneX + Math.cos(sweepAngle) * 200,
                radarPaneY + Math.sin(sweepAngle) * 200
        );

        gc.setFill(Color.rgb(0, 255, 0));
        gc.setFont(Font.font("Monospaced", 10));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText("R", radarPaneX, radarPaneY);

        for (Target t : engine.getTargets()) {
            double tx = PADDING + t.getCoordinate().getX() * xScale;
            double ty = PADDING + t.getCoordinate().getY() * yScale;

            Color color;
            String symbol;
            switch (t.getStatus()) {
                case AUTHORIZED -> {
                    color = Color.GREEN;
                    symbol = switch (t.getType()) {
                        case "Aircraft" -> "A";
                        case "Drone" -> "D";
                        case "Missile" -> "M";
                        default -> "?";
                    };
                }
                case UNAUTHORIZED -> {
                    color = Color.RED;
                    symbol = switch (t.getType()) {
                        case "Aircraft" -> "A";
                        case "Drone" -> "D";
                        case "Missile" -> "M";
                        default -> "?";
                    };
                }
                case DETECTED -> {
                    color = Color.YELLOW;
                    symbol = switch (t.getType()) {
                        case "Aircraft" -> "A";
                        case "Drone" -> "D";
                        case "Missile" -> "M";
                        default -> "?";
                    };
                }
                default -> {
                    color = Color.GRAY;
                    symbol = "?";
                }
            }

            symbol = getTargetEmoji(t);

            gc.setFill(color);
            gc.beginPath();
            gc.arc(tx, ty, 6, 6, 0, 360);
            gc.fill();

            gc.setFill(Color.WHITE);
            gc.setFont(Font.font("Segoe UI Emoji", 18));
            gc.fillText(symbol, tx, ty - 11);
            gc.setFont(Font.font("Monospaced", 9));
            gc.fillText("#" + t.getId(), tx, ty + 12);
        }

        for (Interceptor interceptor : engine.getInterceptors()) {
            double ix = PADDING + interceptor.getCoordinate().getX() * xScale;
            double iy = PADDING + interceptor.getCoordinate().getY() * yScale;

            Target target = interceptor.getTarget();
            if (target != null) {
                double tx = PADDING + target.getCoordinate().getX() * xScale;
                double ty = PADDING + target.getCoordinate().getY() * yScale;

                gc.setStroke(Color.rgb(255, 165, 0));
                gc.setLineWidth(1);
                gc.strokeLine(ix, iy, tx, ty);
            }

            gc.setFill(Color.ORANGE);
            gc.beginPath();
            gc.moveTo(ix, iy - 7);
            gc.lineTo(ix - 5, iy + 5);
            gc.lineTo(ix + 5, iy + 5);
            gc.closePath();
            gc.fill();

            gc.setFill(Color.WHITE);
            gc.setFont(Font.font("Segoe UI Emoji", 16));
            gc.fillText("✈️", ix, iy - 10);
            gc.setFont(Font.font("Monospaced", 8));
            gc.fillText(interceptor.getInterceptorId(), ix, iy + 12);
        }

        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Segoe UI Emoji", 24));
        for (SimulationEngine.CollisionEffect collision : engine.getCollisions()) {
            double cx = PADDING + collision.getCoordinate().getX() * xScale;
            double cy = PADDING + collision.getCoordinate().getY() * yScale;
            gc.fillText("💥", cx, cy);
        }

        gc.setStroke(Color.rgb(0, 180, 0));
        gc.setLineWidth(2);
        double borderX = PADDING;
        double borderY = PADDING;
        double borderW = w - 2 * PADDING;
        double borderH = h - 2 * PADDING;
        gc.strokeRect(borderX, borderY, borderW, borderH);

        gc.setFill(Color.rgb(0, 180, 0));
        gc.setFont(Font.font("Arial", 12));
        gc.setTextAlign(TextAlignment.LEFT);
        gc.setTextBaseline(VPos.TOP);
        gc.fillText("RADAR", borderX + 5, borderY + 5);
        gc.fillText("Targets: " + engine.getTargets().size(), borderX + 5, borderY + 20);
        gc.fillText("Interceptors: " + engine.getInterceptors().size(), borderX + 5, borderY + 35);
    }

    private String getTargetEmoji(Target target) {
        return switch (target.getType()) {
            case "Aircraft" -> "✈️";
            case "Drone" -> "🛸";
            case "Missile" -> "🚀";
            default -> "?";
        };
    }
}
