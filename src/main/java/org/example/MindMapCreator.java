package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

// Main class to parse JSON and create the mind map
public class MindMapCreator {

    public static void main(String[] args) {
        try {
            // Replace with the path to your JSON file
            String jsonData = new String(Files.readAllBytes(Paths.get("/Users/yanweiyang/VS workspace/JSONToMindMap/src/main/resources/JSONFiles/mindmap2.json")));
            JSONObject obj = new JSONObject(jsonData);

            Section root = new Section(obj.getString("title"), "");
            parseSections(root, obj.getJSONArray("sections"));

            root.visualize("");

            JFrame frame = new JFrame("Mind Map");
            MindMapPanel panel = new MindMapPanel(root);
            frame.setLayout(new BorderLayout());

            // Slider for adjusting horizontal space
            JSlider spaceSlider = new JSlider(JSlider.VERTICAL, 20, 100, 40);
            spaceSlider.setMajorTickSpacing(10);
            spaceSlider.setPaintTicks(true);
            spaceSlider.setPaintLabels(true);

            // Add listener to update space
            spaceSlider.addChangeListener(e -> panel.setHorizontalSpace(spaceSlider.getValue()));

            frame.add(panel, BorderLayout.CENTER);
            frame.add(spaceSlider, BorderLayout.EAST);

            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Recursive method to parse sections and subsections
    private static void parseSections(Section parent, JSONArray sections) {
        for (int i = 0; i < sections.length(); i++) {
            JSONObject sectionObj = sections.getJSONObject(i);
            Section section = new Section(sectionObj.getString("title"),
                    sectionObj.optString("content", ""));
            parent.addSubsection(section);

            if (sectionObj.has("subsections")) {
                parseSections(section, sectionObj.getJSONArray("subsections"));
            }
        }
    }
}
