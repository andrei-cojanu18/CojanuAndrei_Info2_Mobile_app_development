public class ValleyCounter {
    public static int countValley(String s) {
        int counter = 0;
        int sum = 0;

        for (char i : s.toCharArray()) {
            if (i == 'U') {
                sum++;
                if (sum == 0)  // If we return to sea level from below
                    counter++;
                } else if (i == 'D') {
                    sum--;
                }

            }

        return counter;
    }
}
