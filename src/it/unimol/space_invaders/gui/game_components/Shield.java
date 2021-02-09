package it.unimol.space_invaders.gui.game_components;

import it.unimol.space_invaders.gui.game_objects.GameObject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Shield extends GameObject {
    int width = 90;
    int height = 10;

    public Shield(int xPosition, int yPosition, Color color) {
        super(xPosition, yPosition, color);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(this.getXPosition(), this.getYPosition(), this.width, this.height);
    }

    @Override
    public Rectangle getBounds() {
        Rectangle shieldHitbox = new Rectangle(this.getXPosition(), this.getYPosition(), this.width, this.height);
        return shieldHitbox;
    }
}