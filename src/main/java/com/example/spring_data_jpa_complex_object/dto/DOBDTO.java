package com.example.spring_data_jpa_complex_object.dto;

public class DOBDTO {
    private Long id;
    private String birthDate;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getBirthDate() { return birthDate; }
    public void setBirthDate(String birthDate) { this.birthDate = birthDate; }
}
