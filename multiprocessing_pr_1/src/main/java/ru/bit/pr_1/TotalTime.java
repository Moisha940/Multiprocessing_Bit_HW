package ru.bit.pr_1;

import java.util.concurrent.ForkJoinPool;

public class TotalTime {
    protected static final int THRESHOLD = 1000;

    protected static long getParTime(int[] array, long parFullTime, int run) {
        ForkJoinPool pool = null;
        try {
            pool = new ForkJoinPool(4);

            long startTime = System.currentTimeMillis();
            pool.invoke(new ParQuickSort(array, 0, array.length - 1, THRESHOLD));
            long endTime = System.currentTimeMillis();

            long parTime = endTime - startTime;
            parFullTime += parTime;
            System.out.printf("Запуск %s: Параллельная сортировка выполнилась за %s мс%n", run, parTime);
        } catch (Exception e) {
            System.err.printf("Ошибка при выполнении параллельной сортировки в запуске %s: %s%n", run, e.getMessage());
        } finally {
            if (pool != null) {
                pool.shutdown();
            }
        }

        if (!correctSort(array, 0, array.length - 1)) {
            System.out.println("Некорректная сортировка");
        }

        return parFullTime;
    }

    protected static long getSeqTime(int[] array, long seqFullTime, int run) {
        long startTime = System.currentTimeMillis();
        SeqQuickSort.quickSort(array, 0, array.length - 1);
        long endTime = System.currentTimeMillis();

        long seqTime = endTime - startTime;
        seqFullTime += seqTime;
        System.out.printf("Запуск %s: Последовательная сортировка выполнилась за %s мс%n\n", run, seqTime);

        if (!correctSort(array, 0, array.length - 1)) {
            System.out.println("Некорректная сортировка");
        }

        return seqFullTime;
    }

    protected static boolean correctSort(int[] array, int start, int end) {
        for (int i = start; i < end; ++i) {
            if (array[i] > array[i + 1]) {
                return false;
            }
        }
        return true;
    }
}

