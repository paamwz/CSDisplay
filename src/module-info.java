module CSDisplay {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.swing;
	requires java.desktop;
	
	opens application to javafx.graphics, javafx.fxml;
}
