//
// Created by damtev on 01/09/18.
//

#include <algorithm>
#include <iostream>

#include "LongitudeSortPartition.h"

std::vector<std::vector<Edge*>> LongitudeSortPartition::buildPartition(const Graph& graph, size_t maxLength) {
    maxLength--;
    auto partition = std::vector<std::vector<Edge*>>();
    partition.emplace_back();
    std::vector<Edge*> sortedEdges = graph.getEdges();
    std::sort(sortedEdges.begin(), sortedEdges.end(),
            [](Edge* const o1, Edge* const o2) {
                std::cout << o1->getFrom().getId() << " -> " << o1->getTo().getId() << " | ";
                std::cout << o2->getFrom().getId() << " -> " << o2->getTo().getId() << "\n";
                return o1->getFrom().getLongitude() - o2->getFrom().getLongitude();
            }
    );
    return partition;
}
