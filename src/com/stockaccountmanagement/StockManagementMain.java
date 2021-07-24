/****************************************************************
 * Purpose : Main method to call all the stock management methods.
 * @author Samarth BM

***************************************************************/

package com.stockaccountmanagement;

public class StockManagementMain {

	public static void main(String[] args) {
		StockAccountOperation stockOperation = new StockAccountOperation();
		stockOperation.performStockManagement();
	}

}
