import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class Test {

    public static void main(String[] args) throws InterruptedException {
//        ProcessBuilder pb = new ProcessBuilder("script.sh");
////        Map<String, String> env = pb.environment();
////        env.put("VAR1", "myValue");
////        env.remove("OTHERVAR");
////        env.put("VAR2", env.get("VAR1") + "suffix");
//        pb.directory(new File("/Users/Cyxzk/Projects/MyLeetcode/src"));
//        try {
//            Process p = pb.start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        try {
            Process process = new ProcessBuilder("/bin/sh","./run.sh").start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;

            while((line=reader.readLine())!=null){
                System.out.println(line);
            }

            int exitCode = process.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
