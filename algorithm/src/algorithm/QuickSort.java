package algorithm;

import java.util.Arrays;
//快速排序
public class QuickSort {

    /**
     * 快速排序，每次选择arr[low]作为基准值，大于它的放在右边，
     * 小于它的放在左边，递归，直到low大于或者等于high,相当于里面只有一个值
     * 每次这样循环
     * @param arr
     * @param low 数组的低位
     * @param high 数组的高位
     */
    private static void quickSort(int[] arr, int low, int high) {
        if (low >= high)
            return;
        int index = arr[low];
        int i = low, j = high;
        while (i < j) {
            while (arr[j] > index && i < j)
                j--;
            if (i < j){
                arr[i] = arr[j];
                i++;
            }
            while (arr[i] < index && i < j)
                i++;
            if (i < j){
                arr[j] = arr[i];
                j--;
            }
        }
        arr[i] = index;
        quickSort(arr, low, i - 1);
        quickSort(arr, i + 1, high);
    }

    private static void quickSort(int[] arr) {
        int low = 0, high = arr.length - 1;
        quickSort(arr, low, high);
    }

    public static void main(String[] args) {
        int[] arr = {50, 2, 54, 23, 65, 7, 34, 54, 98, 77, 4, 21, 20, 88, 66, 55, 70};
        System.out.println(Arrays.toString(arr));
        quickSort(arr);
        System.out.println(Arrays.toString(arr));
    }


}
