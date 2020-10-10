import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;


class Edge {
	int next;
	int cost;
	int start;

	public Edge(int next, int cost, int start) {
		this.next = next;
		this.cost = cost;
		this.start = start;
	}
}


public class P2 {

	public static void main(String[] args) throws IOException {
		File myObj = new File("p2.in");
		Scanner sc = new Scanner(myObj);
		int n = sc.nextInt();
		int m = sc.nextInt();
		int start = sc.nextInt();
		final int stop = sc.nextInt();
		int x, y, cost;
		//folosim vectorul de vizitat ca optimizare peste nodeQ.contains();
		//pentru a afla daca un nod daca este in coada
		boolean[] vizited = new boolean[n + 1];
		int[] dist = new int[n + 1];
		//lista de adiacenta a grafului
		ArrayList<Edge>[] edges = new ArrayList[n + 1];
		//initializare constante
		for (int i = 1; i <= n; i++) {
			dist[i] = Integer.MAX_VALUE;
			edges[i] = new ArrayList<>();
		}

		for (int i = 0; i < m; i++) {
			x = sc.nextInt();
			y = sc.nextInt();
			cost = sc.nextInt();
			edges[x].add(new Edge(y, cost, x));
		}
		//coada pentru Bellman-Ford
		Queue<Integer> nodeQ = new LinkedList<>();
		vizited[start] = true;
		nodeQ.add(start);
		dist[start] = 0;
		//algoritmul Bellman-ford cu optimizare pe coada
		while (!nodeQ.isEmpty()) {
			int curNode = nodeQ.remove();
			//nodul nu se mai afla in coada
			vizited[curNode] = false;
			for (Edge edge : edges[curNode]) {
				int next = edge.next;
				cost = edge.cost;
				//verificam daca exista posibilitatea unui drum mai scurt
				if (dist[curNode] != Integer.MAX_VALUE && dist[curNode] + cost < dist[next]) {
					//daca nodul nu se afla in coada il adaugam
					if (!vizited[next]) {
						vizited[next] = true;
						nodeQ.add(next);
					}
					dist[next] = dist[curNode] + cost;
				}
			}

		}


		File output = new File("p2.out");
		FileWriter fr = new FileWriter(output);
		fr.write(Integer.toString(dist[stop]));
		fr.close();
		sc.close();
	}
}
