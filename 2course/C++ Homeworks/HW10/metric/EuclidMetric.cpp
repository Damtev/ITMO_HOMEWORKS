//
// Created by damtev on 01/09/18.
//

#include <cmath>

#include "EuclidMetric.h"


EuclidMetric::EuclidMetric() = default;

double EuclidMetric::calculate(const Edge& edge) const {
    return sqrt(
            pow(edge.getFrom().getLongitude() - edge.getTo().getLongitude(), 2) +
            pow(edge.getFrom().getLatitude() - edge.getTo().getLatitude(), 2)
    );
}

double EuclidMetric::operator()(const Edge& edge) const {
    return calculate(edge);
}
