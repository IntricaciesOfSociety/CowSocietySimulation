package menus;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public abstract class GenericMenu {

    protected Pane stack;

    protected Rectangle background;

    protected Object objectTie;

    protected abstract void createMenu(Object objectTie);

    protected abstract void updateMenu();

    protected abstract void closeMenu();

    protected abstract void openMenu();
}
