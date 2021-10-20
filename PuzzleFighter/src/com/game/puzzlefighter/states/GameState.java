package com.game.puzzlefighter.states;

import com.game.gfx.AssetManager;
import com.game.puzzlefighter.Game;
import com.game.puzzlefighter.PuzzleFighter;
import com.game.puzzlefighter.board.Board;
import com.game.puzzlefighter.board.BoardObserver;
import com.game.puzzlefighter.board.Gem;
import com.game.puzzlefighter.characters.Character;
import com.game.puzzlefighter.characters.Ken;
import com.game.puzzlefighter.characters.Ryu;
import com.game.puzzlefighter.ui.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 * Game state
 */
public class GameState extends State implements BoardObserver {

    // state
    public static final int PLAY_STATE = 1;
    public static final int END_STATE = 2;

    private final BufferedImage background;
    private final BufferedImage logo;
    private final Board board;
    private final Board board2;

    private final NextUI next1;
    private final NextUI next2;
    private final ScoreUI score1;
    private final ScoreUI score2;
    private final Character player1;
    private final Character player2;
    private final HSlideImage readyImage;
    private final HSlideImage fightImage;
    private final VSlideAnimation winAnimation;
    private final VSlideAnimation loseAnimation;
    private final KOAnimation koAnimation;
    private int state = PLAY_STATE;
    private boolean gameStarted;
    private boolean gameEnded;
    private float delayTimer;


    /**
     * Constructor
     *
     * @param game game
     */
    public GameState(PuzzleFighter game) {
        super(game);

        // background and logo
        background = AssetManager.getImage("stage");
        logo = AssetManager.getImage("logo");

        // board 1
        board = new Board(this, Gem.WIDTH, 0);
        board.setControls(KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_SPACE);

        // board 2
        board2 = new Board(this, Game.getGameWidth() - (Gem.WIDTH * Board.COLS) - Gem.WIDTH, 0);
        board2.setControls(KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_ENTER);

        // next gem ui
        next1 = new NextUI(board.getFallingPair(), "next1", board2.getX() - 400, 10);
        next2 = new NextUI(board2.getFallingPair(), "next2", board2.getX() - 100, 10);

        // score ui
        score1 = new ScoreUI("score1", "pink", board2.getX() - 410, Game.getGameHeight() - 100);
        score2 = new ScoreUI("score2", "purple", board2.getX() - 190, Game.getGameHeight() - 100);

        // players
        player1 = new Ryu(board2.getX() - 410, Game.getGameHeight() - 300);
        player2 = new Ken(board2.getX() - 190, Game.getGameHeight() - 300);

        // ui image
        readyImage = new HSlideImage(100, Game.getGameHeight() / 2, 15, 1.5f, 2, "ready");
        fightImage = new HSlideImage(1300, Game.getGameHeight() / 2 + readyImage.getHeight() + 5, 15, 1, 2, "fight");

        // text animations
        winAnimation = new VSlideAnimation(0, 200, 10, 1, 1.5f, "win");
        loseAnimation = new VSlideAnimation(0, 200, 10, 1, 1.5f, "lose");
        koAnimation = new KOAnimation(Game.getGameWidth() / 2, Game.getGameHeight() / 2, 1.5f);

        Game.soundPlayer.stop();
        Game.soundPlayer.playSoundEffect("versus");
        Game.soundPlayer.playSoundEffect2("ready");
    }

    @Override
    public void update(float delta) {
        if (gameEnded)
            return;

        if (gameStarted) {

            switch (state) {
                case PLAY_STATE:
                    // update board
                    board.update(delta);
                    board2.update(delta);

                    // update next ui
                    next1.update();
                    next2.update();

                    // update scores
                    score1.updateScore(board.getScore());
                    score2.updateScore(board2.getScore());
                    break;
                case END_STATE:

                    // end state animations
                    winAnimation.update(delta);
                    loseAnimation.update(delta);
                    koAnimation.update(delta);

                    if (winAnimation.isSlideDone() && loseAnimation.isSlideDone() && koAnimation.isFinished()) {
                        delayTimer += delta;
                        float afterGameEndDelay = 2f;
                        if (delayTimer >= afterGameEndDelay) {
                            game.transitionToMenu();
                            gameEnded = true;
                            return;
                        }
                    }

                    break;
            }

        } else {
            // start ui animations
            readyImage.update(delta);
            fightImage.update(delta);

            if (readyImage.isAtCenter())
                Game.soundPlayer.playSoundEffect2("fight");

            if (fightImage.isSlideDone()) {
                gameStarted = true;
                Game.soundPlayer.playBackGroundMusic("stageBGM");
            }
        }

        // update players
        player1.update(delta);
        player2.update(delta);
    }

    @Override
    public void render(Graphics2D g2) {
        // background and logo
        g2.drawImage(background, 0, 0, Game.getGameWidth(), Game.getGameHeight(), null);
        g2.drawImage(logo, board2.getX() - 310, 0, logo.getWidth() * 3, logo.getHeight() * 3, null);

        // board
        board.render(g2);
        board2.render(g2);

        // next ui
        next1.render(g2);
        next2.render(g2);

        // score ui
        score1.render(g2);
        score2.render(g2);

        // players
        player1.render(g2);
        player2.render(g2);

        // start game ui
        readyImage.render(g2);
        fightImage.render(g2);

        // end game ui
        koAnimation.render(g2);
        winAnimation.render(g2);
        loseAnimation.render(g2);
    }

    @Override
    public void notifyGemsDestroyed(Board board, int numGems) {
        // produce counter gems at opponents board
        Board opponentBoard = this.board == board ? board2 : this.board;
        opponentBoard.spawnCounterGems(numGems);

        // update characters
        if (board == this.board) {
            player1.advantage();
            player2.disAdvantage();
        } else {
            player1.disAdvantage();
            player2.advantage();
        }
    }

    @Override
    public void notifyGameFinished(Board board) {
        // finish the opponent board too
        Board opponentBoard = this.board == board ? board2 : this.board;
        opponentBoard.finished();

        // update player and set up text animation
        if (board == this.board) {
            player1.lose();
            player2.win();

            winAnimation.setX(Game.getGameWidth() - 220);
            loseAnimation.setX(100);

        } else {
            player1.win();
            player2.lose();

            winAnimation.setX(100);
            loseAnimation.setX(Game.getGameWidth() - 220);
        }

        // start end state animation
        state = END_STATE;
        koAnimation.start();

        Game.soundPlayer.playSoundEffect("ko");
        Game.soundPlayer.playSoundEffect2("notify");
    }

}
