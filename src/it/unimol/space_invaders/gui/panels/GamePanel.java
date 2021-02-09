package it.unimol.space_invaders.gui.panels;

import it.unimol.space_invaders.gui.game_components.*;
import it.unimol.space_invaders.gui.screen_components.ScreenComponent;
import it.unimol.space_invaders.controller.KeyboardController;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class GamePanel extends JPanel {

    ScreenComponent screenComponent = new ScreenComponent();

    private Timer gameTimer;
    private KeyboardController controller;

    private final int framesPerSecond = 120;

    Random r = new Random();
    private int markerX, markerY, explosionMarkerX, explosionMarkerY;
    boolean explosion = false;

    private Player player;
    private ShipBonus bonus;
    private Enemy enemy;
    private Boss boss;
    private Shield shield;
    private Bullet bullet, beam, beam2, beam3;

    private boolean newBonusEnemy = true;
    private boolean hitMarker = false;

    private ArrayList<Enemy> enemyList = new ArrayList();
    private ArrayList<Shield> shieldList = new ArrayList();
    private ArrayList<Bullet> beamList = new ArrayList();
    BufferedImage background = (ImageLoader.loadImage("background.png"));
    BufferedImage enemyDeath = (ImageLoader.loadImage("death.png"));

    private static GamePanel instance = new GamePanel();

    private GamePanel() {
        //Registra KeyboardController come KeyListener
        controller = new KeyboardController();
        this.addKeyListener(controller);

        this.setupGame();
        this.setFocusable(true);
        this.requestFocusInWindow();
        bonus = new ShipBonus();
    }

    public final void setupGame() {

        if (Level.isBossLevel()) {
            boss = new Boss(20, 20, Level.getNumberLevel(), 0, 100, null, 150, 150);
            boss.setActive(true);
        }
        if (!Level.isBossLevel()) {
            for (int row = 0; row < 6; row++) {
                for (int column = 0; column < 5; column++) {
                    enemy = new Enemy((20 + (row * 100)), (20 + (column * 60)), Level.getNumberLevel(), 0, (column + 1) / 2,
                            null, 45, 45); // La velocità del nemico aumenterà ad ogni livello
                    enemyList.add(enemy);
                }
            }
        }
        if (Level.getNumberLevel() == 1) {
            ImageIcon icon = new ImageIcon("images/alien.png");
            JOptionPane.showMessageDialog(null, ScreenComponent.getMessage(), "Space Invaders", JOptionPane.INFORMATION_MESSAGE, icon);
        }

        controller.resetController();

        bonus = new ShipBonus();
        player = new Player(375, 730, controller);

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                shield = new Shield(100 + (column * 250), 650 - (row * 10), Color.RED);
                shieldList.add(shield);
            }
        }
    }

    // PAINT
    @Override
    public void paint(Graphics g) {

        g.drawImage(background, 0, 0, null);
        screenComponent.draw(g);

        // Stringa "+100"
        if (hitMarker && bullet != null) {
            g.setColor(Color.WHITE);
            if (!Level.isBossLevel()) {
                if (explosion) {
                    g.drawImage(enemyDeath, explosionMarkerX, explosionMarkerY, null);
                    new Thread(() -> {
                        try {
                            Thread.sleep(100);
                            explosion = false;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }).start();
                }

                g.drawString("+ 100", markerX + 20, markerY -= 1);

            } else {
                g.drawString("- 1", markerX + 75, markerY += 1);
            }
        }

        //Draw
        player.draw(g);

        for (int index = 0; index < shieldList.size(); index++) {
            shieldList.get(index).draw(g);
        }

        for (int index = 0; index < beamList.size(); index++) {
            beamList.get(index).draw(g);
        }

        try {
            for (int index = 0; index < enemyList.size(); index++) {
                if (!Level.isBossLevel()) {
                    enemyList.get(index).draw(g);
                }
            }

        } catch (IndexOutOfBoundsException e) {
        }

        // Genera proiettili player
        if (controller.getKeyStatus(38)) {
            if (Bullet.isNewBulletCanFire()) {
                bullet = new Bullet(player.getXPosition() + 22, player.getYPosition() - 20, Color.RED);
                Bullet.setNewBulletCanFire(false);
            }
        }
        if (bullet != null) {
            bullet.draw(g);
        }

        // Genera proiettili alieni
        if (!Level.isBossLevel()) {
            if (Bullet.isNewBeamCanFire()) {
                for (int index = 0; index < enemyList.size(); index++) {
                    if (r.nextInt(30) == index) {
                        beam = new Bullet(enemyList.get(index).getXPosition(), enemyList.get(index).getYPosition(), Color.YELLOW);
                        beamList.add(beam);
                    }
                    Bullet.setNewBeamCanFire(false);
                }
            }
        }
        // Genera proiettili alieni boss
        if (Level.isBossLevel()) {
            if (Bullet.isNewBeamCanFire()) {
                int index = 0;
                if (r.nextInt(5) == index) {
                    beam = new Bullet(boss.getXPosition() + 75, boss.getYPosition() + 140, Color.YELLOW);
                    beam2 = new Bullet(boss.getXPosition(), boss.getYPosition() + 110, Color.YELLOW);
                    beam3 = new Bullet(boss.getXPosition() + 150, boss.getYPosition() + 110, Color.YELLOW);
                    beamList.add(beam);
                    beamList.add(beam2);
                    beamList.add(beam3);
                }
                Bullet.setNewBeamCanFire(false);
            }
        }

        //Genera navicella bonus
        if (r.nextInt(3000) == 1500 || ShipBonus.isActive()) {
            ShipBonus.setActive(true);
            bonus.draw(g);
            bonus.move();

            if (bullet != null) {
                if (bonus.isColliding(bullet)) {
                    ShipBonus.setActive(false);
                    ShipBonus.resetX();
                    Bullet.setNewBulletCanFire(true);
                    ScreenComponent.setScore(ShipBonus.getScore());
                }
            }
        }

        //Mostra la vita del boss
        if (Level.isBossLevel()) {
            boss.draw(g);
            g.setColor(Color.WHITE);
            g.drawString("Boss Health: " + boss.getBossLife(), 352, 600);
        }
    }

// UPDATE GAME

    public void updateGameState(int frameNumber) {

        player.move();

        //Resetta Highscore
        if (controller.getKeyStatus(82)) {
            int answer = JOptionPane.showConfirmDialog(null, "Vuoi resettare l'highscore?",
                    "Space Invaders", 0);
            controller.resetController();
            if (answer == 0) {
                screenComponent.resetHighScore();
            }
        }

        screenComponent.updateHighScore();

        //Muove i nemici
        if (!Level.isBossLevel()) {
            if (((enemyList.get(enemyList.size() - 1).getXPosition() + enemyList.get(enemyList.size() - 1).getXVelocity()) > 750 ||
                    (enemyList.get(0).getXPosition() + enemyList.get(0).getXVelocity()) < 0) && !enemyList.isEmpty()) {
                for (int index = 0; index < enemyList.size(); index++) {
                    enemyList.get(index).setXVelocity(enemyList.get(index).getXVelocity() * -1);
                    enemyList.get(index).setYPosition(enemyList.get(index).getYPosition() + 10);
                }
            } else {
                for (int index = 0; index < enemyList.size(); index++) {

                    enemyList.get(index).move();
                }
            }
        }

        if (Level.isBossLevel()) {
            if (boss.getXPosition() + boss.getXVelocity() > 650 || boss.getXPosition() + boss.getXVelocity() < 0) {
                boss.setXVelocity(boss.getXVelocity() * -1);
                boss.setYPosition(boss.getYPosition() + 10);
            } else {
                boss.move();
            }
        }

        //Muove i proiettili
        if (bullet != null) {
            bullet.setYPosition(bullet.getYPosition() - 15);
            if (bullet.getYPosition() < 0) {
                Bullet.setNewBulletCanFire(true);
            }
            //Controlla la collisione con i nemici
            for (int index = 0; index < enemyList.size(); index++) {
                if (bullet.isColliding(enemyList.get(index))) {
                    bullet = new Bullet(0, 0, null);
                    Bullet.setNewBulletCanFire(true);
                    ScreenComponent.setScore(100);
                    hitMarker = true;
                    markerX = enemyList.get(index).getXPosition();
                    explosionMarkerX = markerX;
                    markerY = enemyList.get(index).getYPosition();
                    explosionMarkerY = markerY;
                    explosion = true;
                    enemyList.remove(index);
                }
            }
            //Controlla la collisione con il boss
            if (Level.isBossLevel()) {
                if (bullet.isColliding(boss)) {
                    bullet = new Bullet(0, 0, null);
                    Bullet.setNewBulletCanFire(true);
                    if (Level.isBossLevel()) {
                        hitMarker = true;
                        markerX = boss.getXPosition();
                        markerY = boss.getYPosition() + 165;
                        boss.looseLife();
                        if (boss.getBossLife() == 0) {
                            //Eliminazione Boss.
                            boss.setActive(false);
                            Level.setBossLevel(false);
                            ScreenComponent.setScore(9000);
                        }
                    }
                }
            }
            //Controlla la collisione con lo scudo
            for (int index = 0; index < shieldList.size(); index++) {
                if (bullet.isColliding(shieldList.get(index))) {
                    if (shieldList.get(index).getColor() == Color.RED) {
                        shieldList.get(index).setColor(Color.ORANGE);
                        bullet = new Bullet(0, 0, null);
                        Bullet.setNewBulletCanFire(true);
                    } else if (shieldList.get(index).getColor() == Color.ORANGE) {
                        shieldList.get(index).setColor(Color.YELLOW);
                        bullet = new Bullet(0, 0, null);
                        Bullet.setNewBulletCanFire(true);
                    } else if (shieldList.get(index).getColor() == Color.YELLOW) {
                        shieldList.get(index).setColor(Color.WHITE);
                        bullet = new Bullet(0, 0, null);
                        Bullet.setNewBulletCanFire(true);
                    } else if (shieldList.get(index).getColor() == Color.WHITE) {
                        shieldList.remove(index);
                        bullet = new Bullet(0, 0, null);
                        Bullet.setNewBulletCanFire(true);
                    }
                }
            }
        }

        //Muove i proiettili alieni
        if (!Level.isBossLevel()) {
            if (beam != null) {
                for (int index = 0; index < beamList.size(); index++) {
                    beamList.get(index).setYPosition(beamList.get(index).getYPosition() + (4));
                    if (beamList.get(index).getYPosition() > 800) {
                        beamList.remove(index);
                    }
                }
            }
        }
        //Muove i proiettili del boss
        if (Level.isBossLevel()) {
            if (beam != null) {
                for (int index = 0; index < beamList.size(); index++) {
                    beamList.get(index).setYPosition(beamList.get(index).getYPosition() + (2 * Level.getNumberLevel()));
                    if (beamList.get(index).getYPosition() > 800) {
                        beamList.remove(index);
                    }
                }
            }
        }
        //Controlla la collisione con lo scudo
        try {
            for (int j = 0; j < shieldList.size(); j++) {
                for (int index = 0; index < beamList.size(); index++) {
                    if (beamList.get(index).isColliding(shieldList.get(j))) {
                        if (shieldList.get(j).getColor() == Color.RED) {
                            shieldList.get(j).setColor(Color.ORANGE);
                            beamList.remove(index);
                        } else if (shieldList.get(j).getColor() == Color.ORANGE) {
                            shieldList.get(j).setColor(Color.YELLOW);
                            beamList.remove(index);
                        } else if (shieldList.get(j).getColor() == Color.YELLOW) {
                            shieldList.get(j).setColor(Color.WHITE);
                            beamList.remove(index);
                        } else if (shieldList.get(j).getColor() == Color.WHITE) {
                            shieldList.remove(j);
                            beamList.remove(index);
                        }
                    }
                }
            }
        } catch (IndexOutOfBoundsException e) {
        }

        //Controlla la collisione con il player
        for (int index = 0; index < beamList.size(); index++) {
            if (beamList.get(index).isColliding(player)) {
                beamList.remove(index);
                ScreenComponent.subNumberLife();
            }
        }

        if (beamList.isEmpty()) {
            Bullet.setNewBeamCanFire(true);
        }

        //Collisione nemici con scudi
        for (int input = 0; input < enemyList.size(); input++) {
            for (int j = 0; j < shieldList.size(); j++) {
                if (enemyList.get(input).isColliding(shieldList.get(j))) {
                    shieldList.remove(j);
                }
            }
            if (enemyList.get(input).getYPosition() + 50 >= 750) {
                enemyList.clear();
                shieldList.clear();
                beamList.clear();
                boss.resetLifeBoss();
                ScreenComponent.subNumberLife();
                setupGame();
            }
        }

        if (player.isColliding) {
            ScreenComponent.subNumberLife();

            //Game over
        } else if (ScreenComponent.getNumberLife() == 0) {
            int answer = JOptionPane.showConfirmDialog(null, "Vuoi ricominciare la partita?",
                    "Score: " + ScreenComponent.getScore(), 0);
            if (answer == 0) {
                enemyList.clear();
                shieldList.clear();
                beamList.clear();
                ScreenComponent.resetScore();
                Level.resetGameLevel();
                boss.resetLifeBoss();
                Level.setupBossLevel();
                ScreenComponent.setNumberLife(3);
                Bullet.setNewBulletCanFire(true);
                Bullet.setNewBeamCanFire(true);
                newBonusEnemy = true;
                setupGame();
            }
            if (answer == 1) {
                System.exit(0);
            }
        }

        //Avanti di livello
        if (!Level.isBossLevel()) {
            if (enemyList.isEmpty()) {
                beamList.clear();
                shieldList.clear();
                Level.setNumberLevel(Level.getNumberLevel() + 1);
                Level.setupBossLevel();
                boss.resetLifeBoss();
                setupGame();
            }
        }
    }

    /**
     * Metodo per avviare il timer che guida l'animazione del gioco.
     */

    public void start() {
        // Imposta un nuovo timer da ripetere ogni 20 millisecondi (50 FPS).
        gameTimer = new Timer(1000 / framesPerSecond, new ActionListener() {

            // Tiene traccia del numero di fotogrammi che sono stati prodotti.
            private int frameNumber = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                // Aggiorna lo stato del gioco e ridipinge lo schermo
                updateGameState(frameNumber++);
                repaint();
            }
        });
        Timer gameTimerHitMarker = new Timer(1000, new ActionListener() {

            // Tiene traccia del numero di fotogrammi che sono stati prodotti.
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aggiorna lo stato del gioco e ridipinge lo schermo
                hitMarker = false;
            }
        });

        gameTimer.setRepeats(true);
        gameTimer.start();
        gameTimerHitMarker.setRepeats(true);
        gameTimerHitMarker.start();
    }

    public static GamePanel getInstance() {
        return instance;
    }
}

