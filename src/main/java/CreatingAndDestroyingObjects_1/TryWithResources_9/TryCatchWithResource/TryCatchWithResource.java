package CreatingAndDestroyingObjects_1.TryWithResources_9.TryCatchWithResource;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TryCatchWithResource {

    private static final int BUFFER_SIZE = 16;

    // This is considered out-dated now
    static String firstLineOfFile(String path, String defaultVal) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))){
            return bufferedReader.readLine();
        } catch (IOException e) {
            return defaultVal;
        }
    }

    // this is even worse (trying to manager multiple resources w/ try/finally)
    static void copy(String source, String target) throws IOException {
        //InputStream input = new FileInputStream(source);

        try (InputStream input = new FileInputStream(source);
             OutputStream output = new FileOutputStream(target))
        {
                byte[] buffer = new byte[BUFFER_SIZE];
                int n;
                while ((n = input.read(buffer)) >= 0)
                    output.write(buffer, 0, n);
        }
    }


}
