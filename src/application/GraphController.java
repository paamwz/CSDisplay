package application;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import application.Methods.DispGraph;
import application.Methods.GetNumber;
import application.Methods.GetNumberIndexs;
import application.Methods.SaveGraph;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;


public class GraphController {
	@FXML
	TextField corpName;
	
	@FXML
	TextField beginBalance;
	@FXML
	TextField salesCF;
	@FXML
	TextField investCF;
	@FXML
	TextField financeCF;
	@FXML
	Label endBalance;
	
	@FXML
	Label beginBalanceIndex;
	@FXML
	Label salesCFIndex;
	@FXML
	Label investCFIndex;
	@FXML
	Label financeCFIndex;
	@FXML
	Label endBalanceIndex;
	
	
	@FXML
	Canvas controlledGraph;
	
	@FXML
	TextField saveFileName;
	
	@FXML
	void onDispGraphClick(ActionEvent event){
		String[] numberList = {
				beginBalance.getText(),
				salesCF.getText(),
				investCF.getText(),
				financeCF.getText(),
				endBalance.getText()
		};
		
		String[] numberIndex = {
				"期首残高",
				"営業CF",
				"投資CF",
				"財務CF",
				"期末残高",
				"beginning balance",
				"salesCF",
				"investimentCF",
				"financialCF",
				"ending balance"
		};
		GetNumber gn = new GetNumber();
		List<Double> priceArrays = gn.getNumber(numberList, endBalance);
		GetNumberIndexs gni = new GetNumberIndexs();
		DispGraph dg = new DispGraph();
		dg.displayGraph(controlledGraph, priceArrays, numberIndex);
		
	}
	
	@FXML
	void onSaveContentsClick(ActionEvent event) {
	    DirectoryChooser directoryChooser = new DirectoryChooser();
	    directoryChooser.setTitle("出力先のフォルダを選択");

	    File selectedDirectory = directoryChooser.showDialog(corpName.getScene().getWindow());
	    if (selectedDirectory == null) {
	        System.out.println("ユーザーが保存先選択をキャンセルしました。");
	        return;
	    }

	    try {
	        Path choosedFolder = selectedDirectory.toPath();
	        SaveGraph saveGraph = new SaveGraph();
	        saveGraph.saveChartAsPNG(controlledGraph, choosedFolder, saveFileName, corpName);
	    } catch (Exception e) {
	        e.printStackTrace(); // ログ出力
	        // 必要に応じてUIにエラーメッセージを表示
	    }
	}
}