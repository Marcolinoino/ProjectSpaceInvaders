package it.unimol.space_invaders.gui.game_components;

import it.unimol.space_invaders.gui.game_objects.MovingGameObject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Enemy extends MovingGameObject {
    BufferedImage alien1 = (ImageLoader.loadImage("alien1.png"));
    BufferedImage alien2 = (ImageLoader.loadImage("alien2.png"));
    BufferedImage alien3 = (ImageLoader.loadImage("alien3.png"));

    private int enemyType, width, height;

    public Enemy(int xPosition, int yPosition, int xVelocity, int yVelocity, int enemyType, Color color, int width, int height) {
        super(xPosition, yPosition, xVelocity, yVelocity, color);
        this.enemyType = enemyType;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Graphics g) {
        if (this.enemyType % 3 == 0) {
            g.drawImage(alien1, this.getXPosition(), this.getYPosition(), null);
        } else if (this.enemyType % 3 == 1 && this.enemyType != 100) {
            g.drawImage(alien2, this.getXPosition(), this.getYPosition(), null);
        } else if (this.enemyType % 3 == 2) {
            g.drawImage(alien3, this.getXPosition(), this.getYPosition(), null);
        }
    }

    @Override
    public Rectangle getBounds() {
        Rectangle enemyHitBox = new Rectangle(this.getXPosition(), this.getYPosition(), width, height);
        return enemyHitBox;
    }

    @Override
    public void move() {
        xPos += xVel;
    }
}