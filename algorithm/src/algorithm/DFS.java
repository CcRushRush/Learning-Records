package algorithm;

import java.util.Arrays;
import java.util.Stack;

//深度搜索
public class DFS {
    /**
     * 从第一个顶点开始，先把与它直接相连的所有顶点存入队列，每进行一次循环，弹出一个最先进入的顶点，依次进行
     * @param maze   所有顶底连接信息
     * @param vertex 保存所有已经访问过的顶点
     */
    private static void dFS(int[][] maze, int[] vertex) {
        Stack<Integer> stack = new Stack<>();
        stack.push(vertex[3]);
        System.out.print(stack.peek());
        while (!stack.isEmpty()) {
            int s = stack.peek();
            int i = 0;
            for (; i < maze.length; i++) {
                if (maze[s - 1][i] != 0 && vertex[i + 1] == 0) {
                    System.out.print(i + 1);
                    stack.push(i + 1);
                    vertex[i + 1] = i + 1;
                    break;
                }
            }
            if (i == maze.length)
                stack.pop();
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
        dFS(maze, vertex);
    }


}
