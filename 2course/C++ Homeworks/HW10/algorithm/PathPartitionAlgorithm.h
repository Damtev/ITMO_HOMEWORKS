//
// Created by damtev on 01/09/18.
//

#ifndef INC_10_PATHS_PATHFINDER_H
#define INC_10_PATHS_PATHFINDER_H


#include <vector>

#include "Graph.h"

class PathPartitionAlgorithm {
public:
    virtual std::vector<std::vector<Edge*>> buildPartition(const Graph& graph, size_t maxLength) = 0;

    virtual ~PathPartitionAlgorithm() = default;
};


#endif //INC_10_PATHS_PATHFINDER_H
