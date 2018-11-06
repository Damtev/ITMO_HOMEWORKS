cmake_minimum_required(VERSION 3.12)
project(poly)

set(CMAKE_CXX_STANDARD 14)
set(PROJECT_SOURCE_DIR ${CMAKE_SOURCE_DIR})
set(EXECUTABLE_OUTPUT_PATH ../build/bin)

add_executable(poly rational_poly.cpp)

target_include_directories(poly PRIVATE ../lib_rational)

target_link_libraries(poly rational)