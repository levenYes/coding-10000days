/**
 * VM Args: -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 */

import java.util.ArrayList;
import java.util.List;

public class HeapOOM {
    static class OOMObject {
        Byte[] bytes = new Byte[2000];
    }

    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<OOMObject>();

        int i = 0;
        while(true) {
            i++;
            System.out.println("Printing times: " + i);
            list.add(new OOMObject());
        }
    }
}
