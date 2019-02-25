//Zachary Dulac, Trung Nguyen, Abby Tse
//TODO: DATE
//GUI Pea Pod Client Clone 

package peapod;

import java.text.DecimalFormat;
import java.util.Date;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

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
	
	double total = 0.00; //cart total
	DecimalFormat totalFormat = new DecimalFormat("#0.00");
	
	int cNum = 0; //number of chicken
	int sNum = 0; //number of salmon
	int aNum = 0; //number of asparagus
	int bsNum = 0; //number of brussels sprouts
	int bNum = 0; //number of bread
	int mNum = 0; //number of muffins
	
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
		
		Text user = new Text("Jane Doe");
		user.setFont(Font.font("Verdana", 20));
		
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
	
	
//=====================
//		PROTEIN 
//=====================
		ScrollPane sp = new ScrollPane();
		GridPane proteinGP = new GridPane();
		proteinGP.setPadding(new Insets(20, 60, 20, 60));
		proteinGP.setVgap(8);
		proteinGP.setHgap(40);
		
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
					cNum--;
					calculateAmount(cNum, 4.99, chickenAmount, cartTotal, '-');
				}
			}
		});
		chickenD.setPrefWidth(25);
		Button chickenI = new Button("+");
		chickenI.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				cNum++;
				calculateAmount(cNum, 4.99, chickenAmount, cartTotal, '+');
			}
		});
		chickenI.setPrefWidth(25);
		
		Region cr1 = new Region();
		HBox.setHgrow(cr1, Priority.ALWAYS);
		Region cr2 = new Region();
		HBox.setHgrow(cr2, Priority.ALWAYS);
		
		chickenHB.getChildren().addAll(cr1, chickenD, chickenAmount, chickenI, cr2);
		
		chickenVB.getChildren().addAll(chickenV, chickenSP, chickenHB);
		GridPane.setConstraints(chickenVB, 0, 0);
		
//		PROTEIN - Salmon
		VBox salmonVB = new VBox();
		
		Image salmon = new Image("/resources/salmon-t.png");
		ImageView salmonV = new ImageView(salmon);
		salmonV.setFitWidth(250);
//		salmonnV.setFitHeight();
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
					sNum--;
					calculateAmount(sNum, 10.99, salmonAmount, cartTotal, '-');
				}
			}
		});
		salmonD.setPrefWidth(25);
		Button salmonI = new Button("+");
		salmonI.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				sNum++;
				calculateAmount(sNum, 10.99, salmonAmount, cartTotal, '+');
			}
		});
		salmonI.setPrefWidth(25);
		
		Region sr1 = new Region();
		HBox.setHgrow(sr1, Priority.ALWAYS);
		Region sr2 = new Region();
		HBox.setHgrow(sr2, Priority.ALWAYS);
		
		salmonHB.getChildren().addAll(sr1, salmonD, salmonAmount, salmonI, sr2);
		
		salmonVB.getChildren().addAll(salmonV, salmonSP, salmonHB);
		GridPane.setConstraints(salmonVB, 1, 0);
		
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
					aNum--;
					calculateAmount(aNum, 2.99, asparagusAmount, cartTotal, '-');
				}
			}
		});
		asparagusD.setPrefWidth(25);
		Button asparagusI = new Button("+");
		asparagusI.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				aNum++;
				calculateAmount(aNum, 2.99, asparagusAmount, cartTotal, '+');
			}
		});
		asparagusI.setPrefWidth(25);
		
		Region ar1 = new Region();
		HBox.setHgrow(ar1, Priority.ALWAYS);
		Region ar2 = new Region();
		HBox.setHgrow(ar2, Priority.ALWAYS);
		
		asparagusHB.getChildren().addAll(ar1, asparagusD, asparagusAmount, asparagusI, ar2);
		
		asparagusVB.getChildren().addAll(asparagusV, asparagusSP, asparagusHB);
		GridPane.setConstraints(asparagusVB, 0, 1);
		
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
					bsNum--;
					calculateAmount(bsNum, 2.50, bsAmount, cartTotal, '-');
				}
			}
		});
		bsD.setPrefWidth(25);
		Button bsI = new Button("+");
		bsI.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				bsNum++;
				calculateAmount(bsNum, 2.50, bsAmount, cartTotal, '+');
			}
		});
		bsI.setPrefWidth(25);
		
		Region bsr1 = new Region();
		HBox.setHgrow(bsr1, Priority.ALWAYS);
		Region bsr2 = new Region();
		HBox.setHgrow(bsr2, Priority.ALWAYS);
		
		bsHB.getChildren().addAll(bsr1, bsD, bsAmount, bsI, bsr2);
		
		bsVB.getChildren().addAll(bsV, bsSP, bsHB);
		GridPane.setConstraints(bsVB, 1, 1);
		
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
					bNum--;
					calculateAmount(bNum, 1.99, breadAmount, cartTotal, '-');
				}
			}
		});
		breadD.setPrefWidth(25);
		Button breadI = new Button("+");
		breadI.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				bNum++;
				calculateAmount(bNum, 1.99, breadAmount, cartTotal, '+');
			}
		});
		breadI.setPrefWidth(25);
		
		Region br1 = new Region();
		HBox.setHgrow(br1, Priority.ALWAYS);
		Region br2 = new Region();
		HBox.setHgrow(br2, Priority.ALWAYS);
		
		breadHB.getChildren().addAll(br1, breadD, breadAmount, breadI, br2);
		
		breadVB.getChildren().addAll(breadV, breadSP, breadHB);
		GridPane.setConstraints(breadVB, 0, 2);
		
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
					mNum--;
					calculateAmount(mNum, 3.69, muffinAmount, cartTotal, '-');
				}
			}
		});
		muffinD.setPrefWidth(25);
		Button muffinI = new Button("+");
		muffinI.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				mNum++;
				calculateAmount(mNum, 3.69, muffinAmount, cartTotal, '+');
			}
		});
		muffinI.setPrefWidth(25);
		
		Region mr1 = new Region();
		HBox.setHgrow(mr1, Priority.ALWAYS);
		Region mr2 = new Region();
		HBox.setHgrow(mr2, Priority.ALWAYS);
		
		muffinHB.getChildren().addAll(mr1, muffinD, muffinAmount, muffinI, mr2);
		
		muffinVB.getChildren().addAll(muffinV, muffinSP, muffinHB);
		GridPane.setConstraints(muffinVB, 1, 2);
		
		proteinGP.getChildren().addAll(chickenVB, salmonVB, asparagusVB, bsVB, breadVB, muffinVB);
		
		sp.setContent(proteinGP);
		
//		Border Pane
//		ScrollPane bpSP = new ScrollPane(); TODO: FIX SCROLLING
//		bpSP.setContent(bp);
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
			}
		});
		
		btn2.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e) {
				bp.setCenter(sp);
				window.setScene(scene);
			}
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
		launch(args);
	}

}
