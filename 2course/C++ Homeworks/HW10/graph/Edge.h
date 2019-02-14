//
// Created by damtev on 01/09/18.
//

#ifndef INC_10_PATHS_EDGE_H
#define INC_10_PATHS_EDGE_H


#include "Vertex.h"

class Edge {
public:
    Edge(Vertex& from,
         Vertex& to);

    virtual ~Edge();

    bool operator==(const Edge& rhs) const;

    bool operator!=(const Edge& rhs) const;

    Vertex& getFrom() const;

    Vertex& getTo() const;
private:
    Vertex& mFrom;
    Vertex& mTo;
};


#endif //INC_10_PATHS_EDGE_H
