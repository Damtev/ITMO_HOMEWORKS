#include <iostream>
#include "ComplexFunction.cpp"
#include "Compare.cpp"
#include "IsSameObject.cpp"
#include "ManBearPig.h"

using namespace std;

void attackMessage(Unit& unit, const char* name) {
	cout << name << " was attacked, his hp are " << unit.getHp() << " now" << endl;
}
void healMessage(Man& man, const char* name) {
	cout << name << " healed, his hp are " << man.getHp() << " now" << endl;
}

void statsMessage(Animal& animal, const char* name) {
	cout << name << " howled, his attack is " << animal.getAttackPoints() << " and his defence is "
	<< animal.getDefencePoints() << " now" << endl;
}

void fight() {
	cout << "FIGHT!!!" << endl;
	ManBearPig manBearPig(200, 45, 35, 50, 3);
	Man man(150, 30, 30, 30, 1);
	Bear bear(150, 35, 35, 35);
	Pig pig(800, 0, 40, 1);

	manBearPig.attack(bear);
	bear.roar();
	attackMessage(bear, "Bear");
	cout << endl;

	for (int i = 0; i < 3; ++i) {
		bear.attack(man);
	}
	attackMessage(man, "Man");
	man.heal();
	healMessage(man, "Man");
	man.heal();
	cout << endl;

	man.attack(pig);
	pig.oink();
	attackMessage(pig, "Pig");
	cout << endl;

	pig.attack(manBearPig);
	manBearPig.roar();
	attackMessage(manBearPig, "ManBearPig");
	manBearPig.heal();
	manBearPig.heal();
	healMessage(manBearPig, "ManBearPig");
	cout << endl;

	manBearPig.beastHowl();
	statsMessage(manBearPig, "ManBearPig");
	manBearPig.attack(pig);
	pig.oink();
	attackMessage(pig, "Pig");
	cout << endl;

	manBearPig.ultimate(pig);
	pig.oink();
	cout << "Pig is dead now" << endl;

	cout << "===================================" << endl;
}

void testCompare() {
	cout << "TESTING COMPARE FUNCTION" << endl;

	string s1("Elf");
	string s2("Archer");

	bool r1 = compare(s1, s2, &string::size);
	bool r2 = compare(s1, s1, &string::size);

	cout << "\t" << s1 << " < " << s2 << " : " << r1 << endl;
	cout << "\t" << s1 << " < " << s1 << " : " << r2 << endl;

	cout << "============================" << endl;
}

void testComplexFunctions() {
	cout << "TESTING COMPLEX FUNCTION" << endl;
	complexFunction complex = [](int x, returnInt f) -> returnIntPointer {
		cout << "In complex\n";

		cout << f(x) << "\n";

		returnIntPointer result = [](char const * x) -> int* {
			cout << "In result\n";
			return const_cast<int*>(reinterpret_cast<const int*>(x));
		};

		return result;
	};

	returnInt other = [](double x) -> int {
		cout << "In parameter: ";
		return (int) x;
	};

	cout << (complex(4, other))("3") << "\n";
	cout << "============================" << endl;
}

void testIsSameObject() {
	cout << "TESTING IS_SAME FUNCTION" << endl;
	ManBearPig manBearPig1(150, 35, 35, 35, 3);
	ManBearPig manBearPig2(800, 0, 40, 1, 2);
	cout << "manBearPig1 is manBearPig2 : " << isSameObject(&manBearPig1, &manBearPig2) << endl;
	cout << "manBearPig1 is manBearPig1 : " << isSameObject(&manBearPig1, &manBearPig1) << endl;
}

int main() {
	boolalpha(cout);
	fight();
	testComplexFunctions();
	testCompare();
	testIsSameObject();
}