package algorithm;

import java.util.Arrays;

//合并排序
public class MergeSort {
    private static void mergeSort(int[] arr, int low, int mid, int high, int[] extra) {
        int i = low, j = mid, k = low;
        while (i < mid && j < high) {
            if (arr[i] <= arr[j]) {
                extra[k++] = arr[i++];
            } else {
                extra[k++] = arr[j++];
            }
        }
        while (i < mid) {
            extra[k++] = arr[i++];
        }
        while (j < high) {
            extra[k++] = arr[j++];
        }
        for (int x = low; x < high; x++) {
            arr[x] = extra[x];
        }
    }

    /**
     * 每次将数组评分为两边，递归，直到只剩下两个值或者一个值，然后合并
     *
     * @param arr
     * @param low
     * @param high
     * @param extra 额外数组，保存数据
     */
    private static void mergeSort(int[] arr, int low, int high, int[] extra) {
        if (high <= low + 1)
            return;
        int mid = low + (high - low) / 2;
        mergeSort(arr, low, mid, extra);
        mergeSort(arr, mid, high, extra);
        mergeSort(arr, low, mid, high, extra);
    }

    private static void mergeSort(int[] arr) {
        int low = 0;
        int high = arr.length;
        int[] extra = new int[arr.length];
        mergeSort(arr, low, high, extra);
    }

    public static void main(String[] args) {
        int[] arr = {50, 2, 54, 23, 65, 7, 34, 54, 98, 77, 4, 21, 20, 88, 66, 55, 70};
        System.out.println(Arrays.toString(arr));
        mergeSort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
