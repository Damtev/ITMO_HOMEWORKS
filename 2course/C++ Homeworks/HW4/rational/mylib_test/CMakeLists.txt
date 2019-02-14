cmake_minimum_required(VERSION 2.8)
project(mylib_test)

add_executable(${PROJECT_NAME} mylib_test.cpp)

include_directories(../lib_rational)

target_link_libraries(${PROJECT_NAME} lib_rational)


install(
	DIRECTORY .
	DESTINATION ../_build/mylib_test
	COMPONENT user
	FILES_MATCHING
		PATTERN "test*.txt")

install(
	DIRECTORY .
	DESTINATION ../_build/mylib_test
	COMPONENT developer
	FILES_MATCHING
		PATTERN "test*.txt")

add_test(
	NAME test1
	COMMAND mylib_test test1.txt)

add_test(
	NAME test2
	COMMAND mylib_test test2.txt)



add_test(
	NAME test3
	COMMAND mylib_test test3.txt)