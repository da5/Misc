package practice.Miscellaneous;

import java.util.*;

public class NumberOfIslands {
    private int getHash(int row, int col, int cols) {
        return row*cols + col;
    }

    private Set<Integer> getUnvisited(char[][] grid, int rows, int cols) {
        Set<Integer> unvisited = new HashSet<>();
        for(int i=0; i<rows; i++) {
            for(int j=0; j<cols; j++) {
                if(grid[i][j]=='1'){
                    unvisited.add(getHash(i, j, cols));
                }
            }
        }
        return unvisited;
    }

    private void process(char[][] grid, int rows, int cols, Set<Integer> unvisited, Queue<Integer> queue) {
        int[][] direction = {{-1,0},{1,0},{0,-1},{0,1}};
        while (!queue.isEmpty()) {
            int hash = queue.poll();
            int col = hash%cols;
            int row = hash/cols;
            for(int i=0; i<4; i++) {
                int r = row+direction[i][0];
                int c = col+direction[i][1];
                if(r>=0 && r<rows && c>=0 && c<cols && grid[r][c]=='1') {
                    int h = getHash(r, c, cols);
                    if(unvisited.contains(h)) {
                        unvisited.remove(h);
                        queue.offer(h);
                    }
                }
            }
        }

    }

    private int numIslandsDFS(char[][] grid, int rows, int cols) {
        int islands = 0;
        Set<Integer> unvisited = getUnvisited(grid, rows, cols);
        Queue<Integer> queue = new LinkedList<>();
        while(!unvisited.isEmpty()) {
            Iterator<Integer> iterator = unvisited.iterator();
            queue.offer(iterator.next());
            iterator.remove();
            islands++;
            process(grid, rows, cols, unvisited, queue);
        }
        return islands;
    }

    static class UnionFind {
        int parent[];
        int rank[];
        int sets;

        public UnionFind(char[][] grid, int rows, int cols) {
            parent = new int[rows*cols];
            rank = new int[rows*cols];
            sets = 0;
            for(int i=0; i<rows; i++) {
                for(int j=0; j<cols; j++) {
                    if(grid[i][j]=='1') {
                        int idx = i*cols+j;
                        parent[idx] = idx;
                        rank[idx] = 1;
                        sets++;
                    }

                }
            }
        }

        public int find(int x) {
            if(parent[x]!=x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        public void union(int x, int y) {
            int parentX = find(x);
            int parentY = find(y);
            if(parentX!=parentY) {
                if(rank[parentX] > rank[parentY]) {
                    parent[parentY] = parentX;
                } else {
                    parent[parentX] = parentY;
                    if(rank[parentX] == rank[parentY]) {
                        rank[parentY]++;
                    }
                }
                sets--;
            }

        }
    }

    private int numIslandsUnionFind(char[][] grid, int rows, int cols) {
        int[][] direction = {{-1,0},{1,0},{0,-1},{0,1}};
        UnionFind unionFind = new UnionFind(grid, rows, cols);
        for(int i=0; i<rows; i++) {
            for (int j = 0; j < cols; j++) {
                if(grid[i][j]=='1') {
                    for(int d=0; d<4; d++) {
                        int r = i+direction[d][0];
                        int c = j+direction[d][1];
                        if(r>=0 && r<rows && c>=0 && c<cols && grid[r][c]=='1') {
                            unionFind.union(i*cols+j, r*cols+c);
                        }
                    }
                    grid[i][j]='0';
                }
            }
        }
        return unionFind.sets;
    }

    public int numIslands(char[][] grid) {
        int rows = grid.length;
        if(rows==0) {
            return 0;
        }
        int cols = grid[0].length;
        if(cols==0) {
            return 0;
        }
        return numIslandsUnionFind(grid, rows, cols);
    }
}

class NumberOfIslandsDriver {
    public static void main(String[] args) {
        NumberOfIslands numberOfIslands = new NumberOfIslands();
//        char[][] grid = {{'1','1','1','1','0'},{'1','1','0','1','0'},{'1','1','0','0','0'},{'0','0','0','0','0'}};
        char[][] grid = {{'1','1','1','1','1','0','1','1','1','1','1','1','1','1','1','0','1','0','1','1'},
                         {'0','1','1','1','1','1','1','1','1','1','1','1','1','0','1','1','1','1','1','0'},
                         {'1','0','1','1','1','0','0','1','1','0','1','1','1','1','1','1','1','1','1','1'},
                         {'1','1','1','1','0','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1'},
                         {'1','0','0','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1'},
                         {'1','0','1','1','1','1','1','1','0','1','1','1','0','1','1','1','0','1','1','1'},
                         {'0','1','1','1','1','1','1','1','1','1','1','1','0','1','1','0','1','1','1','1'},
                         {'1','1','1','1','1','1','1','1','1','1','1','1','0','1','1','1','1','0','1','1'},
                         {'1','1','1','1','1','1','1','1','1','1','0','1','1','1','1','1','1','1','1','1'},{'1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1'},{'0','1','1','1','1','1','1','1','0','1','1','1','1','1','1','1','1','1','1','1'},{'1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1'},{'1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1'},{'1','1','1','1','1','0','1','1','1','1','1','1','1','0','1','1','1','1','1','1'},{'1','0','1','1','1','1','1','0','1','1','1','0','1','1','1','1','0','1','1','1'},{'1','1','1','1','1','1','1','1','1','1','1','1','0','1','1','1','1','1','1','0'},{'1','1','1','1','1','1','1','1','1','1','1','1','1','0','1','1','1','1','0','0'},{'1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1'},{'1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1'},{'1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1'}};

        System.out.println(numberOfIslands.numIslands(grid));

    }
}
