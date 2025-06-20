package com.example.spring_data_jpa_complex_object.entity;

@Entity
public class Student {
    @Id @GeneratedValue
    private Long id;
    private String name;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Enrollment> enrollments = new ArrayList<>();
}
