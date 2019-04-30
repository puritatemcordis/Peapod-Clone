//Zachary Dulac, Trung Nguyen, Abby Tse
//29 April 2019
//GUI Pea Pod Client Clone 

package peapod;

import java.util.*;
import java.util.stream.*;

import java.io.File;
import java.util.concurrent.TimeUnit;

import java.text.DecimalFormat;

import javafx.stage.WindowEvent;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Font; 

import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import javafx.beans.value.ChangeListener;

public class peapod extends Application {
	Stage window;
	static Text clock;
	
	static int height = 1100; 	//height of the window
	static int width = 853;		// width of the window
	
	static int cNum = 0; 	// number of chicken
	static int sNum = 0; 	// number of salmon
	static int aNum = 0; 	// number of asparagus
	static int bsNum = 0; 	// number of brussel sprouts
	static int bNum = 0; 	// number of bread
	static int mNum = 0; 	// number of muffins
	
	static double total = 0.00; // integer type for cart total
	static DecimalFormat totalFormat = new DecimalFormat("#0.00");
	
	static Text cartTotal = new Text(); // text for cart total
	static TextField username = new TextField();
	static TextField ipAddress = new TextField();
	static TextField portNum = new TextField();
	
	String selectedTime = ""; 	// selected delivery time 
	String selectedDate = ""; 	// selected delivery date
	String selectedDelivery = ""; //selected type of delivery
	static boolean send = true; 		// boolean to verify transaction
	
	String order;
	
	fileIO wrFile = new fileIO(); //Declare a new fileIO for transaction log.
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		window = primaryStage;
		window.setTitle("PeaPod Client");
		
		// hash table of the amount of items and types of items in the user's cart 
		Map<String, Integer> userCart = new HashMap<String, Integer>();
		userCart.put("Chicken", 0);
		userCart.put("Salmon", 0);
		userCart.put("Asparagus", 0);
		userCart.put("Brussels Sprouts", 0);
		userCart.put("Bread", 0);
		userCart.put("Muffins", 0);
		
		// Navbar -- requires the top of the men and bottom of the menu
		HBox topMenu = components.topMenu();
		HBox bottomMenu = new HBox();
		bottomMenu.setStyle("-fx-font-weight: bold");
		Button btn1 = components.btn1();
		Button btn2 = components.btn2();
		Button btn3 = components.btn3();
		bottomMenu.getChildren().addAll(btn1, btn2, btn3);
		
		VBox navbar = new VBox(); 
		navbar.getChildren().addAll(topMenu, bottomMenu);
		
		// Sidebar -- requires the protein, greens, and bakery vertical box and the exit button
		VBox sidebar = new VBox();
		sidebar.setStyle("-fx-border-color: black;" + "-fx-border-width: 3;");
		sidebar.setMinWidth(.31*height);
		
		ImageView proteinV = components.proteinV();
		ImageView greensV = components.greensV();
		ImageView bakeryV = components.bakeryV();
		
		Button exitButton = new Button("Exit");
		exitButton.setMinWidth(.33*height);
		exitButton.setFont(Font.font("Verdana", 17));
		
		
		sidebar.getChildren().addAll(proteinV, greensV, bakeryV, exitButton);
		
		// Center display		
		ScrollPane sp = new ScrollPane();
		
		VBox center = components.center();
		VBox chickenVB = food.chicken(cNum, userCart, cartTotal);
		VBox salmonVB = food.salmon(sNum, userCart, cartTotal);
		VBox asparagusVB = food.asparagus(aNum, userCart, cartTotal);
		VBox bsVB = food.brusselsprouts(bsNum, userCart, cartTotal);
		VBox breadVB = food.bread(bNum, userCart, cartTotal);
		VBox muffinVB = food.muffin(mNum, userCart, cartTotal);
		
		// Border Pane -- wraps up the navbar, sidebar, and center display
		BorderPane bp = new BorderPane();
		bp.setTop(navbar);
		bp.setLeft(sidebar);
		bp.setCenter(center);
		bp.setStyle("-fx-border-color: black;" + "-fx-border-width: 3;");
		
		refreshClock(); //constantly refreshes the clock
		
		// Scene Setup
		Scene scene = new Scene(bp, height, width);
		window.setScene(scene);
		window.show();
		
		
//================================================
// BUTTON/ONCLICK FUNCTIONALITIES
//================================================
		
