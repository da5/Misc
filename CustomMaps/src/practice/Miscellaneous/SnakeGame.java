package practice.Miscellaneous;

import java.util.Deque;
import java.util.LinkedList;

public class SnakeGame {
    /** Initialize your data structure here.
     @param width - screen width
     @param height - screen height
     @param food - A list of food positions
     E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
    static class Coordinate{
        public int x;
        public int y;
        Coordinate(int a, int b){x=a;y=b;}
    }
    Deque<Coordinate> coordinateDeque;
    Deque<Coordinate> foodLocations;
    int cols;
    int rows;
    int foodIdx;
    int score;

    public SnakeGame(int width, int height, int[][] food) {
        cols = width;
        rows = height;
        foodIdx = 0;
        coordinateDeque = new LinkedList<>();
        coordinateDeque.offer(new Coordinate(0,0));
        score = 0;
        foodLocations = new LinkedList<>();
        for(int[] location: food){
            foodLocations.add(new Coordinate(location[1], location[0]));
        }

    }

    /** Moves the snake.
     @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down
     @return The game's score after the move. Return -1 if game over.
     Game over when snake crosses the screen boundary or bites its body. */

    private boolean safeTurn(Coordinate coordinate, int food){
        if(coordinate.x<0 || coordinate.x >= cols || coordinate.y<0 || coordinate.y>=rows){
            return false;
        }
        int lastIdx = coordinateDeque.size()-1;
        int idx = 0;
        for(Coordinate coordinate1: coordinateDeque){
            if(idx<lastIdx || (food == 1 && idx==lastIdx)) {
                if (coordinate1.x == coordinate.x && coordinate1.y == coordinate.y) {
                    return false;
                }
                idx++;
            }
        }
        return true;
    }

    private int hasFood(Coordinate coordinate){
        Coordinate food = foodLocations.peekFirst();
        if(food!=null && food.x == coordinate.x && food.y == coordinate.y){
            foodLocations.pollFirst();
            return 1;
        }
        return 0;

    }

    public int move(String direction) {
        int result = -1;
        Coordinate head = coordinateDeque.peekFirst();
        Coordinate next = new Coordinate(head.x, head.y);
        if(direction.equals("U")){
            next.y--;
        }else if(direction.equals("D")){
            next.y++;
        }else if(direction.equals("L")){
            next.x--;
        }else if(direction.equals("R")){
            next.x++;
        }
        int food = hasFood(next);
        if(safeTurn(next, food)){
            score += food;
            if(food == 0){
                coordinateDeque.pollLast();
            }
            coordinateDeque.offerFirst(next);
            result = score;
        }
        return result;
    }
}

class SDriver {
    public static void main(String[] args) {
        int[][] food = {{2,0},{0,0},{0,2},{2,2}};
        SnakeGame game = new SnakeGame(3,3, food);
        String moves = "DDRUULDRRULD";
        for(char c: moves.toCharArray()){
            System.out.println(game.move(""+c));
        }
    }

}

