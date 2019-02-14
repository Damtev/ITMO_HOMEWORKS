//
// Created by damtev on 10/01/19.
//

#ifndef INC_10_PATHS_FRENCHMETRIC_H
#define INC_10_PATHS_FRENCHMETRIC_H

#include "Metric.h"

class FrenchMetric : public Metric {
public:
	FrenchMetric();

	double calculate(const Edge& edge) const override;
	double operator()(const Edge& edge) const override;

};

#endif //INC_10_PATHS_FRENCHMETRIC_H