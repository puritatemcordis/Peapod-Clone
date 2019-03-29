//Zachary Dulac, Trung Nguyen, Abby Tse
//TODO: DATE
//GUI Pea Pod Client Clone 

package peapod;

import java.util.*;

import java.io.File;
import java.util.Date;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import java.text.DecimalFormat;

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
	
	//Declare a new fileIO for transaction log.
	fileIO wrFile = new fileIO();
	
	Text clock;
	Stage window;
	
	int height = 1000;
	int width = 800;
	
	double total = 0.00; //cart total
	DecimalFormat totalFormat = new DecimalFormat("#0.00");
	
	int cNum = 0; //number of chicken
	int sNum = 0; //number of salmon
	int aNum = 0; //number of asparagus
	int bsNum = 0; //number of brussels sprouts
	int bNum = 0; //number of bread
	int mNum = 0; //number of muffins
	
	String selectedTime = ""; //selected delivery time 
	String selectedDate = ""; //selected delivery date
	TextField username = new TextField(); //username
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		Map<String, Integer> userCart = new HashMap<String, Integer>();
		userCart.put("Chicken", 0);
		userCart.put("Salmon", 0);
		userCart.put("Asparagus", 0);
		userCart.put("Brussels Sprouts", 0);
		userCart.put("Bread", 0);
		userCart.put("Muffins", 0);
		
		window = primaryStage;
		window.setTitle("PeaPod Clone");
		
//		Navbar 
		VBox navbar = new VBox();
		
//		Top of the navbar
		HBox topMenu = new HBox();
		topMenu.setPadding(new Insets(20));
		topMenu.setStyle("-fx-border-color: black;" + "-fx-border-width: 3;" + "-fx-background-color: white;");
		
		Text title = new Text("Peapod");
		title.setFont(Font.font("Verdana", 40));
		title.setStyle("-fx-font-weight: bold");
		
		Image logo = new Image("/resources/logo.png");
		ImageView logoV = new ImageView(logo);
		logoV.setFitWidth(100);
		logoV.setPreserveRatio(true);
		logoV.setSmooth(true);
		logoV.setCache(true);
		
		Region r1 = new Region();
		HBox.setHgrow(r1, Priority.ALWAYS);
		
//		TOP MIDDLE
		StackPane clockSP = new StackPane();
		clock = new Text();
		clock.setFont(Font.font("Verdana", 20));
		clock.setTextAlignment(TextAlignment.CENTER);
		clockSP.getChildren().add(clock);
		StackPane.setAlignment(clock, Pos.CENTER);
		StackPane.setMargin(clock, new Insets(5, 0, 0, 0));
		
		Region r2 = new Region();
		HBox.setHgrow(r2, Priority.ALWAYS);
		
