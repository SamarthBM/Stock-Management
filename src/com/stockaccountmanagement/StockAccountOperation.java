/****************************************************************
 * Purpose : To create methods to perform Stock Management.
 * @author Samarth BM

***************************************************************/

package com.stockaccountmanagement;

import java.util.Date;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class StockAccountOperation {
	public static JSONArray stockList = new JSONArray();
	public static JSONArray stockBuySell = new JSONArray();
	public static Scanner sc = new Scanner(System.in);
	StockBuySellDetails stocks = new StockBuySellDetails();

	/* Purpose: Method to take user input to perform Stock management. */
	public void performStockManagement() {
		System.out.println("\nEnter what you want to do.\n1.Add stock.\n2.Display all stock.\n3.Buy share.\n4.Sell Share."
						+ "\n5. Display all stocks values.\n6. Dispaly your Account\n7. Exit");
		int choose = sc.nextInt();

		switch (choose) {

		case 1:
			addStockAccount();
			break;
		case 2:
			displayStocks();
			break;
		case 3:
			buyShare();
			break;
		case 4:
			sellShare();
			break;
		case 5:
			displayStockValues();
			break;
		case 6:
			displayAllMessages();
			break;
		case 7:
			System.out.println("Exited");
			System.exit(1);
			break;
		default:
			System.out.println("Enter valid option");

		}
	}

	/* Purpose: Method to Display existing stocks details. */
	public void displayStocks() {
		JSONParser parser = new JSONParser();
		try {
			JSONObject stockObj = new JSONObject();
			FileReader reader = new FileReader(".\\Data\\StockDetails.json");
			JSONArray stockArray = (JSONArray) parser.parse(reader);
			for (int i = 0; i < stockArray.size(); i++) {
				stockObj = (JSONObject) stockArray.get(i);
				String name = (String) stockObj.get("stockName");
				long noOfShare = (long) stockObj.get("numberOfShare");
				double price = (double) stockObj.get("price");

				System.out.println("\nShare name is: " + name);
				System.out.println("Number of share is: " + noOfShare);
				System.out.println("Share price is: " + price);

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		performStockManagement();
	}

	/* Purpose: Method to write the JSON file. */
	public void writeFile() {
		try {
			FileWriter writer = new FileWriter(".\\Data\\StockDetails.json");
			writer.write(stockList.toJSONString());
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/* Purpose: Method to add the stock with number of share and it's price. */
	@SuppressWarnings("unchecked")
	public void addStockAccount() {
		// Creating JSONObject.
		JSONObject jsonObj = new JSONObject();

		// Taking user input and storing it in JSONObject.
		System.out.println("Enter stock name: ");
		String stockName = sc.next();
		jsonObj.put("stockName", stockName);

		System.out.println("Enter number of share: ");
		long numOfShare = sc.nextLong();
		jsonObj.put("numberOfShare", numOfShare);

		System.out.println("Enter price of share: ");
		double price = sc.nextDouble();
		jsonObj.put("price", price);

		// Adding JSONObject to JSONArray.
		stockList.add(jsonObj);
		// Calling my writeFile method.
		writeFile();

		System.out.println("Added: " + jsonObj);
		performStockManagement();

	}

	/* Purpose: Method to buy share from existing stock. */
	@SuppressWarnings("unchecked")
	public void buyShare() {
		for (int i = 0; i < stockList.size(); i++) {
			JSONObject jsonObj = new JSONObject();
			jsonObj = (JSONObject) stockList.get(i);
			String presentName = (String) jsonObj.get("stockName");

			System.out.println("Enter the name of the stock you want to buy: ");
			String buyStock = sc.next();

			// Condition to check whether stock exists.
			if (presentName.equalsIgnoreCase(buyStock)) {
				// Getting the existing number of share.
				long sharesNumber = (long) jsonObj.get("numberOfShare");
				System.out.println("Enter the number of shares you want to buy: ");
				long shareBuy = sc.nextLong();

				// Condition to check whether share to buy is within the existing share limit.
				if (sharesNumber >= shareBuy) {
					sharesNumber = sharesNumber - shareBuy;
					jsonObj.replace("numberOfShare", sharesNumber);
					String message = "Successfully bought share of " + shareBuy + " from " + buyStock;
					getBuySellDetails(message);
				} else
					System.out.println("Enter share less than equal to " + sharesNumber);
			}
		}
		writeFile();
		System.out.println("Final stocks: " + stockList);

		performStockManagement();

	}

	/* Purpose: Method to sell shares to existing stock. */
	@SuppressWarnings("unchecked")
	public void sellShare() {
		for (int i = 0; i < stockList.size(); i++) {
			JSONObject jsonObj = new JSONObject();
			jsonObj = (JSONObject) stockList.get(i);
			String presentName = (String) jsonObj.get("stockName");

			System.out.println("Enter the name of the stock you want to sell to : ");
			String sellStock = sc.next();

			if (presentName.equalsIgnoreCase(sellStock)) {
				long sharesNumber = (long) jsonObj.get("numberOfShare");
				System.out.println("Enter the number of shares you want to sell: ");
				long sellShare = sc.nextLong();

				sharesNumber = sharesNumber + sellShare;
				jsonObj.replace("numberOfShare", sharesNumber);
				String message = "Successfully sold share of " + sellShare + " to " + sellStock;
				getBuySellDetails(message);

				writeFile();
				System.out.println("Stocks after selling is :" + stockList);
			}
			performStockManagement();
		}
	}

	/* Purpose: Method to display total value of each stock */
	public void displayStockValues() {
		JSONParser parser = new JSONParser();
		try {
			JSONObject stockObj = new JSONObject();
			FileReader reader = new FileReader(".\\Data\\StockDetails.json");
			JSONArray stockArray = (JSONArray) parser.parse(reader);
			for (int i = 0; i < stockArray.size(); i++) {
				stockObj = (JSONObject) stockArray.get(i);
				String name = (String) stockObj.get("stockName");
				long noOfShare = (long) stockObj.get("numberOfShare");
				double price = (double) stockObj.get("price");

				System.out.println("Total stocks value of " + name + " is " + noOfShare * price);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		performStockManagement();

	}

	/*
	 * Purpose: Method to create users account. Users account will be stored with
	 * message, date and time of transaction.
	 * 
	 * @param message: holds the message of user.
	 */
	@SuppressWarnings("unchecked")
	public void getBuySellDetails(String message) {
		Date date = new Date();
		stocks.setMessage(message);
		stocks.setDate(date);

		JSONObject stockObj = new JSONObject();

		stockObj.put("message", stocks.getMessage());
		stockObj.put("DateAndTime", stocks.getDate());

		stockBuySell.add(stockObj);
		try {
			FileWriter writer = new FileWriter(".\\Data\\StockBuySellDetails.json");
			writer.write(stockBuySell.toJSONString());
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(stockBuySell);

	}

	/* Purpose: Method to display the users account. */
	public void displayAllMessages() {
		System.out.println(stockBuySell);
		performStockManagement();
	}

}
