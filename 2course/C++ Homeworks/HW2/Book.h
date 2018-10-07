//
// Created by damtev on 26.09.18.
//

#ifndef HW2_BOOK_H
#define HW2_BOOK_H

#endif //HW2_BOOK_H

class Book {
public:
	static const int TITLE_LEN = 20;
	void input();
    void purchase();
private:
    int _bookNumber;
    char _bookTitle[TITLE_LEN + 1];
    float _price;
    float _totalCost(int n);
};