package trianglesfirst;

import java.util.Random;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TrianglesFirst extends Application{
    
    //window var
    final double windowW = 320;
    final double windowH = 200;
    
    //size of shapes, coordinates and names
    double xCoor;
    double yCoor;
    double width = 30;
    double height = 30;
    String tri = "triangle.png";
    String sq = "square.png";
    String pent = "pentagon.png";
    String hex = "hexagon.png";
    
    //keeps of times clicked
    int triCount = 6;
    int sqCount = 6;
    int pentCount = 6;
    int hexCount = 6;
    
    public static void main(String[] args) {
        launch(args);
    }
    
    static Random rng = new Random();
    
    @Override
    public void start(Stage stage) throws Exception{
        //title
        stage.setTitle("Triangles First!");
        
        //setup window
        Group root = new Group();
        Scene scene = new Scene(root, windowW, windowH);
        
        //adversary created with its movements
        Circle adv = new Circle();
        adv.setFill(Color.BLACK);
        adv.setRadius(15);
        adv.setTranslateX(windowW / 2);
        adv.setTranslateY(windowH / 2);
        root.getChildren().add(adv);
        
        //create shapes
        for(int i = 0; i < 6; i++){
            createTriangle(scene, root);
        }
        
        //if mouse moves adv will move towards it
        scene.setOnMouseMoved(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent evt) {
                if(evt.getX() > adv.getTranslateX()){
                    adv.setTranslateX(adv.getTranslateX() + 1);
                }
                
                if(evt.getX() < adv.getTranslateX()){
                    adv.setTranslateX(adv.getTranslateX() - 1);
                }
                
                if(evt.getY() > adv.getTranslateY()){
                    adv.setTranslateY(adv.getTranslateY() + 1);
                }
                
                if(evt.getY() < adv.getTranslateY()){
                    adv.setTranslateY(adv.getTranslateY() - 1);
                }
            }
        });
        
        adv.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent evt) {
                losingScreen(scene, root);
            }
            
        });
        
        //will create beginnig window
        beginning(scene, root);
        
        //create window
        stage.setScene(scene);
        stage.show();
    }
    
    private void beginning(Scene scene, Group root){
        //startup scene
        Rectangle startBack = new Rectangle();
        startBack.setWidth(windowW);
        startBack.setHeight(windowH);
        startBack.setLayoutX(0);
        startBack.setLayoutY(0);
        startBack.setFill(Color.BLUE);
        root.getChildren().add(startBack);
        
        //button
        Button startBut = new Button("Click here to start!");
        startBut.setTranslateX((windowW / 2) - 45);
        startBut.setTranslateY(0);
        root.getChildren().add(startBut);
        
        //remove button and background
        startBut.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent evt) {
                root.getChildren().remove(startBut);
                root.getChildren().remove(startBack);
            }
        });
    }
    
    private ImageView createShape(Scene scene, Group root, String wordShape){
        ImageView shape = new ImageView(new Image(wordShape, width, height, false, false));
        xCoor = (double)rng.nextInt((int)(scene.getWidth()) - (int)width);
        yCoor = (double)rng.nextInt((int)(scene.getHeight()) - (int)height);
        shape.setTranslateX(xCoor);
        shape.setTranslateY(yCoor);
        
        root.getChildren().add(shape);
        
        return shape;
    }
    
    private void createTriangle(Scene scene, Group root){
        ImageView triangle = createShape(scene, root, tri);
        
        triangle.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent evt) {
                root.getChildren().remove(triangle);
                createSquare(scene, root);
                triCount--;
            }
        });
    }
    
    private void createSquare(Scene scene, Group root){
        ImageView square = createShape(scene, root, sq);
        
        square.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent evt) {
                if(triCount > 0){
                    losingScreen(scene, root);
                }
            }
        });

        square.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent evt) {
                if(triCount == 0){
                    root.getChildren().remove(square);
                    createPentagon(scene, root);
                    sqCount--;
                }
            }
        });
    }
    
    private void createPentagon(Scene scene, Group root){
        ImageView pentagon = createShape(scene, root, pent);
        
        pentagon.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent evt){
                if(sqCount > 0){
                    losingScreen(scene, root);
                }
            }
        });
        
        pentagon.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent evt){
                if(sqCount == 0){
                    root.getChildren().remove(pentagon);
                    createHexagons(scene, root);
                    pentCount--;
                }
            }
        });
    }
    
    private void createHexagons(Scene scene, Group root){
        ImageView hexagon = createShape(scene, root, hex);
        
        hexagon.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent evt) {
                if(pentCount > 0){
                    losingScreen(scene, root);
                }
            }
        });
        
        hexagon.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent evtt) {
                if(pentCount == 0){
                    root.getChildren().remove(hexagon);
                    hexCount--;
                }
                if(hexCount == 0){
                    winningScreen(scene, root);
                }
            }
        });
    }
    
    private void losingScreen(Scene scene, Group root){
        Label lose = new Label("You lost!!!!");
        lose.setTextFill(Color.BLACK);
        lose.setFont(new Font("Arial", 20));
        lose.setTranslateX((windowW/2) - 35);
        lose.setTranslateY((windowH/2) - 20);
        
        Rectangle losingScr = new Rectangle();
        losingScr.setWidth(windowW);
        losingScr.setHeight(windowH);
        losingScr.setLayoutX(0);
        losingScr.setLayoutY(0);
        losingScr.setFill(Color.RED);
        
        root.getChildren().removeAll();
        root.getChildren().add(losingScr);
        root.getChildren().add(lose);
    }
    
    private void winningScreen(Scene scene, Group root){
        Label won = new Label("You won!!!!");
        won.setTextFill(Color.BLACK);
        won.setFont(new Font("Arial", 20));
        won.setTranslateX((windowW/2) - 35);
        won.setTranslateY((windowH/2) - 20);
        
        Rectangle winningScr = new Rectangle();
        winningScr.setWidth(windowW);
        winningScr.setHeight(windowH);
        winningScr.setLayoutX(0);
        winningScr.setLayoutY(0);
        winningScr.setFill(Color.GREEN);
        
        root.getChildren().removeAll();
        root.getChildren().add(winningScr);
        root.getChildren().add(won);
    }
}
