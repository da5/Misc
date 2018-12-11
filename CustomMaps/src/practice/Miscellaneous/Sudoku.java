package practice.Miscellaneous;

public class Sudoku {

    void resetFlag(int[] flags){
        for(int i = 0; i<flags.length; i++){
            flags[i] = 0;
        }
    }

    boolean checkRow(char[][] board, int row, int[] flags){
        resetFlag(flags);
        for(char c: board[row]){
            if(c!='.') {
                int n = c - '0';
                if (flags[n] == 1) {
                    return false;
                }
                flags[n] = 1;
            }
        }
        return true;
    }

    boolean checkColumn(char[][] board, int column, int[] flags){
        resetFlag(flags);
        for(int i = 0; i<9; i++){
            if(board[i][column]!='.') {
                int n = board[i][column] - '0';
                if (flags[n] == 1) {
                    return false;
                }
                flags[n] = 1;
            }
        }
        return true;
    }

    boolean checkSquare(char[][] board, int[] flags, int r1, int r2, int c1, int c2){
        resetFlag(flags);
        for(int i = r1; i<=r2; i++){
            for(int j = c1; j<=c2; j++){
                if(board[i][j]!='.') {
                    int n = board[i][j] - '0';
                    if (flags[n] == 1) {
                        return false;
                    }
                    flags[n] = 1;
                }
            }
        }
        return true;
    }


    public boolean isValidSudoku(char[][] board) {
        int[] flags = new int[10];
        boolean result = true;
        for(int i = 0; i<9; i++){
            result = checkRow(board, i, flags);
            if(!result){
                return result;
            }
            result = checkColumn(board, i, flags);
            if(!result){
                return result;
            }
        }
        for(int i = 0; i<9; i+=3){
            for(int j = 0; j<9; j+=3){
                result = checkSquare(board, flags, i, i+2, j, j+2);
                if(!result){
                    return result;
                }
            }
        }
        return result;
    }
}

class SudokuDriver{
    public static void main(String[] args){
        char[][] board = {{'5','3','.','.','7','.','.','.','.'},{'6','.','.','1','9','5','.','.','.'},{'.','9','8','.','.','.','.','6','.'},{'8','.','.','.','6','.','.','.','3'},{'4','.','.','8','.','3','.','.','1'},{'7','.','.','.','2','.','.','.','6'},{'.','6','.','.','.','.','2','8','.'},{'.','.','.','4','1','9','.','.','5'},{'.','.','.','.','8','.','.','7','9'}};
        Sudoku sudoku = new Sudoku();
        System.out.println(sudoku.isValidSudoku(board));
    }
}