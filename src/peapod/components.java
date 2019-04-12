package peapod;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Pair;

public class components {
	
	// Top of the navbar
	public static HBox topMenu() {
		HBox topMenu = new HBox();
		topMenu.setPadding(new Insets(20));
		topMenu.setStyle("-fx-border-color: black;" + "-fx-border-width: 3;" + "-fx-background-color: white;");
		
		Text title = new Text("Peapod");
		title.setFont(Font.font("Verdana", 40));
		title.setStyle("-fx-font-weight: bold");
		
		// left of the top of the navbar 
		Image logo = new Image("/resources/logo.png");
		ImageView logoV = new ImageView(logo);
		logoV.setFitWidth(100);
		logoV.setPreserveRatio(true);
		logoV.setSmooth(true);
		logoV.setCache(true);
		
		Region r1 = new Region();
		HBox.setHgrow(r1, Priority.ALWAYS);
		
		// center of the top of the navbar
		StackPane clockSP = new StackPane();
		peapod.clock = new Text();
		peapod.clock.setFont(Font.font("Verdana", 20));
		peapod.clock.setTextAlignment(TextAlignment.CENTER);
		clockSP.getChildren().add(peapod.clock);
		StackPane.setAlignment(peapod.clock, Pos.CENTER);
		StackPane.setMargin(peapod.clock, new Insets(5, 0, 0, 0));
		
		Region r2 = new Region();
		HBox.setHgrow(r2, Priority.ALWAYS);
		
		// right of the top of the navbar
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

			peapod.username.setPromptText("Username");
			PasswordField password = new PasswordField();
			password.setPromptText("Password");

			grid.add(new Label("Username:"), 0, 0);
			grid.add(peapod.username, 1, 0);
			grid.add(new Label("Password:"), 0, 1);
			grid.add(password, 1, 1);

			// Enable/Disable login button depending on whether a username was entered.
			Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
			loginButton.setDisable(true);

			// Do some validation (using the Java 8 lambda syntax).
			peapod.username.textProperty().addListener((observable, oldValue, newValue) -> {
			    loginButton.setDisable(newValue.trim().isEmpty());
			});

			dialog.getDialogPane().setContent(grid);

			// Request focus on the username field by default.
			Platform.runLater(() -> peapod.username.requestFocus());

			// Convert the result to a username-password-pair when the login button is clicked.
			dialog.setResultConverter(dialogButton -> {
			    if (dialogButton == loginButtonType) {
//			    	wrFile.wrTransactionData("LOGIN: " + username.getText() + " || " + password.getText());
			    	return new Pair<>(peapod.username.getText(), password.getText());
			    }
			    return null;
			});

			Optional<Pair<String, String>> result = dialog.showAndWait();

			boolean found = false;
			String tempUsername = "";
			String tempPassword = "";

			try
			{
				InputStream inputStream = old_peapod.class.getResourceAsStream("users.txt");
				InputStreamReader inputReader = new InputStreamReader(inputStream);
				BufferedReader reader = new BufferedReader(inputReader);
				String line = null;
				while((line = reader.readLine()) != null) {
					String[] login = line.replaceAll("\\s+","").split(",");
					tempUsername = login[0];
					tempPassword = login[1];
					if(tempUsername.trim().equals(peapod.username.getText()) && tempPassword.trim().equals(password.getText())){
						found = true;
					}
				}
			}
			catch(Exception e1)
			{
				e1.printStackTrace();
			}

			if (found == true) {
				user.setText(peapod.username.getText());
//				wrFile.wrTransactionData("LOGIN: SUCCESS");
			}else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error User Not Found!");
				alert.setHeaderText("Error User Not Found!");
				alert.setContentText("Please try again.");

				alert.showAndWait();
//				wrFile.wrTransactionData("ERROR: USER NOT FOUND");
			}

		    }
		});
		
		Image cart = new Image("/resources/cart.png");
		ImageView cartV = new ImageView(cart);
		cartV.setStyle("-fx-padding: 50 0 0 0");
		cartV.setFitWidth(30);
		cartV.setPreserveRatio(true);
		cartV.setSmooth(true);
		cartV.setCache(true);
		
		peapod.cartTotal.setText("$" + peapod.totalFormat.format(peapod.total));
		peapod.cartTotal.setFont(Font.font("Verdana", 20));
		
		right.getChildren().addAll(keyV, user, cartV, peapod.cartTotal);
		rightSP.getChildren().add(right);
		StackPane.setAlignment(right, Pos.CENTER);
		StackPane.setMargin(right, new Insets(17, 0, 0, 0));
		
		topMenu.getChildren().addAll(title, logoV, r1, clockSP, r2, rightSP);
		
		return topMenu;
	}
	
	// left button of the bottom of the navbar
	public static Button btn1() {
		Button btn1 = new Button("Home");
		btn1.setMinSize(.333*peapod.height, 50);
		btn1.setStyle("-fx-background-radius: 0;" + "-fx-background-color: white;" + "-fx-border-color: black;" + "-fx-border-width: 3;");
		btn1.setFont(Font.font("Verdana", 17));
		
		return btn1;
	}
	
	// center button of the bottom of the navbar
	public static Button btn2() {
		Button btn2 = new Button("Browse All Food");
		btn2.setMinSize(.334*peapod.height, 50);
		btn2.setStyle("-fx-background-radius: 0;" + "-fx-background-color: white;" + "-fx-border-color: black;" + "-fx-border-width: 3;");
		btn2.setFont(Font.font("Verdana", 17));
		
		return btn2;
	}
	
	// right button of the bottom of the navbar 
	public static Button btn3() {
		Button btn3 = new Button("Delivery Times");
		btn3.setMinSize(.333*peapod.height, 50);
		btn3.setStyle("-fx-background-radius: 0;" + "-fx-background-color: white;" + "-fx-border-color: black;" + "-fx-border-width: 3;");
		btn3.setFont(Font.font("Verdana", 17));
		
		return btn3;
	}
	
	// protein imageview of the sidebar
	public static ImageView proteinV() {
		Image protein = new Image("/resources/protein.jpg");
		ImageView proteinV = new ImageView(protein);
		proteinV.setFitWidth(.33*peapod.height);
		proteinV.setFitHeight(190);
		proteinV.setSmooth(true);
		proteinV.setCache(true);
		
		return proteinV;
	}
	
	// greens imageview of the sidebar
	public static ImageView greensV() {
		Image greens = new Image("/resources/greens.jpg");
		ImageView greensV = new ImageView(greens);
		greensV.setFitWidth(.33*peapod.height);
		greensV.setFitHeight(230);
		greensV.setSmooth(true);
		greensV.setCache(true);
		
		return greensV;
	}
	
	// bakery imageview of the sidebar
	public static ImageView bakeryV() {
		Image bakery = new Image("/resources/bakery.png");
		ImageView bakeryV = new ImageView(bakery);
		bakeryV.setFitWidth(.33*peapod.height);
		bakeryV.setPreserveRatio(true);
		bakeryV.setSmooth(true);
		bakeryV.setCache(true);
		
		return bakeryV;
	}
	
	// center of the borderpane
	public static VBox center() {
		VBox center = new VBox();
		
		Image main = new Image("/resources/main.png");
		ImageView mainV = new ImageView(main);
		mainV.setFitWidth(.666*peapod.height);
		mainV.setFitHeight(500);
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
		
		return center;
	}
	
}
