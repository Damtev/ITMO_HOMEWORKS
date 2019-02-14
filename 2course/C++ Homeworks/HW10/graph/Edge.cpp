//
// Created by damtev on 01/09/18.
//

#include "Edge.h"

Edge::Edge(Vertex& from, Vertex& to) : mFrom(from), mTo(to) {}

Edge::~Edge() = default;

bool Edge::operator==(const Edge& rhs) const {
    return mFrom == rhs.mFrom && mTo == rhs.mTo ||
            mFrom == rhs.mTo && mTo == rhs.mFrom;
}

bool Edge::operator!=(const Edge& rhs) const {
    return !(rhs == *this);
}

Vertex& Edge::getFrom() const {
    return mFrom;
}

Vertex& Edge::getTo() const {
    return mTo;
}