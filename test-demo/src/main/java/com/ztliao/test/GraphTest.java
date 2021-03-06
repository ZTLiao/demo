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
 * ??????
 * @author liaozetao
 *
 * @param <T>
 */
class Vertex<T>{
	
	T data;
	
	int index;
	
	/**
	 * ?????????
	 */
	List<Edge<T>> edges = new ArrayList<>();
	
	/**
	 * ?????????????????????(????????????)
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
 * ???
 * @author liaozetao
 *
 * @param <T>
 */
class Edge<T>{
	
	/**
	 * ?????????
	 */
	Vertex<T> from;
	
	/**
	 * ?????????
	 */
	Vertex<T> to;
	
	/**
	 * ??????
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
 * ?????????????????????
 * @author liaozetao
 *
 * @param <T>
 */
class EdgeList<T>{
	
	/**
	 * ??????
	 */
	Vertex<T> vertex;
	
	/**
	 * ?????????????????????
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
	 * ????????????
	 */
	List<Vertex<T>> vertices = new ArrayList<>();
	
	/**
	 * ????????????
	 */
	List<EdgeList<T>> adjacencyList = new ArrayList<>();
	
	/**
	 * ????????????
	 * @param value
	 * @return
	 */
	public Vertex<T> createVertex(T value) {
		//?????????????????????????????????
		List<Vertex<T>> matchingVertices = new ArrayList<>();
		if(vertices != null && !vertices.isEmpty()) {
			for(Vertex<T> vertex : vertices) {
				if(value.equals(vertex.data)) {
					matchingVertices.add(vertex);
				}
			}
		}
		//???????????????????????????
		if(!matchingVertices.isEmpty()) {
			return matchingVertices.get(matchingVertices.size() - 1);
		}
		//??????????????????
		Vertex<T> vertex = new Vertex<>(value, adjacencyList.size());
		vertices.add(vertex);
		//?????????????????????????????????
		adjacencyList.add(new EdgeList<>(vertex));
		return vertex;
	}
	
	/**
	 * ???????????????
	 * @param fromVertex
	 * @param toVertex
	 * @param weightValue
	 */
	public void addDirectedEdge(Vertex<T> fromVertex, Vertex<T> toVertex, Double weightValue) {
		//?????????
		Edge<T> edge = new Edge<>(fromVertex, toVertex, weightValue);
		//????????????????????????
		fromVertex.addEdge(edge);
		int fromIndex = vertices.indexOf(fromVertex);
		adjacencyList.get(fromIndex).edges.add(edge);
	}
	
	/**
	 * ??????????????? ?????? ??????????????????????????????
	 * @param fromVertex
	 * @param toVertex
	 * @param weightValue
	 */
	public void addUnDirectedEdge(Vertex<T> fromVertex, Vertex<T> toVertex, Double weightValue) {
		addDirectedEdge(fromVertex, toVertex, weightValue);
		addDirectedEdge(toVertex, fromVertex, weightValue);
	}
	
	/**
	 * ?????????????????????????????????
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
