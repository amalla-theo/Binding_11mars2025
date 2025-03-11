package fr.btsciel.binding_11mars2025;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {


    public TextField hauteur;
    public TextField largeur;
    public TextField perimetre;
    public TextField surface;

    public DoubleProperty h = new SimpleDoubleProperty();
    public DoubleProperty l = new SimpleDoubleProperty();
    public DoubleProperty p = new SimpleDoubleProperty();
    public DoubleProperty s = new SimpleDoubleProperty();

    StringConverter sc = new DoubleStringConverter();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //p=(h+l)*2
        p.bind(l.add(h).multiply(2));
        //s=h*l
        s.bind((l.multiply(h)));
        //code-->ihm
        perimetre.textProperty().bind(p.asString());;
        surface.textProperty().bind(s.asString());
        //ihm-->code
        Bindings.bindBidirectional(hauteur.textProperty(), h, sc);
        Bindings.bindBidirectional(largeur.textProperty(),l,sc);
    }
}