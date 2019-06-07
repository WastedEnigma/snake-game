import java.awt.*;

public class Item {
    private int x, y;
    private static int score;
    private Snake snake;
    private int size;

    public Item(Snake snake, int size) {
        this.snake = snake;
        this.size = size;
        x = (int) (Math.random() * (SnakeGame.SCREEN_WIDTH - size));
        y = (int) (Math.random() * (SnakeGame.SCREEN_HEIGHT - size));
    }

    public void updatePosition() {
        x = (int) (Math.random() * (SnakeGame.SCREEN_WIDTH - size));
        y = (int) (Math.random() * (SnakeGame.SCREEN_HEIGHT - size));
    }

    public static void resetScore() {
        score = 0;
    }

    public static int getScore() {
        return score;
    }

    public void draw(Graphics g) {
        g.setColor(Color.green);
        g.fillRect(x, y, size, size);
    }

    public void collidedWithSnake() {
        int snakeX = snake.getHeadX() + snake.size / 2;
        int snakeY = snake.getHeadY() + snake.size / 2;

        if (snakeX >= x - 1 && snakeX <= x + size + 1) {
            if (snakeY >= y - 1 && snakeY <= y + size + 1) {
                score++;
                snake.setElongate(true);
                updatePosition();
            }
        }
    }
}
