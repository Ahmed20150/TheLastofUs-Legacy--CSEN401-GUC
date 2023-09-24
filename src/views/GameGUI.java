package views;

import java.awt.Point;
import java.io.File;





import java.util.ArrayList;

import model.characters.*;
import model.collectibles.Supply;
import model.world.Cell;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;
import engine.Game;
import exceptions.GameActionException;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


//FINAL
public class GameGUI extends Application{
	private Button startGame;
	private Button exit;
	private Button exit2;
	private Button exit3;
	private Button attack;
	private Button move;
	private Button useSpecial;
	private Button cure;
	private Button endTurn;
	
	
	private static boolean flag=false;
	
	private static Label activatedLabel=new Label();

	
	public static boolean moveAction= false;
	
	private static Label heroInfo2;
	
	private static int heronum=1;
	
	private static Button [][] gameMap= new Button[15][15];
	private static GridPane gridPane= new GridPane();
	
	private static Image [] pixelIcons={new Image("File:/Users/gchehata/Desktop/game media/pixelh1.png"), 
		  new Image("File:/Users/gchehata/Desktop/game media/pixelh2.png"),
		  new Image("File:/Users/gchehata/Desktop/game media/pixelh3.png"),
		  new Image("File:/Users/gchehata/Desktop/game media/pixelh4.png"),
		  new Image("File:/Users/gchehata/Desktop/game media/pixelh5.png"),
		  new Image("File:/Users/gchehata/Desktop/game media/pixelh6.png"),
		  new Image("File:/Users/gchehata/Desktop/game media/pixelh7.jpg"),
		  new Image("File:/Users/gchehata/Desktop/game media/pixelh8.png")};
	
	private static VBox heroList;
	
	private static int tempi=0;
	
	private Label l;
	private Label hint;

	private String type= "";
	
	private static int heroIndex=0;
	private static int heroIndex2=0;
	private static int clickCount=0;
	
	private static Direction d= null;
	
	private ArrayList<Button> mapCells =new ArrayList<Button>();
	
	private Scene s1;
	private Scene loadingScreen;
	private Scene s2;
	private Scene s3;
	private Scene winScreen;
	private Scene loseScreen;
	