//		TOP RIGHT
		StackPane rightSP = new StackPane();
		HBox right = new HBox(7);
		
		Image key = new Image("/resources/key.png");
		ImageView keyV = new ImageView(key);
		keyV.setFitWidth(30);
		keyV.setPreserveRatio(true);
		keyV.setSmooth(true);
		keyV.setCache(true);
		
		//add button for login 
		Button user    = new Button("Login");
		user.setOnAction(new EventHandler<ActionEvent>()
		{
		    @Override public void handle(ActionEvent e)
		    {   
			// Create the custom dialog.
			Dialog<Pair<String, String>> dialog = new Dialog<>();
			dialog.setTitle("Login Dialog");
			dialog.setHeaderText("Login");

			// Set the button types.
			ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
			dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

			// Create the username and password labels and fields.
			GridPane grid = new GridPane();
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(20, 150, 10, 10));

			username.setPromptText("Username");
			PasswordField password = new PasswordField();
			password.setPromptText("Password");

			grid.add(new Label("Username:"), 0, 0);
			grid.add(username, 1, 0);
			grid.add(new Label("Password:"), 0, 1);
			grid.add(password, 1, 1);

			// Enable/Disable login button depending on whether a username was entered.
			Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
			loginButton.setDisable(true);

			// Do some validation (using the Java 8 lambda syntax).
			username.textProperty().addListener((observable, oldValue, newValue) -> {
			    loginButton.setDisable(newValue.trim().isEmpty());
			});

			dialog.getDialogPane().setContent(grid);

			// Request focus on the username field by default.
			Platform.runLater(() -> username.requestFocus());

			// Convert the result to a username-password-pair when the login button is clicked.
			dialog.setResultConverter(dialogButton -> {
			    if (dialogButton == loginButtonType) {
			    	wrFile.wrTransactionData("LOGIN: " + username.getText() + " || " + password.getText());
			    	return new Pair<>(username.getText(), password.getText());
			    }
			    return null;
			});

			Optional<Pair<String, String>> result = dialog.showAndWait();

			boolean found = false;
			String tempUsername = "";
			String tempPassword = "";

			try
			{
				Scanner x = new Scanner(new File("users.txt"));
				x.useDelimiter("[,\n]");

				while(x.hasNext() && !found)
				{
					tempUsername = x.next();
					tempPassword = x.next();
					
					if(tempUsername.trim().equals(username.getText()) && tempPassword.trim().equals(password.getText()))
					{
						found = true;
					}
				}
				x.close();
			}
			catch(Exception e1)
			{
				e1.printStackTrace();
			}

			if (found == true) {
				user.setText(username.getText());
				wrFile.wrTransactionData("LOGIN: SUCCESS");
			}else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error User Not Found!");
				alert.setHeaderText("Error User Not Found!");
				alert.setContentText("Please try again.");

				alert.showAndWait();
				wrFile.wrTransactionData("ERROR: USER NOT FOUND");
			}

		    }
		});
		
		//Text user = new Text("Jane Doe");
		//user.setFont(Font.font("Verdana", 20));
		
		Image cart = new Image("/resources/cart.png");
		ImageView cartV = new ImageView(cart);
		cartV.setStyle("-fx-padding: 50 0 0 0");
		cartV.setFitWidth(30);
		cartV.setPreserveRatio(true);
		cartV.setSmooth(true);
		cartV.setCache(true);
		
		Text cartTotal = new Text();
		cartTotal.setText("$" + totalFormat.format(total));
		cartTotal.setFont(Font.font("Verdana", 20));
		
		right.getChildren().addAll(keyV, user, cartV, cartTotal);
		rightSP.getChildren().add(right);
		StackPane.setAlignment(right, Pos.CENTER);
		StackPane.setMargin(right, new Insets(17, 0, 0, 0));
		
		topMenu.getChildren().addAll(title, logoV, r1, clockSP, r2, rightSP);
		
//		Bottom of the navbar
		HBox bottomMenu = new HBox();
		bottomMenu.setStyle("-fx-font-weight: bold");
		
//		TODO: MAKE BUTTONS DYNAMICALLY CHANGE ACCORDING TO SCENE RATIO
		Button btn1 = new Button("Home");
		btn1.setMinSize(.333*height, 50);
		btn1.setStyle("-fx-background-radius: 0;" + "-fx-background-color: white;" + "-fx-border-color: black;" + "-fx-border-width: 3;");
		btn1.setFont(Font.font("Verdana", 17));
		
		
		Button btn2 = new Button("Browse All Food");
		btn2.setMinSize(.334*height, 50);
		btn2.setStyle("-fx-background-radius: 0;" + "-fx-background-color: white;" + "-fx-border-color: black;" + "-fx-border-width: 3;");
		btn2.setFont(Font.font("Verdana", 17));
		
		Button btn3 = new Button("Delivery Times");
		btn3.setMinSize(.333*height, 50);
		btn3.setStyle("-fx-background-radius: 0;" + "-fx-background-color: white;" + "-fx-border-color: black;" + "-fx-border-width: 3;");
		btn3.setFont(Font.font("Verdana", 17));
		
		bottomMenu.getChildren().addAll(btn1, btn2, btn3);
		
		navbar.getChildren().addAll(topMenu, bottomMenu);
		
//		Sidebar
//		TODO: ADD BORDERS TO IMAGES
		VBox sidebar = new VBox();
		sidebar.setStyle("-fx-border-color: black;" + "-fx-border-width: 3;");
		sidebar.setMinWidth(.31*height);
		
		Image protein = new Image("/resources/protein.jpg");
		ImageView proteinV = new ImageView(protein);
