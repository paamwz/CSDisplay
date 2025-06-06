package application.Methods;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Label;

public class GetNumberIndexs{
	List<String> indexTexts = new ArrayList<>();
	public List<String> getNumberIndexs(Label[] numberIndex){
		for(int i = 0; i < numberIndex.length; i++) {
			String Index = numberIndex[i].getText();
			indexTexts.add(Index);
		}
		return indexTexts;
	}
}