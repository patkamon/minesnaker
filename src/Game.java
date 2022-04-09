
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JFrame {


    private int boardSize = 10;
    private GridUI gridUI;

    private int score;
    private Snake player;
    private Fruit fruit;

    private boolean isStart;
    private boolean notOver;

    private Thread thread;
    private long delayed = 1000/3;


    public Game(){
        setup();
        gridUI = new GridUI();
        add(gridUI);
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);



        Game.this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyChar() == 'w' ||  e.getKeyCode() == 38){
                    isStart = true;
                    player.up();
                    repaint();
                }
                if(e.getKeyChar() == 'd'|| e.getKeyCode() == 39){
                    isStart = true;
                    player.left();
                    repaint();
                }
                if(e.getKeyChar() == 's'|| e.getKeyCode() == 40){
                    player.down();
                    isStart = true;
                    repaint();
                }
                if(e.getKeyChar() == 'a'|| e.getKeyCode() == 37){
                    isStart = true;
                    player.right();
                    repaint();
                }
                if(e.getKeyCode() == 82 && e.getKeyChar() == 'r'){
                    setup();
                    repaint();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

    }

    public void setup(){
        // setup for starting game
        player = new Snake();
        fruit = new Fruit(boardSize);
        score = 0;
        isStart = false;
        notOver = true;
    }

    public void start(){
        setVisible(true);
        thread = new Thread(){
            @Override
            public void run(){
                while(notOver){
                    if (isStart){
                        player.move();
                    }
                    repaint();
                    waitFor(delayed);
                }

                if (!notOver){
                    JOptionPane.showMessageDialog(Game.this, "GameOver Score: "+score
                            , "You lose", JOptionPane.WARNING_MESSAGE);
                }
            }
        };
        thread.start();


    }

    private void waitFor(long delayed){
        try{
            Thread.sleep(delayed);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }


    class GridUI extends JPanel {
        public static final int CELL_PIXEL_SIZE = 30;

        private Image imageCell;
        private Image imageFlag;
        private Image imageMine;
        private Image imageMine2;


        public GridUI() {
            setPreferredSize(new Dimension(boardSize * CELL_PIXEL_SIZE, boardSize * CELL_PIXEL_SIZE));
            imageCell = new ImageIcon("imgs/Cell.png").getImage();
            imageFlag = new ImageIcon("imgs/Flag.png").getImage();
            imageMine = new ImageIcon("imgs/Mine.png").getImage();
            imageMine2 = new ImageIcon("imgs/Mine2.png").getImage();
        }

        public void paintPlayer(Graphics g){
            int size = player.getSize();

            int hx = player.outerL.get(0)[0];
            int hy = player.outerL.get(0)[1];


            for(int i =0 ; i<size;i++){
                int x = player.outerL.get(i)[0];
                int y = player.outerL.get(i)[1];
                // check collapse fruit
                consumeFruit(x,y);
                // check collapse self
                if (i!=0) {
                    gameOver(hx, hy, x, y);
                    x *= CELL_PIXEL_SIZE;
                    y *= CELL_PIXEL_SIZE;
                    g.drawImage(imageMine, x, y, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE, null, null);
                }else{
                    x *= CELL_PIXEL_SIZE;
                    y *= CELL_PIXEL_SIZE;
                    g.drawImage(imageMine2, x, y, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE, null, null);
                }
            }

        }

        public void consumeFruit(int x,int y){
            if (checkCollapse(x,y,fruit.getX(),fruit.getY())){
                fruit.random(boardSize);
                player.addTail();
                score+=1;
            }
        }

        public void gameOver(int x1, int y1, int x2, int y2){
            if (checkCollapse(x1,y1,x2,y2) || x1 >= boardSize || y1 >= boardSize || x1 < 0 || y1 < 0){
                notOver = false;
            }
        }

        public boolean checkCollapse(int x,int y, int x2, int y2){
            return (x== x2 && y2 == y);
        }


        public void paintFruit(Graphics g){
            int x = fruit.getX() * CELL_PIXEL_SIZE;
            int y =  fruit.getY()  * CELL_PIXEL_SIZE;

            g.drawImage(imageFlag, x, y, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE, null, null);

        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);

            for (int row = 0; row < boardSize; row++) {
                for (int col = 0; col < boardSize; col++) {
                    paintCell(g, row, col);
                }
            }

            g.drawString("Score: " + score, 10, 10);
            paintPlayer(g);
            paintFruit(g);
        }

        private void paintCell(Graphics g, int row, int col) {
            int x = col * CELL_PIXEL_SIZE;
            int y = row * CELL_PIXEL_SIZE;

            g.drawImage(imageCell, x, y, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE, null, null);

        }

    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }


}
