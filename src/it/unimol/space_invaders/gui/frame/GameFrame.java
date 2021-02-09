package it.unimol.space_invaders.gui.frame;

import it.unimol.space_invaders.gui.panels.StartPanel;

import javax.swing.JFrame;

public class GameFrame extends JFrame {
    private static GameFrame instance = new GameFrame();

    private GameFrame() {
        super("Space Invaders");
        initFrame();
        startPage();
    }

    public void initFrame() {
        this.setSize(810, 830);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    public void startPage() {
        this.getContentPane().add(StartPanel.getInstance());
        this.repaint();
    }

    public static GameFrame getInstance() {
        return instance;
    }
}