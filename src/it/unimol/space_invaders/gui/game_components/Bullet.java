package it.unimol.space_invaders.gui.game_components;

import it.unimol.space_invaders.gui.game_objects.MovingGameObject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Bullet extends MovingGameObject {

    private static boolean newBulletCanFire = true;
    private static boolean newBeamCanFire = true;

    public Bullet(int xPosition, int yPosition, Color color) {
        super(xPosition, yPosition, 0, 0, color);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(this.getXPosition(), this.getYPosition(), 7, 15);
    }

    @Override
    public Rectangle getBounds() {
        Rectangle bulletHitbox = new Rectangle(xPos, yPos, 7, 15);
        return bulletHitbox;
    }

    public static boolean isNewBulletCanFire() {
        return newBulletCanFire;
    }

    public static void setNewBulletCanFire(boolean newBulletCanFire) {
        Bullet.newBulletCanFire = newBulletCanFire;
    }

    public static boolean isNewBeamCanFire() {
        return newBeamCanFire;
    }

    public static void setNewBeamCanFire(boolean newBeamCanFire) {
        Bullet.newBeamCanFire = newBeamCanFire;
    }
}