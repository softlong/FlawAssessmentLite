package com.softgrid.flawAssessmentLite.util;

import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.HashSet;
import java.util.Set;

public class JavaFxUtils {

    public static void hideRow(RowConstraints... rows) {
        for (RowConstraints row : rows) {
            row.setMinHeight(0);
            row.setPrefHeight(0);
        }
    }

    public static void showRow(double minHeight, double prefHeight, RowConstraints... rows) {
        for (RowConstraints row : rows) {
            row.setMinHeight(minHeight);
            row.setPrefHeight(prefHeight);
        }
    }

    public static void hideNode(Node... nodes) {
        for (Node node : nodes) {
            node.setVisible(false);
            if (node instanceof ComboBox) {
                ((ComboBox) node).setValue(null);
            } else if (node instanceof TextField){
                ((TextField) node).setText(null);
            }
        }
    }

    public static void showNode(Node... nodes) {
        for (Node node : nodes) {
            node.setVisible(true);
        }
    }

    public static void deleteRow(GridPane grid, final int row) {
        Set<Node> deleteNodes = new HashSet<>();
        for (Node child : grid.getChildren()) {
            // get index from child
            Integer rowIndex = GridPane.getRowIndex(child);

            // handle null values for index=0
            int r = rowIndex == null ? 0 : rowIndex;

            if (r > row) {
                // decrement rows for rows after the deleted row
                GridPane.setRowIndex(child, r - 1);
            } else if (r == row) {
                // collect matching rows for deletion
                deleteNodes.add(child);
            }
        }

        // remove nodes from row
        grid.getChildren().removeAll(deleteNodes);
    }

    public static void deleteRowsAfterRowIndex(GridPane grid, final int row) {
        Set<Node> deleteNodes = new HashSet<>();
        for (Node child : grid.getChildren()) {
            // get index from child
            Integer rowIndex = GridPane.getRowIndex(child);

            // handle null values for index=0
            int r = rowIndex == null ? 0 : rowIndex;

            if (r > row) {
                deleteNodes.add(child);
            }
        }

        // remove nodes from row
        grid.getChildren().removeAll(deleteNodes);
    }

}
