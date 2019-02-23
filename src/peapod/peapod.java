//Zachary Dulac, Trung Nguyen, Abby Tse
//TODO: DATE
//GUI Pea Pod Client Clone 

package peapod;

import java.util.Date;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Font; 

import javafx.stage.Stage;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

public class peapod extends Application {
	
	Text clock;
	Stage window;
	
	int height = 1000;
	int width = 800;
	
	@Override
	public void start(Stage primaryStage) throws Exception{
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

			TextField username = new TextField();
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

			if (found == true)
				user.setText(username.getText());
			else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error User Not Found!");
				alert.setHeaderText("Error User Not Found!");
				alert.setContentText("Please try again.");

				alert.showAndWait();
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
		
		Text cartTotal = new Text("$45.98");
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
		VBox center = new VBox();
		
		Image main = new Image("/resources/main.png");
		ImageView mainV = new ImageView(main);
		mainV.setFitWidth(.666*height);
		mainV.setFitHeight(428);
//		mainV.setPreserveRatio(true);
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
