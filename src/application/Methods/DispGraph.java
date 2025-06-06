package application.Methods;

import java.util.Arrays;
import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DispGraph {

    private Double scaleY = null; // 初期はnull（未計算）
    private Double baseLineY = null;

    public void displayGraph(Canvas canvas, List<Double> numberArrays, String[] numberIndex) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        double canvasHeight = gc.getCanvas().getHeight();
        double canvasWidth = gc.getCanvas().getWidth();
        gc.clearRect(0, 0, canvasWidth, canvasHeight);
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvasWidth, canvasHeight);

        double margin = 50;
        double barWidth = 60;
        double spacing = 60;
        double x = 80;
        

        Color[] colors = {
            Color.LIGHTGRAY, Color.LIGHTGREEN, Color.LIGHTBLUE, Color.LIGHTSALMON, Color.GREY
        };

        double[] cumulative = new double[numberArrays.size() + 1];
        cumulative[0] = 0;
        for (int i = 0; i < numberArrays.size(); i++) {
            cumulative[i + 1] = cumulative[i] + numberArrays.get(i);
        }

        double maxVal = Arrays.stream(cumulative).max().getAsDouble();
        double minVal = Arrays.stream(cumulative).min().getAsDouble();
        double availableHeight = canvasHeight  - 2 * margin;

        // 初回だけ scaleY を計算
        if (scaleY == null) {
        	scaleY = availableHeight / (maxVal - minVal);
            baseLineY = canvasHeight - margin + minVal * scaleY;
        }

        // === 描画開始 ===
        double current = 0;

        // --- 期首残高 ---
        double openingHeight = numberArrays.get(0) * scaleY;
        double openingY = baseLineY - openingHeight;

        gc.setFill(colors[0]);
        gc.fillRect(x, openingY, barWidth, Math.abs(openingHeight));
        gc.setStroke(Color.BLACK);
        gc.strokeRect(x, openingY, barWidth, Math.abs(openingHeight));
        gc.setFill(Color.BLACK);
        gc.fillText(numberIndex[0] + "\n" + numberIndex[5], x, baseLineY + 15);
        gc.fillText(numberArrays.get(0).toString(), x, openingY - 5);

        current = numberArrays.get(0);
        x += barWidth + spacing;

        // --- 営業CF～財務CF ---
        double[] intermediateValues = {
            numberArrays.get(1), numberArrays.get(2), numberArrays.get(3)
        };
        for (int i = 0; i < intermediateValues.length; i++) {
            double height = intermediateValues[i] * scaleY;
            double y = height >= 0
                ? baseLineY - (current + intermediateValues[i]) * scaleY
                : baseLineY - current * scaleY;

            gc.setFill(colors[i + 1]);
            gc.fillRect(x, y, barWidth, Math.abs(height));
            gc.setStroke(Color.BLACK);
            gc.strokeRect(x, y, barWidth, Math.abs(height));
            gc.setFill(Color.BLACK);
            gc.fillText(numberIndex[i + 1] + "\n" + numberIndex[i + 5], x, baseLineY + 15);
            gc.fillText((int)(current + intermediateValues[i]) + " : " + (int)intermediateValues[i], x, y - 5);

            current += intermediateValues[i];
            x += barWidth + spacing;
        }
     // --- 期末残高を0に向けて描画 ---
        double finalHeight = current * scaleY;
        double finalY = baseLineY - (current >= 0 ? current : 0) * scaleY;

        gc.setFill(colors[4]);
        gc.fillRect(x, finalY, barWidth, Math.abs(finalHeight));
        gc.setStroke(Color.BLACK);
        gc.strokeRect(x, finalY, barWidth, Math.abs(finalHeight));

        gc.setFill(Color.BLACK);
        gc.fillText(numberIndex[4] + "\n" + numberIndex[9], x, baseLineY + 15);
        gc.fillText((double) numberArrays.get(4) + "", x, finalY - 5);

        // --- 0 の軸線（実線）---
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeLine(0, baseLineY, gc.getCanvas().getWidth(), baseLineY);
    }

    // 必要に応じてスケーリングをリセットするメソッド
    public void resetScale() {
        this.scaleY = null;
        this.baseLineY = null;
    }
}
