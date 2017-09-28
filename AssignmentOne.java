import java.util.Arrays;
import java.util.Scanner;

/**
 * Name: Joseph Tassone
 * Course: COSC4436
 * Description: The program acts as a subnet calculator for an IP or an IP with a VLSM entered.
 */

public class AssignmentOne {
    public static void main(String [] args) {
        Scanner input = new Scanner(System.in);

        //The program loops until specified by the user (type "end")
        while(true) {

            //Prompts the user for an IP or an IP with a VLSM
            System.out.print("Enter an IP or type \"end\": ");
            String ip = input.nextLine();

            //The loop ends once the user types 'end'
            if(ip.equals("end")) {
                System.out.println("\nThanks for using the system!");
                System.exit(0);
            }

            //Uses a regex to split the user's input into an array ('.' , '/', or spaces)
            String temp [] = ip.split("\\.|/|\\s+");

            //If the length corresponds to the containing of a VLSM, pass into the 'vlsm' method
            if(temp.length == 5 || temp.length == 8) {
                vlsm(temp);
            }

            //For all other lengths pass into the 'classic' method (could be improperly formatted)
            else {
                classic(temp);
            }
        }
    }

    //Input: Receives a string array of any length from the main method
    //Precondition: input must be formatted as 'x.x.x.x' or 'x.x.x.x/y' or 'x.x.x.x y.y.y.y'
    //Postcondition: Either prints the resulting ip/subnet information then returns to main, or throws an exception
    //Description: The method verifies an IP is formatted correctly, and then prints out the information related to it
    public static void classic(String temp []) {

        //Entire method wrapped in try-catch to ensure proper formatting and too avoid uncaught errors
        try {

            //Creates a new int ipArray, and then parses the IP string values from temp into the ipArray
            //Will throw an exception if a value is beyond 255 or if it can't be parsed to an int (example: 'aaaa')
            int ipArray [] = new int[temp.length];
            for(int i = 0; i < temp.length; i++) {
                ipArray[i] = Integer.parseInt(temp[i]);
                if(ipArray[i] > 255) {
                    throw new Exception();
                }
            }

            //Determines based on the first octet, which class the IP falls into and then prints result
            if(ipArray[0] == 0) {
                System.out.println("0.0.0.0 to 0.255.255.255 isn't routable, " +
                        "nor is it a general-purpose device address!\n");
            }
            else if(ipArray[0] == 127) {
                System.out.println("Class A addresses 127.0.0.0 to 127.255.255.255 are " +
                        "reserved for loopback and diagnostic functions.\n");
            }
            else if(ipArray[0] >= 1 && ipArray[0] < 127) {
                System.out.println("Network Class: A");
                System.out.println("Subnet Mask: 255.0.0.0");
                System.out.println("CIDR: /8");
                System.out.println("Hosts Per Subnet: 16777214");
                System.out.println("Network Address: " + (ipArray[0] & 255) + "." + (ipArray[1] & 0) +
                        "." + (ipArray[2] & 0) + "." + (ipArray[3] & 0));
                System.out.println("Broadcast Address: " + (ipArray[0] | 0) + "." + (ipArray[1] | 255) +
                        "." + (ipArray[2] | 255) + "." + (ipArray[3] | 255));
                System.out.println("Bits in Host: 24");
                System.out.println("Bits in Network: 8\n");

            }
            else if(ipArray[0] < 192) {
                System.out.println("Network Class: B");
                System.out.println("Subnet Mask: 255.255.0.0");
                System.out.println("CIDR: /16");
                System.out.println("Hosts Per Subnet: 65534");
                System.out.println("Network Address: " + (ipArray[0] & 255) + "." + (ipArray[1] & 255) +
                        "." + (ipArray[2] & 0) + "." + (ipArray[3] & 0));
                System.out.println("Broadcast Address: " + (ipArray[0] | 0) + "." + (ipArray[1] | 0) +
                        "." + (ipArray[2] | 255) + "." + (ipArray[3] | 255));
                System.out.println("Bits in Host: 16");
                System.out.println("Bits in Network: 16\n");

            }
            else if(ipArray[0] < 224) {
                System.out.println("Network Class: C");
                System.out.println("Subnet Mask: 255.255.255.0");
                System.out.println("CIDR: /24");
                System.out.println("Hosts Per Subnet: 254");
                System.out.println("Network Address: " + (ipArray[0] & 255) + "." + (ipArray[1] & 255) +
                        "." + (ipArray[2] & 255) + "." + (ipArray[3] & 0));
                System.out.println("Broadcast Address: " + (ipArray[0] | 0) + "." + (ipArray[1] | 0) +
                        "." + (ipArray[2] | 0) + "." + (ipArray[3] | 255));
                System.out.println("Bits in Host: 8");
                System.out.println("Bits in Network: 24\n");

            }
            else if(ipArray[0] < 240) {
                System.out.println("Class D address was entered, which is reserved for Multicasting.\n");
            }
            else if(ipArray[0] < 256) {
                System.out.println("Class E address was entered, which is reserved for experimental purposes only " +
                        "for R&D or Study.\n");
            }
            else {
                System.out.println("An invalid IP was entered, please try again.");
                System.out.println("Format: 'x.x.x.x' or 'x.x.x.x/y' or 'x.x.x.x y.y.y.y'\n");
            }
        }
        catch(Exception e) {
            System.out.println("An invalid IP was chosen, try again!");
            System.out.println("Format: 'x.x.x.x' or 'x.x.x.x/y' or 'x.x.x.x y.y.y.y'\n");
        }
    }