//		proteinV.setStyle("-fx-border-color: black;" + "-fx-border-width: 3;");
		proteinV.setFitWidth(.33*height);
		proteinV.setFitHeight(190);
//		proteinV.setPreserveRatio(true);
		proteinV.setSmooth(true);
		proteinV.setCache(true);
		
		Image greens = new Image("/resources/greens.jpg");
		ImageView greensV = new ImageView(greens);
//		greensV.setStyle("-fx-border-color: black;" + "-fx-border-width: 3;");
		greensV.setFitWidth(.33*height);
		greensV.setFitHeight(230);
		greensV.setSmooth(true);
		greensV.setCache(true);
		
		Image bakery = new Image("/resources/bakery.png");
		ImageView bakeryV = new ImageView(bakery);
//		bakeryV.setStyle("-fx-border-color: black;" + "-fx-border-width: 3;");
		bakeryV.setFitWidth(.33*height);
		bakeryV.setPreserveRatio(true);
		bakeryV.setSmooth(true);
		bakeryV.setCache(true);
		
		sidebar.getChildren().addAll(proteinV, greensV, bakeryV);
		
//		Center display
//=====================
//		MAIN PAGE
//=====================
		
		VBox center = new VBox();
		
		Image main = new Image("/resources/main.png");
		ImageView mainV = new ImageView(main);
		mainV.setFitWidth(.666*height);
		mainV.setFitHeight(428);
		mainV.setSmooth(true);
		mainV.setCache(true);
		
		StackPane greetingSP = new StackPane();
		Text greeting = new Text("Welcome to Peapod\n" + "Free delivery with orders over $60");
		greeting.setFont(Font.font("Verdana", 30));
		greeting.setStyle("-fx-font-weight: bold;" + "-fx-border-color: black;" + "-fx-border-width: 3;");
		greeting.setTextAlignment(TextAlignment.CENTER);
		greetingSP.getChildren().add(greeting);
		StackPane.setAlignment(greeting, Pos.CENTER);
		StackPane.setMargin(greeting, new Insets(60, 0, 0, 0));	
		
		center.getChildren().addAll(mainV, greetingSP);
	
		ScrollPane sp = new ScrollPane();
		GridPane allGP = new GridPane();
		allGP.setPadding(new Insets(20, 60, 20, 60));
		allGP.setVgap(8);
		allGP.setHgap(40);
//=====================
//		PROTEIN 
//=====================		
//		PROTEIN - Chicken
		VBox chickenVB = new VBox();
		
		Image chicken = new Image("/resources/chicken-t.png");
		ImageView chickenV = new ImageView(chicken);
		chickenV.setFitWidth(250);
		chickenV.setPreserveRatio(true);
		chickenV.setSmooth(true);
		chickenV.setCache(true);
		
		StackPane chickenSP = new StackPane();
		Text chickenDesc = new Text("Perdue Chicken Breasts\nBoneless Skinless Thin\nSliced - 5-6 ct Fresh\n$4.99");
		chickenDesc.setFont(Font.font("Verdana", 20));
		chickenDesc.setTextAlignment(TextAlignment.CENTER);
		chickenSP.getChildren().add(chickenDesc);
		StackPane.setAlignment(chickenDesc, Pos.CENTER);
		
		
		HBox chickenHB = new HBox();
		chickenHB.setSpacing(5);
		
		TextField chickenAmount = new TextField(String.valueOf(cNum));
		chickenAmount.setEditable(false);
		chickenAmount.setStyle("-fx-alignment: center");
		chickenAmount.setFont(Font.font("Verdana", 15));
		chickenAmount.setMaxWidth(60);
		Button chickenD = new Button("-");
		chickenD.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if(cNum > 0) {
					userCart.put("Chicken", userCart.get("Chicken")-1);
					cNum--;
					calculateAmount(cNum, 4.99, chickenAmount, cartTotal, '-');
				}
				wrFile.wrTransactionData("CHICKEN- || NEW: " + cNum);
			}
		});
		chickenD.setPrefWidth(25);
		Button chickenI = new Button("+");
		chickenI.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				userCart.put("Chicken", userCart.get("Chicken")+1);
				cNum++;
				calculateAmount(cNum, 4.99, chickenAmount, cartTotal, '+');
				wrFile.wrTransactionData("CHICKEN+ || NEW: " + cNum);
			}
		});
		chickenI.setPrefWidth(25);
		
		Region cr1 = new Region();
		HBox.setHgrow(cr1, Priority.ALWAYS);
		Region cr2 = new Region();
		HBox.setHgrow(cr2, Priority.ALWAYS);
		
		chickenHB.getChildren().addAll(cr1, chickenD, chickenAmount, chickenI, cr2);
		
		chickenVB.getChildren().addAll(chickenV, chickenSP, chickenHB);
		
