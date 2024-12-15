package ru.bit.pr_2;

public class Checker {
    private static int[] seqDist = null;
    private static int[] parDist = null;

    public Checker(int[] seqDist, int[] parDist) {
        Checker.seqDist = seqDist;
        Checker.parDist = parDist;
    }


    public void check() {
        if (compareDistances()) {
            System.out.println("Результаты корректны, массивы расстояний для обоих алгоритмов совпадают.");
        } else {
            System.out.println("Массивы расстояний не совпадают!\nРезультаты НЕ корректны!");
        }
    }

    protected static boolean compareDistances() {
        assert parDist != null;
        assert seqDist != null;
        assert seqDist.length == parDist.length;

        for (int i = 0; i < seqDist.length; ++i) {
            if (seqDist[i] != parDist[i]) {
                return false;
            }
        }
        return true;
    }
}
