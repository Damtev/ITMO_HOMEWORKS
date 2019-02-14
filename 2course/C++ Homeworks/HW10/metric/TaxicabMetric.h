//
// Created by damtev on 10/01/19.
//

#ifndef INC_10_PATHS_TAXICABMETRIC_H
#define INC_10_PATHS_TAXICABMETRIC_H

#include "Metric.h"

class TaxicabMetric : public Metric {
public:
	TaxicabMetric();

	double calculate(const Edge& edge) const override;
	double operator()(const Edge& edge) const override;

};

#endif //INC_10_PATHS_TAXICABMETRIC_H