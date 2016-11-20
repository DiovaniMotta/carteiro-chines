import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

/**
 * 
 * @author Diovani Bernardi da Motta
 * 
 *         O algoritmo do carteiro chinês tem como passos para a eulerização do
 *         grafo as seguintes etapas
 * 
 *         1 - Verificar se o grafo é conexo e não possui vértices isolados; 2 -
 *         Listar todos os vértices de grau ímpar; 3 - Criar a matriz de
 *         distância entre os vértices de grau ímpar;
 */
public class Main {

	private static Grafo grafo;

	static {
		grafo = new Grafo();
		Vertice a = new Vertice("A");
		Vertice c = new Vertice("C");
		Vertice d = new Vertice("D");
		Vertice e = new Vertice("E");
		Vertice f = new Vertice("F");
		Vertice g = new Vertice("G");
		Vertice h = new Vertice("H");
		Vertice i = new Vertice("I");
		Vertice j = new Vertice("J");
		Vertice m = new Vertice("M");
		Vertice n = new Vertice("N");
		Vertice p = new Vertice("P");
		// arestas do vertice A
		a.addAresta(new Aresta(a, c, 5));
		a.addAresta(new Aresta(a, g, 8));
		// arestas do vertice C
		c.addAresta(new Aresta(c, a, 5));
		c.addAresta(new Aresta(c, d, 3));
		c.addAresta(new Aresta(c, g, 12));
		// arestas do vertice D
		d.addAresta(new Aresta(d, c, 3));
		d.addAresta(new Aresta(d, e, 1));
		d.addAresta(new Aresta(d, h, 11));
		// arestas do vertice E
		e.addAresta(new Aresta(e, d, 1));
		e.addAresta(new Aresta(e, j, 18));
		e.addAresta(new Aresta(e, f, 1));
		// arestas do vertice F
		f.addAresta(new Aresta(f, e, 1));
		f.addAresta(new Aresta(f, n, 20));
		// arestas do vertice G
		g.addAresta(new Aresta(g, a, 8));
		g.addAresta(new Aresta(g, c, 12));
		g.addAresta(new Aresta(g, i, 2));
		// arestas do vertice H
		h.addAresta(new Aresta(h, d, 11));
		h.addAresta(new Aresta(h, i, 4));
		h.addAresta(new Aresta(h, j, 1));
		// arestas do vertice I
		i.addAresta(new Aresta(i, h, 4));
		i.addAresta(new Aresta(i, g, 2));
		i.addAresta(new Aresta(i, p, 6));
		// arestas do vertice J
		j.addAresta(new Aresta(j, e, 18));
		j.addAresta(new Aresta(j, h, 1));
		j.addAresta(new Aresta(j, m, 3));
		// arestas do vertice M
		m.addAresta(new Aresta(m, j, 3));
		m.addAresta(new Aresta(m, p, 7));
		m.addAresta(new Aresta(m, n, 7));
		// arestas do vertice N
		n.addAresta(new Aresta(n, m, 7));
		n.addAresta(new Aresta(n, f, 20));
		// arestas do vertice P
		p.addAresta(new Aresta(p, m, 7));
		p.addAresta(new Aresta(p, i, 6));
		grafo.addVertice(a);
		grafo.addVertice(c);
		grafo.addVertice(d);
		grafo.addVertice(e);
		grafo.addVertice(f);
		grafo.addVertice(g);
		grafo.addVertice(h);
		grafo.addVertice(i);
		grafo.addVertice(j);
		grafo.addVertice(m);
		grafo.addVertice(n);
		grafo.addVertice(p);
	}

	public static void main(String[] args) {
		CarteiroChines carteiroChines = new CarteiroChines();
		carteiroChines.matrizCusto(grafo);
	}
}

class Grafo {

	private List<Vertice> vertices;

	public Grafo() {
		vertices = new ArrayList<Vertice>();
	}

	public List<Vertice> getVertices() {
		return vertices;
	}

	public void setVertices(List<Vertice> vertices) {
		this.vertices = vertices;
	}

	public void addVertice(Vertice vertice) {
		this.vertices.add(vertice);
	}

	public int size() {
		return vertices.size();
	}

	public Vertice getVertice(String vertice) {
		Optional<Vertice> v = vertices.stream().filter(x -> x.getNome().equals(vertice)).findAny();
		return v.get();
	}
}

class Vertice implements Comparable<Vertice> {

	private String nome;
	private int distancia;
	private Vertice pai;
	private boolean visitado;
	private List<Aresta> arestas;

	public Vertice() {
		super();
		arestas = new ArrayList<Aresta>();
	}

