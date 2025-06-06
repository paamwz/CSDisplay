package application.Methods;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Label;

public class GetNumber{
	
	public List<Double> getNumber(String[] numberList, Label endBalance) {
	    List<Double> priceArrays = new ArrayList<>();
	    for (int i = 0; i < numberList.length; i++) {
	        if (numberList[i].isBlank()) {
	            numberList[i] = "0.0";
	        }
	    }

	    // 数値配列として一旦取得（仮の期末残高も含む）
	    for (int i = 0; i < numberList.length; i++) {
	        double number = Double.parseDouble(numberList[i].replace(",", ""));
	        priceArrays.add(number);
	    }

	    // ending balance を上書き計算（= 期首 + 各CF）
	    double endNumber = priceArrays.get(0) + priceArrays.get(1) + priceArrays.get(2) + priceArrays.get(3);
	    endBalance.setText(String.valueOf(endNumber));
	    priceArrays.set(4, endNumber); // ← ここでListを正しく上書き！

	    return priceArrays;
	}
}