//		PROTEIN - Salmon
		VBox salmonVB = new VBox();
		
		Image salmon = new Image("/resources/salmon-t.png");
		ImageView salmonV = new ImageView(salmon);
		salmonV.setFitWidth(250);
		salmonV.setPreserveRatio(true);
		salmonV.setSmooth(true);
		salmonV.setCache(true);
		
		StackPane salmonSP = new StackPane();
		Text salmonDesc = new Text("Atlantic Salmon Fillets\nSkinless Farm-Raised Fresh\n$10.99");
		salmonDesc.setFont(Font.font("Verdana", 20));
		salmonDesc.setTextAlignment(TextAlignment.CENTER);
		salmonSP.getChildren().add(salmonDesc);
		StackPane.setAlignment(salmonDesc, Pos.CENTER);
		
		HBox salmonHB = new HBox();
		salmonHB.setSpacing(5);
		
		TextField salmonAmount = new TextField(String.valueOf(sNum));
		salmonAmount.setEditable(false);
		salmonAmount.setStyle("-fx-alignment: center");
		salmonAmount.setFont(Font.font("Verdana", 15));
		salmonAmount.setMaxWidth(60);
		Button salmonD = new Button("-");
		salmonD.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if(sNum > 0) {
					userCart.put("Salmon", userCart.get("Salmon")-1);
					sNum--;
					calculateAmount(sNum, 10.99, salmonAmount, cartTotal, '-');
				}
				wrFile.wrTransactionData("SALMON- || NEW: " + sNum);
			}
		});
		salmonD.setPrefWidth(25);
		Button salmonI = new Button("+");
		salmonI.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				userCart.put("Salmon", userCart.get("Salmon")+1);
				sNum++;
				calculateAmount(sNum, 10.99, salmonAmount, cartTotal, '+');
				wrFile.wrTransactionData("SALMON+ || NEW: " + sNum);
			}
		});
		salmonI.setPrefWidth(25);
		
		Region sr1 = new Region();
		HBox.setHgrow(sr1, Priority.ALWAYS);
		Region sr2 = new Region();
		HBox.setHgrow(sr2, Priority.ALWAYS);
		
		salmonHB.getChildren().addAll(sr1, salmonD, salmonAmount, salmonI, sr2);
		
		salmonVB.getChildren().addAll(salmonV, salmonSP, salmonHB);
		
		
//=====================
//		VEGETABLES 
//=====================
//		VEGETABLES - Asparagus
		VBox asparagusVB = new VBox();
		
		Image asparagus = new Image("/resources/asparagus.png");
		ImageView asparagusV = new ImageView(asparagus);
		asparagusV.setFitWidth(250);
		asparagusV.setPreserveRatio(true);
		asparagusV.setSmooth(true);
		asparagusV.setCache(true);
		
		StackPane asparagusSP = new StackPane();
		Text asparagusDesc = new Text("Asparagus\n$2.99");
		asparagusDesc.setFont(Font.font("Verdana", 20));
		asparagusDesc.setTextAlignment(TextAlignment.CENTER);
		asparagusSP.getChildren().add(asparagusDesc);
		StackPane.setAlignment(asparagusDesc, Pos.CENTER);
		
		HBox asparagusHB = new HBox();
		asparagusHB.setSpacing(5);
		
		TextField asparagusAmount = new TextField(String.valueOf(aNum));
		asparagusAmount.setEditable(false);
		asparagusAmount.setStyle("-fx-alignment: center");
		asparagusAmount.setFont(Font.font("Verdana", 15));
		asparagusAmount.setMaxWidth(60);
		Button asparagusD = new Button("-");
		asparagusD.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if(aNum > 0) {
					userCart.put("Asparagus", userCart.get("Asparagus")-1);
					aNum--;
					calculateAmount(aNum, 2.99, asparagusAmount, cartTotal, '-');
				}
				wrFile.wrTransactionData("ASPARAGUS- || NEW: " + aNum);
			}
		});
		asparagusD.setPrefWidth(25);
		Button asparagusI = new Button("+");
		asparagusI.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				userCart.put("Asparagus", userCart.get("Asparagus")+1);
				aNum++;
				calculateAmount(aNum, 2.99, asparagusAmount, cartTotal, '+');
				wrFile.wrTransactionData("ASPARAGUS+ || NEW: " + aNum);
			}
		});
		asparagusI.setPrefWidth(25);
		
		Region ar1 = new Region();
		HBox.setHgrow(ar1, Priority.ALWAYS);
		Region ar2 = new Region();
		HBox.setHgrow(ar2, Priority.ALWAYS);
		
		asparagusHB.getChildren().addAll(ar1, asparagusD, asparagusAmount, asparagusI, ar2);
		
		asparagusVB.getChildren().addAll(asparagusV, asparagusSP, asparagusHB);
		
