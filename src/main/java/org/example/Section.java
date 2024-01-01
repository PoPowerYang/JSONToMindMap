package org.example;

import java.util.ArrayList;
import java.util.List;

public class Section {
    String title;
    String content;
    List<org.example.Section> subsections;

    public Section(String title, String content) {
        this.title = title;
        this.content = content;
        this.subsections = new ArrayList<>();
    }

    // Add a subsection
    public void addSubsection(org.example.Section subsection) {
        this.subsections.add(subsection);
    }

    // Method to visualize this section (and its subsections)
    public void visualize(String indent) {
        System.out.println(indent + "Title: " + title);
        System.out.println(indent + "Content: " + content);
        for (org.example.Section sub : subsections) {
            sub.visualize(indent + "  ");
        }
    }
}
