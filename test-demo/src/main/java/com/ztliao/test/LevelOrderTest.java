package com.ztliao.test;

import java.util.ArrayList;
import java.util.List;

public class LevelOrderTest {

	public static void main(String[] args) {
		/**
		 * 给你一个二叉树，请你返回其按 层序遍历 得到的节点值。 （即逐层地，从左到右访问所有节点）。
			
			 
			
			示例：
			二叉树：[3,9,20,null,null,15,7],
			
			    3
			   / \
			  9  20
			    /  \
			   15   7
			返回其层次遍历结果：
			
			[
			  [3],
			  [9,20],
			  [15,7]
			]
		 */
		TreeNode root = new TreeNode(3);
		root.left = new TreeNode(9);
		root.right = new TreeNode(20);
		root.right.left = new TreeNode(15);
		root.right.right = new TreeNode(7);
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
			//System.out.println(" value : " + root.val);
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
}