//		VEGETABLES - Brussel Sprouts
		
		VBox bsVB = new VBox();
		
		Image bs = new Image("/resources/brussel.png");
		ImageView bsV = new ImageView(bs);
		bsV.setFitWidth(250);
		bsV.setPreserveRatio(true);
		bsV.setSmooth(true);
		bsV.setCache(true);
		
		StackPane bsSP = new StackPane();
		Text bsDesc = new Text("Brussels Sprouts\n$2.50");
		bsDesc.setFont(Font.font("Verdana", 20));
		bsDesc.setTextAlignment(TextAlignment.CENTER);
		bsSP.getChildren().add(bsDesc);
		StackPane.setAlignment(bsDesc, Pos.CENTER);
		
		HBox bsHB = new HBox();
		bsHB.setSpacing(5);
		
		TextField bsAmount = new TextField(String.valueOf(cNum));
		bsAmount.setEditable(false);
		bsAmount.setStyle("-fx-alignment: center");
		bsAmount.setFont(Font.font("Verdana", 15));
		bsAmount.setMaxWidth(60);
		Button bsD = new Button("-");
		bsD.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if(bsNum > 0) {
					userCart.put("Brussels Sprouts", userCart.get("Brussels Sprouts")-1);
					bsNum--;
					calculateAmount(bsNum, 2.50, bsAmount, cartTotal, '-');
				}
				wrFile.wrTransactionData("SPROUTS- || NEW: " + bsNum);
			}
		});
		bsD.setPrefWidth(25);
		Button bsI = new Button("+");
		bsI.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				userCart.put("Brussels Sprouts", userCart.get("Brussels Sprouts")+1);
				bsNum++;
				calculateAmount(bsNum, 2.50, bsAmount, cartTotal, '+');
				wrFile.wrTransactionData("SPROUTS+ || NEW: " + bsNum);
			}
		});
		bsI.setPrefWidth(25);
		
		Region bsr1 = new Region();
		HBox.setHgrow(bsr1, Priority.ALWAYS);
		Region bsr2 = new Region();
		HBox.setHgrow(bsr2, Priority.ALWAYS);
		
		bsHB.getChildren().addAll(bsr1, bsD, bsAmount, bsI, bsr2);
		
		bsVB.getChildren().addAll(bsV, bsSP, bsHB);
		
