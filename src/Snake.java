import java.util.ArrayList;
import java.util.List;

public class Snake {


    private int size;

    private int dx = 1;
    private int dy = 0;

    List<int[]> outerL = new ArrayList<int[]>();


    public Snake(){
        this.size=3;
        this.outerL.add(new int[]{4, 5});
        this.outerL.add(new int[]{4, 6});
        this.outerL.add(new int[]{4, 7});
    }

    public int getSize() {
        return size;
    }

    public void move(){

       int gx = outerL.get(0)[0] + this.dx;
       int gy = outerL.get(0)[1] + this.dy;



        outerL.remove(outerL.size()-1);
        outerL.add(0,new int[]{gx, gy});

    }




    public void left(){
        this.dx = 1;
        this.dy = 0;
    }
    public void up(){
        this.dx = 0;
        this.dy = -1;
    }
    public void down(){
        this.dx = 0;
        this.dy = 1;
    }
    public void right(){
        this.dx = -1;
        this.dy = 0;
    }



    public void addTail() {
        this.size +=1;
        int last = (outerL.size() -1) ;
        outerL.add(outerL.get(last));
    }

}
