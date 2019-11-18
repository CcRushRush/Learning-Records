package algorithm;

import java.util.*;

//广度搜索
public class BFS {
    /**
     * 从第一个顶点开始，先把与它直接相连的所有顶点存入队列，每进行一次循环，弹出一个最先进入的顶点，依次进行
     * @param maze   所有顶底连接信息
     * @param vertex 保存所有已经访问过的顶点
     */
    private static void bFS(int[][] maze, int[] vertex) {
        int len = maze.length;
        Queue<Integer> visit = new LinkedList<>();
        visit.add(vertex[3]);
        System.out.print(visit.peek());
        while (!visit.isEmpty()) {
            int v = visit.peek();
            visit.remove();
            for (int i = 0; i < len; i++) {
                if (maze[v - 1][i] > 0 && vertex[i + 1] == 0) {
                    System.out.print(i + 1);
                    visit.add(i + 1);
                    vertex[i + 1] = i + 1;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[][] maze = {
                {0, 1, 1, 1, 0},
                {1, 0, 1, 0, 0},
                {1, 1, 1, 0, 1},
                {1, 0, 0, 0, 1},
                {0, 0, 1, 1, 0}
        };
        int[] vertex = new int[maze.length + 1];
        vertex[3] = 3;
        System.out.println(Arrays.deepToString(maze));
        bFS(maze, vertex);
    }


}
