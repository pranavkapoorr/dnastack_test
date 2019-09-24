package com.dnastack.interview.beaconsummarizer.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Organization {
    private String id;
    private String name;
    private String description;
    private LocalDate createdDate;
    private String url;
    private String address;
}
