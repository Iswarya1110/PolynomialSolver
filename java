import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONObject;
import java.util.*;

public class Main {

    static BigInteger baseToDecimal(String value, int base) {
        return new BigInteger(value, base);
    }

    static BigInteger findConstant(BigInteger[] x, BigInteger[] y, int k) {
        BigInteger result = BigInteger.ZERO;

        for (int i = 0; i < k; i++) {
            BigInteger num = BigInteger.ONE;
            BigInteger den = BigInteger.ONE;

            for (int j = 0; j < k; j++) {
                if (i == j) continue;
                num = num.multiply(x[j].negate());
                den = den.multiply(x[i].subtract(x[j]));
            }

            result = result.add(y[i].multiply(num).divide(den));
        }
        return result;
    }

    public static void main(String[] args) throws Exception {

       
        String content = new String(
                Files.readAllBytes(Paths.get("testcase.json"))
        );

        JSONObject obj = new JSONObject(content);
        JSONObject keys = obj.getJSONObject("keys");

        int k = keys.getInt("k");

        BigInteger[] x = new BigInteger[k];
        BigInteger[] y = new BigInteger[k];

        int count = 0;

        for (String key : obj.keySet()) {
            if (key.equals("keys")) continue;
            if (count == k) break;

            JSONObject root = obj.getJSONObject(key);

            int base = Integer.parseInt(root.getString("base"));
            String value = root.getString("value");

            x[count] = new BigInteger(key);
            y[count] = baseToDecimal(value, base);

            count++;
        }

        System.out.println(findConstant(x, y, k));
    }
}
