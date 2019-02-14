mkdir _build
cd _build

set PATH=D:\ITMO\ITMO_HOMEWORKS\2course\C\HW4\rational;%PATH%

cmake^
	-G "MinGW Makefiles"^
	-D CMAKE_INSTALL_PREFIX=D:\ITMO\ITMO_HOMEWORKS\2course\C\HW4\rational\installer^
	D:\ITMO\ITMO_HOMEWORKS\2course\C\HW4\rational
	
mingw32-make
cmake -D COMPONENT=developer -P cmake_install.cmake
pause
cd ..