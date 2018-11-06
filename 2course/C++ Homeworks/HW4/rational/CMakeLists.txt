cmake_minimum_required(VERSION 3.12)
project(HW4)

set(CMAKE_CXX_STANDARD 14)
set(SOURCE_LIB lib_rational)
set(PROJECT rational_poly)

add_subdirectory(${SOURCE_LIB})
add_subdirectory(${PROJECT})

install(FILES lib_rational/RationalNumber.h lib_rational/RationalPolynom.h
		DESTINATION cmake-build-debug/build/include)

enable_testing()