//
// Created by damtev on 01/09/18.
//

#include "ConsequentPartitionAlgorithm.h"

std::vector<std::vector<Edge*>>
ConsequentPartitionAlgorithm::buildPartition(const Graph& graph, size_t maxLength) {
    maxLength--;
    auto partition = std::vector<std::vector<Edge*>>();
    partition.emplace_back();
    int lastDestination = 1;
    while (lastDestination < graph.size()) {
        auto& vertex = graph.getVertex(lastDestination - 1);
        for (auto* edge: vertex.getAdjacent()) {
            if (edge->getTo().getId() == lastDestination) {
                auto& lastPart = partition[partition.size() - 1];
                if (lastPart.size() == maxLength) {
                    partition.emplace_back();
                    lastPart = partition[partition.size() - 1];
                    lastDestination++;
                }

                lastPart.push_back(edge);
                lastDestination++;
                break;
            }
        }
    }
    return partition;
}