	public Vertice(String nome) {
		super();
		this.nome = nome;
		arestas = new ArrayList<Aresta>();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Aresta> getArestas() {
		return arestas;
	}

	public void setArestas(List<Aresta> arestas) {
		this.arestas = arestas;
	}

	public void addAresta(Aresta aresta) {
		this.arestas.add(aresta);
	}

	public int getDistancia() {
		return distancia;
	}

	public void setDistancia(int distancia) {
		this.distancia = distancia;
	}

	public Vertice getPai() {
		return pai;
	}

	public void setPai(Vertice pai) {
		this.pai = pai;
	}

	public boolean isVisitado() {
		return visitado;
	}

	public void visitado() {
		this.visitado = true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	public int compareTo(Vertice vertice) {
		if (this.getDistancia() < vertice.getDistancia())
			return -1;
		else if (this.getDistancia() == vertice.getDistancia())
			return 0;
		return 1;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vertice other = (Vertice) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Vertice [nome=" + nome + "]";
	}
}

class Aresta {

	private Vertice origem;
	private Vertice destino;
	private int custo;

	public Aresta(Vertice origem, Vertice destino, int custo) {
		super();
		this.origem = origem;
		this.destino = destino;
		this.custo = custo;
	}

	public Vertice getOrigem() {
		return origem;
	}

	public void setOrigem(Vertice origem) {
		this.origem = origem;
	}

	public Vertice getDestino() {
		return destino;
	}

	public void setDestino(Vertice destino) {
		this.destino = destino;
	}

	public int getCusto() {
		return custo;
	}

	public void setCusto(int custo) {
		this.custo = custo;
	}
}

class OrdenacaoList implements Comparator<String> {

	public int compare(String begin, String end) {
		return begin.compareTo(end);
	}
}

class OrdenacaoVertice implements Comparator<Vertice> {

	@Override
	public int compare(Vertice begin, Vertice end) {
		return begin.getNome().compareTo(end.getNome());
	}
}

class Randomize {

	public static int random(int random) {
		Random r = new Random();
		return r.nextInt(random);
	}
}

class CarteiroChines {

	private Dijkstra dijkstra;
	private Map<Vertice, List<Vertice>> matrizCusto = new HashMap<>();

	public CarteiroChines() {
		super();
		dijkstra = new Dijkstra();
	}

	protected List<Vertice> grauImpar(Grafo grafo) {
		List<Vertice> impares = new ArrayList<>();
		for (Vertice vertice : grafo.getVertices())
			if (vertice.getArestas().size() % 2 > 0)
				impares.add(vertice);
		return impares;
	}

	public void matrizCusto(Grafo grafo) {
		List<Vertice> impares = grafo.getVertices();
		List<Vertice> aux = impares;
		for (Vertice origin : impares)
			for (Vertice destination : aux)
				matrizCusto.put(origin, dijkstra.menorCaminho(grafo, origin, destination));
		for(Map.Entry<Vertice,List<Vertice>> road : matrizCusto.entrySet()){
			Vertice vertice = road.getKey();
			System.err.println("Vertice:"+road.getKey().getNome());
			for(Vertice v : road.getValue()){
				System.out.println("Distancia:"+v.getDistancia()+" entre ["+vertice.getNome()+" e "+v.getNome()+"]");
			}
		}
	}
}

class Conexidade {

}

class Dijkstra {

	private List<Vertice> menorCaminho = new ArrayList<>();
	private List<Vertice> naoVisitados = new ArrayList<>();
	private Vertice verticeCaminho;
	private Vertice atual;
	private Vertice vizinho;

	public List<Vertice> menorCaminho(Grafo grafo, Vertice origin, Vertice destination) {
		menorCaminho.clear();
		naoVisitados.clear();
		menorCaminho.add(origin); // Adiciona a origem na lista do menor caminho
		for (Vertice vertice : grafo.getVertices()) {
			if (vertice.equals(origin))
				vertice.setDistancia(0);
			else
				vertice.setDistancia(9999);
			naoVisitados.add(vertice);
		}
		Collections.sort(naoVisitados);
		while (!naoVisitados.isEmpty()) {
			atual = naoVisitados.get(0);
			for (int x = 0; x < atual.getArestas().size(); x++) {
				vizinho = atual.getArestas().get(x).getDestino();
				if (!vizinho.isVisitado()) {
					if (vizinho.getDistancia() > (atual.getDistancia() + atual.getArestas().get(x).getCusto())) {
						vizinho.setDistancia(atual.getDistancia() + atual.getArestas().get(x).getCusto());
						vizinho.setPai(atual);
						if (vizinho.getDistancia() > (atual.getDistancia() + atual.getArestas().get(x).getCusto())) {
							vizinho.setDistancia(atual.getDistancia() + atual.getArestas().get(x).getCusto());
							vizinho.setPai(atual);
							/*
							 * Se o vizinho eh o vertice procurado, e foi feita
							 * uma mudanca na distancia, a lista com o menor
							 * caminho anterior eh apagada, pois existe um
							 * caminho menor vertices pais, ateh o vertice
							 * origem.
							 */
							if (vizinho.equals(destination)) {
								menorCaminho.clear();
								verticeCaminho = vizinho;
								menorCaminho.add(vizinho);
								while (verticeCaminho.getPai() != null) {
									menorCaminho.add(verticeCaminho.getPai());
									verticeCaminho = verticeCaminho.getPai();
								}
								// Ordena a lista do menor caminho, para que ele
								// seja exibido da origem ao destino.
								Collections.sort(menorCaminho);
							}
						}
					}
				}
			}
			atual.visitado();
			naoVisitados.remove(atual);
			Collections.sort(naoVisitados);
		}
		return menorCaminho;
	}
}