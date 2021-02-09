package it.unimol.space_invaders.gui.game_objects;

import it.unimol.space_invaders.controller.KeyboardController;
import it.unimol.space_invaders.interfaces.Moveable;

public abstract class ControlledGameObject extends GameObject implements Moveable {
    public KeyboardController control;

    public ControlledGameObject(int xPosition, int yPosition, KeyboardController control) {
        super(xPosition, yPosition, null);
        this.control = control;
    }

    public ControlledGameObject(int xPosition, int yPosition) {
        super(xPosition, yPosition, null);
    }
}