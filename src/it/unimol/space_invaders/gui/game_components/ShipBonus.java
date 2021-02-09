package it.unimol.space_invaders.gui.game_components;

import it.unimol.space_invaders.gui.game_objects.ControlledGameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ShipBonus extends ControlledGameObject {
    private static final int Y = 30;
    private static int x = -50;
    private static int score = 5000;
    private static boolean active = false;

    BufferedImage[] brImage = new BufferedImage[1];

    public ShipBonus() {
        super(x, Y);
        brImage[0] = (ImageLoader.loadImage("bonusEnemy.png"));
    }

    @Override
    public void draw(Graphics g) {
        if (x < 800) {
            g.drawImage(brImage[0], x, Y, null);
        } else {
            active = false;
        }
    }

    @Override
    public Rectangle getBounds() {
        Rectangle shipBonusHitbox = new Rectangle(x, Y, 50, 50);
        return shipBonusHitbox;
    }

    @Override
    public void move() {
        if (x < 800) {
            x += 2;
        }
    }

    public static boolean isActive() {
        return active;
    }

    public static void setActive(boolean active) {
        ShipBonus.active = active;
    }

    public static void resetX() {
        x = -50;
    }

    public static int getScore() {
        return score;
    }
}