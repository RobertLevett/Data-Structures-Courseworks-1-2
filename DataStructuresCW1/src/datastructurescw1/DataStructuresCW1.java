package datastructurescw1;

import java.text.DecimalFormat;
import java.util.*;

public class DataStructuresCW1 {

    static double[][] arrayFill(double n[][]) {
        Random p = new Random();
        int min = 1;
        int max = 10;
        double randomInteger;

        for (int i = 0; i < n.length; i++) {
            for (int j = 0; j < n.length; j++) {
                n[i][j] = randomInteger = min + p.nextDouble() * max;
//                System.out.print(n[i][j] + "\t");
            }
//            System.out.println("\n");
        }
        return n;
    }
                    //QUESTION 3 NO IMPROVEMENTS //
    static double findSeries(int subSeq[], double serArray[][]) {
        double lowest = Double.MAX_VALUE;
        double result = 0;
        double value;
        double placeHold = Double.MAX_VALUE;
        int row = 0, pos = 0;
        for (int i = 0; i < serArray.length; i++) {
            for (int j = 0; j < (serArray.length - subSeq.length) + 1; j++) {
                int iter = j;
                double square = 0;
                for (int l = 0; l < subSeq.length; l++) {
                    value = subSeq[l] - serArray[i][iter];
                    square += value * value;
                    iter++;
                }
                if (square > placeHold) {
                    break;
                }
                placeHold = square;
                result = Math.sqrt(square);
//                System.out.println(result);
                if (result < lowest) {
                    lowest = result;
                    row = i;
                    pos = j;
                }
                if (result == 0) {
                    break;
                }
            }
            if (result == 0) {
                break;
            }

        }
//        System.out.println("Lowest is in row: " + row + " position: " + pos);
        return lowest;
    }
                //QUESTION 5 -- METHOD WITH IMPROVEMENTS//
       static double findSeriesImprov(int subSeq[], double serArray[][]) {
        double lowest = Double.MAX_VALUE;
        double result = 0;
        double value;
        double placeHold = Double.MAX_VALUE;
        int row = 0, pos = 0;
        for (int i = 0; i < serArray.length; i++) {
            for (int j = 0; j < (serArray.length - subSeq.length) + 1; j++) {
                int iter = j;
                double square = 0;
                for (int l = 0; l < subSeq.length; l++) {
                    value = subSeq[l] - serArray[i][iter];
                    square += value * value;
                    iter++;
                }
                result = Math.sqrt(square);
//                System.out.println(result);
                if (result < lowest) {
                    lowest = result;
                    row = i;
                    pos = j;
                }                
            }
        }
//        System.out.println("Lowest is in row: " + row + " position: " + pos);
        return lowest;
    }

    static int[] subArrayFill(int x[]) {
        Random p = new Random();
//        System.out.print("SubArray: ");
        for (int i = 0; i < x.length; i++) {
            x[i] = p.nextInt(10);
//            System.out.print(x[i] + " ");
        }
        return x;
    }

    public static void timingExperiment(int x, int n, int reps) {
        int subArray[] = new int[x];
        double sum = 0;
        double s = 0;
        double sumSquared = 0;
        double seriesArray[][] = new double[n][n];
        
        
        subArrayFill(subArray);
        arrayFill(seriesArray);
        ////////////////////////////////////////////////////////////////////
        findSeries(subArray, seriesArray);
        /////////CHANGE TO findSeries1(subArray, seriesArray); for Q5///////
        
        

        for (int i = 0; i < reps; i++) {

            long t1 = System.nanoTime();
            findSeries(subArray, seriesArray);
            long t2 = System.nanoTime() - t1;

            sum += (double) t2 / 1000000.0;
            sumSquared += (t2 / 1000000.0) * (t2 / 1000000.0);
        }
        double mean = sum / reps;
        double variance = sumSquared / reps - (mean * mean);
        double stdDev = Math.sqrt(variance);
        DecimalFormat df = new DecimalFormat("#.####");
        System.out.println(df.format(mean));

    }

    public static void main(String[] args) {

        for (int i = 100; i <= 6400; i += 100) {
            timingExperiment(3, i, 100);
        }

        
       
    }
}

