package view;

import model.PlayerColor;

        import javax.swing.*;
        import java.awt.*;

/**
 * This is the equivalent of the ChessPiece class,
 * but this class only cares how to draw Chess on ChessboardComponent
 */
public class Component extends JComponent {
    private PlayerColor owner;

    private boolean selected;//有没有被选中

    public PlayerColor getOwner() {
        return owner;
    }

    public void setOwner(PlayerColor owner) {
        this.owner = owner;
    }

    public Component(PlayerColor owner, int size) {
        this.owner = owner;
        this.selected = false;
        setSize(size/2, size/2);
        setLocation(0,0);
        setVisible(true);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


    @Override
    protected void paintComponent(Graphics g) {

    }
}

