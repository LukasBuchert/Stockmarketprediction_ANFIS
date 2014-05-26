package gui;

import java.io.File;

import anfis.Main;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
 
public class MainApplication  extends Application {
    
	public File input;
	public File output;
	
	private String inputPath;
	private int numberOfInputVariables = 3;
	private int percentage = 90;
	private int numberOfShapes = 3;
	private int bellSlope = 2;
	private int epochNumber = 2;
	private String outputPath;
	
	private Main prediction;
	
	public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(final Stage primaryStage) {
        primaryStage.setTitle("Stock Market Prediction");
        
        BorderPane border = new BorderPane();
        border.setTop(new Label ("Welcome to Stock Market Prediction !!!"));

        
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
        
        border.setCenter(grid);
        
        Label lblInput = new Label ("Inputdata:");
        Label lblOutput = new Label ("Output Path:");
        final Label lblStatus = new Label ("Prediction not started yet!");
        
        Button btnInputConfig = new Button ("Configure");
        Button btnProperties = new Button ("Properties");
        Button btnPrediction = new Button ("Prediction");
        
        final TextField tfInput = new TextField();
        final TextField tfOutput = new TextField();
        
        grid.add(lblInput, 0, 0);
        grid.add(tfInput, 1, 0);
        grid.add(lblOutput, 0, 1);
        grid.add(tfOutput, 1, 1);
        grid.add(lblStatus, 0, 2);
        grid.add(btnProperties, 0, 3);
        grid.add(btnPrediction, 2, 3);
        
        // getting Files
        
        final FileChooser fileChooser = new FileChooser();
        
        tfInput.setOnMouseClicked(new EventHandler<MouseEvent>() {
        	
        	@Override
			public void handle(MouseEvent arg0) {
				        		
        		input = fileChooser.showOpenDialog(primaryStage);
				
				if (input != null){
			   	tfInput.setText(input.getAbsolutePath());
			   	inputPath = input.getAbsolutePath();
				System.out.println("Path choosen: " + input.getAbsolutePath());
				}
			}
        	
        });
        
        
        
        tfOutput.setOnMouseClicked(new EventHandler<MouseEvent>() {
        	
        	@Override
			public void handle(MouseEvent arg0) {
        		fileChooser.setInitialFileName("Prediction");
				output = fileChooser.showSaveDialog(primaryStage);
				
				if (output != null){
				
				tfOutput.setText(output.getAbsolutePath());
				outputPath = output.getAbsolutePath();
				System.out.println("Path choosen: " + output.getAbsolutePath());}
				
			}
        	
        });
        
        
        btnPrediction.setOnAction(new EventHandler<ActionEvent>(){
        	public void handle (final ActionEvent ee){
        		System.out.println("checkout Parameters before starting prediction");
        		lblStatus.setText("Paramter not correct !!!");
        		lblStatus.setTextFill(new Color(1,0,0,1.0));
        		
        		prediction = new Main(inputPath, numberOfInputVariables, percentage,
        				numberOfShapes, bellSlope, epochNumber, outputPath);
        		Main.runANFIS();
        		
        	}
        });
        
                    
        StackPane root = new StackPane();
        root.getChildren().add(border);
        final Scene scene1 = new Scene(root);
        
        
       
        
     // build scene for properties 
        
        Label lblNumberOf = new Label ("Number of Input Variables:");
        Label lblMembf = new Label ("Membership Functions");
        Label lblEpoch = new Label ("Epochs");
        Label lblPercent = new Label ("Percentage training / test:");
        
       
        
        final TextField tfNumberOf = new TextField();
        tfNumberOf.setText(Integer.toString(numberOfInputVariables));
        final TextField tfPercent = new TextField();
        tfPercent.setText(Integer.toString(percentage));
        final TextField tfMembf = new TextField();
        tfMembf.setText(Integer.toString(numberOfShapes));
        final TextField tfEpoch = new TextField();
        tfEpoch.setText(Integer.toString(epochNumber));
        
        Button btnBack = new Button ("Save Properties");
    	btnBack.setOnAction(new EventHandler<ActionEvent>(){
        	public void handle (final ActionEvent ee){
        		
        		numberOfInputVariables = Integer.valueOf(tfNumberOf.getText());
        		percentage = Integer.valueOf(tfPercent.getText());
        		numberOfShapes = Integer.valueOf(tfMembf.getText());
        		epochNumber = Integer.valueOf(tfEpoch.getText());
        		
        		
        		primaryStage.setScene(scene1);
        	}
        });
        
        GridPane gridAll = new GridPane();
        gridAll.setHgap(10);
        gridAll.setVgap(10);
    	
        gridAll.add(lblNumberOf, 0, 0);
        gridAll.add(tfNumberOf, 1, 0);
        gridAll.add(lblMembf, 0, 1);
        gridAll.add(tfMembf, 1, 1);
        gridAll.add(lblEpoch, 0, 4);
        gridAll.add(tfEpoch, 1, 4);
        
        
        gridAll.add(lblPercent, 0, 5);
        gridAll.add(tfPercent, 1, 5);
        
        
        gridAll.add(btnBack, 1, 6);
       
    	
    	
    	final Scene sceneProp = new Scene(gridAll);
                
     
    	
    	
    	
        
        btnProperties.setOnAction(new EventHandler<ActionEvent>() {
            
        	
        	
        	@Override
            public void handle(final ActionEvent e) {
                primaryStage.setScene(sceneProp);
        
            }
        });
       
        primaryStage.setScene(scene1);
        primaryStage.show();
    }
}
