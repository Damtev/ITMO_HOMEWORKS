//
// Created by damtev on 01/09/18.
//

#include "RandomPartitionAlgorithm.h"
#include "utilities.h"

using utilities::getRandomInt;

std::vector<std::vector<Edge*>>
RandomPartitionAlgorithm::buildPartition(const Graph& graph, size_t maxLength) {
    auto partition = std::vector<std::vector<Edge*>>();

    std::vector<bool> usedVertices;
    usedVertices.assign(graph.size(), false);
    auto usedCount = 0;

    for (auto* vertex: graph.getVertices()) {
        if (usedVertices[vertex->getId()]) {
            continue;
        }

        if (usedCount == graph.size() - 1) {
            partition.emplace_back();
            break;
        }

        usedVertices[vertex->getId()] = true;
        usedCount++;
        auto* edge = vertex->getAdjacent()[getRandomInt(0, (int) vertex->getAdjacent().size())];
        while (usedVertices[edge->getTo().getId()]) {
            edge = vertex->getAdjacent()[getRandomInt(0, (int) vertex->getAdjacent().size())];
        }
        usedVertices[edge->getTo().getId()] = true;
        usedCount++;
        partition.push_back(std::vector<Edge*>{edge});
    }

    return partition;
}
