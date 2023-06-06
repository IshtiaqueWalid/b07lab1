
public class StringHelper {
    public static String[] splitAndInclude(String str, String delimeters) {
        String token = "";
        String[] tokens = new String[str.length()];
        int length = 0;
        for (int i = 0; i < str.length(); i++) {
            String c = str.charAt(i) + "";
            // System.out.println(c);
            if (delimeters.indexOf(c) != -1) {
                tokens[length] = token;
                tokens[length + 1] = c;
                token = "";
                length += 2;
                continue;
            }
            token += c;
        }
        tokens[length] = token;
        length++;
        return ArrayHelper.trim(tokens, length);
    }
}
