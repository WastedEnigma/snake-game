import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class SnakeGame extends Applet implements Runnable, KeyListener {
    final static int SCREEN_WIDTH = 720;
    final static int SCREEN_HEIGHT = 680;
    private Graphics graphics;
    private Image image;
    private Snake snake;
    private ArrayList<Item> items;
    private boolean gameStart, gameOver, paused;
    private int y;
    private int maxScore;

    public void init() {
        this.resize(SCREEN_WIDTH, SCREEN_HEIGHT);
        gameOver = false;
        image = createImage(SCREEN_WIDTH, SCREEN_HEIGHT);
        graphics = image.getGraphics();
        this.addKeyListener(this);
        snake = new Snake(15, 10, 200, 200);
        items = new ArrayList<>();
        this.addItems(1);
        Thread thread = new Thread(this);
        thread.start();
    }

    private void addItems(int itemsCount) {
        for (int i = 0; i < itemsCount; i++)
            items.add(new Item(snake, 17));
    }

    public void paint(Graphics g) {
        graphics.setColor(Color.decode("#258290"));
        graphics.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        this.drawGame();
        g.drawImage(image, 0, 0, null);
    }

    private void drawGame() {
        if (!gameStart)
            this.displayStartScreen();
        else if (!gameOver) {
            snake.draw(graphics);
            for (Item item : items)
                item.draw(graphics);
            if (paused)
                this.drawPauseText();
        } else
            this.displayGameOverScreen();
    }

    private void displayStartScreen() {
        y = 300;

        graphics.setColor(Color.decode("#2b2727"));
        graphics.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        graphics.setColor(Color.green);
        graphics.setFont(new Font("Monospace", Font.BOLD, 40));
        drawCenteredString(graphics, "~SNAKE GAME~", y);

        graphics.setFont(new Font("Monospace", Font.PLAIN, 20));
        graphics.setColor(Color.white);
        drawCenteredString(graphics, "--Press ENTER to start--", y + 30);
    }

    private void displayGameOverScreen() {
        y = 300;

        graphics.setColor(Color.decode("#2b2727"));
        graphics.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        graphics.setColor(Color.red);
        graphics.setFont(new Font("Monospace", Font.BOLD, 40));
        drawCenteredString(graphics, "~Game Over~", y);

        graphics.setFont(new Font("Monospace", Font.PLAIN, 20));

        graphics.setColor(Color.green);
        drawCenteredString(graphics, "Score: " + Item.getScore(), y + 30);

        graphics.setColor(Color.green);
        drawCenteredString(graphics, "Highest Score: " + maxScore, y + 60);

        graphics.setColor(Color.white);
        drawCenteredString(graphics, "To restart the game, press R", y + 90);

        graphics.setColor(Color.white);
        drawCenteredString(graphics, "To return at the start menu, press ESC", y + 120);
    }

    private void drawPauseText() {
        graphics.setColor(Color.white);
        graphics.setFont(new Font("Monospace", Font.PLAIN, 20));
        drawCenteredString(graphics, "Pause", 150);
    }

    private void drawCenteredString(Graphics graphics, String text, int textPosY) {
        Dimension d = getSize();
        FontMetrics fm = graphics.getFontMetrics();
        int textPosX = d.width / 2 - fm.stringWidth(text) / 2;
        graphics.drawString(text, textPosX, textPosY);
    }

    public void update(Graphics g) {
        paint(g);
    }

    private void restartGame() {
        this.init();
    }

    public void run() {
        while(!gameOver) {
            snake.move();
            this.checkGameOver();

            for (Item item : items)
                item.collidedWithSnake();

            this.repaint();

            if (Item.getScore() > maxScore)
                maxScore = Item.getScore();

            try {
                Thread.sleep(35);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void checkGameOver() {
        if (snake.getHeadX() < 0 || snake.getHeadX() > SCREEN_WIDTH - snake.size)
            gameOver = true;

        if (snake.getHeadY() < 0 || snake.getHeadY() > SCREEN_HEIGHT - snake.size)
            gameOver = true;

        if (snake.collided())
            gameOver = true;
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP ||
                e.getKeyCode() == KeyEvent.VK_LEFT ||
                e.getKeyCode() == KeyEvent.VK_DOWN ||
                e.getKeyCode() == KeyEvent.VK_RIGHT) {

            snake.setIsMoving(true);
            paused = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            if (snake.getDirY() != 1) {
                snake.setDirY(-1);
                snake.setDirX(0);
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (snake.getDirX() != 1) {
                snake.setDirX(-1);
                snake.setDirY(0);
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (snake.getDirY() != -1) {
                snake.setDirY(1);
                snake.setDirX(0);
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (snake.getDirX() != -1) {
                snake.setDirX(1);
                snake.setDirY(0);
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!gameOver && !gameStart) {
                this.drawGame();
                gameStart = true;
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_P) {
            if (gameStart && !gameOver) {
                snake.setIsMoving(false);
                paused = true;
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (gameOver) {
                Item.resetScore();
                this.restartGame();
                gameStart = false;
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_R) {
            if (gameOver) {
                Item.resetScore();
                this.restartGame();
            }
        }
    }

    public void keyReleased(KeyEvent e) {

    }

    public void keyTyped(KeyEvent e) {

    }
}
