package algorithm;

import java.util.Arrays;

//希尔排序
public class ShellSort {

    private static void shellSort(int[] arr, int gap, int len) {
        for (int i = 0; i < len; i++) {
            int key = arr[i];
            int j;
            for (j = i - gap; j >= 0; j -= gap) {
                if (arr[j] <= key) {
                    break;
                }
                arr[j + gap] = arr[j];
            }
            arr[j + gap] = key;
        }
    }

    private static void shellSort(int[] arr) {
        int gap = arr.length;
        int len = arr.length;
        while (true) {
            gap = gap / 3 + 1;
            System.out.println(gap);
            shellSort(arr, gap, len);
            if (gap == 1)
                return;
        }
    }

    public static void main(String[] args) {
        int[] arr = {50, 2, 54, 23, 65, 7, 34, 54, 98, 77, 4, 21, 20, 88, 66, 55, 70};
        System.out.println(Arrays.toString(arr));
        shellSort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
