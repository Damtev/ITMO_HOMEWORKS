cmake_minimum_required(VERSION 3.12)
project(lib)

set(CMAKE_CXX_STANDARD 14)


set(SOURCE_LIB RationalPolynom.cpp RationalNumber.cpp)
set(HEADERS_LIB ../cmake-build-debug/build/include/RationalPolynom.h ../cmake-build-debug/build/include/RationalNumber.h)
set(PROJECT_SOURCE_DIR ${CMAKE_SOURCE_DIR})
set(CMAKE_ARCHIVE_OUTPUT_DIRECTORY ../build/lib)
set(CMAKE_RUNTIME_OUTPUT_DIRECTORY ../build/bin)
#set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ../build/lib)

add_library(rational STATIC ${SOURCE_LIB} ${HEADERS_LIB})
add_library(rational_shared SHARED ${SOURCE_LIB} ${HEADERS_LIB})