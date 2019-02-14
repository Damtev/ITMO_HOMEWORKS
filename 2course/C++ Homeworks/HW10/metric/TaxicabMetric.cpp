//
// Created by damtev on 10/01/19.
//

#include "TaxicabMetric.h"

TaxicabMetric::TaxicabMetric() = default;

double TaxicabMetric::calculate(const Edge& edge) const {
	return abs(edge.getFrom().getLatitude() - edge.getTo().getLatitude()) +
	abs(edge.getFrom().getLongitude() - edge.getTo().getLongitude());
}

double TaxicabMetric::operator()(const Edge& edge) const {
	return calculate(edge);
}
