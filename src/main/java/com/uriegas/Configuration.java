package com.uriegas;

import java.util.function.Supplier;

import javafx.beans.binding.*;
import javafx.beans.property.*;
import javafx.scene.Parent;
import javafx.scene.paint.*;

/**
 * Binding class, this class stores all the data about the css styling of the app,
 * the values of this class could be changed and the will reflect in the view of the app
 * Copied from: https://stackoverflow.com/questions/42541171/javafx-css-dynamic-styling#42541771
 */
public class Configuration {
    private final ObjectProperty<Color> base = new SimpleObjectProperty<>(Color.web("#D43C3C"));
    private final ObjectProperty<Color> accent = new SimpleObjectProperty<>(Color.web("#0096c9"));
    private final ObjectProperty<Color> defaultButton = new SimpleObjectProperty<>(Color.web("#abd8ed"));
    private final ObjectProperty<Color> focusColor = new SimpleObjectProperty<>(Color.web("#039ed3"));
    private final ObjectProperty<Color> faintFocusColor = new SimpleObjectProperty<>(Color.web("039ed322"));

    public ObjectProperty<Color> baseProperty() {
        return base ;
    }

    public final Color getBase() {
        return baseProperty().get();
    }

    public final void setBase(Color base) {
        baseProperty().set(base);
    }

    public ObjectProperty<Color> accentProperty() {
        return base ;
    }

    public final Color getAccent() {
        return accentProperty().get();
    }

    public final void setAccent(Color base) {
        accentProperty().set(base);
    }

    public ObjectProperty<Color> defalutButProperty() {
        return base ;
    }

    public final Color getDefaultButton() {
        return defalutButProperty().get();
    }

    public final void setDefaultButton(Color base) {
        defalutButProperty().set(base);
    }

    public ObjectProperty<Color> focusColorProperty() {
        return base ;
    }

    public final Color getFocusColor() {
        return focusColorProperty().get();
    }

    public final void setFoucsColor(Color base) {
        focusColorProperty().set(base);
    }

    public ObjectProperty<Color> faintFocusColorProperty() {
        return base ;
    }

    public final Color getFaintFocusColor() {
        return faintFocusColorProperty().get();
    }

    public final void setFaintFocusColor(Color base) {
        faintFocusColorProperty().set(base);
    }
    // etc etc

    private static ReadOnlyStringWrapper css = new ReadOnlyStringWrapper() ;
	
    public Configuration() {
        css.bind(Bindings.createStringBinding(() -> String.format(
             "-fx-base: %s; "
            +"-fx-accent: %s; "
            +"-fx-default-button: %s; "
            +"-fx-focus-color: %s ; "
            +"-fx-faint-focus-color: %s ;",
            toRgba(getBase()),
            toRgba(getAccent()),
            toRgba(getDefaultButton()),
            toRgba(getFocusColor()),
            toRgba(getFaintFocusColor())),
            base, accent, defaultButton, focusColor, faintFocusColor));
    }

    private String toRgba(Color color) {
        int r = (int) (255 * color.getRed());
        int g = (int) (255 * color.getGreen());
        int b = (int) (255 * color.getBlue());
        int a = (int) (255 * color.getOpacity());
        return String.format("#%02x%02x%02x%02x", r, g, b, a);
    }

    public static ReadOnlyStringProperty cssProperty() {
        return css.getReadOnlyProperty();
    }
	
	public <T extends Parent> T createNode(Supplier<T> factory) {
		T node = factory.get();
		node.styleProperty().bind(cssProperty());
		return node ;
	}
}
