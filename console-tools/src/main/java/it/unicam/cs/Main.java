package it.unicam.cs;

import it.unicam.cs.tables.Table;
import it.unicam.cs.tables.TableTypes;

public class Main {
    public static void main(String[] args) {
        Table table = new Table(TableTypes.BOLD);
        table.setHeaders("ID", "Name", " w", "City");

        table.addRow("1" , "Alice", "20", "");
        table.addRow("2", "Bob", "", "Los Angeles");
        table.addRow("3", "Charlie", "35", "Chicago");

        System.out.println(table);
    }
}