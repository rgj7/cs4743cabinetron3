package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

import database.GatewayException;
import database.ItemTableGateway;
import database.PartTableGateway;

public class Inventory {
	public static void main(String[] args) {
		
		PartTableGateway ptg = null;
		try {
			ptg = new PartTableGateway();		
		} catch(GatewayException e) {
			System.out.println("Error creating DB connection: " + e.getMessage());
			System.exit(0);
		}
		
		ItemTableGateway itg = null;
		try {
			itg = new ItemTableGateway(ptg);		
		} catch(GatewayException e) {
			System.out.println("Error creating DB connection: " + e.getMessage());
			System.exit(0);
		}
		
		// model
		InventoryModel model = new InventoryModel(ptg, itg);
		// view
		InventoryView view = new InventoryView(model);
		// controllers
		InventoryController menuController = new InventoryController(model, view);
		InventoryTableController tableController = new InventoryTableController(model, view);
		ReloadController reloadController = new ReloadController(model, view);

		// register controllers
		view.registerListeners(menuController, tableController);
		
		// configure view
		view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		view.setSize(800, 480);
		view.setLocationRelativeTo(null);
		view.setVisible(true);
         
		Timer timer = new Timer(2000, reloadController);
		timer.start();
				
	}
}
