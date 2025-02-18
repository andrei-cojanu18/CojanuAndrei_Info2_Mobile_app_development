public class FriendlyNumbers {
    public static boolean divSum(int num, int check) {
        int sum = 0;

        for (int i = 1; i <= num/2; i++) {
            if (num % i == 0) {
                sum += i;
            }
        }

        System.out.println(sum);

        if(sum == check){
            return true;
        }
        else return false;
    }
}
