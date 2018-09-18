#include <iostream>
#include "cmath"

using namespace std;

int n;

void voidFunction() {
    cout << "\tVoid function:" << endl;
}

int factorial(int n) {
    int factorial = 1;
    for (int i = 2; i <= n; ++i) {
        factorial *= i;
    }
    return factorial;
}

void print(int i) {
    cout << "\t" << "print(" << i << "): " << i << endl;
}

void print(bool b) {
    cout << "\t" << "print(" << b << "): " << b << endl;
}

void print(const string &s) {
    cout << "\t" << "print(" << s << "): " << s << endl;
}

int power(int degree, int foundation = 3) {
    return (int) pow(foundation, degree);
}

int increment(int n) {
    return ++n;
}

int decrement(int &n) {
    return --n;
}

int &getElement(int index, int array[]) {
    return array[index];
}

class Student {
public:
    static int studentCounter;

    Student() {
        ++studentCounter;
        _course = 1;
    }

    explicit Student(int course) {
        ++studentCounter;
        this->_course = course;
    }

    void failExam() {
        ++_debts;
    }

    void retakeExam() {
        if (_debts > 0) {
            --_debts;
        }
    }

    void graduate() {
        --studentCounter;
        delete this;
    }

    void up() {
        if (_course < 4) {
            ++_course;
        } else {
            graduate();
        }
    }

    int getCourse() {
        return _course;
    }

    int getDebts() {
        return _debts;
    }

private:
    int _debts = 0;
    int _course;
};

int Student::studentCounter = 0;

void task(int number) {
    switch (number) {
        case 1:
            cout << "Functions" << endl;
            voidFunction();
            printf("\tReturnable function: %d! is %d\n", n, factorial(n));
            break;
        case 2:
            cout << "Function overloading" << endl;
            print(n);
            print(false);
            print(*new string("Hello, world"));
            break;
        case 3:
            cout << "Default arguments" << endl;
            printf("\t3 in %d degree is %d\n", n, power(n));
            break;
        case 4:
            cout << "Pass by value, pass by reference and return by reference" << endl;
            cout << "\t" << "Pass by value:" << endl;
            cout << "\t\t" << "n is " << n << endl;
            cout << "\t\t" << "Increment n is " << increment(n) << endl;
            cout << "\t\t" << "n still is " << n << endl;

            cout << "\t" << "Pass by reference:" << endl;
            cout << "\t\t" << "n is " << n << endl;
            cout << "\t\t" << "Decrement n is " << decrement(n) << endl;
            cout << "\t\t" << "n is " << n << " now" << endl;

            {
                cout << "\t" << "Return by reference:" << endl;
                int array[] = {1, 2, 3};
                cout << "\t\t" << "Array is ";
                for (int i : array) {
                    cout << i << " ";
                }
                cout << endl;
                getElement(2, array) = 228;
                cout << "\t\t" << "Now array is ";
                for (int i : array) {
                    cout << i << " ";
                }
                cout << endl;
            }
            break;
        case 5:
            cout << "Classes, objects and constructors" << endl;
            auto *vasya = new Student();
            auto *petya = new Student();
            cout << "\tAmount of students: " << Student::studentCounter << endl;
            vasya->failExam();
            vasya->failExam();
            vasya->up();
            petya->failExam();
            petya->up();
            printf("\tVasya: %d course, %d debts\n", vasya->getCourse(), vasya->getDebts());
            printf("\tPetya: %d course, %d debts\n", petya->getCourse(), petya->getDebts());
            vasya->retakeExam();
            petya->graduate();
            auto *vanya = new Student(4);
            vanya->up();
            printf("\tVasya: %d course, %d debts\n", vasya->getCourse(), vasya->getDebts());
            cout << "\tAmount of students: " << Student::studentCounter << endl;
    }
}

void run(int amount) {
    cout << "Enter the integer > 0 ";
    cin >> n;
    for (int i = 1; i <= amount; ++i) {
        cout << "Task " << i << ": ";
        task(i);
        cout << "_______________________________________" << endl;
    }
}

int main() {
    cout << boolalpha;
    run(5);
}