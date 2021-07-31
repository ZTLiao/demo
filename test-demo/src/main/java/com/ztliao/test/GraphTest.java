package com.ztliao.test;

import java.util.ArrayList;
import java.util.List;

public class GraphTest {

	public static void main(String[] args) {
		//test1();
		//test2();
		test3();
	}
	
	public static void test1() {
		Graph<Integer> graph = new Graph<>();
		Vertex<Integer> v1 = graph.createVertex(1);
		Vertex<Integer> v2 = graph.createVertex(2);
		Vertex<Integer> v3 = graph.createVertex(3);
		Vertex<Integer> v4 = graph.createVertex(4);
		Vertex<Integer> v5 = graph.createVertex(5);
		
		graph.addDirectedEdge(v1, v2, 1.0);
		graph.addDirectedEdge(v2, v3, 1.0);
		graph.addDirectedEdge(v3, v4, 4.5);
		graph.addDirectedEdge(v4, v1, 2.8);
		graph.addDirectedEdge(v2, v5, 3.2);
		
		graph.printAdjacencyList();
	}
	
	public static void test2() {
		Graph<Integer> graph = new Graph<>();
		Vertex<Integer> v1 = graph.createVertex(1);
		Vertex<Integer> v2 = graph.createVertex(2);
		Vertex<Integer> v3 = graph.createVertex(3);
		Vertex<Integer> v4 = graph.createVertex(4);
		Vertex<Integer> v5 = graph.createVertex(5);
		
		graph.addUnDirectedEdge(v1, v2, 1.0);
		graph.addUnDirectedEdge(v2, v3, 1.0);
		graph.addUnDirectedEdge(v3, v4, 4.5);
		graph.addUnDirectedEdge(v4, v1, 2.8);
		graph.addUnDirectedEdge(v2, v5, 3.2);

		graph.printAdjacencyList();
	}
	
	public static void test3() {
		Graph<String> planeGraph = new Graph<>();
		Vertex<String> hk = planeGraph.createVertex("Hong Kong");
		Vertex<String> ny = planeGraph.createVertex("New York");
		Vertex<String> mosc = planeGraph.createVertex("Moscow");
		Vertex<String> ld = planeGraph.createVertex("London");
		Vertex<String> pairs = planeGraph.createVertex("Pairs");
		Vertex<String> am = planeGraph.createVertex("Amsterdam");
		Vertex<String> sf = planeGraph.createVertex("San Francisco");
		Vertex<String> ja = planeGraph.createVertex("Juneau Alaska");
		Vertex<String> tm = planeGraph.createVertex("Timbuktu");
		
		planeGraph.addUnDirectedEdge(hk, sf, 500.0);
        planeGraph.addUnDirectedEdge(hk,mosc,900.0);
        planeGraph.addDirectedEdge(sf, ja, 300.0);
        planeGraph.addUnDirectedEdge(sf, ny, 150.0);
        planeGraph.addDirectedEdge(mosc,ny, 750.0);
        planeGraph.addDirectedEdge(ld, mosc, 200.0);
        planeGraph.addUnDirectedEdge(ld, pairs, 70.0);
        planeGraph.addDirectedEdge(sf,pairs, 800.0);
        planeGraph.addUnDirectedEdge(pairs, tm, 250.0);
        planeGraph.addDirectedEdge(am, pairs, 50.0);
        
        planeGraph.printAdjacencyList();

	}
}

/**
 * 顶点
 * @author liaozetao
 *
 * @param <T>
 */
class Vertex<T>{
	
	T data;
	
	int index;
	
	/**
	 * 边集合
	 */
	List<Edge<T>> edges = new ArrayList<>();
	
	/**
	 * 是否已经遍历过(遍历标识)
	 */
	boolean visited = false;
	
	public Vertex(T data, int index) {
		this.data = data;
		this.index = index;
	}
	
	public void addEdge(Edge<T> edge) {
		edges.add(edge);
	}
	
	@Override
	public String toString() {
		return data.toString();
	}
}

/**
 * 边
 * @author liaozetao
 *
 * @param <T>
 */
class Edge<T>{
	
	/**
	 * 起始点
	 */
	Vertex<T> from;
	
	/**
	 * 结束点
	 */
	Vertex<T> to;
	
	/**
	 * 权重
	 */
	double weight;
	
	public Edge(Vertex<T> from, Vertex<T> to, Double weight) {
		this.from = from;
		this.to = to;
		this.weight = weight;
	}
	
	@Override
	public String toString() {
		return " from = " + from.toString() + " | to = " + to.toString() + " | weight = " + weight;
	}
}

/**
 * 顶点与边的集合
 * @author liaozetao
 *
 * @param <T>
 */
class EdgeList<T>{
	
	/**
	 * 顶点
	 */
	Vertex<T> vertex;
	
	/**
	 * 顶点所关联的边
	 */
	List<Edge<T>> edges = new ArrayList<>();
	
	public EdgeList(Vertex<T> vertex) {
		this.vertex = vertex;
	}
	
	public void addEdge(Edge<T> edge) {
		edges.add(edge);
	}
}

class Graph<T>{
	
	/**
	 * 顶点集合
	 */
	List<Vertex<T>> vertices = new ArrayList<>();
	
	/**
	 * 邻接列表
	 */
	List<EdgeList<T>> adjacencyList = new ArrayList<>();
	
	/**
	 * 创建顶点
	 * @param value
	 * @return
	 */
	public Vertex<T> createVertex(T value) {
		//缓存顶点保证顶点不重复
		List<Vertex<T>> matchingVertices = new ArrayList<>();
		if(vertices != null && !vertices.isEmpty()) {
			for(Vertex<T> vertex : vertices) {
				if(value.equals(vertex.data)) {
					matchingVertices.add(vertex);
				}
			}
		}
		//返回已经存在的顶点
		if(!matchingVertices.isEmpty()) {
			return matchingVertices.get(matchingVertices.size() - 1);
		}
		//构建新的顶点
		Vertex<T> vertex = new Vertex<>(value, adjacencyList.size());
		vertices.add(vertex);
		//构建顶点对应的邻接列表
		adjacencyList.add(new EdgeList<>(vertex));
		return vertex;
	}
	
	/**
	 * 添加有向边
	 * @param fromVertex
	 * @param toVertex
	 * @param weightValue
	 */
	public void addDirectedEdge(Vertex<T> fromVertex, Vertex<T> toVertex, Double weightValue) {
		//构建边
		Edge<T> edge = new Edge<>(fromVertex, toVertex, weightValue);
		//顶点关联对应的边
		fromVertex.addEdge(edge);
		int fromIndex = vertices.indexOf(fromVertex);
		adjacencyList.get(fromIndex).edges.add(edge);
	}
	
	/**
	 * 添加无向边 等于 两个顶点都有相互关联
	 * @param fromVertex
	 * @param toVertex
	 * @param weightValue
	 */
	public void addUnDirectedEdge(Vertex<T> fromVertex, Vertex<T> toVertex, Double weightValue) {
		addDirectedEdge(fromVertex, toVertex, weightValue);
		addDirectedEdge(toVertex, fromVertex, weightValue);
	}
	
	/**
	 * 打印对应顶点的边的数据
	 */
	public void printAdjacencyList() {
		for(int i = 0; i < vertices.size(); i++) {
			if(adjacencyList.get(i).edges.isEmpty()) {
				continue;
			}
			System.out.println(vertices.get(i).data + " -> [" + adjacencyList.get(i).edges.toString() + "]");
		}
	}
}
