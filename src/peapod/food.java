package peapod;

import java.util.HashMap;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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

public class food {
	
	public static VBox chicken(int cNum, Map<String,Integer> userCart, Text cartTotal) {
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
				if(peapod.cNum > 0) {
					userCart.put("Chicken", userCart.get("Chicken")-1);
					peapod.cNum--;
					peapod.calculateAmount(peapod.cNum, 4.99, chickenAmount, cartTotal, '-');
				}
			}
		});
		chickenD.setPrefWidth(25);
		Button chickenI = new Button("+");
		chickenI.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				userCart.put("Chicken", userCart.get("Chicken")+1);
				peapod.cNum++;
				peapod.calculateAmount(peapod.cNum, 4.99, chickenAmount, cartTotal, '+');
			}
		});
		chickenI.setPrefWidth(25);
		
		Region cr1 = new Region();
		HBox.setHgrow(cr1, Priority.ALWAYS);
		Region cr2 = new Region();
		HBox.setHgrow(cr2, Priority.ALWAYS);
		
		chickenHB.getChildren().addAll(cr1, chickenD, chickenAmount, chickenI, cr2);
		
		chickenVB.getChildren().addAll(chickenV, chickenSP, chickenHB);
		
		return chickenVB;
	}
	
	public static VBox salmon(int sNum, Map<String,Integer> userCart, Text cartTotal) {
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
				if(peapod.sNum > 0) {
					userCart.put("Salmon", userCart.get("Salmon")-1);
					peapod.sNum--;
					peapod.calculateAmount(peapod.sNum, 10.99, salmonAmount, cartTotal, '-');
				}
			}
		});
		salmonD.setPrefWidth(25);
		Button salmonI = new Button("+");
		salmonI.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				userCart.put("Salmon", userCart.get("Salmon")+1);
				peapod.sNum++;
				peapod.calculateAmount(peapod.sNum, 10.99, salmonAmount, cartTotal, '+');
			}
		});
		salmonI.setPrefWidth(25);
		
		Region sr1 = new Region();
		HBox.setHgrow(sr1, Priority.ALWAYS);
		Region sr2 = new Region();
		HBox.setHgrow(sr2, Priority.ALWAYS);
		
		salmonHB.getChildren().addAll(sr1, salmonD, salmonAmount, salmonI, sr2);
		
		salmonVB.getChildren().addAll(salmonV, salmonSP, salmonHB);
		
		return salmonVB;
	}
	
	public static VBox asparagus(int aNum, Map<String,Integer> userCart, Text cartTotal) {
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
				if(peapod.aNum > 0) {
					userCart.put("Asparagus", userCart.get("Asparagus")-1);
					peapod.aNum--;
					peapod.calculateAmount(peapod.aNum, 2.99, asparagusAmount, cartTotal, '-');
				}
			}
		});
		asparagusD.setPrefWidth(25);
		Button asparagusI = new Button("+");
		asparagusI.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				userCart.put("Asparagus", userCart.get("Asparagus")+1);
				peapod.aNum++;
				peapod.calculateAmount(peapod.aNum, 2.99, asparagusAmount, cartTotal, '+');
			}
		});
		asparagusI.setPrefWidth(25);
		
		Region ar1 = new Region();
		HBox.setHgrow(ar1, Priority.ALWAYS);
		Region ar2 = new Region();
		HBox.setHgrow(ar2, Priority.ALWAYS);
		
		asparagusHB.getChildren().addAll(ar1, asparagusD, asparagusAmount, asparagusI, ar2);
		
		asparagusVB.getChildren().addAll(asparagusV, asparagusSP, asparagusHB);
		
		return asparagusVB;
	}
	
	public static VBox brusselsprouts(int bsNum, Map<String,Integer> userCart, Text cartTotal) {
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
		
		TextField bsAmount = new TextField(String.valueOf(bsNum));
		bsAmount.setEditable(false);
		bsAmount.setStyle("-fx-alignment: center");
		bsAmount.setFont(Font.font("Verdana", 15));
		bsAmount.setMaxWidth(60);
		Button bsD = new Button("-");
		bsD.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if(peapod.bsNum > 0) {
					userCart.put("Brussels Sprouts", userCart.get("Brussels Sprouts")-1);
					peapod.bsNum--;
					peapod.calculateAmount(peapod.bsNum, 2.50, bsAmount, cartTotal, '-');
				}
			}
		});
		bsD.setPrefWidth(25);
		Button bsI = new Button("+");
		bsI.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				userCart.put("Brussels Sprouts", userCart.get("Brussels Sprouts")+1);
				peapod.bsNum++;
				peapod.calculateAmount(peapod.bsNum, 2.50, bsAmount, cartTotal, '+');
			}
		});
		bsI.setPrefWidth(25);
		
		Region bsr1 = new Region();
		HBox.setHgrow(bsr1, Priority.ALWAYS);
		Region bsr2 = new Region();
		HBox.setHgrow(bsr2, Priority.ALWAYS);
		
		bsHB.getChildren().addAll(bsr1, bsD, bsAmount, bsI, bsr2);
		
		bsVB.getChildren().addAll(bsV, bsSP, bsHB);
		
		return bsVB;
	}
	
	public static VBox bread(int bNum, Map<String,Integer> userCart, Text cartTotal) {
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
		
		TextField breadAmount = new TextField(String.valueOf(bNum));
		breadAmount.setEditable(false);
		breadAmount.setStyle("-fx-alignment: center");
		breadAmount.setFont(Font.font("Verdana", 15));
		breadAmount.setMaxWidth(60);
		Button breadD = new Button("-");
		breadD.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if(peapod.bNum > 0) {
					userCart.put("Bread", userCart.get("Bread")-1);
					peapod.bNum--;
					peapod.calculateAmount(peapod.bNum, 1.99, breadAmount, cartTotal, '-');
				}
			}
		});
		breadD.setPrefWidth(25);
		Button breadI = new Button("+");
		breadI.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				userCart.put("Bread", userCart.get("Bread")+1);
				peapod.bNum++;
				peapod.calculateAmount(peapod.bNum, 1.99, breadAmount, cartTotal, '+');
			}
		});
		breadI.setPrefWidth(25);
		
		Region br1 = new Region();
		HBox.setHgrow(br1, Priority.ALWAYS);
		Region br2 = new Region();
		HBox.setHgrow(br2, Priority.ALWAYS);
		
		breadHB.getChildren().addAll(br1, breadD, breadAmount, breadI, br2);
		
		breadVB.getChildren().addAll(breadV, breadSP, breadHB);
		
		return breadVB;
	}
	
	public static VBox muffin(int mNum, Map<String,Integer> userCart, Text cartTotal) {
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
		
		TextField muffinAmount = new TextField(String.valueOf(mNum));
		muffinAmount.setEditable(false);
		muffinAmount.setStyle("-fx-alignment: center");
		muffinAmount.setFont(Font.font("Verdana", 15));
		muffinAmount.setMaxWidth(60);
		Button muffinD = new Button("-");
		muffinD.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if(peapod.mNum > 0) {
					userCart.put("Muffins", userCart.get("Muffins")-1);
					peapod.mNum--;
					peapod.calculateAmount(peapod.mNum, 3.69, muffinAmount, cartTotal, '-');
				}
			}
		});
		muffinD.setPrefWidth(25);
		Button muffinI = new Button("+");
		muffinI.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				userCart.put("Muffins", userCart.get("Muffins")+1);
				peapod.mNum++;
				peapod.calculateAmount(peapod.mNum, 3.69, muffinAmount, cartTotal, '+');
			}
		});
		muffinI.setPrefWidth(25);
		
		Region mr1 = new Region();
		HBox.setHgrow(mr1, Priority.ALWAYS);
		Region mr2 = new Region();
		HBox.setHgrow(mr2, Priority.ALWAYS);
		
		muffinHB.getChildren().addAll(mr1, muffinD, muffinAmount, muffinI, mr2);
		
		muffinVB.getChildren().addAll(muffinV, muffinSP, muffinHB);
		
		return muffinVB;
	}
}
