//
// Created by damtev on 01/09/18.
//

#include "Graph.h"


Graph::Graph(const std::vector<Vertex*>& vertices) {
    mVertices = vertices;
}

Graph::~Graph() {
    mVertices.clear();
    mEdges.clear();
}

Vertex& Graph::getVertex(int id) const {
    return *mVertices[id];
}

const std::vector<Vertex*>& Graph::getVertices() const {
    return mVertices;
}

const Edge& Graph::getEdge(int id) const {
    return *mEdges[id];
}

size_t Graph::edgesAmount() const {
    return mEdges.size();
}

const std::vector<Edge*>& Graph::getEdges() const {
    return mEdges;
}

void Graph::addEdge(int from, int to) {
    addEdge(*mVertices[from], *mVertices[to]);
}

void Graph::addEdge(Vertex& from, Vertex& to) {
    Edge* edge = new Edge(from, to);
    from.getAdjacent().push_back(edge);
    to.getAdjacent().push_back(edge);
    mEdges.push_back(edge);
}

size_t Graph::size() const {
    return mVertices.size();
}