//		BAKERY - bread
		VBox breadVB = new VBox();
		
		Image bread = new Image("/resources/bread.png");
		ImageView breadV = new ImageView(bread);
		breadV.setFitWidth(250);
		breadV.setPreserveRatio(true);
		breadV.setSmooth(true);
		breadV.setCache(true);
		
		StackPane breadSP = new StackPane();
		Text breadDesc = new Text("NY Brooklyn Bread\nHandmade Italian Loaf\n$1.99");
		breadDesc.setFont(Font.font("Verdana", 20));
		breadDesc.setTextAlignment(TextAlignment.CENTER);
		breadSP.getChildren().add(breadDesc);
		StackPane.setAlignment(breadDesc, Pos.CENTER);
		
		HBox breadHB = new HBox();
		breadHB.setSpacing(5);
		
		TextField breadAmount = new TextField(String.valueOf(cNum));
		breadAmount.setEditable(false);
		breadAmount.setStyle("-fx-alignment: center");
		breadAmount.setFont(Font.font("Verdana", 15));
		breadAmount.setMaxWidth(60);
		Button breadD = new Button("-");
		breadD.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if(bNum > 0) {
					userCart.put("Bread", userCart.get("Bread")-1);
					bNum--;
					calculateAmount(bNum, 1.99, breadAmount, cartTotal, '-');
				}
				wrFile.wrTransactionData("BREAD- || NEW: " + bNum);
			}
		});
		breadD.setPrefWidth(25);
		Button breadI = new Button("+");
		breadI.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				userCart.put("Bread", userCart.get("Bread")+1);
				bNum++;
				calculateAmount(bNum, 1.99, breadAmount, cartTotal, '+');
				wrFile.wrTransactionData("BREAD+ || NEW: " + bNum);
			}
		});
		breadI.setPrefWidth(25);
		
		Region br1 = new Region();
		HBox.setHgrow(br1, Priority.ALWAYS);
		Region br2 = new Region();
		HBox.setHgrow(br2, Priority.ALWAYS);
		
		breadHB.getChildren().addAll(br1, breadD, breadAmount, breadI, br2);
		
		breadVB.getChildren().addAll(breadV, breadSP, breadHB);
		
//=====================
//		BAKERY 
//=====================
//		BAKERY - muffin
		VBox muffinVB = new VBox();
		
		Image muffin = new Image("/resources/muffin.png");
		ImageView muffinV = new ImageView(muffin);
		muffinV.setFitWidth(250);
		muffinV.setPreserveRatio(true);
		muffinV.setSmooth(true);
		muffinV.setCache(true);
		
		StackPane muffinSP = new StackPane();
		Text muffinDesc = new Text("Thomas' English Muffins\nOriginal - 6ct\n$3.69");
		muffinDesc.setFont(Font.font("Verdana", 20));
		muffinDesc.setTextAlignment(TextAlignment.CENTER);
		muffinSP.getChildren().add(muffinDesc);
		StackPane.setAlignment(muffinDesc, Pos.CENTER);
		
		HBox muffinHB = new HBox();
		muffinHB.setSpacing(5);
		
		TextField muffinAmount = new TextField(String.valueOf(cNum));
		muffinAmount.setEditable(false);
		muffinAmount.setStyle("-fx-alignment: center");
		muffinAmount.setFont(Font.font("Verdana", 15));
		muffinAmount.setMaxWidth(60);
		Button muffinD = new Button("-");
		muffinD.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if(mNum > 0) {
					userCart.put("Muffins", userCart.get("Muffins")-1);
					mNum--;
					calculateAmount(mNum, 3.69, muffinAmount, cartTotal, '-');
				}
				wrFile.wrTransactionData("MUFFIN- || NEW: " + mNum);
			}
		});
		muffinD.setPrefWidth(25);
		Button muffinI = new Button("+");
		muffinI.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				userCart.put("Muffins", userCart.get("Muffins")+1);
				mNum++;
				calculateAmount(mNum, 3.69, muffinAmount, cartTotal, '+');
				wrFile.wrTransactionData("MUFFIN+ || NEW: " + mNum);
			}
		});
		muffinI.setPrefWidth(25);
		
		Region mr1 = new Region();
		HBox.setHgrow(mr1, Priority.ALWAYS);
		Region mr2 = new Region();
		HBox.setHgrow(mr2, Priority.ALWAYS);
		
		muffinHB.getChildren().addAll(mr1, muffinD, muffinAmount, muffinI, mr2);
		
		muffinVB.getChildren().addAll(muffinV, muffinSP, muffinHB);
		
//		Border Pane
		BorderPane bp = new BorderPane();
		bp.setTop(navbar);
		bp.setLeft(sidebar);
		bp.setCenter(center);
		bp.setStyle("-fx-border-color: black;" + "-fx-border-width: 3;");
		
		refreshClock();
		
//		Scene Setup
		Scene scene = new Scene(bp, height, width);
		window.setScene(scene);
		window.show();
		
