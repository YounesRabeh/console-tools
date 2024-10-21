package it.unicam.cs.table;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A class to create and print tables in the console
 * @author Younes Rabeh
 */
public class Table {
    /** The rows of the table */
    private List<List<String>> rows = new ArrayList<>();
    /** The headers of the table */
    private List<String> headers;
    /** The widths of the columns */
    private List<Integer> columnWidths;
    /** The borders of the table */
    private String[] borders;

    /**
     * Create a table with the specified style
     * @param tableType the style of the table
     * @see TableTypes
     */
    public Table(TableTypes tableType) {
        this.borders = TableStyles.getTableStyle(tableType);
    }

    /**
     * Create a table with the default style
     */
    public Table() {
        this.borders = TableStyles.getTableStyle(TableTypes.NORMAL);
    }

    /**
     * Set the headers of the table
     * @param headers the headers of the table
     * @throws IllegalArgumentException if headers are empty, null, or contain duplicates
     */
    public void setHeaders(List<String> headers) {
        validateHeaders(headers); // Validate headers
        this.headers = trimHeaders(headers); // Trim whitespace
        columnWidths = new ArrayList<>();

        // Set column widths based on header lengths initially
        for (String header : this.headers) {
            columnWidths.add(header.length());
        }
    }

    /**
     * Set the headers of the table
     * @param headers the headers of the table
     * @throws IllegalArgumentException if headers are empty, null, or contain duplicates
     */
    public void setHeaders(String... headers) {
        setHeaders(List.of(headers));
    }



    /**
     * Trim leading and trailing whitespace from each header.
     * @param headers the headers to trim
     * @return a list of trimmed headers
     */
    private List<String> trimHeaders(List<String> headers) {
        List<String> trimmedHeaders = new ArrayList<>();
        for (String header : headers) {
            String trimmedHeader = header.toUpperCase().trim();
            if (trimmedHeaders.contains(trimmedHeader)) {
                throw new IllegalArgumentException("Duplicate header found: " + trimmedHeader);
            }
            trimmedHeaders.add(trimmedHeader);
        }
        return trimmedHeaders;
    }

    /**
     * Add a row to the table
     * @param row the row to add
     */
    public void addRow(List<String> row) {
        List<String> adjustedRow = new ArrayList<>();

        for (int i = 0; i < headers.size(); i++) {
            if (i < row.size() && row.get(i) != null && !row.get(i).isEmpty()) {
                adjustedRow.add(row.get(i).trim());
            } else {
                adjustedRow.add("_null_");  // Simulate italic null
            }
        }

        rows.add(adjustedRow);

        // Adjust column widths based on row contents
        for (int i = 0; i < adjustedRow.size(); i++) {
            columnWidths.set(i, Math.max(columnWidths.get(i), adjustedRow.get(i).length()));
        }
    }

    /**
     * Add a row to the table
     * @param row the row to add
     */
    public void addRow(String... row) {
        addRow(List.of(row));
    }

    /**
     * Check for duplicates in the headers list.
     * @param headers the headers to check
     * @throws IllegalArgumentException if duplicates are found
     */
    private void validateHeaders(List<String> headers) {
        if (headers == null || headers.isEmpty()) {
            throw new IllegalArgumentException("Headers cannot be null or empty.");
        }
        Set<String> headerSet = new HashSet<>();
        for (String header : headers) {
            if (header == null || header.trim().isEmpty()) {
                throw new IllegalArgumentException("Headers cannot contain null or empty values.");
            }
            if (!headerSet.add(header)) {
                throw new IllegalArgumentException("Duplicate header found: " + header);
            }
        }
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        append(sb, generateTopBorder());
        append(sb, generateRow(headers));
        append(sb, generateMiddleBorder());
        for (List<String> row : rows) {
            append(sb, generateRow(row));
        }
        append(sb, generateBottomBorder());
        return sb.toString();
    }

    /**
     * Append a row to the table
     * @param sb the string builder to append to
     * @param row the row to append
     */
    private void append(StringBuilder sb, StringBuilder row) {
        sb.append(row).append("\n");
    }

    /**
     * Generate the top border of the table
     * @return the top border
     */
    private StringBuilder generateTopBorder() {
        StringBuilder sb = new StringBuilder();
        sb.append(borders[TableRegion.UPPER_LEFT_INTERSECTION.ordinal()]);  // ┌ or equivalent
        for (int i = 0; i < columnWidths.size(); i++) {
            sb.append(borders[TableRegion.LINE.ordinal()].repeat(columnWidths.get(i) + 2));  // ─ or equivalent
            if (i < columnWidths.size() - 1) {
                sb.append(borders[TableRegion.UPPER_CENTER_INTERSECTION.ordinal()]);  // ┬ or equivalent
            }
        }
        sb.append(borders[TableRegion.UPPER_RIGHT_INTERSECTION.ordinal()]);  // ┐ or equivalent
        return sb;
    }


    /**
     * Generate the middle border of the table
     * @return the middle border
     */
    private StringBuilder generateMiddleBorder() {
        StringBuilder sb = new StringBuilder();
        sb.append(borders[TableRegion.MIDDLE_LEFT_INTERSECTION.ordinal()]);  // ├ or equivalent
        for (int i = 0; i < columnWidths.size(); i++) {
            sb.append(borders[TableRegion.LINE.ordinal()].repeat(columnWidths.get(i) + 2));  // ─ or equivalent
            if (i < columnWidths.size() - 1) {
                sb.append(borders[TableRegion.MIDDLE_CENTER_INTERSECTION.ordinal()]);  // ┼ or equivalent
            }
        }
        sb.append(borders[TableRegion.MIDDLE_RIGHT_INTERSECTION.ordinal()]);  // ┤ or equivalent
        return sb;
    }


    /**
     * Generate the bottom border of the table
     * @return the bottom border
     */
    private StringBuilder generateBottomBorder() {
        StringBuilder sb = new StringBuilder();
        sb.append(borders[TableRegion.LOWER_LEFT_INTERSECTION.ordinal()]);  // └ or equivalent
        for (int i = 0; i < columnWidths.size(); i++) {
            sb.append(borders[TableRegion.LINE.ordinal()].repeat(columnWidths.get(i) + 2));  // ─ or equivalent
            if (i < columnWidths.size() - 1) {
                sb.append(borders[TableRegion.LOWER_CENTER_INTERSECTION.ordinal()]);  // ┴ or equivalent
            }
        }
        sb.append(borders[TableRegion.LOWER_RIGHT_INTERSECTION.ordinal()]);  // ┘ or equivalent
        return sb;
    }

    /**
     * Print a row of the table
     * @param row the row to print
     */
    private StringBuilder generateRow(List<String> row) {
        StringBuilder sb = new StringBuilder();
        sb.append(borders[TableRegion.WALL.ordinal()]);  // │ or equivalent
        for (int i = 0; i < row.size(); i++) {
            sb.append(" ");
            sb.append(padRight(row.get(i), columnWidths.get(i)));
            sb.append(" ").append(borders[TableRegion.WALL.ordinal()]);  // │ or equivalent
        }
        return sb;
    }

    /**
     * Pad a string with spaces on the right
     * @param text the text to pad
     * @param length the length to pad to
     * @return the padded text
     */
    private String padRight(String text, int length) {
        return String.format("%-" + length + "s", text);
    }
}
