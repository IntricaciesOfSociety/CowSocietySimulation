package menus.menuImplementations;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public abstract class GenericMenu {

    Pane stack;

    Rectangle background;

    Object objectTie;

    protected abstract void createMenu(Object objectTie);

    public abstract void updateMenu();

    public abstract void closeMenu();

    protected abstract void openMenu();
}
