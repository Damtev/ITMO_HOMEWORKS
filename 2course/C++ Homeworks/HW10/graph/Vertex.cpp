//
// Created by damtev on 01/09/18.
//

#include "Vertex.h"

Vertex::Vertex(int id, int longitude, int latitude) :
        mId(id), mLongitude(longitude), mLatitude(latitude) {}

Vertex::~Vertex() {
    mId = -1;
    mLongitude = 0;
    mLatitude = 0;
};

Vertex::Vertex(Vertex&& other) noexcept {
    mId = other.mId;
    mLongitude = other.mLongitude;
    mLatitude = other.mLatitude;
    std::move(other.mAdjacent.begin(), other.mAdjacent.end(), mAdjacent.begin());
}

Vertex& Vertex::operator=(Vertex&& other) noexcept {
    mId = other.mId;
    mLongitude = other.mLongitude;
    mLatitude = other.mLatitude;
    std::move(other.mAdjacent.begin(), other.mAdjacent.end(), mAdjacent.begin());
    return *this;
}

Vertex::Vertex(const Vertex& other) {
    mId = other.mId;
    mLongitude = other.mLongitude;
    mLatitude = other.mLatitude;
    mAdjacent = other.mAdjacent;
}

Vertex& Vertex::operator=(const Vertex& other) {
    mId = other.mId;
    mLongitude = other.mLongitude;
    mLatitude = other.mLatitude;
    mAdjacent = other.mAdjacent;
}

bool Vertex::operator==(const Vertex& rhs) const {
    return mId == rhs.mId;
}

bool Vertex::operator!=(const Vertex& rhs) const {
    return !(rhs == *this);
}

int Vertex::getId() const {
    return mId;
}

int Vertex::getLongitude() const {
    return mLongitude;
}

int Vertex::getLatitude() const {
    return mLatitude;
}

std::vector<Edge*>& Vertex::getAdjacent() {
    return mAdjacent;
}
