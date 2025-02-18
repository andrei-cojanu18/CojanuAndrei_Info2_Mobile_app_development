public class Main {
    public static void main(String[] args) {
        String input = "hElLoWoRLd";
        System.out.println(CustomStringSort.sortString(input));

        System.out.println();

        if(FriendlyNumbers.divSum(220,284)){
            System.out.println("True");
        }
        else System.out.println("False");

        System.out.println();


        String hexNr = "1A3";
        System.out.println(Integer.parseInt(hexNr, 16));

        System.out.println();

        String path = "UDDDUDUU"; // Example path
        System.out.println(ValleyCounter.countValley(path));



    }
}