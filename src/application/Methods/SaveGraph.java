package application.Methods;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;


public class SaveGraph{
	public void saveChartAsPNG(Canvas canvas, Path choosedFolder, TextField saveFileName, TextField corpName) {
		String filename;
		if(saveFileName.getText().isEmpty() && corpName.getText().isEmpty()) {
			filename = "SampleCorp-CS";
		} else if(saveFileName.getText().isEmpty()) {
			filename = corpName.getText() + "-CS";
		} else {
			filename = saveFileName.getText();
		}
		WritableImage image = canvas.snapshot(new SnapshotParameters(), null);
		BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
		
		try {
			ImageIO.write(bufferedImage, "png", choosedFolder.resolve(filename + ".png").toFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}