    //Input: Receives a string array of length 5 or length 8
    //Precondition: input must be formatted as 'x.x.x.x' or 'x.x.x.x/y' or 'x.x.x.x y.y.y.y'
    //Postcondition: Either prints the resulting ip/subnet information then returns to main, or throws an exception
    //Description: The method verifies an IP, and either the VLSM or an IP address with a dotted decimal notation
    //             subnet mask are formatted correctly, and then prints out the resulting information
    public static void vlsm(String temp[]) {

        //Entire method wrapped in try-catch to ensure proper formatting and too avoid uncaught errors
        try {
            int [] ipArray = new int [temp.length];
            int [] cidrArray;
            int cidr;

            //Parses the IP string values from temp into the ipArray
            //Throws an exception if a value is beyond 255 or if it can't be parsed to an int (example: 'aaaa')
            for(int i = 0; i < temp.length; i++) {
                ipArray[i] = Integer.parseInt(temp[i]);
                if(ipArray[i] > 255) {
                    throw new Exception();
                }
            }

            //Prints the identity as a class D or E (not routable), then returns to the main method
            if(ipArray[0] >= 224) {
                System.out.println("Class D and E addresses are reserved for other purposes!\n");
                return;
            }

            //Prints the identity as 0.0.0.0 based address (not routable), then returns to the main method
            if (ipArray[0] == 0) {
                System.out.println("0.0.0.0 to 0.255.255.255 isn't routable, " +
                        "nor is it a general-purpose device address!\n");
                return;
            }

            //IP of length 8 implies that the address has the subnet with a dotted decimal notation
            if(ipArray.length == 8) {
                cidr = 0;

                //Break the original array into the dotted decimal subnet and the actual IP
                cidrArray = Arrays.copyOfRange(ipArray, 4, 8);
                ipArray = Arrays.copyOfRange(ipArray, 0, 4);

                //Sends the cidrArray to the validateSubnet, to ensure it's a valid subnet, returns to main if not
                if(validateSubnet(cidrArray) == false) {
                    System.out.println("Please check to ensure your CIDR notation is correct!");
                    System.out.println("Format: 'x.x.x.x' or 'x.x.x.x/y' or 'x.x.x.x y.y.y.y'\n");
                    return;
                }

                //Varifies that the subnet is 255.255.255.255, which doesn't have any hosts
                if(cidrArray[3] == 255) {
                    System.out.println("Please make a different choice as 255.255.255.255 has a single host!\n");
                    return;
                }

                //Loops through the cidrArray, and counts the number of '1' bits in the subnet (determines actual cidr)
                for(int i = 0; i < cidrArray.length; i++) {
                    cidr += Integer.bitCount(cidrArray[i]);
                }
            }

            //This is executed if the ipArray is of length 5, which means it's has a VLSM (not dotted decimal)
            else {

                //If the cidr isn't in the correct range 1-32, an exception is thrown (not formatted correctly)
                cidr = ipArray[4];
                if(cidr < 1 || cidr > 32) {
                    throw new Exception();
                }

                //32 isn't used as it represents only a single host
                if(cidr == 32) {
                    System.out.println("/32 represents a single host!\n");
                    return;
                }

                //Passes the cidr to the subnetMask method to get an array representing the dotted decimal subnet
                cidrArray = subnetMask(cidr);
                ipArray = Arrays.copyOfRange(ipArray, 0, 4);
            }

            //Reaching here means everything is formatted correctly, therefore the IP/Subnet information is printed
            System.out.println("Subnet Mask: " + cidrArray[0] + "." + cidrArray[1] + "." + cidrArray[2] + "." +
                    cidrArray[3]);
            System.out.println("CIDR: /" + cidr);
            System.out.println("Hosts Per Subnet: " + (int)(Math.pow(2.0, (32 - cidr)) - 2) );
            System.out.println("Network Address: " + (ipArray[0] & cidrArray[0]) + "." + (ipArray[1] & cidrArray[1]) +
                    "." + (ipArray[2] & cidrArray[2]) + "." + (ipArray[3] & cidrArray[3]));
            System.out.println("Broadcast Address: " + ((ipArray[0] | (255 - cidrArray[0])) +
                    "." + (ipArray[1] | (255 - cidrArray[1]))) + "." + (ipArray[2] | (255 - cidrArray[2])) +
                    "." + (ipArray[3] | (255 - cidrArray[3])));
            System.out.println("Bits in Host: " + (32 - cidr));
            System.out.println("Bits in Network: " + cidr);
            System.out.println();
        }
        catch(Exception e) {
            System.out.println("Try again!");
            System.out.println("Format: 'x.x.x.x' or 'x.x.x.x/y' or 'x.x.x.x y.y.y.y'\n");
        }
    }

