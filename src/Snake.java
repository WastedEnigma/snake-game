import java.util.*;
import java.awt.*;

public class Snake {
    private ArrayList<Point> snakeBody;
    int size;
    private int dirX, dirY;
    private boolean isMoving, elongate;

    public Snake(int size, int length, int startX, int startY) {
        this.size = size;
        snakeBody = new ArrayList<>();
        dirX = 0;
        dirY = 0;
        isMoving = false;
        elongate = false;
        snakeBody.add(new Point(startX, startY));

        for (int i = 1; i < length; i++)
            snakeBody.add(new Point(startX - i * this.size, startY));
    }

    public void draw(Graphics g) {
        for (int i = 0; i < snakeBody.size(); i++) {
            if (i % 2 == 0)
                g.setColor(Color.decode("#895139"));
            else
                g.setColor(Color.decode("#d8ab54"));

            g.fillRect(snakeBody.get(i).getX(), snakeBody.get(i).getY(), size, size);
        }
    }

    public boolean collided() {
        int x = this.getHeadX();
        int y = this.getHeadY();

        for (int i = 1; i < snakeBody.size(); i++) {
            if (snakeBody.get(i).getX() == x && snakeBody.get(i).getY() == y)
                return true;
        }

        return false;
    }

    public void setIsMoving(boolean isMoving) {
        this.isMoving = isMoving;
    }

    public void setElongate(boolean elongate) {
        this.elongate = elongate;
    }

    public void move() {
        if (isMoving) {
            Point temp = snakeBody.get(0);
            Point last = snakeBody.get(snakeBody.size() - 1);
            Point newStart = new Point(temp.getX() + dirX * size, temp.getY() + dirY * size);

            for (int i = snakeBody.size() - 1; i > 0; i--)
                snakeBody.set(i, snakeBody.get(i - 1));

            snakeBody.set(0, newStart);

            if (elongate) {
                snakeBody.add(last);
                elongate = false;
            }
        }
    }

    public void setDirX(int dirX) {
        this.dirX = dirX;
    }

    public void setDirY(int dirY) {
        this.dirY = dirY;
    }

    public int getDirX() {
        return dirX;
    }

    public int getDirY() {
        return dirY;
    }

    // get the position of the snake's head
    public int getHeadX() {
        return snakeBody.get(0).getX();
    }

    public int getHeadY() {
        return snakeBody.get(0).getY();
    }
}
