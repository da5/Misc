package practice.Miscellaneous;


//https://leetcode.com/problems/word-search/

import java.util.HashSet;
import java.util.Set;

public class WordSearch {

    public static class Cell {
        int row;
        int col;

        public Cell(int r, int c) {
            row = r;
            col = c;
        }

        @Override
        public boolean equals(Object o) {
            if(o==this){
                return true;
            }
            if(!(o instanceof Cell)){
                return false;
            }
            Cell c = (Cell)o;
            return this.row==c.row && this.col==c.col;
        }

        @Override
        public int hashCode() {
            int result = 17;

            result = result*31 + row;
            result = result*31 + col;

            return result;
        }
    }

    boolean isValid(Cell cell, int rows, int cols) {
        if(cell.row<0 || cell.row>=rows || cell.col<0 || cell.col>=cols) {
            return false;
        }
        return true;
    }

    boolean dfs(char[][] board, Cell cell, int rows, int cols,
                String word, int idx, Set<Cell> visited) {
        if(idx>=word.length()) {
            return true;
        }
        if(board[cell.row][cell.col]!=word.charAt(idx)) {
            return false;
        }
        if(idx==word.length()-1){
            return true;
        }
        visited.add(cell);
        boolean possible = false;
        Cell _cell = new Cell(cell.row+1, cell.col);
        if(isValid(_cell, rows, cols) && !visited.contains(_cell)) {
            possible |= dfs(board, _cell, rows, cols, word, idx+1, visited);
        }
        _cell = new Cell(cell.row-1, cell.col);
        if(!possible && isValid(_cell, rows, cols) && !visited.contains(_cell)) {
            possible |= dfs(board, _cell, rows, cols, word, idx+1, visited);
        }
        _cell = new Cell(cell.row, cell.col+1);
        if(!possible && isValid(_cell, rows, cols) && !visited.contains(_cell)) {
            possible |= dfs(board, _cell, rows, cols, word, idx+1, visited);
        }
        _cell = new Cell(cell.row, cell.col-1);
        if(!possible && isValid(_cell, rows, cols) && !visited.contains(_cell)) {
            possible |= dfs(board, _cell, rows, cols, word, idx+1, visited);
        }

        visited.remove(cell);

        return possible;

    }

    public boolean exist(char[][] board, String word) {
        int rows = board.length;
        if(rows==0){
            return false;
        }
        int cols = board[0].length;
        if(cols==0) {
            return false;
        }
        if(word==null || word.isEmpty()) {
            return true;
        }
        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                if(board[i][j]==word.charAt(0)){
                    if(dfs(board, new Cell(i, j), rows, cols, word, 0, new HashSet<>())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

class WordSearchDriver {
    public static void main(String[] args) {
        char[][] board = {{'A','B','C','E'},
                          {'S','F','E','S'},
                          {'A','D','E','E'}};
        String word = "ABCESEEEFS";


        WordSearch wordSearch = new WordSearch();
        System.out.println(wordSearch.exist(board, word));
    }
}
