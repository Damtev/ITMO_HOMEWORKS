//
// Created by damtev on 23.09.18.
//

#ifndef HW2_STUDENT_H
#define HW2_STUDENT_H

#endif //HW2_STUDENT_H

class Student {
public:
    void takeData();
    void showData();

private:
    int _administrativeNumber;
    char _name[20];
    float _eng;
    float _math;
    float _science;
    float _total;

    float _calculateTotal();
};