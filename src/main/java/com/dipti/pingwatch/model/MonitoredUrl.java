package com.dipti.pingwatch.model;

import jakarta.persistence.*;

@Entity
@Table(name = "monitored_urls")
public class MonitoredUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;
    private String name; // friendly name like "Google"

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
