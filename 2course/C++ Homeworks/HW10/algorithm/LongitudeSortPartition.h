//
// Created by damtev on 01/09/18.
//

#ifndef INC_10_PATHS_LONGITUDESORTPARTITION_H
#define INC_10_PATHS_LONGITUDESORTPARTITION_H


#include "PathPartitionAlgorithm.h"

class LongitudeSortPartition: public PathPartitionAlgorithm {

public:
    std::vector<std::vector<Edge*>> buildPartition(const Graph& graph, size_t maxLength) override;

};


#endif //INC_10_PATHS_LONGITUDESORTPARTITION_H
