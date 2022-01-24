package com.company;


         import javafx.application.Application;
         import javafx.geometry.Pos;
         import javafx.scene.control.Label;
         import javafx.scene.control.TextField;
         import javafx.scene.paint.Color;
         import javafx.stage.Stage;
         import javafx.stage.FileChooser;
         import javafx.scene.Scene;
         import javafx.scene.layout.VBox;
         import javafx.scene.layout.HBox;
         import javafx.scene.text.Text;
         import javafx.scene.control.Button;
         import javafx.event.ActionEvent;
         import javafx.event.EventHandler;

         import java.io.BufferedReader;
         import java.io.File;
         import java.io.FileReader;
         import java.io.IOException;
         import java.util.ArrayList;

public class Main
        extends Application {

    private Text actionStatus;
    //private Text nameOfTheFile;
    private Text result1;
    private Text result2;
    private TextField fact;
    Label err = new Label();

    private Stage savedStage;
    ArrayList<String> basedesfaintcloned = new ArrayList<>();


    public static void main(String [] args) {

        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Button open = new Button("ouvrir un fichier");
        open.setOnAction(new SingleFcButtonListener());
        Button forwardChanning = new Button("chainage avant");
        forwardChanning.setOnAction(new forwardHeandler());
        Button backwardChanning = new Button("chainage arrier");
        backwardChanning.setOnAction(new backwardHeandler());
        HBox save1 = new HBox(10);
        //nameOfTheFile = new Text();
        actionStatus = new Text();
        result1 = new Text();
        result2 = new Text();
        fact = new TextField();
        Label l1 = new Label("Result : ");
        Label l2 = new Label("Result : ");

        VBox vbox = new VBox(30);
        vbox.getChildren().addAll( open ,actionStatus,forwardChanning,l1,result1,backwardChanning,fact,l2, result2 , err );
        vbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vbox, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
        savedStage = primaryStage;
    }

    private class SingleFcButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {

            showSingleFileChooser();
        }
    }
    private class forwardHeandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {

            forwardchainingfunction();
        }
    }
    private class backwardHeandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {

            backwardchainingfunction();
        }
    }
    ArrayList <regle> regles = new ArrayList<>();
    ArrayList <String> baseDesFait = new ArrayList<>();
    boolean done = false;
    private void showSingleFileChooser() {

        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {

            actionStatus.setText("File selected:  " + selectedFile.getName());
            // lire les ligne fu fichier

            String[] tmp ;
            String file = selectedFile.getPath();


            boolean action = false;
            int i = 0 ;
            try(BufferedReader br = new BufferedReader(new FileReader(file.toString())))
            {
                String line;
                // loop pour lire les ligne
                //1ere line est pour BF
                line = br.readLine();
                tmp = line.split(" ");
                for (String x:tmp) baseDesFait.add(x.toLowerCase());
                // les rests pour les regles
                while ((line = br.readLine()) != null) {
                    action = false;
                    regle regle = new regle();
                    String[] words = line.split(" ");
                    // loop pour lire les mots
                    for (int j = 2; j < words.length; j++)
                    {
                        if (words[j].equals("alors")) action = true;
                        else if (!action) regle.premisses.add(words[j].toLowerCase());
                        else regle.actions.add(words[j].toLowerCase());

                    }
                    regles.add(regle);
                }


                basedesfaintcloned.addAll(baseDesFait);

                //for (regle R:regles) System.out.println(R.toString());
            }
            catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }

    }
    private void forwardchainingfunction() {

// the forward chaining ********************************************************************************

        ArrayList<regle> regleToDesctivate = new ArrayList<>();
        while (!done) {
            regleToDesctivate.clear();
            //System.out.println(regleToDesctivate.toString());
            for (regle regle : regles)
                if (baseDesFait.containsAll(regle.premisses) && regle.activte) regleToDesctivate.add(regle);

            for (regle regle : regleToDesctivate) {
                for (String act : regle.actions) if (!baseDesFait.contains(act)) baseDesFait.add(act);
                regle.activte = false;
            }
            done = regleToDesctivate.isEmpty();

        }

        // revenire a l'etat initial
        for (regle regle : regles) regle.activte = true;

        //System.out.println("***************************************");
        result1.setText("la base des faits complet est :  "+baseDesFait.toString());

    }
    private void backwardchainingfunction() {

        test test = new test();
        String path = "";
        if ((fact.getText() != null && !fact.getText().isEmpty())) {
            path = fact.getText();
            err.setText("");
            result2.setText(test.verify(path,basedesfaintcloned,regles));
        } else {
            err.setText("donnez le fait a verifier");
            result2.setText("");
            err.setTextFill(Color.color(1, 0, 0));
        }
    }

}