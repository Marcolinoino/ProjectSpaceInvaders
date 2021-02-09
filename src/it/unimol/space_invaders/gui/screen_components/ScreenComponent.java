package it.unimol.space_invaders.gui.screen_components;

import it.unimol.space_invaders.gui.game_components.ImageLoader;
import it.unimol.space_invaders.gui.game_components.Level;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import static java.lang.Integer.parseInt;

public class ScreenComponent {
    private static int score = 0;
    private static int highScore;
    private static int numberLife = 3;

    private static String message = "Benvenuto in Space Invaders. \n" +
            "Lo scopo del gioco e' distruggere gli invasori alieni e schivare i loro proiettili proteggendoti con gli scudi.\n" +
            "Ogni tre livelli che supererai dovrei combattere con un boss." +
            "\nAppariranno casualmente navicelle bonus, colpiscile per guadagnare punti extra.\n\n" +
            "Premi R per ripristinare l'highscore.";

    BufferedImage[] brImage = new BufferedImage[1];

    public ScreenComponent() {
        readFileHighScore();

        brImage[0] = (ImageLoader.loadImage("livesPlayer.png"));
    }

    private void readFileHighScore() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("Highscore.txt"));
            highScore = parseInt(br.readLine());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeFileHighScore() {
        try {
            FileWriter f = new FileWriter("Highscore.txt");
            f.write("" + highScore);
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getMessage() {
        return message;
    }

    public void updateHighScore() {
        if (score > highScore) {
            highScore = score;
            writeFileHighScore();
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 260, 20);

        g.setColor(Color.WHITE);
        g.drawString("Lives:", 11, 20);
        for (int index = 0; index < numberLife; index++) {
            g.drawImage(brImage[0], 48 + (index * 20), 10, null);
        }
        g.setColor(Color.WHITE);
        g.drawString("Level " + Level.getNumberLevel(), 750, 20);

        g.setColor(Color.WHITE);
        g.drawString("Highscore: " + highScore, 440, 20);
    }

    public void resetHighScore() {
        highScore = 0;
        writeFileHighScore();
    }

    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        ScreenComponent.score += score;
    }

    public static void resetScore() {
        ScreenComponent.score = 0;
    }

    public static void subNumberLife() {
        numberLife--;
    }

    public static int getNumberLife() {
        return numberLife;
    }

    public static void setNumberLife(int numberLife) {
        ScreenComponent.numberLife = numberLife;
    }
}