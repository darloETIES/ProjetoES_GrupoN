package grupo.n.gestaodoterritorio;

import com.vividsolutions.jts.geom.Geometry;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.jgrapht.graph.SimpleGraph;

import java.util.List;
import java.util.Map;

public class Graph {
    /**Grafo que armazena:
     * - Property, como vertices (nós)
     * - DefaultEdge, como arestas (adjacências)*/
    private SimpleGraph<Property, DefaultEdge> graph;
    private SimpleGraph<Owner, DefaultEdge> graphOwner;

    public Graph() {
        this.graph = new SimpleGraph<>(DefaultEdge.class);
        this.graphOwner = new SimpleGraph<>(DefaultEdge.class);
    }

    /** Constrói o grafo a partir do mapa de propriedades lido do ficheiro CSV em Properties Loader */
    public void createGraph(Map<String, Property> properties) {

        System.out.println("\nCreating graph:");
        //Adiciona os nós (propiedades)
        for (Property property : properties.values()) {
            graph.addVertex(property);
        }

        //Adiciona as arestas (vizinhança/adjacencias)
        for(Property p1 : properties.values()) {
            for(Property p2 : properties.values()) {
                if (!p1.getObjectId().equals(p2.getObjectId())) {
                    Geometry g1 = p1.getGeometry();
                    Geometry g2 = p2.getGeometry();
                    boolean intersects = g1.intersects(g2);
                    if (intersects) {
                        System.out.println("Comparing " + p1.getObjectId() + " and " + p2.getObjectId() + ": " + intersects);
                        graph.addEdge(p1, p2);
                    }
                }
            }
        }

    }
    public void createGraphOwners(Map<String, Owner> Owner) {

        System.out.println("\nCreating graph:");
        //Adiciona os nós (propiedades)
        for (Owner o : Owner.values()) {
            graphOwner.addVertex(o);
        }

        //Adiciona as arestas (vizinhança/adjacencias)
        for (Owner o1 : Owner.values()) {
            for (Owner o2 : Owner.values()) {
                if (!o1.getOwnerID().equals(o2.getOwnerID())) { // Evita comparar o mesmo proprietário com ele mesmo
                    // Verifica as propriedades de o1 e o2
                    for (Property p1 : o1.getOwnerPropertyList()) {
                        for (Property p2 : o2.getOwnerPropertyList()) {
                            Geometry g1 = p1.getGeometry();
                            Geometry g2 = p2.getGeometry();
                            if ( g1.intersects(g2)) {
                                // Adiciona uma aresta entre o1 e o2 no grafo
                                graphOwner.addEdge(o1, o2);
                                System.out.println("Edge added between " + o1.getOwnerID() + " and " + o2.getOwnerID());
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Informações sobre o grafo
     */
    public void printGraphStats() {
        System.out.println("Sobre o Grafo:");
        System.out.println("- " + graphOwner.vertexSet().size() + " nós (propriedades)");
        System.out.println("- " + graphOwner.edgeSet().size() + " arestas (vizinhancas)");
    }

    /**
     * Obter grafo
     * @return
     */
    public SimpleGraph<Property, DefaultEdge> getGraph() {
        return graph;
    }


}