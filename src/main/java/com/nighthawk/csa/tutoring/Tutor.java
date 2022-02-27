package com.nighthawk.csa.tutoring;

import java.io.Serializable;

public class Tutor implements Serializable {

private String name;
private String subject;
private String description;
private String image;

    public Tutor(String name, String subject, String description, String image) {
        this.name = name;
        this.subject = subject;
        this.description = description;
        this.image = image;
    }

    public Tutor(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