    //Input: An integer array of length 4
    //Precondition: Array has already been checked to ensure it's made of numerical values, and is a valid size (4)
    //Postcondition: Returns true or false as to whether the subnet is valid (returns to the vlsm method)
    //Description: The method accepts an array representing the dotted decimal subnet, and varifies whether it's
    //             formatted correctly (whether it corresponds to a valid subnet address)
    private static boolean validateSubnet(int [] temp) {

        //A dotted decimal subnet cannot be 0.0.0.0
        if(temp[0] == 0 && temp[1] == 0 && temp[2] == 0 && temp[3] == 0) {
            return false;
        }

        //Loops through the subnet array, and either returns false or finishes (then returns true)
        for(int i = 0; i < temp.length; i++) {

            //Only certain numbers can be in a subnet, which returns false if it isn't one of the values
            if ((temp[i] != 128) && (temp[i] != 192) && (temp[i] != 224) && (temp[i] != 240) && (temp[i] != 248)
                    && (temp[i] != 252) && (temp[i] != 255) && (temp[i] != 0) && (temp[i] != 254)) {
                return false;
            }

            //The octets are in decreasing order from left to right (or they're equal)
            if (i < temp.length - 1) {
                if (temp[i] < (temp[i + 1])) {
                    return false;
                }

                //If two of the octets are equal then the previous has to be 255 or 0 (example: 128.0.0.0)
                if (temp[i] == temp[i + 1] && temp[i] != 255) {
                    if (temp[i] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    //Input: An integer representing the cidr notation
    //Precondition: The number representing the cidr has already been ranged checked (between 1 and 31)
    //Postcondition: Returns an integer array representing the dotted decimal subnet (to the vlsm method)
    //Description: The method accepts an integer representing the cidr notation and returns the dotted decimal subnet
    private static int [] subnetMask(int cidr) {
        int array [] = {0, 0, 0, 0};

        //Less than 8; add it to first octet by performing 2n-1 then shift on 8 - cidr (one position off otherwise)
        if(cidr <= 8) {
            int shift = 8 - cidr;
            array [0] = (int)(Math.pow(2.0, cidr) - 1) << shift;
        }

        //Greater than 8; spread it across the octets by reducing until 0 is hit
        else {
            int temp = cidr;
            int i = 0;
            int shift = 0;
            while(temp > 0) {
                if(temp - 8 >= 0) {
                    array[i] = (int)(Math.pow(2.0, 8) - 1);
                    temp -= 8;
                    i++;
                }
                else {
                    shift = 8 - temp;
                    array [i] = (int)(Math.pow(2.0, temp) - 1) << shift;
                    temp -= 8;
                }
            }
        }
        return array;
    }
}
