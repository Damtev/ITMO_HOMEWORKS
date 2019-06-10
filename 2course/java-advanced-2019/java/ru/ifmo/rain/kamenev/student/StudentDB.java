package ru.ifmo.rain.kamenev.student;

import info.kgeorgiy.java.advanced.student.Group;
import info.kgeorgiy.java.advanced.student.Student;
import info.kgeorgiy.java.advanced.student.StudentGroupQuery;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StudentDB implements StudentGroupQuery {

    private static final Comparator<Group> groupCompBySize = Comparator.comparingInt(value -> value.getStudents().size());

    private final Comparator<Group> groupCompBySizeWithDistinctNames = Comparator.comparingInt(value -> getDistinctFirstNames(value.getStudents()).size());

    private static final Comparator<Group> groupCompByName = Comparator.comparing(Group::getName);

    private Stream<String> getDistinctSortedGroups(Collection<Student> students) {
        return getGroups(new ArrayList<>(students)).stream().distinct().sorted();
    }

    private List<Group> getGroupsByParam(Collection<Student> students, Function<Collection<Student>, List<Student>> sort) {
        return getDistinctSortedGroups(students).map(s -> new Group(s, sort.apply(students.stream()
                .filter(student -> student.getGroup().equals(s)).collect(Collectors.toList())))).collect(Collectors.toList());
    }

    @Override
    public List<Group> getGroupsByName(Collection<Student> students) {
        return getGroupsByParam(students, this::sortStudentsByName);
    }

    @Override
    public List<Group> getGroupsById(Collection<Student> students) {
        return getGroupsByParam(students, this::sortStudentsById);
    }

    private String getGroupByTwoParams(Collection<Student> students, Comparator<Group> comparator1, Comparator<Group> comparator2) {
        return getGroupsByName(students).stream().max(comparator1.thenComparing(comparator2)).map(Group::getName)
                .orElse("");
    }

    @Override
    public String getLargestGroup(Collection<Student> students) {
        return getGroupByTwoParams(students, groupCompBySize, Collections.reverseOrder(groupCompByName));
    }

    @Override
    public String getLargestGroupFirstName(Collection<Student> students) {
        return getGroupByTwoParams(students, groupCompBySizeWithDistinctNames, Collections.reverseOrder(groupCompByName));
    }

    private List<String> getListParams(List<Student> students, Function<Student, String> param) {
        return students.stream().map(param).collect(Collectors.toList());
    }

    @Override
    public List<String> getFirstNames(List<Student> students) {
        return getListParams(students, Student::getFirstName);
    }

    @Override
    public List<String> getLastNames(List<Student> students) {
        return getListParams(students, Student::getLastName);
    }

    @Override
    public List<String> getGroups(List<Student> students) {
        return getListParams(students, Student::getGroup);
    }

    @Override
    public List<String> getFullNames(List<Student> students) {
        return getListParams(students, student -> student.getFirstName() + " " + student.getLastName());
    }

    @Override
    public Set<String> getDistinctFirstNames(List<Student> students) {
        return students.stream().map(Student::getFirstName).collect(Collectors.toCollection(TreeSet::new));
    }

    @Override
    public String getMinStudentFirstName(List<Student> students) {
        return students.stream().min(Student::compareTo).map(Student::getFirstName).orElse(""); //orElse("");
    }

    @Override
    public List<Student> sortStudentsById(Collection<Student> students) {
        return students.stream().sorted(Student::compareTo).collect(Collectors.toList());
    }

    @Override
    public List<Student> sortStudentsByName(Collection<Student> students) {
        return students.stream().sorted(Comparator.comparing(Student::getLastName).thenComparing(Student::getFirstName)
                .thenComparing(Student::getId)).collect(Collectors.toList());
    }

    private List<Student> findStudentsByParam(Collection<Student> students, Predicate<Student> predicate) {
        return students.stream().filter(predicate).collect(Collectors.toList());
    }

    @Override
    public List<Student> findStudentsByFirstName(Collection<Student> students, String firstName) {
        return sortStudentsByName(findStudentsByParam(students, student -> student.getFirstName().equals(firstName)));
    }

    @Override
    public List<Student> findStudentsByLastName(Collection<Student> students, String lastName) {
        return sortStudentsByName(findStudentsByParam(students, student -> student.getLastName().equals(lastName)));
    }

    @Override
    public List<Student> findStudentsByGroup(Collection<Student> students, String group) {
        return sortStudentsByName(findStudentsByParam(students, student -> student.getGroup().equals(group)));
    }

    @Override
    public Map<String, String> findStudentNamesByGroup(Collection<Student> students, String group) {
        return findStudentsByGroup(students, group).stream()
                .collect(Collectors.toMap(Student::getLastName, Student::getFirstName, BinaryOperator.minBy(String::compareTo)));
    }
}
