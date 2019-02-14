//
// Created by damtev on 10/01/19.
//

#include <cmath>
#include "FrenchMetric.h"
#include "EuclidMetric.h"

FrenchMetric::FrenchMetric() = default;

double FrenchMetric::calculate(const Edge& edge) const {
	if (edge.getFrom().getLongitude() == 0 || edge.getTo().getLongitude() == 0 ||
	edge.getFrom().getLatitude() == 0 || edge.getTo().getLatitude() == 0 ||
	edge.getFrom().getLongitude() / edge.getTo().getLongitude() !=
	edge.getFrom().getLatitude() / edge.getTo().getLatitude()) {
		return sqrt(pow(edge.getFrom().getLatitude(), 2) + pow(edge.getFrom().getLongitude(), 2)) +
				sqrt(pow(edge.getTo().getLatitude(), 2) + pow(edge.getTo().getLongitude(), 2));
	} else {
		EuclidMetric euclidMetric;
		return euclidMetric.calculate(edge);
	}
}

double FrenchMetric::operator()(const Edge& edge) const {
	return calculate(edge);
}
