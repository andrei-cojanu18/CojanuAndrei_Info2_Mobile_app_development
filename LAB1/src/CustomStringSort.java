public class CustomStringSort {
    public static String sortString(String input) {
        StringBuilder lowercase = new StringBuilder();
        StringBuilder uppercase = new StringBuilder();

        for (char c : input.toCharArray()) {
            if (Character.isLowerCase(c)) {
                lowercase.append(c);
            } else if (Character.isUpperCase(c)) {
                uppercase.append(c);
            }
        }

        return lowercase.toString() + uppercase.toString();
    }
}
