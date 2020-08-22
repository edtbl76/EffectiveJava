package Serialization_12.SerializationProxies_90.Example;

import Serialization_12.DefensiveReadObjectMethods_88.ImmutableClassWithDefensiveCopy.Period;

import java.io.Serializable;
import java.util.Date;

public class SerializationProxy  implements Serializable {
    private final Date start;
    private final Date end;

    SerializationProxy(Period period) {
        this.start = period.start();
        this.end = period.end();
    }

    private static final long serialVersionUID = 42;
}
