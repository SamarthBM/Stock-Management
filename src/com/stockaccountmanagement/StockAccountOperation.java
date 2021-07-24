/****************************************************************
 * Purpose : To create methods to perform Stock Management.
 * @author Samarth BM

***************************************************************/

package com.stockaccountmanagement;

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
	public static Scanner sc = new Scanner(System.in);

	/* Purpose: Method to take user input to perform Stock management. */
	public void performStockManagement() {
		System.out.println("\nEnter what you want to do.\n1.Add stock.\n2.Display all stock.\n3.Exit");
		int choose = sc.nextInt();

		switch (choose) {

		case 1:
			addStockAccount();
			break;
		case 2:
			displayStocks();
			break;
		case 3:
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

}
