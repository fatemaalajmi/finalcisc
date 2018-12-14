package pkgCore;

public class Retirement {

	private int iYearsToWork;
	private double dAnnualReturnWorking;
	private int iYearsRetired;
	private double dAnnualReturnRetired;
	private double dRequiredIncome;
	private double dMonthlySSI;
	
	public Retirement(int iYearsToWork, int iYearsRetired, double dAnnualReturnWorking, double dAnnualReturnRetired, double dRequiredIncome, double dMonthlySSI) {
		this.iYearsToWork = iYearsToWork;
		this.iYearsRetired = iYearsRetired;
		this.dAnnualReturnWorking = dAnnualReturnWorking;
		this.dAnnualReturnRetired = dAnnualReturnRetired;
		this.dRequiredIncome = dRequiredIncome;
		this.dMonthlySSI = dMonthlySSI;
	}
	
	public double AmountToSave(double totalAmountSaved)
	{
		return Math.round((totalAmountSaved * dAnnualReturnWorking) / (Math.pow(1+dAnnualReturnWorking, iYearsToWork)-1))/12;
	}
	
	public double TotalAmountSaved()
	{
		double totalNonSSIIncome = 12*(dRequiredIncome - dMonthlySSI);
		return Math.round(totalNonSSIIncome * ((1/this.dAnnualReturnRetired)-(1/(this.dAnnualReturnRetired*Math.pow(1+this.dAnnualReturnRetired, this.iYearsRetired)))));
	}
	
	public static void testCase() {
		Retirement r = new Retirement(40,20,0.07,0.02,10000,2642);
		double totalAmountSaved = r.TotalAmountSaved();
		double amountToSave = r.AmountToSave(totalAmountSaved);
		System.out.println("Total Amount Saved: " + totalAmountSaved);
		System.out.println("Monthly savings needed: " + amountToSave);
		System.out.println("Test passed? " + (1454485 == totalAmountSaved && amountToSave == 554));
	}
}
