package it.unicam.cs.table;

final class TableStyles {
    private static final String[] LIGHT_BORDERS = {"─", "│", "┌", "┬", "┐", "├", "┼", "┤", "└", "┴", "┘"};
    private static final String[] BOLD_BORDERS = {"━", "┃", "┏", "┳", "┓", "┣", "╋", "┫", "┗", "┻", "┛"};
    private static final String[] DOUBLE_LINED_BORDERS = {"═", "║", "╔", "╦", "╗", "╠", "╬", "╣", "╚", "╩", "╝"};

    public static String[] getTableStyle(TableTypes type) {
        return switch (type) {
            case BOLD -> BOLD_BORDERS;
            case DOUBLE_LINED -> DOUBLE_LINED_BORDERS;
            default -> LIGHT_BORDERS;
        };
    }
}
