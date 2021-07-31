package com.ztliao.test;

import cn.hutool.json.JSONUtil;

public class SortedArrayToBSTTest {

	public static void main(String[] args) {
		/**
		 *  将有序数组转换为二叉搜索树
			将一个按照升序排列的有序数组，转换为一棵高度平衡二叉搜索树。

			本题中，一个高度平衡二叉树是指一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1。

			示例:

			给定有序数组: [-10,-3,0,5,9],

			一个可能的答案是：[0,-3,9,-10,null,5]，它可以表示下面这个高度平衡二叉搜索树：

			      0
			     / \
			   -3   9
			   /   /
			 -10  5
		 */
		int[] numbers = {-10, -3, 0, 5, 9};
		TreeNode root = sortedArrayToBST(numbers);
//		TreeNode root = new TreeNode(0);
//		root.left = new TreeNode(-3);
//		root.right = new TreeNode(9);
//		root.left.left = new TreeNode(-10);
//		root.right.left = new TreeNode(5);
//		System.out.println(getDepth(root));
		System.out.println(JSONUtil.toJsonStr(root));
	}

	public static int getDepth(TreeNode root) {
		if(root == null) {
			return 0;
		}else {
			int left = getDepth(root.left);
			int right = getDepth(root.right);
			return 1 + (left > right ? left : right);
		}
	}

	public static TreeNode sortedArrayToBST(int[] nums) {
		TreeNode root = null;
		int length = nums.length;
		int mid = length / 2;
		root = new TreeNode(nums[mid]);
		for(int i = 0; i < nums.length; i++) {
			if(i != mid) {
				root = insertBST(nums[i], root);
				System.out.println(" root : " + root);
			}
		}
		return root;
    }

	public static TreeNode insertBST(int val, TreeNode node) {
		if(node == null) {
			return new TreeNode(val);
		}else {
			if(node.val > val) {
				node.left = insertBST(val, node.left);
				if(getDepth(node.left) - getDepth(node.right) > 1) {
					if(node.left.val > val) {
						node = adjustLL(node);
					}else {
						node = adjustRL(node);
						System.out.println(" left node val : " + val);
					}
				}
			}else {
				node.right = insertBST(val, node.right);
				if(getDepth(node.right) - getDepth(node.left) > 1) {
					if(node.right.val > val) {
						node = adjustRR(node);
					}else {
						node = adjustLR(node);
						System.out.println(" right node val : " + val);
					}
				}
			}
		}
		return node;
	}

	public static TreeNode adjustLL(TreeNode node) {
		TreeNode leftNode = node.left;
		node.left = leftNode.right;
		leftNode.right = node;
		return leftNode;
	}

	public static TreeNode adjustRR(TreeNode node) {
		TreeNode rightNode = node.right;
		node.right = rightNode.left;
		rightNode.left = node;
		return rightNode;
	}

	public static TreeNode adjustRL(TreeNode node) {
		node.left = adjustRR(node.left);
		return adjustLL(node);
	}

	public static TreeNode adjustLR(TreeNode node) {
		node.right = adjustLL(node.right);
		return adjustRR(node);
	}

}
