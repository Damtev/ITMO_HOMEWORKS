cmake_minimum_required(VERSION 3.12)
project(HW12)

set(CMAKE_CXX_STANDARD 14)

set(BUILD_A A_Tree)
set(SOURCE_FILES_A A_Tree.cpp)
add_executable(${BUILD_A} ${SOURCE_FILES_A})

set(BUILD_B B_Permutations)
set(SOURCE_FILES_B B_Permutations.cpp)
add_executable(${BUILD_B} ${SOURCE_FILES_B})

set(BUILD_C C_Queue.cpp)
set(SOURCE_FILES_C C_Queue.cpp)
add_executable(${BUILD_C} ${SOURCE_FILES_C})

set(BUILD_D D_Stack.cpp)
set(SOURCE_FILES_D D_Stack.cpp)
add_executable(${BUILD_D} ${SOURCE_FILES_D})

set(BUILD_E E_Nth.cpp)
set(SOURCE_FILES_E E_Nth.cpp)
add_executable(${BUILD_E} ${SOURCE_FILES_E})

set(BUILD_F F_BinarySearch.cpp)
set(SOURCE_FILES_F F_BinarySearch.cpp)
add_executable(${BUILD_F} ${SOURCE_FILES_F})

set(BUILD_G G_TestingSystem.cpp)
set(SOURCE_FILES_G G_TestingSystem.cpp)
add_executable(${BUILD_G} ${SOURCE_FILES_G})

set(BUILD_H H_Set.cpp)
set(SOURCE_FILES_H H_Set.cpp)
add_executable(${BUILD_H} ${SOURCE_FILES_H})

set(BUILD_I I_Map.cpp)
set(SOURCE_FILES_I I_Map.cpp)
add_executable(${BUILD_I} ${SOURCE_FILES_I})

set(BUILD_J J_NthMaximum.cpp)
set(SOURCE_FILES_J J_NthMaximum.cpp)
add_executable(${BUILD_J} ${SOURCE_FILES_J})

set(BUILD_K K_MoveToBegin.cpp)
set(SOURCE_FILES_K K_MoveToBegin.cpp)
add_executable(${BUILD_K} ${SOURCE_FILES_K})