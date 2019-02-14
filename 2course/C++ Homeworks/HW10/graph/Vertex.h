//
// Created by damtev on 01/09/18.
//

#ifndef INC_10_PATHS_VERTEX_H
#define INC_10_PATHS_VERTEX_H

#include <vector>

class Edge;

class Vertex {
public:
    explicit Vertex(int id, int longitude, int latitude);

    ~Vertex();

    Vertex(const Vertex& other);
    Vertex& operator=(const Vertex& other);

    Vertex(Vertex&& other) noexcept;
    Vertex& operator=(Vertex&& other) noexcept;

    bool operator==(const Vertex& rhs) const;

    bool operator!=(const Vertex& rhs) const;

    int getId() const;

    int getLongitude() const;

    int getLatitude() const;

    std::vector<Edge*>& getAdjacent();

private:
    int mId;
    int mLongitude;
    int mLatitude;
    std::vector<Edge*> mAdjacent{};
};

#endif //INC_10_PATHS_VERTEX_H
