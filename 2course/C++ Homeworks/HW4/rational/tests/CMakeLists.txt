cmake_minimum_required(VERSION 3.10)
#project(tests)

project(mylib_test)

add_executable(${PROJECT_NAME} mylib_test.cpp)

include_directories(../lib_rational ../polynom)

target_link_libraries(${PROJECT_NAME} lib_rational polynom )

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


add_test(NAME test1
		COMMAND tests)

add_test(NAME test2
		COMMAND tests)

add_test(NAME test3
		COMMAND tests)