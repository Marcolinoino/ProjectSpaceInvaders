package it.unimol.space_invaders.gui.panels;

import it.unimol.space_invaders.gui.frame.GameFrame;
import it.unimol.space_invaders.gui.game_components.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class CommandPanel extends JPanel {
    private static CommandPanel instance = new CommandPanel();

    private JButton back;
    private BufferedImage image;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }

    private CommandPanel() {
        initPanel();
    }

    public void initBotton() {
        back = new JButton("BACK");
        back.setBounds(325, 600, 150, 50);
        back.setBackground(Color.WHITE);
        this.add(back);
    }

    public void commandGame() {
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameFrame.getInstance().getContentPane().remove(CommandPanel.getInstance());
                GameFrame.getInstance().getContentPane().add(StartPanel.getInstance());
                GameFrame.getInstance().validate();
                GameFrame.getInstance().repaint();
            }
        });
    }

    public void initPanel() {
        this.setLayout(null);
        image = (ImageLoader.loadImage("background2.png"));

        this.initBotton();
        this.add(back);
        commandGame();
    }

    public static CommandPanel getInstance() {
        return instance;
    }
}