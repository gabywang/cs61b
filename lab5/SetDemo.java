/** @author Xiaowen Wang*/

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;

public class SetDemo {
    public static void main(String[] args) {
        HashSet<String> s = new HashSet<String>();
        s.add("papa"); s.add("bear"); s.add("mama");
        s.add("bear"); s.add("baby"); s.add("bear");
        System.out.println(s);
    }
}