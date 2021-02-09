package it.unimol.space_invaders.gui.game_objects;

import it.unimol.space_invaders.interfaces.Drawable;

import java.awt.Color;
import java.awt.Rectangle;

public abstract class GameObject implements Drawable {
    public int xPos;
    public int yPos;
    public Color color;
    public boolean isColliding;

    public GameObject(int xPosition, int yPosition, Color color) {
        this.xPos = xPosition;
        this.yPos = yPosition;
        this.color = color;
    }

    public abstract Rectangle getBounds();

    public int getXPosition() {
        return xPos;
    }

    public int getYPosition() {
        return yPos;
    }

    public Color getColor() {
        return color;
    }

    public void setYPosition(int yPosition) {
        this.yPos = yPosition;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isColliding(GameObject other) {
        isColliding = other.getBounds().intersects(this.getBounds());
        return isColliding;
    }
}