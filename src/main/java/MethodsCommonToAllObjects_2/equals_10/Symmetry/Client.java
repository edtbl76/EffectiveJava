package MethodsCommonToAllObjects_2.equals_10.Symmetry;

import java.util.ArrayList;
import java.util.List;

public class Client {

    public static void main(String[] args) {

        CaseInsensitiveString cis = new CaseInsensitiveString("Denmark");
        String s = "denmark";

        /*
         *  Demonstrates violation of Symmetry property of equals() GENERAL CONTRACT
         */
        // test cis -> to String
        System.out.println("OurObject -> String: " + cis.equals(s));
        // test String -> cis
        System.out.println("String -> OurObject: " + s.equals(cis));

        /*
            Put our CIS inside a list and try to use list.contains(s) to see what happens.
             - this demonstrates how breaking the contract can proliferate "unpredictable behavior"
             into other areas of the language.

         */
        List<CaseInsensitiveString> list = new ArrayList<>();
        list.add(cis);
        System.out.println("List#contains has been tainted by bad equals() -> " + list.contains(s));

        /*
            NOW test it with our "fixed object"
         */
        CaseInsensitiveStringWorks cisw = new CaseInsensitiveStringWorks("Denmark");

        // test cisw -> String
        // (This is false, as the first one should have been)
        System.out.println("OurBetterObject -> String: " + cisw.equals(s));

    }
}