//		BUTTONS TO CHANGE BORDERPANE
		btn1.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e) {
				bp.setCenter(center);
				window.setScene(scene);
				wrFile.wrTransactionData("HOME");
			}
		});
		
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
				wrFile.wrTransactionData("BROWSE ALL");
			}
		});
		
//=========================
//		DELIVERY TIME
//=========================
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
				
//				Date Choice Box
				ChoiceBox<String> dateCB = new ChoiceBox<>();
				
				dateCB.getItems().addAll("Monday, March 4", "Tuesday, March 5", "Wednesday, March 6", "Thursday, March 7", "Friday, March 8", "Saturday, March 9", "Sunday, March 10");
				dateCB.setValue("Monday, March 4");
				GridPane.setConstraints(dateCB, 1,0);
				
//				Times Radio Button
				VBox radio = new VBox();
				radio.setSpacing(5);
				ToggleGroup times = new ToggleGroup();
				RadioButton morning = new RadioButton("9:00 A.M.");
				morning.setToggleGroup(times);
				morning.setUserData("9:00 A.M.");
				RadioButton noon = new RadioButton("12:00 P.M.");
				noon.setToggleGroup(times);
				noon.setUserData("12:00 P.M.");
				RadioButton afternoon = new RadioButton("3:00 P.M.");
				afternoon.setToggleGroup(times);
				afternoon.setUserData("3:00 P.M.");
				radio.getChildren().addAll(morning, noon, afternoon);
				GridPane.setConstraints(radio, 1, 1);
				
				Button checkout = new Button("Checkout");
				checkout.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent e) {
						selectedTime = dateCB.getValue();
						selectedDate = times.getSelectedToggle().getUserData().toString();
						String order = 
								"Username: " 	  + username.getText() + "\n" + 
								"Selected Time: " + selectedTime 	   + "\n" + 
								"Selected Date: " + selectedDate 	   + "\n" +
								"Cart: "		  + userCart		   + "\n" +
								"Total: "		  + "$"+total		   + "\n";
						
						
//						Confirmation
						Alert confirmation = new Alert(
								AlertType.CONFIRMATION,
										"Username: " 	  + username.getText() 					   + "\n" + 
										"Selected Time: " + selectedTime 	   					   + "\n" + 
										"Selected Date: " + selectedDate 	   					   + "\n" +
										"Cart: "		  + userCart		   					   + "\n" +
										"Total: "		  + "$" + totalFormat.format(total)		   + "\n",
								ButtonType.FINISH, ButtonType.CANCEL);
//						SOCKET CODE
						socketUtils.sendMessage(order);
						socketUtils.closeSocket();
						System.out.println("Socket Server Closed...");
						confirmation.setTitle("Checkout Confirmation!");
						confirmation.setHeaderText("Checkout Confirmation");
						confirmation.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
						confirmation.showAndWait().ifPresent(response -> {
							if(response == ButtonType.FINISH) {
								Alert submitted = new Alert(
										AlertType.NONE,
										"Order Submitted!",
										ButtonType.OK);
								submitted.setTitle("Order Submitted!");
								submitted.setHeaderText("Order Submitted");
								submitted.showAndWait().ifPresent(resp -> {
									System.exit(0);
								});
							}
						});;
					}
				});
				GridPane.setConstraints(checkout, 1, 2);

				deliveryGP.getChildren().addAll(dateLabel, dateCB, radio, checkout);
				deliverySP.setContent(deliveryGP);
				
				bp.setCenter(deliverySP);
				window.setScene(scene);
			}
		});
		
//		IMAGEVIEW FROM SIDEBAR TO CHANGE BORDERPANE
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
			
			wrFile.wrTransactionData("PROTEIN");
		});
		
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
			
			wrFile.wrTransactionData("VEGETABLES");
		});
		
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
			
			wrFile.wrTransactionData("BAKERY");
		});
	}
	
// Calculate total and amount of item
	private void calculateAmount(int itemAmount, double price, TextField textAmount, Text cartTotal, char sign) {
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
//		OPENS SOCKET SEREVER
		socketUtils.socketConnect();
		System.out.println("Socket Server Opened...");
		launch(args);
	}

}
