//
// Created by damtev on 01/09/18.
//

#ifndef INC_10_PATHS_ALGORITHM_H
#define INC_10_PATHS_ALGORITHM_H

#include <string>

enum Algorithm {
    CONSEQUENT,
    RANDOM,
    LONGITUDE
};

std::string getAlgorithmMessage(Algorithm algorithm) {
    switch (algorithm) {
        case CONSEQUENT:
            return "consequent";
        case RANDOM:
            return "random";
        case LONGITUDE:
            return "longitude";
    }
}

#endif //INC_10_PATHS_ALGORITHM_H
