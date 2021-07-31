package com.ztliao.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TreeTest {
	
	static List<Integer> result;

	public static void main(String[] args) {
		Tree root = new Tree(3);
		root.left = new Tree(9);
		root.right = new Tree(20);
		root.right.left = new Tree(15);
		root.right.right = new Tree(7);
		System.out.println(depth(root));
	}
	
	public static List<Integer> depth(Tree root) {
		List<Integer> list = new ArrayList<>();
		if(root == null) {
			return list;
		}
		Stack<Tree> stack = new Stack<>();
		stack.push(root);
		while(!stack.isEmpty()) {
			Tree node = stack.pop();
			if(node.right != null) {
				stack.push(node.right);
			}
			if(node.left != null) {
				stack.push(node.left);
			}
			list.add(node.val);
		}
		return list;
	}
	
	public static List<Integer> inorderTraversal(Tree root){
		inorder(root);
		return result;
	}
	
	public static void inorder(Tree root) {
		if(root == null) {
			return;
		}
		inorder(root.left);
		result.add(root.val);
		inorder(root.right);
	}

}

class Tree {
	int val;
	Tree left;
	Tree right;
	Tree(int x) {
		val = x;
	}
}
