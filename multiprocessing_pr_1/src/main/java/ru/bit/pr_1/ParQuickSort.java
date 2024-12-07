package ru.bit.pr_1;

import lombok.AllArgsConstructor;

import java.util.concurrent.RecursiveAction;

// call me java developer plz
@AllArgsConstructor
public class ParQuickSort extends RecursiveAction {
    private final int[] arr;
    private final int start;
    private final int end;
    private final int threshold;

    @Override
    protected void compute() {
        if (end - start < threshold) {
            SeqQuickSort.quickSort(arr, start, end);
        } else {
            int pivot = Partition.partition(arr, start, end);

            ParQuickSort leftTask = new ParQuickSort(arr, start, pivot - 1, threshold);
            ParQuickSort rightTask = new ParQuickSort(arr, pivot + 1, end, threshold);

            invokeAll(leftTask, rightTask);
        }
    }
}
