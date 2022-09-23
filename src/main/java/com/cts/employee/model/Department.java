package com.cts.employee.model;

public abstract class Department {

    abstract int getNumberOfEmployees();
    abstract String getDepartmentDescription();

}

sealed class Engineering extends Department permits Development, Testing, Data  {

    @Override
    public int getNumberOfEmployees() {
        return 200;
    }

    @Override
    public String getDepartmentDescription() {
        return "At engineering, amazing products are built";
    }

    public static void main(String[] args) {
        Engineering engineering = new Engineering();
    }
}

final class Development extends Engineering {}
final class Testing extends Engineering {}
final class Data extends Engineering {}

sealed class Sales extends Department permits LeadGeneration, Qualifier, Closing, Upselling {
    @Override
    int getNumberOfEmployees() {
        return 300;
    }

    @Override
    String getDepartmentDescription() {
        return "At sales, the amazing products that are built are sold here";
    }
}

final class LeadGeneration extends Sales{}
final class Qualifier extends Sales {}
final class Closing extends Sales{}
final class Upselling extends Sales {}
