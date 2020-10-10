import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;


public class P3 {

	public static void main(String[] args) throws IOException {
		//foarte asemantor cu solutia pentru P2 tot folosind Bellman-Ford
		File myObj = new File("p3.in");
		Scanner sc = new Scanner(myObj);
		int n = sc.nextInt();
		int m = sc.nextInt();
		int startEnergy = sc.nextInt();
		int start = 1;
		int x, y, cost;
		double[] dist = new double[n + 1];
		int[] parent = new int[n + 1];
		ArrayList<Edge>[] edges = new ArrayList[n + 1];

		for (int i = 1; i <= n; i++) {
			edges[i] = new ArrayList<>();
		}
		dist[start] = startEnergy;

		for (int i = 0; i < m; i++) {
			x = sc.nextInt();
			y = sc.nextInt();
			cost = sc.nextInt();
			edges[x].add(new Edge(y, cost, x));
		}

		Queue<Integer> nodeQ = new LinkedList<>();
		boolean[] vizited = new boolean[n + 1];
		vizited[start] = true;
		nodeQ.add(start);

		while (!nodeQ.isEmpty()) {
			int curNode = nodeQ.remove();
			vizited[curNode] = false;
			for (Edge edge : edges[curNode]) {
				int next = edge.next;
				cost = edge.cost;

				double effort = dist[curNode] * (100 - cost) / 100;
				//modificam conditia fata de P2 pentru a calcula energia ramasa
				if (effort > dist[next]) {
					if (!vizited[next]) {
						vizited[next] = true;
						nodeQ.add(next);
					}
					dist[next] = effort;
					//folosim vectorul pentru a reconstrui drumul minim
					parent[next] = curNode;
				}
			}

		}


		int stop = n;
		File output = new File("p3.out");
		FileWriter fr = new FileWriter(output);
		fr.write(Double.toString(dist[stop]) + "\n");

		ArrayList<Integer> roadQ = new ArrayList<>();
		roadQ.add(stop);
		//reconstruim drumul
		while (parent[stop] != start) {
			roadQ.add(parent[stop]);
			stop = parent[stop];
		}

		roadQ.add(1);
		//afisam lista pentru drum in ordine inversa
		for (int i = roadQ.size() - 1; i >= 0; i--) {
			fr.write(Integer.toString(roadQ.get(i)) + " ");
		}

		sc.close();
		fr.close();
	}
}
