package com.ztliao.test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LeetCodeTest {

	public static void main(String[] args) {
		// 给你一个二叉树，请你返回其按 层序遍历 得到的节点值。 （即逐层地，从左到右访问所有节点）。
		// [3,9,20,null,null,15,7]
		TreeNode root = new TreeNode(3);
		root.left = new TreeNode(9);
		root.right = new TreeNode(20);
		root.right.left = new TreeNode(15);
		root.right.right = new TreeNode(7);
		//DFS(root);
		//levelOrder(root);
		//System.out.println(level(root, 2));
		//System.out.println(depth(root));
		List<List<Integer>> list = levelOrder(root);
		System.out.println(list.toString());
	}
	
	public static List<List<Integer>> levelOrder(TreeNode root) {
		List<List<Integer>> levelList = new ArrayList<>();
		int depth = depth(root);
		for(int i = 0; i < depth; i++) {
			List<Integer> treeNodes = new ArrayList<>();
			level(treeNodes, root, i);
			levelList.add(treeNodes);
		}
		return levelList;
	}
	
	public static int level(List<Integer> treeNodes, TreeNode root, int level) {
		if(root == null || level < 0) {
			return 0;
		}
		if(level == 0) {
			System.out.println(" value : " + root.val);
			treeNodes.add(root.val);
		}
		return level(treeNodes, root.left, level - 1) + level(treeNodes, root.right, level - 1);
	}
	
	public static int depth(TreeNode root) {
		if(root == null) {
			return 0;
		}
		int left = depth(root.left);
		int right = depth(root.right);
		return 1 + (left > right ? left : right);
	}
	
	public static void DFS(TreeNode root) {
		if(root != null) {
			System.out.println(" value : " + root.val);
			if(root.right != null) {
				DFS(root.right);
			}
			if(root.left != null) {
				DFS(root.left);
			}
		}
	}
	
	public static void BFS(TreeNode root) {
		LinkedList<TreeNode> queue = new LinkedList<>();
		queue.push(root);
		TreeNode front = null;
		while (!queue.isEmpty()) {
			front = queue.getFirst();
			queue.pop();
			if (front.right != null) {
				queue.push(front.right);
			}
			if (front.left != null) {
				queue.push(front.left);
			}
			System.out.println("value : " + front.val);
		}
	}

	public static boolean isSymmetric(TreeNode root) {
		if (root == null) {
			return true;
		}
		TreeNode left = root.left;
		TreeNode right = root.right;
		if (left == null && right == null) {
			return true;
		} else if (left != null && right != null) {
			boolean isLeft = recursion(left, right);
			System.out.println("isLeft : " + isLeft);
			boolean isRight = recursion(right, left);
			System.out.println("isRight : " + isRight);
			if (isLeft && isRight) {
				return true;
			}
		}
		return false;
	}

	public static boolean recursion(TreeNode left, TreeNode right) {
		if (left != null && right != null) {
			if (left.val == right.val) {
				System.out.println(" left : " + left.val);
				System.out.println(" right : " + right.val);
				boolean isLeft = recursion(left.left, right.right);
				boolean isRight = recursion(left.right, right.left);
				if (isLeft && isRight) {
					return true;
				}
			}
		} else if (left == null && right == null) {
			return true;
		}
		return false;
	}

	public static void reverse(TreeNode from, TreeNode to) {
		if (from == to) {
			return;
		}
	}

	public static void printReverse(TreeNode from, TreeNode to) {
		reverse(from, to);
		TreeNode p = to;
		while (true) {
			System.out.println(" val : " + p.val);
			if (p == from) {
				break;
			}
			p = p.right;
		}
		reverse(to, from);
	}

	public static void postorderMorrisTraversal(TreeNode root) {
		TreeNode dump = new TreeNode(0);
		dump.left = root;
		TreeNode curr = dump, prev = null;
		while (curr != null) {
			if (curr.left == null) {
				curr = curr.right;
			} else {
				prev = curr.left;
				while (prev.right != null && prev.right != curr) {
					prev = prev.right;
				}
				if (prev.right != null) {
					prev.right = curr;
					curr = curr.left;
				} else {
					printReverse(curr.left, prev);
					prev.right = null;
					curr = curr.right;
				}
			}
		}
	}

}