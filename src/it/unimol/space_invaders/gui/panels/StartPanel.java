package it.unimol.space_invaders.gui.panels;

import it.unimol.space_invaders.gui.frame.GameFrame;
import it.unimol.space_invaders.gui.game_components.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class StartPanel extends JPanel {
    private static StartPanel instance = new StartPanel();

    private JButton play, command;
    private BufferedImage image;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }

    private StartPanel() {
        initPanel();
    }

    public void initBotton() {
        play = new JButton("START");
        play.setBounds(325, 400, 150, 50);
        command = new JButton("COMMAND");
        command.setBounds(325, 500, 150, 50);
        this.add(play);
        this.add(command);
    }

    public void playGame() {
        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameFrame.getInstance().getContentPane().remove(StartPanel.getInstance());
                GameFrame.getInstance().getContentPane().add(GamePanel.getInstance());
                GamePanel.getInstance().start();
                GameFrame.getInstance().validate();
                GameFrame.getInstance().repaint();
            }
        });
        command.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameFrame.getInstance().getContentPane().remove(StartPanel.getInstance());
                GameFrame.getInstance().getContentPane().add(CommandPanel.getInstance());
                GameFrame.getInstance().validate();
                GameFrame.getInstance().repaint();
            }
        });
    }

    public void initPanel() {
        this.setLayout(null);

        image = (ImageLoader.loadImage("background1.png"));

        this.initBotton();
        this.add(play);
        this.add(command);
        playGame();
    }

    public static StartPanel getInstance() {
        return instance;
    }
}