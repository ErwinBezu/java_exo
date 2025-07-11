package org.example.exo5.part4;

class Student {
    private String name;
    private int age;

    public Student() {}

    public Student(String name, int age) throws InvalidAgeException {
        if (age < 0) {
            throw new InvalidAgeException("L'âge ne peut pas être négatif : " + age);
        }
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Étudiant: " + name + ", Age: " + age + " ans";
    }
}
