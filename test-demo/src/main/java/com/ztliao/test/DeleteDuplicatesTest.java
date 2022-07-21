package com.ztliao.test;

/**
 * @author: liaozetao
 * @date: 2022/3/4 3:26 PM
 * @description:
 */
public class DeleteDuplicatesTest {

    public static void main(String[] args) {
        ListNode head = new ListNode(1, new ListNode(1, new ListNode(1)));
        ListNode temp = head;
        while (temp != null) {
            while (temp.next != null && temp.val == temp.next.val) {
                temp.next = temp.next.next;
            }
            temp = temp.next;
        }
        while (head != null) {
            System.out.println(head.val);
            head = head.next;
        }
    }
}