		// sends the server a "QUIT" message, closes the socket, and exits the screen if user clicks "x" instead of exit
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() { 
				@Override
				public void handle(WindowEvent t) {
					try {
						socketUtils.sendMessage("QUIT");
						socketUtils.closeSocket();
						System.out.println("Socket Server Closed...");
					}
					catch (Exception e1) {
						
					}
					Platform.exit();
					System.exit(0);
				}
		});
		
		// sends the server a "QUIT" message, closes the socket, and exits the screen if user clicks "EXIT"
		exitButton.setOnAction(new EventHandler <ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					socketUtils.sendMessage("QUIT");
					socketUtils.closeSocket();
					System.out.println("Socket Server Closed...");
				}
				catch (Exception e1) {
					
				}
				System.exit(0);
			}		
		});
		
		// button that returns the user to the original home menu
		btn1.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e) {
				bp.setCenter(center);
				window.setScene(scene);
			}
		});
		
		// button that sets the center border pane to all the different food items 
		btn2.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e) {
				ScrollPane sp = new ScrollPane();
				GridPane tempGP = new GridPane();
				tempGP.setPadding(new Insets(20, 60, 20, 60));
				tempGP.setVgap(8);
				tempGP.setHgap(40);
				
				GridPane.setConstraints(chickenVB, 0, 0);
				GridPane.setConstraints(salmonVB, 1, 0);
				GridPane.setConstraints(asparagusVB, 0, 1);
				GridPane.setConstraints(bsVB, 1, 1);
				GridPane.setConstraints(breadVB, 0, 2);
				GridPane.setConstraints(muffinVB, 1, 2);
				
				tempGP.getChildren().addAll(chickenVB, salmonVB, asparagusVB, bsVB, breadVB, muffinVB);
				sp.setContent(tempGP);
				
				bp.setCenter(sp);
				window.setScene(scene);
			}
		});
		
		// button that sets the center border pane to the delivery time section
		btn3.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				ScrollPane deliverySP = new ScrollPane();
				GridPane deliveryGP = new GridPane();
				deliveryGP.setPadding(new Insets(20, 60, 20, 60));
				deliveryGP.setVgap(20);
				deliveryGP.setHgap(40);
				
				Label dateLabel = new Label("Date:");
				GridPane.setConstraints(dateLabel, 0, 0);
				
				// Date Choice Box
				ChoiceBox<String> dateCB = new ChoiceBox<>();
				
				dateCB.getItems().addAll("Monday, March 4", "Tuesday, March 5", "Wednesday, March 6", "Thursday, March 7", "Friday, March 8", "Saturday, March 9", "Sunday, March 10");
				dateCB.setValue("Monday, March 4");
				GridPane.setConstraints(dateCB, 1,0);
				
				// Hours of the Day Radio Button
				VBox radio = new VBox();
				radio.setSpacing(5);
				ToggleGroup times = new ToggleGroup();
				RadioButton morning = new RadioButton("9:00 A.M.");
				morning.setToggleGroup(times);
				morning.setUserData("1");
				RadioButton noon = new RadioButton("12:00 P.M.");
				noon.setToggleGroup(times);
				noon.setUserData("2");
				RadioButton afternoon = new RadioButton("3:00 P.M.");
				afternoon.setToggleGroup(times);
				afternoon.setUserData("3");
				radio.getChildren().addAll(morning, noon, afternoon);
				GridPane.setConstraints(radio, 1, 1);
				morning.setSelected(true);
				
				VBox radio1 = new VBox();
				radio1.setSpacing(5);
				ToggleGroup choice = new ToggleGroup();
				RadioButton res = new RadioButton("Residential Delivery");
				res.setToggleGroup(choice);
				res.setUserData("1");
				RadioButton bus = new RadioButton("Business Delivery");
				bus.setToggleGroup(choice);
				bus.setUserData("2");
				radio1.getChildren().addAll(res, bus);
				GridPane.setConstraints(radio1, 1, 2);
				res.setSelected(true);
				
				// Checkout button and the different alerts that checks to see if the user has logged in
				// and have selected at least one item
				Button checkout = new Button("Checkout");
				checkout.setOnAction(new EventHandler<ActionEvent>() { 
					@Override
					public void handle(ActionEvent e) {
						Collection<Integer> values = userCart.values();
						int sum = 0;
						for(int i : values) {
							sum += i;
						}
						
						// conditional to check if the cart has at least one item
						if(sum == 0) {
							Alert carterr = new Alert(
									AlertType.ERROR,
									"You must have at least one item in your cart!",
									ButtonType.OK);
							carterr.setTitle("Cart Error");
							carterr.setHeaderText("Cart Error");
							carterr.showAndWait();
						}
						else {
							// conditional to check if the user is logged in
							if(!username.getText().trim().isEmpty()) {
								selectedTime = dateCB.getValue();
								selectedDate = times.getSelectedToggle().getUserData().toString();
								selectedDelivery = choice.getSelectedToggle().getUserData().toString();
								if(selectedDelivery == "1") selectedDelivery = "Residential Delivery";
								else if (selectedDelivery == "2") selectedDelivery = "Business Delivery";
								String selected = (String) times.getSelectedToggle().getUserData();
								String deliv = (String) choice.getSelectedToggle().getUserData();
								
								//order of order: action | username | truck # | muffins | salmon | asparagus | chicken | brussel sprouts | bread | delivery type
								order =  "TRANSACTION|"+ username.getText() + "|" + selected + "|" + userCart.values() + "|" + deliv;
								order = order.replace("[", "");
								order = order.replace("]", "");
								order = order.replace(",", "|");
								order = order.replaceAll("\\s+", "");
								System.out.println(order);
								
								
								// Confirmation Alert
								Alert confirmation = new Alert(
										AlertType.CONFIRMATION,
												"Username: " 	  + username.getText() 					   + "\n" + 
												"Selected Time: " + selectedTime 	   					   + "\n" + 
												"Selected Date: " + selectedDate 	   					   + "\n" +
												"Cart: "		  + userCart		   					   + "\n" +
												"Total: "		  + "$" + totalFormat.format(total)		   + "\n" +
												"Delivery: "	  + selectedDelivery 					   + "\n", 
										ButtonType.FINISH, ButtonType.CANCEL);
								
								confirmation.setTitle("Checkout Confirmation!");
								confirmation.setHeaderText("Checkout Confirmation");
								confirmation.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
								
								confirmation.showAndWait().ifPresent(response -> {
									if(response == ButtonType.FINISH) {
										/*if(send)*/ socketUtils.sendMessage(order);
										if(send == true) {
											Alert submitted = new Alert(
													AlertType.NONE,
													"Order Submitted!",
													ButtonType.OK);
											submitted.setTitle("Order Submitted!");
											submitted.setHeaderText("Order Submitted");
											submitted.showAndWait();
											//send = true;
										}
									}
								});

							} else {
								Alert loginerr = new Alert(
										AlertType.ERROR,
										"You must login to checkout!",
										ButtonType.OK);
								loginerr.setTitle("Login Error");
								loginerr.setHeaderText("Login Error");
								loginerr.showAndWait();
							}

						}
						
					}
				});
				GridPane.setConstraints(checkout, 1, 3);

				deliveryGP.getChildren().addAll(dateLabel, dateCB, radio, radio1, checkout);
				deliverySP.setContent(deliveryGP);
				
				bp.setCenter(deliverySP);
				window.setScene(scene);
			}
		});
		
		// ImageView from sidebar that changes the center border pane to ONLY protein options
		proteinV.setOnMouseClicked(e->{
			GridPane proteinGP = new GridPane();
			proteinGP.setPadding(new Insets(20, 60, 20, 60));
			proteinGP.setVgap(8);
			proteinGP.setHgap(40);
			
			GridPane.setConstraints(chickenVB, 0, 0);
			GridPane.setConstraints(salmonVB, 1, 0);
			
			proteinGP.getChildren().addAll(chickenVB, salmonVB);
			
			sp.setContent(proteinGP);
			
			bp.setCenter(sp);
			window.setScene(scene);
		});
		
		// ImageView from sidebar that changes the center border pane to ONLY vegetable options
		greensV.setOnMouseClicked(e->{
			GridPane greensGP = new GridPane();
			greensGP.setPadding(new Insets(20, 60, 20, 60));
			greensGP.setVgap(8);
			greensGP.setHgap(40);
			
			GridPane.setConstraints(asparagusVB, 0, 0);
			GridPane.setConstraints(bsVB, 1, 0);
			
			greensGP.getChildren().addAll(asparagusVB, bsVB);
			
			sp.setContent(greensGP);
			
			bp.setCenter(sp);
			window.setScene(scene);
		});
		
		// ImageView from sidebar that changes the center border pane to ONLY bakery options
		bakeryV.setOnMouseClicked(e->{
			GridPane bakeryGP = new GridPane();
			bakeryGP.setPadding(new Insets(20, 60, 20, 60));
			bakeryGP.setVgap(8);
			bakeryGP.setHgap(40);
			
			GridPane.setConstraints(breadVB, 0, 0);
			GridPane.setConstraints(muffinVB, 1, 0);
			
			bakeryGP.getChildren().addAll(breadVB, muffinVB);
			
			sp.setContent(bakeryGP);
			
			bp.setCenter(sp);
			window.setScene(scene);
			
		});
	}
	
	// Calculate total and amount of item -- used in food.java
	public static void calculateAmount(int itemAmount, double price, TextField textAmount, Text cartTotal, char sign) {
		if(sign == '+') total += price;
		else total -= price;
		
		textAmount.setText(String.valueOf(itemAmount));	
		cartTotal.setText("$" +  totalFormat.format(total));
	
	}
	
	// Clock - thread code
    private void refreshClock()
    {
    	Thread refreshClock = new Thread()
		   {  
			  public void run()
			  {	 
				while (true)
				{
					Date dte = new Date();
		
					String topMenuStr = "       " + dte.toString();					      
				    clock.setText(topMenuStr); 
				    clock.setStyle("-fx-padding:150;");
			               
				    try
				    {
					   sleep(1000L);
				    }
				    catch (InterruptedException e) 
				    {
					   e.printStackTrace();
				    }
				  
	            }  // end while ( true )
				 
		    } // end run thread
		 };

	     refreshClock.start();
    }

	public static void main(String[] args) {
		launch(args);
	}
}
