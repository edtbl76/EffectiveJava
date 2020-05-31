package EnumsAndAnnotations_5.EnumsOverIntConstants_34.ConstantSpecificMethodImplementation;

public enum PayrollDayBad {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;

    private static final int MINS_PER_SHIFT = 8 * 60;

    /*
        Great code, but dangerous in terms of MAINTENANCE.
        - if we add a vacation day, but forget to add it to the switch statement, then the worker will be paid in the
        same manner as a standard weekday.

        SOLUTION 1:
        - to do this safely, we would have to duplicate the overtime pay computation for each constant
        - OR move the computation into two helper methods (one for weekends, one for weekdays) and then invoke those
        methods from each constant.

            (NOTE: the second is the preferred of the two if you HAVE to go that way)

        BOTH of these options result in a lot of boilerplate code
        - reduces readability
        - makes code error prone.

        SOLUTION 2:
        - ultimately, you want to be FORCED to choose the overtime pay strategy each time an enum constant is added
     */
    int pay(int minutesWorked, int payRate) {
        int basePay = minutesWorked * payRate;

        int overtimePay;
        switch(this) {
            case SATURDAY:
            case SUNDAY:
                overtimePay = basePay / 2;
                break;
            default:
                overtimePay = minutesWorked <= MINS_PER_SHIFT ? 0 : (minutesWorked - MINS_PER_SHIFT) * payRate / 2;
        }
        return basePay + overtimePay;
    }
}
