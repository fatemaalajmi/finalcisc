package pkgApp.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import pkgApp.RetirementApp;
import pkgCore.Retirement;

public class RetirementController implements Initializable {

		
	private RetirementApp mainApp = null;
	
	@FXML
	private TextField txtYearsToWork;
	@FXML
	private TextField txtSaveEachMonth;
	@FXML
	private TextField txtAnnualReturnRetired;
	@FXML
	private TextField txtNeedToSave;
	@FXML
	private TextField txtAnnualReturnWorking;
	@FXML
	private TextField txtYearsRetired;
	@FXML
	private TextField txtRequiredIncome;
	@FXML
	private TextField txtMonthlySSI;
	

	public RetirementApp getMainApp() {
		return mainApp;
	}

	public void setMainApp(RetirementApp mainApp) {
		this.mainApp = mainApp;
	}

    private UnaryOperator<Change> getFilter(double max) {
        return change -> {
            String text = change.getText();

            if (!change.isContentChange()) {
                return change;
            }
            
            if(text.isEmpty()) {
            	return change;
            }
            
            try {
            	double d = Double.parseDouble(change.getControlNewText());
            	if(d > 0 && d <= max) {
            		return change;
            	}
            	return null;
            }catch(Exception ex) {
            	
            }

            return null;
         };
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		txtYearsToWork.setTextFormatter(new TextFormatter(getFilter(100)));
		txtYearsRetired.setTextFormatter(new TextFormatter(getFilter(100)));
		txtRequiredIncome.setTextFormatter(new TextFormatter(getFilter(1000000000)));
		txtMonthlySSI.setTextFormatter(new TextFormatter(getFilter(2642)));
		txtAnnualReturnRetired.setTextFormatter(new TextFormatter(getFilter(3)));
		txtAnnualReturnWorking.setTextFormatter(new TextFormatter(getFilter(20)));
		Retirement.testCase();
	}
	
	@FXML
	public void btnClear(ActionEvent event) {
		txtYearsRetired.clear();
		txtYearsToWork.clear();
		txtAnnualReturnWorking.clear();
		txtAnnualReturnRetired.clear();
		txtRequiredIncome.clear();
		txtMonthlySSI.clear();
		txtNeedToSave.clear();
		txtSaveEachMonth.clear();
	}
	
	@SuppressWarnings("restriction")
	@FXML
	public void btnCalculate(ActionEvent event) {
		int iYearsToWork, iYearsRetired;
		double dAnnualReturnWorking, dAnnualReturnRetired, dRequiredIncome, dMonthlySSI;
		try {
			iYearsToWork = Integer.parseInt(txtYearsToWork.getText());
			iYearsRetired = Integer.parseInt(txtYearsRetired.getText());
			dAnnualReturnWorking = Double.parseDouble(txtAnnualReturnWorking.getText())/100;
			dAnnualReturnRetired = Double.parseDouble(txtAnnualReturnRetired.getText())/100;
			dRequiredIncome = Double.parseDouble(txtRequiredIncome.getText());
			dMonthlySSI = Double.parseDouble(txtMonthlySSI.getText());
			Retirement r = new Retirement(iYearsToWork, iYearsRetired, dAnnualReturnWorking, dAnnualReturnRetired, dRequiredIncome, dMonthlySSI);
			double totalAmountSaved = r.TotalAmountSaved();
			txtNeedToSave.setText("" + totalAmountSaved);
			txtSaveEachMonth.setText("" + r.AmountToSave(totalAmountSaved));
		}catch(Exception ex) {
			System.out.println("Not Correct input");
			btnClear(event);
		}
	}
	
	
}
