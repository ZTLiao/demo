#include <stdio.h>

void merge(int* nums1, int nums1Size, int m, int* nums2, int nums2Size, int n);

int main(int argc, char* argv[]) {
    int nums1[] = {4,5,6,0,0,0};
    int m = 3;
    int nums2[] = {1,2,3};
    int n = 3;
    merge(nums1, 3, m, nums2, 3, n);
//    for (int i = 0; i < 6; i++) {
//        printf("nums1 = %d\n", nums1[i]);
//    }
    return 0;
}

void merge(int* nums1, int nums1Size, int m, int* nums2, int nums2Size, int n) {
    for (int i = 0; i < n; i++) {
        nums1[m + i] = nums2[i];
    }
    for (int i = 0; i < 6; i++) {
        printf("nums1 = %d\n", nums1[i]);
    }
    for (int i = 0, j = m; i < n;) {
        printf("nums1[%d] = %d, nums1[%d] = %d\n", i, nums1[i], (j), nums1[j]);
        if (nums1[i] > nums1[j]) {
            int temp = nums1[i];
            nums1[i] = nums1[j];
            nums1[j] = temp;
        }
        if (nums1[i] <= nums1[j]) {
            i++;
        } else {
            j++;
        }
    }
    for (int i = 0; i < 6; i++) {
        printf("nums1 = %d\n", nums1[i]);
    }
}