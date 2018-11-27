//
// Created by damtev on 22.11.18.
//

typedef int (*returnInt)(double);
typedef int* (*returnIntPointer)(char const*);
typedef returnIntPointer (*complexFunction)(int, returnInt);