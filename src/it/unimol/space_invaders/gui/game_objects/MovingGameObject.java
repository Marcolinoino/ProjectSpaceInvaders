package it.unimol.space_invaders.gui.game_objects;

import it.unimol.space_invaders.interfaces.Moveable;

import java.awt.Color;

public abstract class MovingGameObject extends GameObject implements Moveable {
    public int xVel;
    public int yVel;

    public MovingGameObject(int xPosition, int yPosition, int xVelocity, int yVelocity, Color color) {
        super(xPosition, yPosition, color);
        this.xVel = xVelocity;
        this.yVel = yVelocity;
    }

    public int getXVelocity() {
        return xVel;
    }

    public void setXVelocity(int xVelocity) {
        this.xVel = xVelocity;
    }

    @Override
    public void move() {
        this.xPos += xVel;
        this.yPos += yVel;
    }
}