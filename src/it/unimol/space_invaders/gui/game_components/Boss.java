package it.unimol.space_invaders.gui.game_components;

import it.unimol.space_invaders.gui.game_objects.MovingGameObject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Boss extends MovingGameObject {

    private static int bossLife = 30;
    private int width;
    private int height;
    private static boolean active = false;

    BufferedImage alienBoss1 = (ImageLoader.loadImage("boss1.png"));
    BufferedImage alienBoss2 = (ImageLoader.loadImage("boss2.png"));
    BufferedImage alienBoss3 = (ImageLoader.loadImage("boss3.png"));

    public Boss(int xPosition, int yPosition, int xVelocity, int yVelocity, int enemyType, Color color, int width, int height) {
        super(xPosition, yPosition, xVelocity, yVelocity, color);
        this.width = width;
        this.height = height;
    }

    @Override
    public Rectangle getBounds() {
        Rectangle enemyBoss = new Rectangle(this.getXPosition(), this.getYPosition(), width, height);
        return enemyBoss;
    }

    @Override
    public void draw(Graphics g) {
        if (active) {
            if (bossLife > 20) {
                g.drawImage(alienBoss1, this.getXPosition(), this.getYPosition(), null);
            } else if (bossLife > 10) {
                g.drawImage(alienBoss2, this.getXPosition(), this.getYPosition(), null);
            } else if (bossLife > 0) {
                g.drawImage(alienBoss3, this.getXPosition(), this.getYPosition(), null);
            }
        }
    }

    @Override
    public void move() {
        xPos += xVel;
    }

    public static void setActive(boolean active) {
        Boss.active = active;
    }

    public static int getBossLife() {
        return bossLife;
    }

    public static void looseLife() {
        Boss.bossLife--;
    }

    public static void resetLifeBoss() {
        Boss.bossLife = 30;
    }
}