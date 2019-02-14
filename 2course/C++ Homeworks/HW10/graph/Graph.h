//
// Created by damtev on 01/09/18.
//

#ifndef INC_10_PATHS_GRAPH_H
#define INC_10_PATHS_GRAPH_H


#include <cstddef>
#include <vector>
#include <unordered_set>

#include "Vertex.h"
#include "Edge.h"


class Graph {
public:
    explicit Graph(const std::vector<Vertex*>& vertices);
    ~Graph();

    Vertex& getVertex(int id) const;
    const std::vector<Vertex*>& getVertices() const;

    const Edge& getEdge(int id) const;
    const std::vector<Edge*>& getEdges() const;

    void addEdge(int from, int to);
    void addEdge(Vertex& from, Vertex& to);

    size_t size() const;
    size_t edgesAmount() const;

private:
    std::vector<Vertex*> mVertices{};
    std::vector<Edge*> mEdges{};
};


#endif //INC_10_PATHS_GRAPH_H
