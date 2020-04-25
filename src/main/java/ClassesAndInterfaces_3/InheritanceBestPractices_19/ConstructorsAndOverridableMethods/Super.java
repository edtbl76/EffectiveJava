package ClassesAndInterfaces_3.InheritanceBestPractices_19.ConstructorsAndOverridableMethods;

public class Super {

    /*
        Example of a constructor that violates the rule of NOT invoking overridable methods from any
        constructor (directly or indirectly)
     */
    public Super() {
        overrideMe();
    }

    public void overrideMe() {};
}
