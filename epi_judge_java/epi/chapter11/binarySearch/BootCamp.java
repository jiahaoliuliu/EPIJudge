package epi.chapter11.binarySearch;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BootCamp {
    public static class Student {
        public String name;
        public double gradePointAverage;

        Student(String name, double gradePointAverage) {
            this.name = name;
            this.gradePointAverage = gradePointAverage;
        }

        private static final Comparator<Student> compGPA = (a, b) -> {
            if (a.gradePointAverage != b.gradePointAverage) {
                // By default, Double.compareTo sorts the list from small to big
                // If we want it to sort the list from big to small, we need to alter a and b
                return Double.compare(b.gradePointAverage, a.gradePointAverage);
            }
            return a.name.compareTo(b.name);
        };

        public static boolean searchStudent(List<Student> students, Student target, Comparator<Student> compGPA) {
            // binary search returns the position of the element in the list if it is found, or the
            // position of the element it would be inserted if it is possible
            //
            return Collections.binarySearch(students, target, compGPA) >= 0;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", gradePointAverage=" + gradePointAverage +
                    '}';
        }
    }

    public static void main(String[] arg) {
        Student felipe = new Student("Felipe", 3.0);
        Student raul = new Student("Raul", 2.0);
        Student logan = new Student("Logan", 1.0);
        List<Student> studentsList = Arrays.asList(felipe, logan, raul);

        studentsList.sort(Student.compGPA);
        System.out.println(Arrays.toString(studentsList.toArray()));
        //{Felipe, Raul, Logan}

        // Search
        Student maria = new Student("Maria", 2.5);
        int positionMaria = Collections.binarySearch(studentsList, maria, Student.compGPA);
        System.out.println(maria + " position in the list is " + positionMaria);

        int positionRaul = Collections.binarySearch(studentsList, raul, Student.compGPA);
        System.out.println(raul + " position in the list is " + positionRaul);
    }
}
