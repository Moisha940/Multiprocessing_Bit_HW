package ru.bit.pr_1;

public class SeqQuickSort {
    public static void quickSort(int[] arr, int start, int end) {
        if (start < end) {
            int pivot = Partition.partition(arr, start, end);
            quickSort(arr, start, pivot - 1);
            quickSort(arr, pivot + 1, end);
        }
    }
}
