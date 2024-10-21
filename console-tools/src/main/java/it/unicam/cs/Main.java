package it.unicam.cs;

import it.unicam.cs.table.Table;
import it.unicam.cs.table.TableTypes;

public class Main {
    public static void main(String[] args) {
        Table table = new Table(TableTypes.BOLD);
        table.setHeaders("ID", "Name", " w", "City");

        table.addRow("1" , "Giuseppe", "24", "");
        table.addRow("2", "Younes", "", "Los Angeles");
        table.addRow("3", "Charlie", "35", "Chicago");

        System.out.println(table);
    }
}