package org.example;

import javax.swing.*;
import java.awt.*;


class MindMapPanel extends JPanel {
    private Section rootSection;
    private int horizontalSpace = 200; // Initial horizontal space

    public MindMapPanel(Section root) {
        this.rootSection = root;
        setPreferredSize(new Dimension(1200, 800)); // Adjust the size as needed
    }

    public void setHorizontalSpace(int space) {
        this.horizontalSpace = space;
        repaint(); // Redraw the panel with new spacing
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawSection(g, rootSection, 10, 30, 0);
    }

    private void drawSection(Graphics g, Section section, int x, int y, int depth) {
        if (section == null) return;

        // Drawing settings
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 12));
        FontMetrics fm = g.getFontMetrics();

        // Prepare title and content text
        String title = section.title;
        String content = section.content;
        int titleWidth = fm.stringWidth(title) + 20;
        int contentWidth = fm.stringWidth(content) + 20;
        int maxWidth = Math.max(titleWidth, contentWidth);
        int heightPerLine = fm.getHeight();
        int titleHeight = heightPerLine + 5;
        int contentHeight = (content.isEmpty() ? 0 : heightPerLine * (content.split("\n").length + 1));

        // Draw title
        g.drawRect(x, y, maxWidth, titleHeight);
        g.drawString(title, x + 10, y + titleHeight - 5);

        // Draw content
        if (!content.isEmpty()) {
            g.setFont(new Font("Arial", Font.PLAIN, 12));
            g.drawRect(x, y + titleHeight, maxWidth, contentHeight);
            drawStringMultiLine(g, content, x + 10, y + titleHeight + 5);
        }

        int horizontalSpace = this.horizontalSpace; // Horizontal space between boxes
        int verticalSpace = calculateVerticalSpace(section); // Calculate vertical space

        int childX = x + maxWidth + horizontalSpace;
        int childY = y;

        for (Section sub : section.subsections) {
            // Draw line from parent to child
            g.drawLine(x + maxWidth, y + titleHeight / 2, childX, childY + titleHeight / 2);

            drawSection(g, sub, childX, childY, depth + 1);
            childY += verticalSpace;
        }
    }

    private int calculateVerticalSpace(Section section) {
        int leafCount = countLeafNodes(section);
        int baseSpace = 30; // Base vertical space
        return baseSpace + (leafCount * 10); // Increase space based on leaf count
    }

    private int countLeafNodes(Section section) {
        if (section.subsections.isEmpty()) {
            return 1; // This is a leaf node
        }
        int count = 0;
        for (Section sub : section.subsections) {
            count += countLeafNodes(sub);
        }
        return count;
    }

    private void drawStringMultiLine(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n")) {
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
        }
    }
}

// ... [rest of the MindMapCreator class]

