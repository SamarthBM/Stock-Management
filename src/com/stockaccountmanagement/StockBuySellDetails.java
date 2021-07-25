/****************************************************************
 * Purpose : To create a pojo class for user account.
 * @author Samarth BM

***************************************************************/

package com.stockaccountmanagement;

import java.util.Date;

public class StockBuySellDetails {
	String message;
	Date date;

	public StockBuySellDetails() {

	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
