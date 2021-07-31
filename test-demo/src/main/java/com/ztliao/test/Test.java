package com.ztliao.test;

import java.util.Arrays;

public class Test {

	public static void main(String[] args) {
		int[] num = { 5, 7, 8, 2, 4, 6, 0, 6, 1, 5, 8, -1, -2 };
		average(num);
	}
	
	public static void average(int[] num) {
		sort(num);
		int len = num.length;
		int[] arr1 = new int[len];
		int[] arr2 = new int[len];
		int[][] dp = new int[len][4];
		arr1[0] = num[0];
		arr2[0] = num[1];
		int n1 = 1;
		int n2 = 1;
		for(int i = 2, j = 3, k = 0; i < len && j < len && k < len; i += 2, j += 2, k++) {
			System.out.println(" num[i] = " + num[i]);
			System.out.println(" num[j] = " + num[j]);
			dp[k][0] = abs(sum(arr1), sum(arr2) + num[j]);
			dp[k][1] = abs(sum(arr1) + num[i], sum(arr2) + num[j]);
			dp[k][2] = abs(sum(arr1) + num[j], sum(arr2));
			dp[k][3] = abs(sum(arr1) + num[j], sum(arr2) + num[i]);
			int min = dp[k][0];
			int index = 0;
			for(int a = 0; a < dp[k].length; a++) {
				if(min >= dp[k][a]) {
					min = dp[k][a];
					index = a;
				}
			}
			System.out.println(" index = " + index);
			if(index == 0) {
				arr2[n2] = num[j];
				n2++;
			}else if(index == 1) {
				arr1[n1] = num[i];
				arr2[n2] = num[j];
				n1++;
				n2++;
			}else if(index == 2) {
				arr1[n1] = num[j];
				n1++;
			}else if(index == 3) {
				arr1[n1] = num[j];
				arr2[n2] = num[i];
				n1++;
				n2++;
			}
		}
		test(arr1, arr2);
	}
	
	public static int abs(int num1, int num2) {
		int num = num1 - num2;
		if(num < 0) {
			num = 0 - num;
		}
		System.out.println(" num = " + num);
		return num;
	}
	
	public static int sum(int[] num) {
		int sum = 0;
		for(int i = 0; i < num.length; i++) {
			sum += num[i];
		}
		return sum;
	}

	public static void sort(int[] num) {
		for (int i = 0; i < num.length; i++) {
			for (int j = 0; j < num.length - 1; j++) {
				if(num[i] < num[j]) {
					int temp = num[i];
					num[i] = num[j];
					num[j] = temp;
				}
			}
		}
		for(int i = 0; i < num.length; i++) {
			System.out.print(num[i] + " ");
		}
		System.out.println();
	}
	
	public static void test(int[] num1, int[] num2) {
		System.out.println(Arrays.toString(num1));
		System.out.println(Arrays.toString(num2));
		int len = 0;
		if(num1.length < num2.length) {
			len = num2.length;
		}else {
			len = num1.length;
		}
		int i = 0, j = 0, n = 0;
		for(; n < len; n++) {
			if(i < num1.length) {
				System.out.println(num1[i]);
			}
			if(j < num2.length) {
				System.out.println(num2[j]);
			}
			i++;
			j++;
		}
	}
}
