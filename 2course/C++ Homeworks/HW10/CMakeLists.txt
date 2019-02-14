cmake_minimum_required(VERSION 3.10)
project(10_paths)

set(CMAKE_CXX_STANDARD 14)

include_directories(common metric algorithm graph ui)
add_executable(10_paths
        algorithm/Algorithm.hpp
        algorithm/ConsequentPartitionAlgorithm.cpp
        algorithm/ConsequentPartitionAlgorithm.h
        algorithm/PathPartitionAlgorithm.h
        algorithm/RandomPartitionAlgorithm.cpp
        algorithm/RandomPartitionAlgorithm.h
        common/utilities.cpp
        graph/Edge.cpp
        graph/Edge.h
        graph/Graph.cpp
        graph/Graph.h
        graph/Vertex.cpp
        graph/Vertex.h
        metric/EuclidMetric.cpp
        metric/Metric.h
		metric/TaxicabMetric.h
		metric/TaxicabMetric.cpp
		metric/FrenchMetric.cpp
		metric/FrenchMetric.h
        ui/Message.hpp metric/EuclidMetric.h
        ui/main.cpp common/utilities.h algorithm/LongitudeSortPartition.cpp algorithm/LongitudeSortPartition.h metric/MetricName.hpp)
