//
// Created by damtev on 01/09/18.
//

#ifndef INC_10_PATHS_METRIC_H
#define INC_10_PATHS_METRIC_H

#include "Graph.h"

class Metric {
public:
    virtual double calculate(const Edge& edge) const = 0;
    virtual double operator()(const Edge& edge) const = 0;

    virtual ~Metric() = default;
};

#endif //INC_10_PATHS_METRIC_H
