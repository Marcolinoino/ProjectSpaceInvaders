package it.unimol.space_invaders.gui.game_components;

public class Level {
    private static int numberLevel = 1;
    private static boolean bossLevel = false;

    public Level() {
    }

    public static void setupBossLevel() {
        if (numberLevel != 3 && numberLevel != 6 && numberLevel != 9 && numberLevel != 12) {
            Level.bossLevel = false;
        }

        if (numberLevel == 3 || numberLevel == 6 || numberLevel == 9 || numberLevel == 12) {
            Level.bossLevel = true;
        }
    }

    public static int getNumberLevel() {
        return numberLevel;
    }

    public static boolean isBossLevel() {
        return bossLevel;
    }

    public static void setNumberLevel(int numberLevel) {
        Level.numberLevel = numberLevel;
    }

    public static void setBossLevel(boolean bossLevel) {
        Level.bossLevel = bossLevel;
    }

    public static void resetGameLevel() {
        Level.numberLevel = 1;
    }
}
