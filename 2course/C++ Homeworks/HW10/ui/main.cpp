#include <iostream>
#include <LongitudeSortPartition.h>
#include <TaxicabMetric.h>
#include <FrenchMetric.h>
#include "EuclidMetric.h"
#include "ConsequentPartitionAlgorithm.h"
#include "RandomPartitionAlgorithm.h"
#include "Metric.h"
#include "PathPartitionAlgorithm.h"
#include "Algorithm.hpp"
#include "MetricName.hpp"
#include "Message.hpp"

using std::cout;
using std::cin;
using std::endl;
using std::string;


Graph readGraph(const std::string& filename = "") {
    if (!filename.empty()) {
        freopen(filename.c_str(), "r", stdin);
    }
    std::vector<Vertex*> orders;
    cout << "Enter orders coordinates. Type '" << getMessage(SUBMIT) << "' to stop:\n";
    string from, to;
    cin >> from;
    int id = 0;
    while (from != getMessage(SUBMIT)) {
        cin >> to;
        orders.push_back(new Vertex(id, std::stoi(from), std::stoi(to)));
        cout << " -> " << id + 1 << "\n";
        id++;
        cin >> from;
    }
    Graph graph = Graph(orders);
    for (int i = 0; i < graph.size(); ++i) {
        for (int j = i; j < graph.size(); ++j) {
            if (i == j) {
                continue;
            }
            graph.addEdge(i, j);
        }
    }
    return graph;
}

PathPartitionAlgorithm* chooseAlgorithm() {
    PathPartitionAlgorithm* algorithm = nullptr;
    while (algorithm == nullptr) {
        cout << "Choose algorithm: (available: " <<
        getAlgorithmMessage(CONSEQUENT) << ", " <<
        getAlgorithmMessage(RANDOM) << ", " <<
        getAlgorithmMessage(LONGITUDE) << ")\n";

        string algorithmName;
        cin >> algorithmName;
        if (algorithmName == getAlgorithmMessage(CONSEQUENT)) {
            algorithm = new ConsequentPartitionAlgorithm();

        } else if (algorithmName == getAlgorithmMessage(RANDOM)) {
            algorithm = new RandomPartitionAlgorithm();

        } else if (algorithmName == getAlgorithmMessage(LONGITUDE)) {
            algorithm = new LongitudeSortPartition();

        } else {
            cout << "Unknown algorithm, try again\n";
        }
    }

    return algorithm;
}

Metric* chooseMetric() {
    Metric* metric = nullptr;
    while (metric == nullptr) {
		cout << "Choose metric: (available: " <<
		getMetricMessage(EUCLID) << ", " <<
		getMetricMessage(TAXICAB) << ", " <<
		getMetricMessage(FRENCH) << ")\n";

        string metricName;
        cin >> metricName;
		if (metricName == getMetricMessage(EUCLID)) {
			metric = new EuclidMetric();

		} else if (metricName == getMetricMessage(TAXICAB)) {
			metric = new TaxicabMetric();

		} else if (metricName == getMetricMessage(FRENCH)) {
			metric = new FrenchMetric();
		} else {
			cout << "Unknown metric, try again\n";
		}
    }
    return metric;
}

size_t chooseMaxLength() {
    size_t maxPathLength;
    cout << "Enter maximum path length: ";
    cin >> maxPathLength;
    return maxPathLength;
}

void run() {
    Graph graph = readGraph();

    PathPartitionAlgorithm* algorithm = chooseAlgorithm();
    Metric* metric = chooseMetric();
    size_t maxPathLength = chooseMaxLength();

    auto partition = algorithm->buildPartition(graph, maxPathLength);
    double summaryMetricResult = 0;
    for (int part = 0; part < partition.size(); part++) {
        if (part == 0) {
            cout << "\n";
        }
        cout << "path " << part << ": | ";
        double partMetric = 0;
        for (Edge* edge: partition[part]) {
            cout << edge->getFrom().getId() + 1 << " -> " << edge->getTo().getId() + 1 << " | ";
            partMetric += metric->calculate(*edge);
        }
        cout << "Metric result: " << partMetric;
        summaryMetricResult += partMetric;
        cout << endl;
    };
    cout << "Summary metric result: " << summaryMetricResult << endl;

    delete metric;
    delete algorithm;
}

int main(int argc, char* argv[]) {
    if (argc == 2) {
        freopen(argv[1], "r", stdin);
    }
    run();
    return 0;
}