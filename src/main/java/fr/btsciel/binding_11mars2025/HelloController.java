package fr.btsciel.binding_11mars2025;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class HelloController implements Initializable {


    public TextField hauteur;
    public TextField largeur;
    public TextField perimetre;
    public TextField surface;

    RectangleProperty monRectangle = new RectangleProperty();

//    public DoubleProperty h = new SimpleDoubleProperty();
//    public DoubleProperty l = new SimpleDoubleProperty();
//    public DoubleProperty p = new SimpleDoubleProperty();
//    public DoubleProperty s = new SimpleDoubleProperty();

    double SEUIL_S=5000.0;

    public Slider Slider_Largeur;
    public Slider Slider_Hauteur;
    public Rectangle rectangle;

    StringConverter sc = new DoubleStringConverter() {
        @Override
        public String toString(Double value) {
            DecimalFormat df = new DecimalFormat("#.## m");
            return df.format(value);
        }

        @Override
        public Double fromString(String value) {
            System.out.println(value);
            value = value.replace(",", ".");
            value = value.replace(" m", "");
            return Double.parseDouble(value);
        }
    };


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //p=(h+l)*2
        monRectangle.perimetreProperty().bind(monRectangle.largeurProperty().add(monRectangle.hauteurProperty()).multiply(2));

        //s=h*l
        monRectangle.surfaceProperty().bind(monRectangle.largeurProperty().multiply(monRectangle.hauteurProperty()));

        //code-->ihm
        perimetre.textProperty().bind(monRectangle.perimetreProperty().asString("%.2f m"));;
        surface.textProperty().bind(monRectangle.surfaceProperty().asString("%.2f m²"));

        //ihm-->code
        Bindings.bindBidirectional(hauteur.textProperty(), monRectangle.hauteurProperty(), sc);
        Bindings.bindBidirectional(largeur.textProperty(),monRectangle.largeurProperty(),sc);

        //bind text et slider
        Bindings.bindBidirectional(hauteur.textProperty(),Slider_Hauteur.valueProperty(),sc);
        Bindings.bindBidirectional(largeur.textProperty(),Slider_Largeur.valueProperty(),sc);

        //slider
        Slider_Hauteur.visibleProperty().bind(Bindings.when(monRectangle.hauteurProperty().greaterThan(100))
                .then(false)
                .otherwise(true));
        Slider_Largeur.visibleProperty().bind(Bindings.when(monRectangle.largeurProperty().greaterThan(100))
                .then(false)
                .otherwise(true));


        //rectangle
        Bindings.bindBidirectional(Slider_Hauteur.valueProperty(),rectangle.heightProperty());
        Bindings.bindBidirectional(Slider_Largeur.valueProperty(),rectangle.widthProperty());

        rectangle.visibleProperty().bind(Bindings.when(monRectangle.largeurProperty().greaterThan(100).or(monRectangle.hauteurProperty().greaterThan(100)))
                .then(false)
                .otherwise(true));

        //Changement de couleur
        surface.backgroundProperty().bind(Bindings.when(monRectangle.surfaceProperty().greaterThan(SEUIL_S))
                .then(new Background(new BackgroundFill(Color.RED,null, null )))
                .otherwise(new Background(new BackgroundFill(Color.AQUA, null, null))));

        rectangle.fillProperty().bind(Bindings.when(monRectangle.surfaceProperty().greaterThan(SEUIL_S))
                .then(Color.RED)
                .otherwise(Color.AQUA));

    }
}