	public void init() throws Exception{
		Game.loadHeroes("C:\\Users\\gchehata\\Desktop\\GUC\\Sem 4\\Game (CSEN401)\\FINAL WORKSPACE GAME\\Milestone2-Solution\\Heroes.csv");
	}
	
	
	public void start(Stage ps) throws Exception{
		ps.setTitle("The Last of Us: Legacy");
		ps.setFullScreenExitHint("");
		ps.setFullScreen(true);
		
		
		startGame= new Button("Start Game");
		exit= new Button("Exit");
		exit2= new Button("Close Game");
		exit3= new Button("Close Game");
		
		VBox v= new VBox(startGame,exit);
		BorderPane bp = new BorderPane(v,null,null,null,null);
		
		
		//BUTTON SOUND
		Media bs = new Media(new File("C:\\Users\\gchehata\\Desktop\\game media\\buttonSound.mp3").toURI().toString());
		MediaPlayer buttonSound = new MediaPlayer(bs);
		
		
		//Button Funcionality / Aesthetic 
		setStyling(startGame,60,true);
		setStyling(exit,60,true);
		
		exit.setOnAction(e->{
			buttonSound.play();
			ps.close();
		});
		
		exit2.setOnAction(e->{
			buttonSound.play();
			ps.close();
		});
		
		exit3.setOnAction(e->{
			buttonSound.play();
			ps.close();
		});
		
		
		//SCENE 1 (MAIN MENU)	
		Image logoimage= new Image("File:/Users/gchehata/Desktop/game media/transpLogo.png");
		ImageView logo= new ImageView(logoimage);
		logo.setFitWidth(300);
		logo.setFitHeight(425);
		VBox Logo= new VBox(logo);
		Logo.setAlignment(Pos.TOP_LEFT);
		Logo.setPadding(new Insets(30));
		Logo.setSpacing(15);
		bp.setPadding(new Insets(20));
//		bp.setRight(Logo);
		bp.setTop(Logo);
	
		
		Media music= new Media(new File("C:\\Users\\gchehata\\Desktop\\game media\\menu music.mp3").toURI().toString());
		MediaPlayer menuMusic = new MediaPlayer(music);
		menuMusic.setAutoPlay(true);
		menuMusic.setCycleCount(MediaPlayer.INDEFINITE);

		v.setPadding(new Insets(40));
		v.setAlignment(Pos.CENTER_LEFT);
		v.setSpacing(40);
		v.getChildren().add(new MediaView(menuMusic));
		s1= new Scene(bp,1000,1000);
		
		
		Image img= new Image("File:/Users/gchehata/Desktop/game media/menu8.gif");	

		BackgroundImage backgroundImage = new BackgroundImage(img,
		        BackgroundRepeat.NO_REPEAT,
		        BackgroundRepeat.NO_REPEAT,
		        BackgroundPosition.DEFAULT,
		        new BackgroundSize(1.0, 1.0, true, true, false, false));
		bp.setBackground(new Background(backgroundImage));
		
		
		
		//SCENE 2 (HERO SELECT)
		Button gameStart= new Button("Lets Fight!");
		setStyling(gameStart,50,true);
		
		Label selectHero= new Label("Choose Your Hero!");
		selectHero.setFont(Font.font("Impact", FontWeight.BOLD, FontPosture.ITALIC, 70));
		Button rightButton= new Button();
		Button leftButton= new Button();
		
		
		Image right= new Image("File:/Users/gchehata/Desktop/game media/rightarrow.png");
		ImageView r= new ImageView(right);
		rightButton.setStyle("-fx-background-color: transparent;");
		r.setFitWidth(200);
		r.setFitHeight(200);
		rightButton.setAlignment(Pos.CENTER);
		rightButton.setGraphic(r);

		
		Image left= new Image("File:/Users/gchehata/Desktop/game media/leftarrow.png");
		ImageView l= new ImageView(left);
		leftButton.setGraphic(l);
		leftButton.setAlignment(Pos.CENTER);
		leftButton.setStyle("-fx-background-color: transparent;");
		l.setFitWidth(200);
		l.setFitHeight(200);
		
		Image [] heroimgs= {new Image("File:/Users/gchehata/Desktop/game media/h1.jpg"),new Image("File:/Users/gchehata/Desktop/game media/h2.jpg"),
				new Image("File:/Users/gchehata/Desktop/game media/h3.jpg"),new Image("File:/Users/gchehata/Desktop/game media/h4.jpg"),
				new Image("File:/Users/gchehata/Desktop/game media/h5.jpg"),new Image("File:/Users/gchehata/Desktop/game media/h6.jpg"),
				new Image("File:/Users/gchehata/Desktop/game media/h7.jpg"),new Image("File:/Users/gchehata/Desktop/game media/h8.jpg")};
		
		
		
		Label heroInfo= new Label();
		heroInfo.setTextFill(Color.ANTIQUEWHITE);
		heroInfo.setFont(Font.font("Impact", FontWeight.BOLD, FontPosture.ITALIC, 40));
		
		ImageView heroView= new ImageView(heroimgs[0]);
		heroView.setFitHeight(400);
		heroView.setFitWidth(600);
		
		if(Game.availableHeroes.get(heroIndex) instanceof Fighter){
			type= "FIGH";
		}
		else if(Game.availableHeroes.get(heroIndex) instanceof Explorer){
			type= "EXP";
		}
		else if(Game.availableHeroes.get(heroIndex) instanceof Medic){
			type= "MED";
		}
		String Info= "Name: "+Game.availableHeroes.get(heroIndex).getName()+ "         "
				+"Type: "+type+ "\n"+
				"MaxHp:"+Game.availableHeroes.get(heroIndex).getMaxHp()+ "               "+ 
				"ActionsAvailable: "+ Game.availableHeroes.get(heroIndex).getActionsAvailable()+ "\n"+
				"Damage: "+ Game.availableHeroes.get(heroIndex).getAttackDmg();
		
		heroInfo.setText(Info);
		
		
		rightButton.setOnMouseClicked(e->{
			heroIndex+=1;
			if(heroIndex==8){
				heroIndex=0;
			}
			if(Game.availableHeroes.get(heroIndex) instanceof Fighter){
				type= "FIGH";
			}
			else if(Game.availableHeroes.get(heroIndex) instanceof Explorer){
				type= "EXP";
			}
			else if(Game.availableHeroes.get(heroIndex) instanceof Medic){
				type= "MED";
			}
			heroInfo.setText("Name: "+Game.availableHeroes.get(heroIndex).getName()+ "         "
					+"Type: "+type+ "\n"+
					"MaxHp:"+Game.availableHeroes.get(heroIndex).getMaxHp()+ "               "+ 
					"ActionsAvailable: "+ Game.availableHeroes.get(heroIndex).getActionsAvailable()+ "\n"+
					"Damage: "+ Game.availableHeroes.get(heroIndex).getAttackDmg());
			heroView.setImage(heroimgs[heroIndex]);
		});
		
		rightButton.setOnMouseEntered(e->{
			rightButton.setStyle("-fx-background-color: transparent;");
		});
		
		rightButton.setOnMouseExited(e->{
			rightButton.setStyle("-fx-background-color: transparent;");
		});
		
		leftButton.setOnMouseClicked(e->{
			heroIndex-=1;
			if(heroIndex==-1){
				heroIndex=7;
			}
			if(Game.availableHeroes.get(heroIndex) instanceof Fighter){
				type= "FIGH";
			}
			else if(Game.availableHeroes.get(heroIndex) instanceof Explorer){
				type= "EXP";
			}
			else if(Game.availableHeroes.get(heroIndex) instanceof Medic){
				type= "MED";
			}
			heroInfo.setText("Name: "+Game.availableHeroes.get(heroIndex).getName()+ "         "
					+"Type: "+type+ "\n"+
					"MaxHp:"+Game.availableHeroes.get(heroIndex).getMaxHp()+ "               "+ 
					"ActionsAvailable: "+ Game.availableHeroes.get(heroIndex).getActionsAvailable()+ "\n"+
					"Damage: "+ Game.availableHeroes.get(heroIndex).getAttackDmg());
			heroView.setImage(heroimgs[heroIndex]);
		});
		
		leftButton.setOnMouseEntered(e->{
			leftButton.setStyle("-fx-background-color: transparent;");
		});
		
		leftButton.setOnMouseExited(e->{
			leftButton.setStyle("-fx-background-color: transparent;");
		});
		
		
		
		Label heroInfo2= new Label("GAME");
		
		
		gameStart.setOnMouseClicked(event->{
			flag=true;
			System.out.println(Game.availableHeroes.get(heroIndex).getName());
			heroInfo2.setText("Name: "+Game.availableHeroes.get(heroIndex).getName()+ "         "
					+"Type: "+type+ "      "+
					"MaxHp:"+Game.availableHeroes.get(heroIndex).getMaxHp()+ "               "+ 
					"ActionsAvailable: "+ Game.availableHeroes.get(heroIndex).getActionsAvailable()+ "       "+
					"Damage: "+ Game.availableHeroes.get(heroIndex).getAttackDmg());
			heroInfo2.setFont(Font.font("Impact", FontWeight.BOLD, FontPosture.ITALIC, 20));
			initialHeroSetup();
			Game.startGame(Game.availableHeroes.get(heroIndex));
			initialMap(gameMap,gridPane);
			ps.setScene(s3);
			ps.setFullScreen(true);
		});
		
		
		HBox h= new HBox(leftButton,heroView,rightButton);
		VBox v2= new VBox(selectHero,h,heroInfo,gameStart);
		v2.setSpacing(20);
		v2.setAlignment(Pos.CENTER);
		h.setPadding(new Insets(30));
		h.setSpacing(50);
		h.setAlignment(Pos.CENTER);
		
		
		Image img2= new Image("File:/Users/gchehata/Desktop/game media/select1.jpg");	

		BackgroundImage backgroundImage2 = new BackgroundImage(img2,
		        BackgroundRepeat.NO_REPEAT,
		        BackgroundRepeat.NO_REPEAT,
		        BackgroundPosition.DEFAULT,
		        new BackgroundSize(1.0, 1.0, true, true, false, false));
		v2.setBackground(new Background(backgroundImage2));
		
		s2= new Scene(v2,1000,1000);
		
		
			//SCENE 3 (GAME MAP)
				
				Label activatedLabel= new Label();
				activatedLabel.setStyle("-fx-accent: red");
				activatedLabel.setFont(Font.font("Impact", FontWeight.BOLD, FontPosture.ITALIC, 30));
				
				attack= new Button("Attack");
				move= new Button("Move");
				useSpecial= new Button("Use Special");
				cure= new Button("Cure");
				endTurn= new Button("End Turn");
				
				setStyling(attack,30,false);
				setStyling(move,30,false);
				setStyling(useSpecial,30,false);
				setStyling(cure,30,false);
				setStyling(endTurn,30,false);
				
				
				HBox buttons= new HBox(attack,move,useSpecial,cure,endTurn);
				buttons.setAlignment(Pos.CENTER);
				buttons.setSpacing(30);
//				Game.availableHeroes.get(heroIndex);
				
				gridPane.setAlignment(Pos.CENTER);
				heroInfo2.setAlignment(Pos.TOP_CENTER);
				
				
				Button rightButton2= new Button();
				Button leftButton2= new Button();
				
				
				Image right2= new Image("File:/Users/gchehata/Desktop/game media/rightarrow.png");
				ImageView r2= new ImageView(right2);
				rightButton2.setStyle("-fx-background-color: transparent;");
				r2.setFitWidth(100);
				r2.setFitHeight(100);
				rightButton2.setAlignment(Pos.CENTER);
				rightButton2.setGraphic(r2);

				
				Image left2= new Image("File:/Users/gchehata/Desktop/game media/leftarrow.png");
				ImageView l2= new ImageView(left2);
				leftButton2.setGraphic(l2);
				leftButton2.setAlignment(Pos.CENTER);
				leftButton2.setStyle("-fx-background-color: transparent;");
				l2.setFitWidth(100);
				l2.setFitHeight(100);
				
				
				rightButton2.setOnMouseClicked(e->{
					if(heronum>1){
						heroIndex2++;
						if(heroIndex2==heronum){
							heroIndex2=0;
						}
						if(Game.heroes.get(heroIndex2) instanceof Fighter){
							type= "FIGH";
						}
						else if(Game.heroes.get(heroIndex2) instanceof Explorer){
							type= "EXP";
						}
						else if(Game.heroes.get(heroIndex2) instanceof Medic){
							type= "MED";
						}
						heroInfo2.setText("Name: "+Game.heroes.get(heroIndex2).getName()+ "         "
								+"Type: "+type+ "\n"+
								"MaxHp:"+Game.heroes.get(heroIndex2).getMaxHp()+ "               "+ 
								"ActionsAvailable: "+ Game.heroes.get(heroIndex2).getActionsAvailable()+ "\n"+
								"Current HP:"+ Game.heroes.get(heroIndex2).getCurrentHp()+"      "+
								"Vaccine Inventory:"+ Game.heroes.get(heroIndex2).getVaccineInventory().size()+ "       " + 
								"Supply Inventory:"+ Game.heroes.get(heroIndex2).getSupplyInventory().size()+ "       " + 
								"Damage: "+ Game.heroes.get(heroIndex2).getAttackDmg());
					}
					else{
						activatedLabel.setText("You only have One hero on the Map!");
					}
				});
				
				leftButton2.setOnMouseClicked(e->{
					if(heronum>1){
						heroIndex2--;
						if(heroIndex2==-1){
							heroIndex2= (heronum-1);
						}
						if(Game.heroes.get(heroIndex2) instanceof Fighter){
							type= "FIGH";
						}
						else if(Game.heroes.get(heroIndex2) instanceof Explorer){
							type= "EXP";
						}
						else if(Game.heroes.get(heroIndex2) instanceof Medic){
							type= "MED";
						}
						heroInfo2.setText("Name: "+Game.heroes.get(heroIndex2).getName()+ "         "
								+"Type: "+type+ "\n"+
								"MaxHp:"+Game.heroes.get(heroIndex2).getMaxHp()+ "               "+ 
								"ActionsAvailable: "+ Game.heroes.get(heroIndex2).getActionsAvailable()+ "\n"+
								"Current HP:"+ Game.heroes.get(heroIndex2).getCurrentHp()+"      "+
								"Vaccine Inventory:"+ Game.heroes.get(heroIndex2).getVaccineInventory().size()+ "       " + 
								"Supply Inventory:"+ Game.heroes.get(heroIndex2).getSupplyInventory().size()+ "       " + 
								"Damage: "+ Game.heroes.get(heroIndex2).getAttackDmg());
					}
					else{
						activatedLabel.setText("You only have One hero on the Map!");
					}
				});
				
				HBox hs3= new HBox(leftButton2,heroInfo2,rightButton2);
				hs3.setAlignment(Pos.CENTER);
				VBox vs3= new VBox(hs3,activatedLabel,gridPane,buttons);
				vs3.setSpacing(10);
				vs3.setAlignment(Pos.CENTER);
				s3= new Scene(vs3,1000,1000);

		//EndTurn functionality 
			endTurn.setOnMouseClicked(e->{
				try {
					Game.endTurn();
					if(Game.checkWin()){
						ps.setScene(winScreen);
						ps.setFullScreen(true);
					}
					else if(Game.checkGameOver()){
						ps.setScene(loseScreen);
						ps.setFullScreen(true);
					}
					updateMap();
					heroInfo2.setText("Name: "+Game.heroes.get(heroIndex2).getName()+ "         "
							+"Type: "+type+ "\n"+
							"MaxHp:"+Game.heroes.get(heroIndex2).getMaxHp()+ "               "+ 
							"ActionsAvailable: "+ Game.heroes.get(heroIndex2).getActionsAvailable()+ "\n"+
							"Current HP:"+ Game.heroes.get(heroIndex2).getCurrentHp()+"      "+
							"Vaccine Inventory:"+ Game.heroes.get(heroIndex2).getVaccineInventory().size()+ "       " + 
							"Supply Inventory:"+ Game.heroes.get(heroIndex2).getSupplyInventory().size()+ "       " + 
							"Damage: "+ Game.heroes.get(heroIndex2).getAttackDmg());
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				activatedLabel.setText("Turn has Ended");
				
			});
				
		//Move Functionality
			move.setOnAction(e->{
//				if(!moveAction){
				moveAction=true;
				activatedLabel.setText("Move Activated");
				s3.setOnKeyPressed(e1->{
					Direction d=null;
					e1.consume();
					if(e1.getCode()== KeyCode.W){
						d= Direction.UP;
					}
					if(e1.getCode()== KeyCode.S){
					d=Direction.DOWN;
					}
					if(e1.getCode()== KeyCode.A){
						 d=Direction.LEFT;
					}
					if(e1.getCode()== KeyCode.D){
						 d=Direction.RIGHT;
					}
					if(d!=null){
					try{
						int i= Game.heroes.get(heroIndex2).getLocation().x;
						int j= Game.heroes.get(heroIndex2).getLocation().y;
						if(d==Direction.UP) {
							i++;
						}
						else if(d==Direction.DOWN) {
							i--;
						}
						else if(d==Direction.LEFT) {
							j--;
						}
						else {
							j++;
						}
						if(Game.map[i][j] instanceof TrapCell) {
							activatedLabel.setText("You stepped in a trapped Cell!");
						}
						Game.heroes.get(heroIndex2).move(d);
						if(Game.checkWin()) {
							ps.setScene(winScreen);
							ps.setFullScreen(true);
						}
						else if(Game.checkGameOver()) {
							ps.setScene(loseScreen);
							ps.setFullScreen(true);
						}
						int x= Game.heroes.get(heroIndex2).getLocation().x;
						int y= Game.heroes.get(heroIndex2).getLocation().y;
						if(Game.map[x][y] instanceof TrapCell){
							activatedLabel.setText("You stepped in a trap Cell!");
						}
						updateMap();
						heroInfo2.setText("Name: "+Game.heroes.get(heroIndex2).getName()+ "         "
								+"Type: "+type+ "\n"+
								"MaxHp:"+Game.heroes.get(heroIndex2).getMaxHp()+ "               "+ 
								"ActionsAvailable: "+ Game.heroes.get(heroIndex2).getActionsAvailable()+ "\n"+
								"Current HP:"+ Game.heroes.get(heroIndex2).getCurrentHp()+"      "+
								"Vaccine Inventory:"+ Game.heroes.get(heroIndex2).getVaccineInventory().size()+ "       " + 
								"Supply Inventory:"+ Game.heroes.get(heroIndex2).getSupplyInventory().size()+ "       " + 
								"Damage: "+ Game.heroes.get(heroIndex2).getAttackDmg());
					}
					catch(GameActionException e2){
						activatedLabel.setText("WARNING:"+ e2.getMessage());
					}
				}
					 
				});
//				}
//				else{
//					moveAction=false;
////					move.setDisable(true);				
//					}
			});
			
			
			//Attack Functionality
			attack.setOnAction(e->{
				activatedLabel.setText("Attack Activated");
				s3.setOnKeyPressed(e1->{
					int x=0;
					int y=0;
					int mapIndex;
					e1.consume();
					if(e1.getCode()== KeyCode.W){
						try{
							Point p= Game.heroes.get(heroIndex2).getLocation();
							x=p.x;
							y=p.y;
							x++;
							Game.heroes.get(heroIndex2).setTarget(((CharacterCell) Game.map[x][y]).getCharacter());
							activatedLabel.setText("You attacked the Zombie!");
							Game.heroes.get(heroIndex2).attack();
							if(Game.checkWin()) {
								ps.setScene(winScreen);
								ps.setFullScreen(true);
							}
							else if(Game.checkGameOver()) {
								ps.setScene(loseScreen);
								ps.setFullScreen(true);
							}
							updateMap();
							heroInfo2.setText("Name: "+Game.heroes.get(heroIndex2).getName()+ "         "
									+"Type: "+type+ "\n"+
									"MaxHp:"+Game.heroes.get(heroIndex2).getMaxHp()+ "               "+ 
									"ActionsAvailable: "+ Game.heroes.get(heroIndex2).getActionsAvailable()+ "\n"+
									"Current HP:"+ Game.heroes.get(heroIndex2).getCurrentHp()+"      "+
									"Vaccine Inventory:"+ Game.heroes.get(heroIndex2).getVaccineInventory().size()+ "       " + 
									"Supply Inventory:"+ Game.heroes.get(heroIndex2).getSupplyInventory().size()+ "       " + 
									"Damage: "+ Game.heroes.get(heroIndex2).getAttackDmg());
						}
						catch(GameActionException e2){
							activatedLabel.setText("WARNING:" + e2.getMessage());
						}
					}
					if(e1.getCode()== KeyCode.S){
						try{
							Point p= Game.heroes.get(heroIndex2).getLocation();
							x=p.x;
							y=p.y;
							x--;
							Game.heroes.get(heroIndex2).setTarget(((CharacterCell) Game.map[x][y]).getCharacter());
							activatedLabel.setText("You attacked the Zombie!");
							Game.heroes.get(heroIndex2).attack();
							if(Game.checkWin()) {
								ps.setScene(winScreen);
								ps.setFullScreen(true);
							}
							else if(Game.checkGameOver()) {
								ps.setScene(loseScreen);
								ps.setFullScreen(true);
							}
							updateMap();
							heroInfo2.setText("Name: "+Game.heroes.get(heroIndex2).getName()+ "         "
									+"Type: "+type+ "\n"+
									"MaxHp:"+Game.heroes.get(heroIndex2).getMaxHp()+ "               "+ 
									"ActionsAvailable: "+ Game.heroes.get(heroIndex2).getActionsAvailable()+ "\n"+
									"Current HP:"+ Game.heroes.get(heroIndex2).getCurrentHp()+"      "+
									"Vaccine Inventory:"+ Game.heroes.get(heroIndex2).getVaccineInventory().size()+ "       " + 
									"Supply Inventory:"+ Game.heroes.get(heroIndex2).getSupplyInventory().size()+ "       " + 
									"Damage: "+ Game.heroes.get(heroIndex2).getAttackDmg());
						}
						catch(GameActionException e3){
							activatedLabel.setText("WARNING:" + e3.getMessage());
						}
					}
					 if(e1.getCode()== KeyCode.A){
						try{
							Point p= Game.heroes.get(heroIndex2).getLocation();
							x=p.x;
							y=p.y;
							y--;
							Game.heroes.get(heroIndex2).setTarget(((CharacterCell) Game.map[x][y]).getCharacter());
							activatedLabel.setText("You attacked the Zombie!");
							Game.heroes.get(heroIndex2).attack();
							if(Game.checkWin()) {
								ps.setScene(winScreen);
								ps.setFullScreen(true);
							}
							else if(Game.checkGameOver()) {
								ps.setScene(loseScreen);
								ps.setFullScreen(true);
							}
							updateMap();
							heroInfo2.setText("Name: "+Game.heroes.get(heroIndex2).getName()+ "         "
									+"Type: "+type+ "\n"+
									"MaxHp:"+Game.heroes.get(heroIndex2).getMaxHp()+ "               "+ 
									"ActionsAvailable: "+ Game.heroes.get(heroIndex2).getActionsAvailable()+ "\n"+
									"Current HP:"+ Game.heroes.get(heroIndex2).getCurrentHp()+"      "+
									"Vaccine Inventory:"+ Game.heroes.get(heroIndex2).getVaccineInventory().size()+ "       " + 
									"Supply Inventory:"+ Game.heroes.get(heroIndex2).getSupplyInventory().size()+ "       " + 
									"Damage: "+ Game.heroes.get(heroIndex2).getAttackDmg());
						}
						catch(GameActionException e4){
							activatedLabel.setText("WARNING:" + e4.getMessage());
						}
					}
					 if(e1.getCode()== KeyCode.D){
						try{
							Point p= Game.heroes.get(heroIndex2).getLocation();
							x=p.x;
							y=p.y;
							y++;
							Game.heroes.get(heroIndex2).setTarget(((CharacterCell) Game.map[x][y]).getCharacter());
							activatedLabel.setText("You attacked the Zombie!");
							Game.heroes.get(heroIndex2).attack();
							if(Game.checkWin()) {
								ps.setScene(winScreen);
								ps.setFullScreen(true);
							}
							else if(Game.checkGameOver()) {
								ps.setScene(loseScreen);
								ps.setFullScreen(true);
							}
							updateMap();
							heroInfo2.setText("Name: "+Game.heroes.get(heroIndex2).getName()+ "         "
									+"Type: "+type+ "\n"+
									"MaxHp:"+Game.heroes.get(heroIndex2).getMaxHp()+ "               "+ 
									"ActionsAvailable: "+ Game.heroes.get(heroIndex2).getActionsAvailable()+ "\n"+
									"Current HP:"+ Game.heroes.get(heroIndex2).getCurrentHp()+"      "+
									"Vaccine Inventory:"+ Game.heroes.get(heroIndex2).getVaccineInventory().size()+ "       " + 
									"Supply Inventory:"+ Game.heroes.get(heroIndex2).getSupplyInventory().size()+ "       " + 
									"Damage: "+ Game.heroes.get(heroIndex2).getAttackDmg());
						}
						catch(GameActionException e5){
							activatedLabel.setText("WARNING:" + e5.getMessage());
						}
					}
					 if(e1.getCode()== KeyCode.Q){
							try{
								Point p= Game.heroes.get(heroIndex2).getLocation();
								x=p.x;
								y=p.y;
								x++;
								y--;
								Game.heroes.get(heroIndex2).setTarget(((CharacterCell) Game.map[x][y]).getCharacter());
								activatedLabel.setText("You attacked the Zombie!");
								Game.heroes.get(heroIndex2).attack();
								if(Game.checkWin()) {
									ps.setScene(winScreen);
									ps.setFullScreen(true);
								}
								else if(Game.checkGameOver()) {
									ps.setScene(loseScreen);
									ps.setFullScreen(true);
								}
								updateMap();
								heroInfo2.setText("Name: "+Game.heroes.get(heroIndex2).getName()+ "         "
										+"Type: "+type+ "\n"+
										"MaxHp:"+Game.heroes.get(heroIndex2).getMaxHp()+ "               "+ 
										"ActionsAvailable: "+ Game.heroes.get(heroIndex2).getActionsAvailable()+ "\n"+
										"Current HP:"+ Game.heroes.get(heroIndex2).getCurrentHp()+"      "+
										"Vaccine Inventory:"+ Game.heroes.get(heroIndex2).getVaccineInventory().size()+ "       " + 
										"Supply Inventory:"+ Game.heroes.get(heroIndex2).getSupplyInventory().size()+ "       " + 
										"Damage: "+ Game.heroes.get(heroIndex2).getAttackDmg());
							}
							catch(GameActionException e5){
								activatedLabel.setText("WARNING:" + e5.getMessage());
							}
						}
					 if(e1.getCode()== KeyCode.E){
							try{
								Point p= Game.heroes.get(heroIndex2).getLocation();
								x=p.x;
								y=p.y;
								y++;
								x++;
								Game.heroes.get(heroIndex2).setTarget(((CharacterCell) Game.map[x][y]).getCharacter());
								activatedLabel.setText("You attacked the Zombie!");
								Game.heroes.get(heroIndex2).attack();
								if(Game.checkWin()) {
									ps.setScene(winScreen);
									ps.setFullScreen(true);
								}
								else if(Game.checkGameOver()) {
									ps.setScene(loseScreen);
									ps.setFullScreen(true);
								}
								updateMap();
								heroInfo2.setText("Name: "+Game.heroes.get(heroIndex2).getName()+ "         "
										+"Type: "+type+ "\n"+
										"MaxHp:"+Game.heroes.get(heroIndex2).getMaxHp()+ "               "+ 
										"ActionsAvailable: "+ Game.heroes.get(heroIndex2).getActionsAvailable()+ "\n"+
										"Current HP:"+ Game.heroes.get(heroIndex2).getCurrentHp()+"      "+
										"Vaccine Inventory:"+ Game.heroes.get(heroIndex2).getVaccineInventory().size()+ "       " + 
										"Supply Inventory:"+ Game.heroes.get(heroIndex2).getSupplyInventory().size()+ "       " + 
										"Damage: "+ Game.heroes.get(heroIndex2).getAttackDmg());
							}
							catch(GameActionException e5){
								activatedLabel.setText("WARNING:" + e5.getMessage());
							}
						}
					 if(e1.getCode()== KeyCode.Z){
							try{
								Point p= Game.heroes.get(heroIndex2).getLocation();
								x=p.x;
								y=p.y;
								y--;
								x--;
								Game.heroes.get(heroIndex2).setTarget(((CharacterCell) Game.map[x][y]).getCharacter());
								activatedLabel.setText("You attacked the Zombie!");
								Game.heroes.get(heroIndex2).attack();
								if(Game.checkWin()) {
									ps.setScene(winScreen);
									ps.setFullScreen(true);
								}
								else if(Game.checkGameOver()) {
									ps.setScene(loseScreen);
									ps.setFullScreen(true);
								}
								updateMap();
								heroInfo2.setText("Name: "+Game.heroes.get(heroIndex2).getName()+ "         "
										+"Type: "+type+ "\n"+
										"MaxHp:"+Game.heroes.get(heroIndex2).getMaxHp()+ "               "+ 
										"ActionsAvailable: "+ Game.heroes.get(heroIndex2).getActionsAvailable()+ "\n"+
										"Current HP:"+ Game.heroes.get(heroIndex2).getCurrentHp()+"      "+
										"Vaccine Inventory:"+ Game.heroes.get(heroIndex2).getVaccineInventory().size()+ "       " + 
										"Supply Inventory:"+ Game.heroes.get(heroIndex2).getSupplyInventory().size()+ "       " + 
										"Damage: "+ Game.heroes.get(heroIndex2).getAttackDmg());
							}
							catch(GameActionException e5){
								activatedLabel.setText("WARNING:" + e5.getMessage());
							}
						}
					 if(e1.getCode()== KeyCode.C){
							try{
								Point p= Game.heroes.get(heroIndex2).getLocation();
								x=p.x;
								y=p.y;
								x--;
								y++;
								Game.heroes.get(heroIndex2).setTarget(((CharacterCell) Game.map[x][y]).getCharacter());
								activatedLabel.setText("You attacked the Zombie!");
								Game.heroes.get(heroIndex2).attack();
								if(Game.checkWin()) {
									ps.setScene(winScreen);
									ps.setFullScreen(true);
								}
								else if(Game.checkGameOver()) {
									ps.setScene(loseScreen);
									ps.setFullScreen(true);
								}
								updateMap();
								heroInfo2.setText("Name: "+Game.heroes.get(heroIndex2).getName()+ "         "
										+"Type: "+type+ "\n"+
										"MaxHp:"+Game.heroes.get(heroIndex2).getMaxHp()+ "               "+ 
										"ActionsAvailable: "+ Game.heroes.get(heroIndex2).getActionsAvailable()+ "\n"+
										"Current HP:"+ Game.heroes.get(heroIndex2).getCurrentHp()+"      "+
										"Vaccine Inventory:"+ Game.heroes.get(heroIndex2).getVaccineInventory().size()+ "       " + 
										"Supply Inventory:"+ Game.heroes.get(heroIndex2).getSupplyInventory().size()+ "       " + 
										"Damage: "+ Game.heroes.get(heroIndex2).getAttackDmg());
							}
							catch(GameActionException e5){
								activatedLabel.setText("WARNING:" + e5.getMessage());
							}
						}
				});
			});
			
			//UseSpecial functionality
			useSpecial.setOnMouseClicked(e->{
				activatedLabel.setText("Special ability Activated");
				if(Game.heroes.get(heroIndex2) instanceof Fighter){
					try{
						Game.heroes.get(heroIndex2).useSpecial();
						if(Game.checkWin()) {
							ps.setScene(winScreen);
							ps.setFullScreen(true);
						}
						else if(Game.checkGameOver()) {
							ps.setScene(loseScreen);
							ps.setFullScreen(true);
						}
						heroInfo2.setText("Name: "+Game.heroes.get(heroIndex2).getName()+ "         "
								+"Type: "+type+ "\n"+
								"MaxHp:"+Game.heroes.get(heroIndex2).getMaxHp()+ "               "+ 
								"ActionsAvailable: "+ Game.heroes.get(heroIndex2).getActionsAvailable()+ "\n"+
								"Current HP:"+ Game.heroes.get(heroIndex2).getCurrentHp()+"      "+
								"Vaccine Inventory:"+ Game.heroes.get(heroIndex2).getVaccineInventory().size()+ "       " + 
								"Supply Inventory:"+ Game.heroes.get(heroIndex2).getSupplyInventory().size()+ "       " + 
								"Damage: "+ Game.heroes.get(heroIndex2).getAttackDmg());
					}
					catch(GameActionException e1){
						activatedLabel.setText("WARNING:" + e1.getMessage());
					}
				}
				else if(Game.heroes.get(heroIndex2) instanceof Explorer){
					try{
						Game.heroes.get(heroIndex2).useSpecial();
						if(Game.checkWin()) {
							ps.setScene(winScreen);
							ps.setFullScreen(true);
						}
						else if(Game.checkGameOver()) {
							ps.setScene(loseScreen);
							ps.setFullScreen(true);
						}
						updateMap();
						heroInfo2.setText("Name: "+Game.heroes.get(heroIndex2).getName()+ "         "
								+"Type: "+type+ "\n"+
								"MaxHp:"+Game.heroes.get(heroIndex2).getMaxHp()+ "               "+ 
								"ActionsAvailable: "+ Game.heroes.get(heroIndex2).getActionsAvailable()+ "\n"+
								"Current HP:"+ Game.heroes.get(heroIndex2).getCurrentHp()+"      "+
								"Vaccine Inventory:"+ Game.heroes.get(heroIndex2).getVaccineInventory().size()+ "       " + 
								"Supply Inventory:"+ Game.heroes.get(heroIndex2).getSupplyInventory().size()+ "       " + 
								"Damage: "+ Game.heroes.get(heroIndex2).getAttackDmg());
					}
					catch(GameActionException e1){
						activatedLabel.setText("WARNING:" + e1.getMessage());
					}
				}
				else{
					s3.setOnKeyPressed(e1->{
						int x=0;
						int y=0;
						int mapIndex;
						e1.consume();
						if(e1.getCode()== KeyCode.W){
							try{
								Point p= Game.heroes.get(heroIndex2).getLocation();
								x=p.x;
								y=p.y;
								x++;
								Game.heroes.get(heroIndex2).setTarget(((CharacterCell) Game.map[x][y]).getCharacter());
								activatedLabel.setText("You Healed your fellow hero!");
								Game.heroes.get(heroIndex2).useSpecial();
								if(Game.checkWin()) {
									ps.setScene(winScreen);
									ps.setFullScreen(true);
								}
								else if(Game.checkGameOver()) {
									ps.setScene(loseScreen);
									ps.setFullScreen(true);
								}
								heroInfo2.setText("Name: "+Game.heroes.get(heroIndex2).getName()+ "         "
										+"Type: "+type+ "\n"+
										"MaxHp:"+Game.heroes.get(heroIndex2).getMaxHp()+ "               "+ 
										"ActionsAvailable: "+ Game.heroes.get(heroIndex2).getActionsAvailable()+ "\n"+
										"Current HP:"+ Game.heroes.get(heroIndex2).getCurrentHp()+"      "+
										"Vaccine Inventory:"+ Game.heroes.get(heroIndex2).getVaccineInventory().size()+ "       " + 
										"Supply Inventory:"+ Game.heroes.get(heroIndex2).getSupplyInventory().size()+ "       " + 
										"Damage: "+ Game.heroes.get(heroIndex2).getAttackDmg());
							}
							catch(GameActionException e2){
								activatedLabel.setText("WARNING:" + e2.getMessage());
							}
						}
						if(e1.getCode()== KeyCode.S){
							try{
								Point p= Game.heroes.get(heroIndex2).getLocation();
								x=p.x;
								y=p.y;
								x--;
								Game.heroes.get(heroIndex2).setTarget(((CharacterCell) Game.map[x][y]).getCharacter());
								activatedLabel.setText("You Healed your fellow hero!");
								Game.heroes.get(heroIndex2).useSpecial();
								if(Game.checkWin()) {
									ps.setScene(winScreen);
									ps.setFullScreen(true);
								}
								else if(Game.checkGameOver()) {
									ps.setScene(loseScreen);
									ps.setFullScreen(true);
								}
								heroInfo2.setText("Name: "+Game.heroes.get(heroIndex2).getName()+ "         "
										+"Type: "+type+ "\n"+
										"MaxHp:"+Game.heroes.get(heroIndex2).getMaxHp()+ "               "+ 
										"ActionsAvailable: "+ Game.heroes.get(heroIndex2).getActionsAvailable()+ "\n"+
										"Current HP:"+ Game.heroes.get(heroIndex2).getCurrentHp()+"      "+
										"Vaccine Inventory:"+ Game.heroes.get(heroIndex2).getVaccineInventory().size()+ "       " + 
										"Supply Inventory:"+ Game.heroes.get(heroIndex2).getSupplyInventory().size()+ "       " + 
										"Damage: "+ Game.heroes.get(heroIndex2).getAttackDmg());
							}
							catch(GameActionException e3){
								activatedLabel.setText("WARNING:" + e3.getMessage());
							}
						}
						 if(e1.getCode()== KeyCode.A){
							try{
								Point p= Game.heroes.get(heroIndex2).getLocation();
								x=p.x;
								y=p.y;
								y--;
								Game.heroes.get(heroIndex2).setTarget(((CharacterCell) Game.map[x][y]).getCharacter());
								activatedLabel.setText("You Healed your fellow hero!");
								Game.heroes.get(heroIndex2).useSpecial();
								if(Game.checkWin()) {
									ps.setScene(winScreen);
									ps.setFullScreen(true);
								}
								else if(Game.checkGameOver()) {
									ps.setScene(loseScreen);
									ps.setFullScreen(true);
								}
								heroInfo2.setText("Name: "+Game.heroes.get(heroIndex2).getName()+ "         "
										+"Type: "+type+ "\n"+
										"MaxHp:"+Game.heroes.get(heroIndex2).getMaxHp()+ "               "+ 
										"ActionsAvailable: "+ Game.heroes.get(heroIndex2).getActionsAvailable()+ "\n"+
										"Current HP:"+ Game.heroes.get(heroIndex2).getCurrentHp()+"      "+
										"Vaccine Inventory:"+ Game.heroes.get(heroIndex2).getVaccineInventory().size()+ "       " + 
										"Supply Inventory:"+ Game.heroes.get(heroIndex2).getSupplyInventory().size()+ "       " + 
										"Damage: "+ Game.heroes.get(heroIndex2).getAttackDmg());
							}
							catch(GameActionException e4){
								activatedLabel.setText("WARNING:" + e4.getMessage());
							}
						}
						 if(e1.getCode()== KeyCode.D){
							try{
								Point p= Game.heroes.get(heroIndex2).getLocation();
								x=p.x;
								y=p.y;
								y++;
								Game.heroes.get(heroIndex2).setTarget(((CharacterCell) Game.map[x][y]).getCharacter());
								activatedLabel.setText("You Healed your fellow hero!");
								Game.heroes.get(heroIndex2).useSpecial();
								if(Game.checkWin()) {
									ps.setScene(winScreen);
									ps.setFullScreen(true);
								}
								else if(Game.checkGameOver()) {
									ps.setScene(loseScreen);
									ps.setFullScreen(true);
								}
								heroInfo2.setText("Name: "+Game.heroes.get(heroIndex2).getName()+ "         "
										+"Type: "+type+ "\n"+
										"MaxHp:"+Game.heroes.get(heroIndex2).getMaxHp()+ "               "+ 
										"ActionsAvailable: "+ Game.heroes.get(heroIndex2).getActionsAvailable()+ "\n"+
										"Current HP:"+ Game.heroes.get(heroIndex2).getCurrentHp()+"      "+
										"Vaccine Inventory:"+ Game.heroes.get(heroIndex2).getVaccineInventory().size()+ "       " + 
										"Supply Inventory:"+ Game.heroes.get(heroIndex2).getSupplyInventory().size()+ "       " + 
										"Damage: "+ Game.heroes.get(heroIndex2).getAttackDmg());
							}
							catch(GameActionException e5){
								activatedLabel.setText("WARNING:" + e5.getMessage());
							}
						}
						 if(e1.getCode()== KeyCode.Q){
								try{
									Point p= Game.heroes.get(heroIndex2).getLocation();
									x=p.x;
									y=p.y;
									x++;
									y--;
									Game.heroes.get(heroIndex2).setTarget(((CharacterCell) Game.map[x][y]).getCharacter());
									activatedLabel.setText("You Healed your fellow hero!");
									Game.heroes.get(heroIndex2).useSpecial();
									if(Game.checkWin()) {
										ps.setScene(winScreen);
										ps.setFullScreen(true);
									}
									else if(Game.checkGameOver()) {
										ps.setScene(loseScreen);
										ps.setFullScreen(true);
									}
									heroInfo2.setText("Name: "+Game.heroes.get(heroIndex2).getName()+ "         "
											+"Type: "+type+ "\n"+
											"MaxHp:"+Game.heroes.get(heroIndex2).getMaxHp()+ "               "+ 
											"ActionsAvailable: "+ Game.heroes.get(heroIndex2).getActionsAvailable()+ "\n"+
											"Current HP:"+ Game.heroes.get(heroIndex2).getCurrentHp()+"      "+
											"Vaccine Inventory:"+ Game.heroes.get(heroIndex2).getVaccineInventory().size()+ "       " + 
											"Supply Inventory:"+ Game.heroes.get(heroIndex2).getSupplyInventory().size()+ "       " + 
											"Damage: "+ Game.heroes.get(heroIndex2).getAttackDmg());
								}
								catch(GameActionException e5){
									activatedLabel.setText("WARNING:" + e5.getMessage());
								}
							}
						 if(e1.getCode()== KeyCode.E){
								try{
									Point p= Game.heroes.get(heroIndex2).getLocation();
									x=p.x;
									y=p.y;
									y++;
									x++;
									Game.heroes.get(heroIndex2).setTarget(((CharacterCell) Game.map[x][y]).getCharacter());
									activatedLabel.setText("You Healed your fellow hero!");
									Game.heroes.get(heroIndex2).useSpecial();
									if(Game.checkWin()) {
										ps.setScene(winScreen);
										ps.setFullScreen(true);
									}
									else if(Game.checkGameOver()) {
										ps.setScene(loseScreen);
										ps.setFullScreen(true);
									}
									heroInfo2.setText("Name: "+Game.heroes.get(heroIndex2).getName()+ "         "
											+"Type: "+type+ "\n"+
											"MaxHp:"+Game.heroes.get(heroIndex2).getMaxHp()+ "               "+ 
											"ActionsAvailable: "+ Game.heroes.get(heroIndex2).getActionsAvailable()+ "\n"+
											"Current HP:"+ Game.heroes.get(heroIndex2).getCurrentHp()+"      "+
											"Vaccine Inventory:"+ Game.heroes.get(heroIndex2).getVaccineInventory().size()+ "       " + 
											"Supply Inventory:"+ Game.heroes.get(heroIndex2).getSupplyInventory().size()+ "       " + 
											"Damage: "+ Game.heroes.get(heroIndex2).getAttackDmg());
								}
								catch(GameActionException e5){
									activatedLabel.setText("WARNING:" + e5.getMessage());
								}
							}
						 if(e1.getCode()== KeyCode.Z){
								try{
									Point p= Game.heroes.get(heroIndex2).getLocation();
									x=p.x;
									y=p.y;
									y--;
									x--;
									Game.heroes.get(heroIndex2).setTarget(((CharacterCell) Game.map[x][y]).getCharacter());
									activatedLabel.setText("You Healed your fellow hero!");
									Game.heroes.get(heroIndex2).useSpecial();
									if(Game.checkWin()) {
										ps.setScene(winScreen);
										ps.setFullScreen(true);
									}
									else if(Game.checkGameOver()) {
										ps.setScene(loseScreen);
										ps.setFullScreen(true);
									}
									heroInfo2.setText("Name: "+Game.heroes.get(heroIndex2).getName()+ "         "
											+"Type: "+type+ "\n"+
											"MaxHp:"+Game.heroes.get(heroIndex2).getMaxHp()+ "               "+ 
											"ActionsAvailable: "+ Game.heroes.get(heroIndex2).getActionsAvailable()+ "\n"+
											"Current HP:"+ Game.heroes.get(heroIndex2).getCurrentHp()+"      "+
											"Vaccine Inventory:"+ Game.heroes.get(heroIndex2).getVaccineInventory().size()+ "       " + 
											"Supply Inventory:"+ Game.heroes.get(heroIndex2).getSupplyInventory().size()+ "       " + 
											"Damage: "+ Game.heroes.get(heroIndex2).getAttackDmg());
								}
								catch(GameActionException e5){
									activatedLabel.setText("WARNING:" + e5.getMessage());
								}
							}
						 if(e1.getCode()== KeyCode.C){
								try{
									Point p= Game.heroes.get(heroIndex2).getLocation();
									x=p.x;
									y=p.y;
									x--;
									y++;
									Game.heroes.get(heroIndex2).setTarget(((CharacterCell) Game.map[x][y]).getCharacter());
									activatedLabel.setText("You Healed your fellow hero!");
									Game.heroes.get(heroIndex2).useSpecial();
									if(Game.checkWin()) {
										ps.setScene(winScreen);
										ps.setFullScreen(true);
									}
									else if(Game.checkGameOver()) {
										ps.setScene(loseScreen);
										ps.setFullScreen(true);
									}
									heroInfo2.setText("Name: "+Game.heroes.get(heroIndex2).getName()+ "         "
											+"Type: "+type+ "\n"+
											"MaxHp:"+Game.heroes.get(heroIndex2).getMaxHp()+ "               "+ 
											"ActionsAvailable: "+ Game.heroes.get(heroIndex2).getActionsAvailable()+ "\n"+
											"Current HP:"+ Game.heroes.get(heroIndex2).getCurrentHp()+"      "+
											"Vaccine Inventory:"+ Game.heroes.get(heroIndex2).getVaccineInventory().size()+ "       " + 
											"Supply Inventory:"+ Game.heroes.get(heroIndex2).getSupplyInventory().size()+ "       " + 
											"Damage: "+ Game.heroes.get(heroIndex2).getAttackDmg());
								}
								catch(GameActionException e5){
									activatedLabel.setText("WARNING:" + e5.getMessage());
								}
							}
						 if(e1.getCode()== KeyCode.U){
								try{
									Point p= Game.heroes.get(heroIndex2).getLocation();
									x=p.x;
									y=p.y;
									Game.heroes.get(heroIndex2).setTarget(((CharacterCell) Game.map[x][y]).getCharacter());
									activatedLabel.setText("You Healed your fellow hero!");
									Game.heroes.get(heroIndex2).useSpecial();
									if(Game.checkWin()) {
										ps.setScene(winScreen);
										ps.setFullScreen(true);
									}
									else if(Game.checkGameOver()) {
										ps.setScene(loseScreen);
										ps.setFullScreen(true);
									}
									heroInfo2.setText("Name: "+Game.heroes.get(heroIndex2).getName()+ "         "
											+"Type: "+type+ "\n"+
											"MaxHp:"+Game.heroes.get(heroIndex2).getMaxHp()+ "               "+ 
											"ActionsAvailable: "+ Game.heroes.get(heroIndex2).getActionsAvailable()+ "\n"+
											"Current HP:"+ Game.heroes.get(heroIndex2).getCurrentHp()+"      "+
											"Vaccine Inventory:"+ Game.heroes.get(heroIndex2).getVaccineInventory().size()+ "       " + 
											"Supply Inventory:"+ Game.heroes.get(heroIndex2).getSupplyInventory().size()+ "       " + 
											"Damage: "+ Game.heroes.get(heroIndex2).getAttackDmg());
								}
								catch(GameActionException e5){
									activatedLabel.setText("WARNING:" + e5.getMessage());
								}
							}
						 
					});
				}
			});
			
			//Cure Functionality
			cure.setOnAction(e->{
				activatedLabel.setText("Cure Activated");
				s3.setOnKeyPressed(e1->{
					int x=0;
					int y=0;
					int mapIndex;
					e1.consume();
					if(e1.getCode()== KeyCode.W){
						try{
							Point p= Game.heroes.get(heroIndex2).getLocation();
							x=p.x;
							y=p.y;
							x++;
							Game.heroes.get(heroIndex2).setTarget(((CharacterCell) Game.map[x][y]).getCharacter());
							activatedLabel.setText("You cured a Zombie!");
							Game.heroes.get(heroIndex2).cure();
							if(Game.checkWin()) {
								ps.setScene(winScreen);
								ps.setFullScreen(true);
							}
							else if(Game.checkGameOver()) {
								ps.setScene(loseScreen);
								ps.setFullScreen(true);
							}
							updateMap();
							heroInfo2.setText("Name: "+Game.heroes.get(heroIndex2).getName()+ "         "
									+"Type: "+type+ "\n"+
									"MaxHp:"+Game.heroes.get(heroIndex2).getMaxHp()+ "               "+ 
									"ActionsAvailable: "+ Game.heroes.get(heroIndex2).getActionsAvailable()+ "\n"+
									"Current HP:"+ Game.heroes.get(heroIndex2).getCurrentHp()+"      "+
									"Vaccine Inventory:"+ Game.heroes.get(heroIndex2).getVaccineInventory().size()+ "       " + 
									"Supply Inventory:"+ Game.heroes.get(heroIndex2).getSupplyInventory().size()+ "       " + 
									"Damage: "+ Game.heroes.get(heroIndex2).getAttackDmg());
							updateHeroes();
						}
						catch(GameActionException e2){
							activatedLabel.setText("WARNING:" + e2.getMessage());
						}
						
						
					}
					if(e1.getCode()== KeyCode.S){
						try{
							Point p= Game.heroes.get(heroIndex2).getLocation();
							x=p.x;
							y=p.y;
							x--;
							Game.heroes.get(heroIndex2).setTarget(((CharacterCell) Game.map[x][y]).getCharacter());
							activatedLabel.setText("You cured a Zombie!");
							Game.heroes.get(heroIndex2).cure();
							if(Game.checkWin()) {
								ps.setScene(winScreen);
								ps.setFullScreen(true);
							}
							else if(Game.checkGameOver()) {
								ps.setScene(loseScreen);
								ps.setFullScreen(true);
							}
							updateMap();
							heroInfo2.setText("Name: "+Game.heroes.get(heroIndex2).getName()+ "         "
									+"Type: "+type+ "\n"+
									"MaxHp:"+Game.heroes.get(heroIndex2).getMaxHp()+ "               "+ 
									"ActionsAvailable: "+ Game.heroes.get(heroIndex2).getActionsAvailable()+ "\n"+
									"Current HP:"+ Game.heroes.get(heroIndex2).getCurrentHp()+"      "+
									"Vaccine Inventory:"+ Game.heroes.get(heroIndex2).getVaccineInventory().size()+ "       " + 
									"Supply Inventory:"+ Game.heroes.get(heroIndex2).getSupplyInventory().size()+ "       " + 
									"Damage: "+ Game.heroes.get(heroIndex2).getAttackDmg());
							updateHeroes();
						}
						catch(GameActionException e3){
							activatedLabel.setText("WARNING:" + e3.getMessage());
						}
					}
					 if(e1.getCode()== KeyCode.A){
						try{
							Point p= Game.heroes.get(heroIndex2).getLocation();
							x=p.x;
							y=p.y;
							y--;
							Game.heroes.get(heroIndex2).setTarget(((CharacterCell) Game.map[x][y]).getCharacter());
							activatedLabel.setText("You cured a Zombie!");
							Game.heroes.get(heroIndex2).cure();
							if(Game.checkWin()) {
								ps.setScene(winScreen);
								ps.setFullScreen(true);
							}
							else if(Game.checkGameOver()) {
								ps.setScene(loseScreen);
								ps.setFullScreen(true);
							}
							updateMap();
							heroInfo2.setText("Name: "+Game.heroes.get(heroIndex2).getName()+ "         "
									+"Type: "+type+ "\n"+
									"MaxHp:"+Game.heroes.get(heroIndex2).getMaxHp()+ "               "+ 
									"ActionsAvailable: "+ Game.heroes.get(heroIndex2).getActionsAvailable()+ "\n"+
									"Current HP:"+ Game.heroes.get(heroIndex2).getCurrentHp()+"      "+
									"Vaccine Inventory:"+ Game.heroes.get(heroIndex2).getVaccineInventory().size()+ "       " + 
									"Supply Inventory:"+ Game.heroes.get(heroIndex2).getSupplyInventory().size()+ "       " + 
									"Damage: "+ Game.heroes.get(heroIndex2).getAttackDmg());
							updateHeroes();
						}
						catch(GameActionException e4){
							activatedLabel.setText("WARNING:" + e4.getMessage());
						}
					}
					 if(e1.getCode()== KeyCode.D){
						try{
							Point p= Game.heroes.get(heroIndex2).getLocation();
							x=p.x;
							y=p.y;
							y++;
							Game.heroes.get(heroIndex2).setTarget(((CharacterCell) Game.map[x][y]).getCharacter());
							activatedLabel.setText("You cured a Zombie!");
							Game.heroes.get(heroIndex2).cure();
							if(Game.checkWin()) {
								ps.setScene(winScreen);
								ps.setFullScreen(true);
							}
							else if(Game.checkGameOver()) {
								ps.setScene(loseScreen);
								ps.setFullScreen(true);
							}
							updateMap();
							heroInfo2.setText("Name: "+Game.heroes.get(heroIndex2).getName()+ "         "
									+"Type: "+type+ "\n"+
									"MaxHp:"+Game.heroes.get(heroIndex2).getMaxHp()+ "               "+ 
									"ActionsAvailable: "+ Game.heroes.get(heroIndex2).getActionsAvailable()+ "\n"+
									"Current HP:"+ Game.heroes.get(heroIndex2).getCurrentHp()+"      "+
									"Vaccine Inventory:"+ Game.heroes.get(heroIndex2).getVaccineInventory().size()+ "       " + 
									"Supply Inventory:"+ Game.heroes.get(heroIndex2).getSupplyInventory().size()+ "       " + 
									"Damage: "+ Game.heroes.get(heroIndex2).getAttackDmg());
							updateHeroes();
						}
						catch(GameActionException e5){
							activatedLabel.setText("WARNING:" + e5.getMessage());
						}
					}
					 if(e1.getCode()== KeyCode.Q){
							try{
								Point p= Game.heroes.get(0).getLocation();
								x=p.x;
								y=p.y;
								x++;
								y--;
								Game.heroes.get(heroIndex2).setTarget(((CharacterCell) Game.map[x][y]).getCharacter());
								activatedLabel.setText("You cured a Zombie!");
								Game.heroes.get(heroIndex2).cure();
								if(Game.checkWin()) {
									ps.setScene(winScreen);
									ps.setFullScreen(true);
								}
								else if(Game.checkGameOver()) {
									ps.setScene(loseScreen);
									ps.setFullScreen(true);
								}
								updateMap();
								heroInfo2.setText("Name: "+Game.heroes.get(heroIndex2).getName()+ "         "
										+"Type: "+type+ "\n"+
										"MaxHp:"+Game.heroes.get(heroIndex2).getMaxHp()+ "               "+ 
										"ActionsAvailable: "+ Game.heroes.get(heroIndex2).getActionsAvailable()+ "\n"+
										"Current HP:"+ Game.heroes.get(heroIndex2).getCurrentHp()+"      "+
										"Vaccine Inventory:"+ Game.heroes.get(heroIndex2).getVaccineInventory().size()+ "       " + 
										"Supply Inventory:"+ Game.heroes.get(heroIndex2).getSupplyInventory().size()+ "       " + 
										"Damage: "+ Game.heroes.get(heroIndex2).getAttackDmg());
								updateHeroes();
							}
							catch(GameActionException e5){
								activatedLabel.setText("WARNING:" + e5.getMessage());
							}
						}
					 if(e1.getCode()== KeyCode.E){
							try{
								Point p= Game.heroes.get(heroIndex2).getLocation();
								x=p.x;
								y=p.y;
								y++;
								x++;
								Game.heroes.get(heroIndex2).setTarget(((CharacterCell) Game.map[x][y]).getCharacter());
								activatedLabel.setText("You cured a Zombie!");
								Game.heroes.get(heroIndex2).cure();
								if(Game.checkWin()) {
									ps.setScene(winScreen);
									ps.setFullScreen(true);
								}
								else if(Game.checkGameOver()) {
									ps.setScene(loseScreen);
									ps.setFullScreen(true);
								}
								updateMap();
								heroInfo2.setText("Name: "+Game.heroes.get(heroIndex2).getName()+ "         "
										+"Type: "+type+ "\n"+
										"MaxHp:"+Game.heroes.get(heroIndex2).getMaxHp()+ "               "+ 
										"ActionsAvailable: "+ Game.heroes.get(heroIndex2).getActionsAvailable()+ "\n"+
										"Current HP:"+ Game.heroes.get(heroIndex2).getCurrentHp()+"      "+
										"Vaccine Inventory:"+ Game.heroes.get(heroIndex2).getVaccineInventory().size()+ "       " + 
										"Supply Inventory:"+ Game.heroes.get(heroIndex2).getSupplyInventory().size()+ "       " + 
										"Damage: "+ Game.heroes.get(heroIndex2).getAttackDmg());
								updateHeroes();
							}
							catch(GameActionException e5){
								activatedLabel.setText("WARNING:" + e5.getMessage());
							}
						}
					 if(e1.getCode()== KeyCode.Z){
							try{
								Point p= Game.heroes.get(heroIndex2).getLocation();
								x=p.x;
								y=p.y;
								y--;
								x--;
								Game.heroes.get(heroIndex2).setTarget(((CharacterCell) Game.map[x][y]).getCharacter());
								activatedLabel.setText("You cured a Zombie!");
								Game.heroes.get(heroIndex2).cure();
								if(Game.checkWin()) {
									ps.setScene(winScreen);
									ps.setFullScreen(true);
								}
								else if(Game.checkGameOver()) {
									ps.setScene(loseScreen);
									ps.setFullScreen(true);
								}
								updateMap();
								heroInfo2.setText("Name: "+Game.heroes.get(heroIndex2).getName()+ "         "
										+"Type: "+type+ "\n"+
										"MaxHp:"+Game.heroes.get(heroIndex2).getMaxHp()+ "               "+ 
										"ActionsAvailable: "+ Game.heroes.get(heroIndex2).getActionsAvailable()+ "\n"+
										"Current HP:"+ Game.heroes.get(heroIndex2).getCurrentHp()+"      "+
										"Vaccine Inventory:"+ Game.heroes.get(heroIndex2).getVaccineInventory().size()+ "       " + 
										"Supply Inventory:"+ Game.heroes.get(heroIndex2).getSupplyInventory().size()+ "       " + 
										"Damage: "+ Game.heroes.get(heroIndex2).getAttackDmg());
								updateHeroes();
							}
							catch(GameActionException e5){
								activatedLabel.setText("WARNING:" + e5.getMessage());
							}
						}
					 if(e1.getCode()== KeyCode.C){
							try{
								Point p= Game.heroes.get(heroIndex2).getLocation();
								x=p.x;
								y=p.y;
								x--;
								y++;
								Game.heroes.get(heroIndex2).setTarget(((CharacterCell) Game.map[x][y]).getCharacter());
								activatedLabel.setText("You cured a Zombie!");
								Game.heroes.get(heroIndex2).cure();
								if(Game.checkWin()) {
									ps.setScene(winScreen);
									ps.setFullScreen(true);
								}
								else if(Game.checkGameOver()) {
									ps.setScene(loseScreen);
									ps.setFullScreen(true);
								}
								updateMap();
								heroInfo2.setText("Name: "+Game.heroes.get(heroIndex2).getName()+ "         "
										+"Type: "+type+ "\n"+
										"MaxHp:"+Game.heroes.get(heroIndex2).getMaxHp()+ "               "+ 
										"ActionsAvailable: "+ Game.heroes.get(heroIndex2).getActionsAvailable()+ "\n"+
										"Current HP:"+ Game.heroes.get(heroIndex2).getCurrentHp()+"      "+
										"Vaccine Inventory:"+ Game.heroes.get(heroIndex2).getVaccineInventory().size()+ "       " + 
										"Supply Inventory:"+ Game.heroes.get(heroIndex2).getSupplyInventory().size()+ "       " + 
										"Damage: "+ Game.heroes.get(heroIndex2).getAttackDmg());
								updateHeroes();
							}
							catch(GameActionException e5){
								activatedLabel.setText("WARNING:" + e5.getMessage());
							}
						}
				});
			});
				
				
		//UPDATED SCENE3 (ACTUAL MAP)
		//gui map-->gameMap
//			updateMap();
		
		
		
		//LOADING SCREEN
		
		ProgressBar lb = new ProgressBar();
		String [] Imgs= {"File:/Users/gchehata/Desktop/game media/zombie.gif","File:/Users/gchehata/Desktop/game media/hero.gif"};
		int index= (int) (Math.random()*Imgs.length);
		Image runningChar= new Image(Imgs[index]);
		ImageView runner= new ImageView(runningChar);
		runner.setFitHeight(200);
		runner.setFitWidth(400);
		

		
			//Hint label
		hint= new Label();
		Label boldHint= new Label("HINT:");
		boldHint.setStyle("-fx-background-color: white");
		String [] hintText= {"There are Traps around the map, Watch your step!","Check the Game description in order to know each hero's special ability!","Each hero has a unique special ability!", "Collect and use all Vaccines to win the Game!"};
		int i= (int) (Math.random()*hintText.length);
		hint.setTextFill(Color.ANTIQUEWHITE);
		boldHint.setFont(Font.font("Impact", FontWeight.BOLD, FontPosture.REGULAR, 50));
		hint.setFont(Font.font("Impact", FontWeight.THIN, FontPosture.REGULAR, 40));
		hint.setText(hintText[i]);
		
		
		lb.setPrefWidth(200);
		lb.setPrefHeight(20);
		lb.setStyle("-fx-accent: green");
		Timeline timeline = new Timeline(
			    new KeyFrame(Duration.ZERO, new KeyValue(lb.progressProperty(), 0)),
			    new KeyFrame(Duration.seconds(3), new KeyValue(lb.progressProperty(), 1))
			);
		timeline.play();
		VBox load= new VBox(runner,lb,boldHint,hint);
		
		
		  //Loading background

		Image loading= new Image("File:/Users/gchehata/Desktop/game media/loading2.jpg");	

		BackgroundImage loadingImage = new BackgroundImage(loading,
	        BackgroundRepeat.NO_REPEAT,
	        BackgroundRepeat.NO_REPEAT,
	        BackgroundPosition.DEFAULT,
	        new BackgroundSize(1.0, 1.0, true, true, false, false));
		load.setBackground(new Background(loadingImage));
		
		load.setSpacing(20);
		load.setCursor(Cursor.WAIT);
		load.setAlignment(Pos.CENTER);
		loadingScreen= new Scene(load);
		Timeline timeline2 = new Timeline(
			    new KeyFrame(Duration.seconds(3),e-> {
			    	ps.setScene(s2);
			    	ps.setFullScreen(true);
			    })
			);
		
		startGame.setOnMouseClicked(e-> {
			buttonSound.play();
			ps.setScene(loadingScreen);
			ps.setFullScreen(true);
			timeline2.play();
		});
		
		
		Image winGif= new Image("File:/Users/gchehata/Desktop/game media/game win.gif");
		ImageView winGifView= new ImageView(winGif);
		VBox winImageBox= new VBox(winGifView);
		winImageBox.setAlignment(Pos.TOP_CENTER);
		setStylingButton(exit2,60,true);
		VBox win= new VBox(winImageBox,exit2);
		win.setAlignment(Pos.CENTER);
		win.setSpacing(300);
		
		Image winImage= new Image("File:/Users/gchehata/Desktop/game media/temp.jpg");
		BackgroundImage winbackground = new BackgroundImage(winImage,
		        BackgroundRepeat.NO_REPEAT,
		        BackgroundRepeat.NO_REPEAT,
		        BackgroundPosition.DEFAULT,
		        new BackgroundSize(1.0, 1.0, true, true, false, false));
		win.setBackground(new Background(winbackground));
		
		winScreen = new Scene(win);
		
		
		VBox lose= new VBox(exit3);
		lose.setAlignment(Pos.BOTTOM_CENTER);
		setStylingButton(exit3,60,true);
		lose.setSpacing(500);
		
		Image loseImage= new Image("File:/Users/gchehata/Desktop/game media/gameover3.gif");
		BackgroundImage losebackground = new BackgroundImage(loseImage,
		        BackgroundRepeat.NO_REPEAT,
		        BackgroundRepeat.NO_REPEAT,
		        BackgroundPosition.DEFAULT,
		        new BackgroundSize(1.0, 1.0, true, true, false, false));
		lose.setBackground(new Background(losebackground));
		
		loseScreen = new Scene(lose);
		
		
		ps.setScene(s1);
		ps.show();
	}
	
	public void setGraphicSize(ImageView iv){
		iv.setFitHeight(20);
		iv.setFitWidth(30);
	}
	
	public void initialMap(Button[][]b,GridPane g){
		ImageView fogView = null;	
		g.setVgap(10);
		g.setHgap(10);
		if(flag==true){
		for(int i=0;i<b.length;i++){
			for(int j=0;j<b.length;j++){
				Button gridCell= new Button();
				if(  (i==0 && j==1) || (i==1 && j==0) || (i==1 && j==1) ){
						if(((CharacterCell) Game.map[i][j]).getCharacter() != null){
							if(((CharacterCell) Game.map[i][j]).getCharacter() instanceof Zombie){
								 Image fogPic= new Image("File:/Users/gchehata/Desktop/game media/zombie2.png");
								 fogView= new ImageView(fogPic);
								 setGraphicSize(fogView);
						}
				    }
						else if(Game.map[i][j] instanceof CollectibleCell){
							 Image fogPic= new Image("File:/Users/gchehata/Desktop/game media/chest.png");
							 fogView= new ImageView(fogPic);
							 setGraphicSize(fogView);
						}
					else{
//						gridCell.setStyle("-fx-background-color: green;");
//						gridCell.setPrefSize(50, 40);
						Image fogPic= new Image("File:/Users/gchehata/Desktop/game media/grass.png");
						 fogView= new ImageView(fogPic);
						 setGraphicSize(fogView);
					}
						gridCell.setGraphic(fogView);
				}
				
				else if(i==0 && j==0){
//					Image fogPic= new Image("File:/Users/gchehata/Desktop/game media/pixelh3.png");
//					((Hero) ((CharacterCell) Game.map[i][j]).getCharacter()).setIndex(heroIndex);
					Image fogPic= pixelIcons[heroIndex];
					 fogView= new ImageView(fogPic);
					 setGraphicSize(fogView);
					 gridCell.setGraphic(fogView);
				}
				else{
//					fogView=new ImageView(new Image("File:/Users/gchehata/Desktop/game media/fog5.jpg"));
//			    	setGraphicSize(fogView);
//			    	gridCell.setGraphic(fogView);
					gridCell.setStyle("-fx-background-color: black;");
					gridCell.setPrefSize(50, 40);
				}
				mapCells.add(gridCell);
				g.add(gridCell,j,14-i);
			}
		}
	}
		
}
	
	
	public void initialHeroSetup(){
		for(int i=0;i<Game.availableHeroes.size();i++){
			 Game.availableHeroes.get(i).setIndex(i);
		}
	}
	public void updateHeroes(){
		int mapIndex=0;
		ImageView iv=null;
		if(heronum>1){
			for(int i=0;i<Game.map.length;i++){
				for(int j=0;j<Game.map.length;j++){
					mapIndex= (15*i) + j;
					if(Game.map[i][j].isVisible()){
						if(Game.map[i][j] instanceof CharacterCell){
								if(((CharacterCell) Game.map[i][j]).getCharacter() instanceof Hero){
									int index=  ((Hero) ((CharacterCell) Game.map[i][j]).getCharacter()).getIndex();
									iv=new ImageView(pixelIcons[index]);
									heronum--;
								}
							}
						}
					setGraphicSize(iv);
					mapCells.get(mapIndex).setGraphic(iv);
					
					if(heronum==1){
						break;
					}
				}
			}
		}
	}
	
	public void updateMap(){
		int mapIndex=0;
		heronum=0;
		ImageView iv= null;
		for(int i=0;i<Game.map.length;i++){
			for(int j=0;j<Game.map.length;j++){
				mapIndex= (15*i) + j;
				if(Game.map[i][j].isVisible()){
						if(Game.map[i][j] instanceof CharacterCell){
								if(((CharacterCell) Game.map[i][j]).getCharacter() instanceof Zombie){
									iv=new ImageView(new Image("File:/Users/gchehata/Desktop/game media/zombie2.png"));
									setGraphicSize(iv);
									mapCells.get(mapIndex).setGraphic(iv);
									mapCells.get(mapIndex).setStyle("-fx-background-color: green;");
								}
								else if(((CharacterCell) Game.map[i][j]).getCharacter() instanceof Hero){
									int index=  ((Hero) ((CharacterCell) Game.map[i][j]).getCharacter()).getIndex();
									iv=new ImageView(pixelIcons[index]);
									heronum++;
									setGraphicSize(iv);
									mapCells.get(mapIndex).setGraphic(iv);
								}
								else{
//									iv=new ImageView(new Image("File:/Users/gchehata/Desktop/game media/grass.png"));
							    	mapCells.get(mapIndex).setGraphic(null);
									mapCells.get(mapIndex).setPrefSize(50, 40);
							    	mapCells.get(mapIndex).setStyle("-fx-background-color: green;");
								}
							}
							else if(Game.map[i][j] instanceof CollectibleCell){
								if(((CollectibleCell) Game.map[i][j]).getCollectible() instanceof Supply){
									iv=new ImageView(new Image("File:/Users/gchehata/Desktop/game media/chest.png"));
									setGraphicSize(iv);
									mapCells.get(mapIndex).setGraphic(iv);
								}
								else{
									iv=new ImageView(new Image("File:/Users/gchehata/Desktop/game media/vaccine.png"));
									setGraphicSize(iv);
									mapCells.get(mapIndex).setGraphic(iv);
								}
							}
						else{
//							iv=new ImageView(new Image("File:/Users/gchehata/Desktop/game media/grass.png"));
					    	mapCells.get(mapIndex).setPrefSize(50, 40);
					    	mapCells.get(mapIndex).setStyle("-fx-background-color: green;");
						}
						
					}   	
			    else{
//			    	iv=new ImageView(new Image("File:/Users/gchehata/Desktop/game media/fog5.jpg"));
//			    	setGraphicSize(iv);
//					mapCells.get(mapIndex).setGraphic(iv);
			    	mapCells.get(mapIndex).setGraphic(null);
			    	mapCells.get(mapIndex).setPrefSize(50, 40);
			    	mapCells.get(mapIndex).setStyle("-fx-background-color: black;");	
				}
//			setGraphicSize(iv);
			
		}
	}
		updateHeroes();
}
//	
//	public void updateMapExplore() {
//		
//	}
	public void setStyling(Button b, int s,boolean anim){
		if(anim){
		b.setStyle("-fx-background-color: transparent;");
		b.setTextFill(Color.DARKGREEN);
		b.setFont(Font.font("Impact", FontWeight.BOLD, FontPosture.ITALIC, s));
		b.setOnMouseEntered(e-> {
			b.setFont(Font.font("Impact", FontWeight.BOLD, FontPosture.ITALIC, s+10));
			b.setTextFill(Color.GRAY);
			b.setUnderline(true);
		});
		b.setOnMouseExited(e-> {
			b.setFont(Font.font("Impact", FontWeight.BOLD, FontPosture.REGULAR, s));
			b.setTextFill(Color.DARKGREEN);
			b.setUnderline(false);
		});
	}
		else{
			b.setStyle("-fx-background-color: transparent;");
			b.setTextFill(Color.DARKGREEN);
			b.setFont(Font.font("Impact", FontWeight.BOLD, FontPosture.ITALIC, s));
			b.setOnMouseEntered(e-> {
				b.setTextFill(Color.GRAY);
				b.setUnderline(true);
			});
			b.setOnMouseExited(e-> {
				b.setTextFill(Color.DARKGREEN);
				b.setUnderline(false);
			});
		}
	}
	
	public void setStyling(Label b, int s,boolean anim){
		if(anim){
		b.setStyle("-fx-background-color: transparent;");
		b.setTextFill(Color.DARKGREEN);
		b.setFont(Font.font("Impact", FontWeight.BOLD, FontPosture.ITALIC, s));
		b.setOnMouseEntered(e-> {
			b.setFont(Font.font("Impact", FontWeight.BOLD, FontPosture.ITALIC, s+10));
			b.setTextFill(Color.GRAY);
			b.setUnderline(true);
		});
		b.setOnMouseExited(e-> {
			b.setFont(Font.font("Impact", FontWeight.BOLD, FontPosture.REGULAR, s));
			b.setTextFill(Color.DARKGREEN);
			b.setUnderline(false);
		});
	}
		else{
			b.setStyle("-fx-background-color: transparent;");
			b.setTextFill(Color.DARKGREEN);
			b.setFont(Font.font("Impact", FontWeight.BOLD, FontPosture.ITALIC, s));
			b.setOnMouseEntered(e-> {
				b.setTextFill(Color.GRAY);
				b.setUnderline(true);
			});
			b.setOnMouseExited(e-> {
				b.setTextFill(Color.DARKGREEN);
				b.setUnderline(false);
			});
		}
	}
	
	public void setStylingButton(Button b, int s,boolean anim){
		if(anim){
		b.setStyle("-fx-background-color: transparent;");
		b.setTextFill(Color.RED);
		b.setFont(Font.font("Impact", FontWeight.BOLD, FontPosture.ITALIC, s));
		b.setOnMouseEntered(e-> {
			b.setFont(Font.font("Impact", FontWeight.BOLD, FontPosture.ITALIC, s+10));
			b.setTextFill(Color.GRAY);
			b.setUnderline(true);
		});
		b.setOnMouseExited(e-> {
			b.setFont(Font.font("Impact", FontWeight.BOLD, FontPosture.REGULAR, s));
			b.setTextFill(Color.DARKGREEN);
			b.setUnderline(false);
		});
	}
		else{
			b.setStyle("-fx-background-color: transparent;");
			b.setTextFill(Color.RED);
			b.setFont(Font.font("Impact", FontWeight.BOLD, FontPosture.ITALIC, s));
			b.setOnMouseEntered(e-> {
				b.setTextFill(Color.GRAY);
				b.setUnderline(true);
			});
			b.setOnMouseExited(e-> {
				b.setTextFill(Color.DARKGREEN);
				b.setUnderline(false);
			});
		}
	}
	
	public void updateText(){
		heroInfo2.setText("Name: "+Game.heroes.get(heroIndex2).getName()+ "         "
				+"Type: "+type+ "\n"+
				"MaxHp:"+Game.heroes.get(heroIndex2).getMaxHp()+ "               "+ 
				"ActionsAvailable: "+ Game.heroes.get(heroIndex2).getActionsAvailable()+ "\n"+
				"Current HP:"+ Game.heroes.get(heroIndex2).getCurrentHp()+"      "+
				"Damage: "+ Game.heroes.get(heroIndex2).getAttackDmg());
	}
	
	public void checkGameStatus(Stage ps){
		if(Game.checkWin()) {
			ps.setScene(winScreen);
			ps.setFullScreen(true);
		}
		if(Game.checkGameOver()) {
			ps.setScene(loseScreen);
			ps.setFullScreen(true);
		}
	}
	
//	public void 
	
	
//	public void spawnHero(){
//		
//	}
	
	
	public static void main(String [] args){
		launch(args);
	}
}
