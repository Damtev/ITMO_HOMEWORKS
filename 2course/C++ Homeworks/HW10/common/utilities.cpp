//
// Created by damtev on 01/09/18.
//

#include <random>
#include <iostream>

namespace utilities {
    double getRandomNumber() {
        std::random_device r;
        std::default_random_engine random_engine(r());
        std::uniform_real_distribution<double> uniform_dist(0, 1);
        return uniform_dist(random_engine);
    }

    int getRandomInt(int min, int max) {
        std::random_device r;
        std::default_random_engine e1(r());
        std::uniform_int_distribution<int64_t> uniform_dist(min, max - 1);
        return (int) uniform_dist(e1);
    }
}
