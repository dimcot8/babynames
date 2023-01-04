package application;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class Main extends Application {
	static  Label labelName;
    static Label labelGender;
    static Label labelYear;
    static HBox hbButtons;
    static TextField name, gender, year;

	static void showRecord(String nameStr, String yearStr, String ranked, String genderStr, GridPane g) {
	      Label label, label2 ;
	      Button yes, no;
	      yes = new Button("Yes");
	      no = new Button("No");
	      label = new Label("");


	      if(nameStr.equals("No record")) {
		      label = new Label("No record for "+ yearStr);
	      } else {

		if(genderStr.equals("M")) {
         label = new Label("Boy name " + nameStr +" is ranked #" + ranked + " in " + yearStr);
		} else  if(genderStr.equals("F")){
	      label = new Label("Girl name " + nameStr +" is ranked #" + ranked + " in " + yearStr);

		}else {
		 label = new Label("The given word is not present, or entered gender is not in the list.");
		}
	      }

		label2 = new Label("Do you want to search for another name? ");
		HBox hbButtons1 = new HBox();
		HBox hbLabels = new HBox();
		HBox hbLabels2 = new HBox();

	      hbButtons1.setSpacing(40.0);
		  hbButtons1.getChildren().addAll(yes, no);
	      yes.setPrefWidth(100);
	      no.setPrefWidth(100);
	      hbLabels.getChildren().addAll(label);
	      hbLabels2.getChildren().addAll(label2);

	    g.add(hbLabels, 0, 0, 2, 1);
	    g.add(hbLabels2, 0, 1, 2, 1);
	    g.add(hbButtons1, 0, 2, 2, 1);


	    yes.setOnAction(new EventHandler<ActionEvent>()
	      {
	          @Override
	          public void handle(ActionEvent event)
	          {
	        	  hbButtons.setVisible(true);
	        	  year.setVisible(true);
	        	  gender.setVisible(true);
	        	  name.setVisible(true);
	        	  labelName.setVisible(true);
	        	  labelGender.setVisible(true);
	        	  labelName.setVisible(true);
	        	  labelYear.setVisible(true);
	        	  g.getChildren().removeAll(hbLabels, hbLabels2, hbButtons1);
	          }
	      }
	     );

	    no.setOnAction(new EventHandler<ActionEvent>()
	      {
	          @Override
	          public void handle(ActionEvent event)
	          {
	              System.exit(1);
	          }
	      }
	     );
	}

	static void getRank(TextField name, TextField gender, TextField year, GridPane grid) {
		String fileName = "babynamesranking" + year.getText() + ".txt";
		try {
		File f = new File(fileName);
		if (!f.exists()) {
            System.out.println("No record for " + year.getText());
            showRecord("No record", year.getText(), "", gender.getText(), grid);
        }else {
		FileReader fileReader = new FileReader(f);
		BufferedReader bufferedReader = new BufferedReader(fileReader);

	      boolean flagMale = false;
	      boolean flagFemale = false;

	      String[] words=null;
	      String s;
	      String ranked="";
	      while((s=bufferedReader.readLine())!=null)
	      {
	         words=s.split("	");

	         if(gender.getText().equals("M")) {
	        	 if(words[1]!=null) {
	                 if (words[1].equals(name.getText()))
	                 {
	                	 ranked = words[0];
	                  flagMale = true;
	                 }
	        	 }

	        	  } else if(gender.getText().equals("F")) {
	        		  if(words[3]!=null) {
		                 if (words[3].equals(name.getText()))
		                 {
		                	 ranked = words[0];

		                  flagFemale = true;
		                 }
	        		  }
	        	  }
	      }

	      if(flagMale)
	      {
	         System.out.println("The given word is present for male: " + name.getText() + " ranked #" + ranked);
	         showRecord(name.getText(), year.getText(), ranked, gender.getText(), grid);
	      }
	      else if(flagFemale)
	      {
		         System.out.println("The given word is present for female: " + name.getText() + " ranked #" + ranked);
		         showRecord(name.getText(), year.getText(), ranked, gender.getText(), grid);
	      } else {
		         System.out.println("The given word is not present, or entered gender is not in the list");
		         showRecord(name.getText(), year.getText(), ranked, "N", grid);
	      }
        }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			  GridPane grid = new GridPane();
			  grid.setAlignment(Pos.CENTER);

		      grid.setVgap(5);
		      grid.setHgap(5);

		       hbButtons = new HBox();

		      labelName = new Label("Enter the Name:");
		      labelGender = new Label("Enter the Gender:");
		      labelYear = new Label("Enter the Year:");
		      hbButtons.setSpacing(50.0);
		      Button submitQuery, exit;

		      name = new TextField("");
		      gender = new TextField("");
		      year = new TextField("");

		      year.setMaxWidth(150);
		      gender.setMaxWidth(40);
		      name.setMaxWidth(150);

		      grid.add(labelYear, 0, 0);
		      grid.add(year, 1, 0);

		      grid.add(labelGender, 0, 1);
		      grid.add(gender, 1, 1);

		      grid.add(labelName, 0, 2);
		      grid.add(name, 1, 2);

		      submitQuery = new Button("Submit Query");
		      exit = new Button("Exit");

		      hbButtons.getChildren().addAll(submitQuery, exit);
		      submitQuery.setPrefWidth(100);
		      exit.setPrefWidth(100);
		      grid.add(hbButtons, 0, 4, 2, 1);

		      submitQuery.setOnAction(new EventHandler<ActionEvent>()
		      {
		          @Override
		          public void handle(ActionEvent event)
		          {
		        	  hbButtons.setVisible(false);
		        	  year.setVisible(false);
		        	  gender.setVisible(false);
		        	  name.setVisible(false);
		        	  labelName.setVisible(false);
		        	  labelGender.setVisible(false);
		        	  labelName.setVisible(false);
		        	  labelYear.setVisible(false);

		        	  getRank(name, gender, year, grid);
		          }
		      }
		     );

		      exit.setOnAction(new EventHandler<ActionEvent>()
		      {
		          @Override
		          public void handle(ActionEvent event)
		          {
		        	 System.exit(1);
		          }
		      }
		     );

			  Scene scene = new Scene(grid);
		      primaryStage.setTitle("Search Name Ranking Application");
		      primaryStage.setScene(scene);
		      primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
