package practice.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by arindam.das on 20/05/16.
 */
public class CustomMaze {

    char[][] maze;
    int n;
    int index;
    int leftBorder, rightBorder, upBorder, downBorder;

    enum direction {left, right, up, down};
    Point[] pointIndex;
    Map<String, Integer> pointMap;

    class Point{
        direction dir;
        int row;
        int col;


        public Point(direction dir, int row, int col){
            this.row = row;
            this.col = col;
            this.dir = dir;
        }

        public String getUniqueCode(){
            return row + "~" + col + "~" + dir;
        }
    }

    private void requestAddPotentialPoint(direction dir, int row, int col){
        boolean flag = false;
        if(dir == direction.left){
            if(col - 2 > leftBorder){
                if( maze[row][col-2]==' ' && maze[row][col-1]==' '){
                    flag = true;
                }
            }
        }else if(dir == direction.right){
            if(col + 2 < rightBorder){
                if( maze[row][col+2]==' ' && maze[row][col+1]==' '){
                    flag = true;
                }
            }
        }else if(dir == direction.up){
            if(row - 2 > upBorder){
                if( maze[row-2][col]==' ' && maze[row-1][col]==' '){
                    flag = true;
                }
            }
        }else{
            if(row + 2 < downBorder){
                if(maze[row+2][col]==' ' && maze[row+1][col]==' '){
                    flag = true;
                }
            }
        }
        if(flag){
            Point point = new Point(dir, row, col);
            pointIndex[index] = point;
            pointMap.put(point.getUniqueCode(), index);
            index++;
        }
    }

    private void requestAddPotentialPoint_(direction dir, int row, int col){
        boolean flag = false;
        if(dir == direction.left){
            if(col - 3 > leftBorder){
                if(maze[row][col-3]==' ' && maze[row][col-2]==' ' && maze[row][col-1]==' '){
                     flag = true;
                }
            }
        }else if(dir == direction.right){
            if(col + 3 < rightBorder){
                if(maze[row][col+3]==' ' && maze[row][col+2]==' ' && maze[row][col+1]==' '){
                    flag = true;
                }
            }
        }else if(dir == direction.up){
            if(row - 3 > upBorder){
                if(maze[row-3][col]==' ' && maze[row-2][col]==' ' && maze[row-1][col]==' '){
                    flag = true;
                }
            }
        }else{
            if(row + 3 < downBorder){
                if(maze[row+3][col]==' ' && maze[row+2][col]==' ' && maze[row+1][col]==' '){
                    flag = true;
                }
            }
        }
        if(flag){
            Point point = new Point(dir, row, col);
            pointIndex[index] = point;
            pointMap.put(point.getUniqueCode(), index);
            index++;
        }
    }

    private void removePotentialPoint(int idx){
//        if(idx > 0 && idx<index){
            Point point = pointIndex[idx];
            pointIndex[idx] = pointIndex[--index];
            pointMap.remove(point.getUniqueCode());
            pointMap.put(pointIndex[idx].getUniqueCode(), idx);
//        }
    }


    public CustomMaze(int n) {
        pointMap = new HashMap<>();
        leftBorder = upBorder = 0;
        rightBorder = downBorder = 2*n-2;
        this.n = n;
        index = 0;
        maze = new char[2 * n - 1][2 * n - 1];
        pointIndex = new Point[n*n-4];
        for (int i = 0; i < 2 * n - 1; i++) {
            for (int j = 0; j < 2 * n - 1; j++) {
                if(i==0 || j==0 || i==downBorder || j==rightBorder){
                    if(i%2==0 && j%2==0){
                        maze[i][j] = 'x';

                        if(i==0 || i==downBorder){
                            if(!(j==0 || j==rightBorder)){
                                Point point = new Point((i==0)?direction.down:direction.up, i, j);
                                pointIndex[index] = point;
                                pointMap.put(point.getUniqueCode(), index);
                                index++;
                            }
                        }else if(j==0 || j==rightBorder){
                            if(!(i==0 || i==downBorder)){
                                Point point = new Point((j==0)?direction.right:direction.left, i, j);
                                pointIndex[index] = point;
                                pointMap.put(point.getUniqueCode(), index);
                                index++;
                            }
                        }

                    }else {
                        maze[i][j] = 'o';
                    }
                }else{
                    maze[i][j] = ' ';
                }

            }

        }
    }

    private void fillIndex(Point p){
//        System.out.println(p.getUniqueCode());
        int r = p.row, c = p.col;
        if(p.dir == direction.down){
            maze[p.row+1][p.col] = 'o';
            maze[p.row+2][p.col] = 'x';
            r+=2;
        }else if(p.dir == direction.up){
            maze[p.row-1][p.col] = 'o';
            maze[p.row-2][p.col] = 'x';
            r-=2;
        }else if(p.dir == direction.left){
            maze[p.row][p.col-1] = 'o';
            maze[p.row][p.col-2] = 'x';
            c-=2;
        }else{
            maze[p.row][p.col+1] = 'o';
            maze[p.row][p.col+2] = 'x';
            c+=2;
        }
        Integer idx;
        idx = pointMap.get((r - 2) + "~" + c + "~" + direction.down);
        if(idx!=null){
            removePotentialPoint(idx);
        }else {
            requestAddPotentialPoint(direction.up, r,c);
        }
        idx = pointMap.get((r+2)  + "~"+c  + "~"+ direction.up);
        if(idx!=null){
            removePotentialPoint(idx);
        }else {
            requestAddPotentialPoint(direction.down, r,c);
        }
        idx = pointMap.get(r  + "~"+ (c-2)  + "~"+ direction.right);
        if(idx !=null){
            removePotentialPoint(idx);
        }else {
            requestAddPotentialPoint(direction.left, r,c);
        }
        idx = pointMap.get(r  + "~"+ (c+2)  + "~"+ direction.left);
        if(idx!=null){
            removePotentialPoint(idx);
        }else {
            requestAddPotentialPoint(direction.right, r,c);
        }
    }

    public void fillMaze(){
        Random random = new Random();
        int idx;
        while(!pointMap.isEmpty()){
            idx = random.nextInt(index);
            fillIndex(pointIndex[idx]);
            printMaze();
        }
    }

    public void printMaze(){
        for (int i = 0; i < 2 * n - 1; i++) {
            for (int j = 0; j < 2 * n - 1; j++) {
                System.out.print(maze[i][j]);
            }
            System.out.println("");
        }
        for (int i = 0; i < 2 * n - 1; i++) {
            System.out.print("-");
        }
        System.out.println("");
    }
}
