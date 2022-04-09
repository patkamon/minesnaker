import java.util.Random;

public class Board {

    private Cell [][] cells;
    private int size;
    private Random random = new Random();

    public Board(int size) {
        this.size = size;
        initCells();

    }




    private void initCells(){
        cells = new Cell[size][size];
        for(int row = 0 ; row < size; row++){
            for( int col = 0; col < size ; col ++){
                cells[row][col] = new Cell();
            }
        }

    }






    public Cell getCell(int row,int col){
        if(row <0 || col<0 || row >= size || col >= size){
            return null;
        }
        return cells[row][col];
    }



}
