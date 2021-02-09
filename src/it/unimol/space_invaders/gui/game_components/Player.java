package it.unimol.space_invaders.gui.game_components;

import it.unimol.space_invaders.controller.KeyboardController;
import it.unimol.space_invaders.gui.game_objects.ControlledGameObject;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Player extends ControlledGameObject {

    BufferedImage player = (ImageLoader.loadImage("ship.png"));

    public Player(int xPosition, int yPosition, KeyboardController control) {
        super(xPosition, yPosition, control);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(player, this.getXPosition(), this.getYPosition(), null);
    }

    @Override
    public Rectangle getBounds() {
        Rectangle shipHitbox = new Rectangle(this.getXPosition(), this.getYPosition(), 50, 50);
        return shipHitbox;
    }

    @Override
    public void move() {
        if (control.getKeyStatus(37)) {
            xPos -= 10;
        }
        if (control.getKeyStatus(39)) {
            xPos += 10;
        }
        if (xPos > 800) {
            xPos = -50;
        }
        if (xPos < -50) {
            xPos = 800;
        }
    }
}