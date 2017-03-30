//package fr.lip6.move.gal.cegar.graph.tests;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Set;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import fr.lip6.move.gal.cegar.graph.Graph;
//import fr.lip6.move.gal.cegar.interfaces.IGraph;
//
//public class GraphTest {
//
//	private IGraph<String> graph;
//
//	private List<String> vertices;
//
//	@Before
//	public void setUp() {
//		List<List<String>> edges = new ArrayList<List<String>>();
//		vertices = new ArrayList<String>(Arrays.asList("v1", "v2", "v3"));
//		edges.add(vertices);
//		edges.add(Arrays.asList("v4"));
//		edges.add(Arrays.asList("v2", "v5"));
//		edges.add(Arrays.asList("v5", "v6"));
//		graph = new Graph<String>();
//		graph.setEdges(edges);
//	}
//
//	@Test
//	public void testConnectedDepth0() {
//		System.out.println("Testing connectedDepth0...");
//		
//		Set<String> component = graph.getConnectedComponent("v1", 0);
//		
//		assertEquals(1, component.size());
//		assertTrue(component.contains("v1"));
//	}
//
//	@Test
//	public void testConnectedDepth1() {
//		System.out.println("Testing connectedDepth1...");
//		
//		Set<String> component = graph.getConnectedComponent("v1", 1);
//		
//		assertEquals(3, component.size());
//		assertTrue(component.containsAll(Arrays.asList("v1", "v2", "v3")));
//	}
//
//	@Test
//	public void testConnectedSingleton() {
//		System.out.println("Testing connectedSingleton...");
//		
//		Set<String> component = graph.getConnectedComponent("v4", 3);
//
//		assertEquals(1, component.size());
//		assertTrue(component.contains("v4"));
//	}
//}
