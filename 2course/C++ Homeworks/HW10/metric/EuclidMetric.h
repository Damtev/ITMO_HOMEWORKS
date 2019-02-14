//
// Created by damtev on 01/09/18.
//

#ifndef INC_10_PATHS_EUCLIDMETRIC_H
#define INC_10_PATHS_EUCLIDMETRIC_H

#include "Metric.h"

class EuclidMetric: public Metric {
public:
    EuclidMetric();

    double calculate(const Edge& edge) const override;
    double operator()(const Edge& edge) const override;
};

#endif //INC_10_PATHS_EUCLIDMETRIC_H
