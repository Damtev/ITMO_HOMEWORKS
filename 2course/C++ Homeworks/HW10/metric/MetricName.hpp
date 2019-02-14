//
// Created by damtev on 10.01.19.
//

#ifndef INC_10_PATHS_METRICNAME_HPP
#define INC_10_PATHS_METRICNAME_HPP

#include <string>

enum MetricName {
	EUCLID,
	TAXICAB,
	FRENCH
};

std::string getMetricMessage(MetricName metricName) {
	switch (metricName) {
		case EUCLID:
			return "euclid";
		case TAXICAB:
			return "taxicab";
		case FRENCH:
			return "french";
	}
}

#endif //INC_10_PATHS_METRICNAME_HPP
