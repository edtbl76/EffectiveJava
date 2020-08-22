package Serialization_12.DefensiveReadObjectMethods_88.ImmutableClassWithDefensiveCopy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

public class MutablePeriod {
    // Period instance
    public final Period period;

    // period's start and end fields (we shouldn't have access to this)
    public final Date start;
    public final Date end;

    public MutablePeriod() {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(bos)) {

            // Serialize a VALID Period instance
            out.writeObject(new Period(new Date(), new Date()));

            /*
                Append rogue "previous object refs" for internal
                Date fields in Period. For details, see "Java
                object Serialization Specification, " Section 6.4
             */
            byte[] ref = { 0x71, 0, 0x7e, 0, 5 };   // Ref #5
            bos.write(ref);                         // start field
            ref[4] = 4;                             // Ref # 4
            bos.write(ref);                         // The end field

            // Deserialize Period and "stolen" Date references
            ObjectInputStream in = new ObjectInputStream(
                    new ByteArrayInputStream(bos.toByteArray()));

            period = (Period) in.readObject();
            start  = (Date)   in.readObject();
            end    = (Date)   in.readObject();


        } catch (IOException | ClassNotFoundException e) {
            throw new AssertionError();
        }
    }

    public static void main(String[] args) {
        MutablePeriod mutablePeriod = new MutablePeriod();
        Period period = mutablePeriod.period;
        Date periodEnd = mutablePeriod.end;

        periodEnd.setYear(78);
        System.out.println(period);
        periodEnd.setYear(69);
        System.out.println(period);
    }
}
