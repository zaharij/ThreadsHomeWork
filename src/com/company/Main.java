package com.company;

import java.util.Random;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        // I
        initArray();

	    int step = nums.length / threadNumbers;
	    for (int i = 0; i < threadNumbers; i++){
            new MyThread(step, i).thread.join();
        }

        new MyThread().thread.join();


        // II
        long startTime = System.currentTimeMillis();
        int step2 = numbers2 / threadNumbers2;
        for (int i = 0; i < threadNumbers2; i++){
            new MyThread2(step2, i).thread.join();
        }

        new MyThread2().thread.join();
        long endTime = System.currentTimeMillis();

        System.out.println("working time: " + ((endTime - startTime) / 1000) + "," + ((endTime - startTime) % 1000) + " seconds");// 10 - 15 seconds
}

// first task
    private static int threadNumbers = 5;
    private static int numbers = 50000000;
    private static int[] nums = new int[numbers];
    private static int[] biggestNumbers = new int[threadNumbers];
    private static int theBiggestNumber = 0;

    private static void initArray(){
        Random random = new Random();
        for (int i = 0; i < numbers; i++){
            nums[i] = random.nextInt(1000);
        }
    }

    static class MyThread implements Runnable {
        public Thread thread;
        private int firstElement, lastElement, threadNumber, step;
        private boolean finalCheck = false;

        public MyThread(int step, int threadNumber){
            this.step = step;
            this.threadNumber = threadNumber;
            initBorderElements();
            startThread();
        }

        public MyThread(){
            finalCheck = true;
            startThread();
        }

        private void startThread() {
            thread = new Thread(this);
            thread.start();
        }

        private void initBorderElements(){
            firstElement = threadNumber * step;
            lastElement = firstElement + step;
        }

        @Override
        public void run() {
            if (!finalCheck) {
                for (int i = firstElement; i < lastElement; i++) {
                    if (biggestNumbers[threadNumber] < nums[i]) {
                        biggestNumbers[threadNumber] = nums[i];
                    }
                }
            } else {
                for (int i = 0; i < threadNumbers; i++) {
                    if (theBiggestNumber < biggestNumbers[i]) {
                        theBiggestNumber = biggestNumbers[i];
                    }
                }
                System.out.println(theBiggestNumber);
            }
        }
    }


    // second task (hangouts)

    private static int threadNumbers2 = 5;
    private static int numbers2 = 100000;
    private static int[][] nums2 = new int[threadNumbers2][2];

    static class MyThread2 implements Runnable {
        public Thread thread;
        private int firstElement, lastElement, threadNumber, step;
        private boolean finalCheck = false;

        public MyThread2(int step, int threadNumber){
            this.threadNumber = threadNumber;
            this.step = step;
            initBorderElements();
            startThread();
        }

        public MyThread2(){
            finalCheck = true;
            startThread();
        }

        private void startThread() {
            thread = new Thread(this);
            thread.start();
        }

        private void initBorderElements(){
            firstElement = threadNumber * step == 0 ? 1 : threadNumber * step;
            lastElement = threadNumber != threadNumbers2 - 1 ? firstElement + step : numbers2 + 1;
        }

        @Override
        public void run() {
            if (!finalCheck) {
                int count;
                for (int i = firstElement; i < lastElement; i++) {
                    count = 0;
                    for(int j = i; j >= 1; j--){
                        int div = i / j;
                        if (div * j == i ){
                            count++;
                        }
                    }
                    if (nums2[threadNumber][1] < count){
                        nums2[threadNumber][0] = i;
                        nums2[threadNumber][1] = count;
                    }
                }
            } else {
                int biggestDiv = 0;
                int number = 0;
                for (int i = 0; i < nums2.length; i++) {
                    if (biggestDiv < nums2[i][1]) {
                        biggestDiv = nums2[i][1];
                        number = i;
                    }
                }
                System.out.println("the number: " + nums2[number][0]);
                System.out.println("the number of dividers: " + biggestDiv);
            }
        }
    }
}
