package gui;

import java.io.File;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
 
public class MainApplication  extends Application {
    
	public File input;
	public File output;
	
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
        grid.add(btnInputConfig, 2, 0);
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
				System.out.println("Path choosen: " + input.getAbsolutePath());
				}
			}
        	
        });
        
        
        
        tfOutput.setOnMouseClicked(new EventHandler<MouseEvent>() {
        	
        	@Override
			public void handle(MouseEvent arg0) {
        		fileChooser.setInitialFileName("Prediction.csv");
				output = fileChooser.showSaveDialog(primaryStage);
				
				if (output != null){
				
				tfOutput.setText(output.getAbsolutePath());
				System.out.println("Path choosen: " + output.getAbsolutePath());}
				
			}
        	
        });
        
        
        btnPrediction.setOnAction(new EventHandler<ActionEvent>(){
        	public void handle (final ActionEvent ee){
        		System.out.println("checkout Parameters before starting prediction");
        		lblStatus.setText("Paramter not correct !!!");
        		lblStatus.setTextFill(new Color(1,0,0,1.0));
        		
        	}
        });
        
        
        // getting other stages
        
        StackPane root = new StackPane();
        root.getChildren().add(border);
        final Scene scene1 = new Scene(root);
        
        
       
        
        Button btnBack = new Button ("Stage for Configuration");
    	btnBack.setOnAction(new EventHandler<ActionEvent>(){
        	public void handle (final ActionEvent ee){
        		primaryStage.setScene(scene1);
        	}
        });
    	
    	Button btnBack2 = new Button ("Stage for Properties");
    	btnBack2.setOnAction(new EventHandler<ActionEvent>(){
        	public void handle (final ActionEvent ee){
        		primaryStage.setScene(scene1);
        	}
        });
    	
    	final Scene sceneConfig = new Scene(btnBack);
    	final Scene sceneProp = new Scene(btnBack2);
                
        btnInputConfig.setOnAction(new EventHandler<ActionEvent>() {
            
        	
        	
        	@Override
            public void handle(final ActionEvent e) {
                primaryStage.setScene(sceneConfig);
        
            }
        });
        
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
