import java.util.Random;

public class Fruit {

    private int x;
    private int y;

    private Random random = new Random();


    public Fruit(int size){
        random(size);
    }

    public void random(int size){
        this.x = random.nextInt(size);
        this.y = random.nextInt(size);
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
