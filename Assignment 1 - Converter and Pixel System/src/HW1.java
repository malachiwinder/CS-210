import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.io.*;

/**
 * @author Malachi Winder, 134145125
 * CS240-02
 * Assignment 1
 */
public class HW1
{
    public static void main (String[] args)
    {
        Scanner scan = new Scanner(System.in);
        int selection = -1;
        
        // Main menu loop — keeps showing options until the user selects 6 (quit)
        while (selection != 0)
        {
            System.out.println("1. ASCII Converter");
            System.out.println("2. Number Base Converter");
            System.out.println("3. Image Reader");
            System.out.println("4. Image Creator");
            System.out.println("5. Boundary Test Cases");
            System.out.println("6. Quit");
            System.out.println("Enter selection: ");
            selection = scan.nextInt();
            scan.nextLine(); // clear leftover newline before any sub-menu reads text
            
            // Dispatch to the chosen tool based on menu selection
            switch (selection)
            {
                case 1:
                    ASCII_Converter.run(scan);
                    break;
                case 2:
                    Number_Base_Converter.run(scan);
                    break;
                case 3:
                    Image_Reader.run(scan);
                    break;
                case 4:
                    Image_Creator.run(scan);
                    break;
                case 5:
                    Boundary_Tests.run(scan);
                    break;
                case 6:
                    System.out.println("See you later!");
                    selection = 0; // ensure the loop actually exits on quit
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
                    break;
            }
        }
        
        scan.close(); // close once, after the loop is completely done
    }
    
    // Build an ASCII-to-decimal converter.
    
    static class ASCII_Converter
    {
        public static void run(Scanner scan)
        {
            // Read a name/string from the user and print each character's ASCII decimal value
            System.out.println("Enter your name: ");
            String name = scan.nextLine();
            
            for (char c : name.toCharArray())
            {
                // Casting char to int gives its underlying ASCII/Unicode value
                System.out.println(c + "-->" + (int) c);
            }
        }
    }
    
    // Build a number-base converter supporting binary, decimal, octal, and hexadecimal.
    static class Number_Base_Converter
    {
        public static void run(Scanner scan)
        {
            int selection = -1;
            
            // Sub-menu loop for repeated conversions until user selects 0
            while (selection != 0)
            {
                // Prompt for the base of the input number
                System.out.println("Convert from:");
                System.out.println("1. Binary");
                System.out.println("2. Decimal");
                System.out.println("3. Octal");
                System.out.println("4. Hexadecimal");
                System.out.println("0. Back to main menu");
                System.out.println("Enter selection: ");
                
                int userSelection = scan.nextInt();
                scan.nextLine(); // clear leftover newline from nextInt()
                
                if (userSelection == 0)
                {
                    return; // return to main menu
                }
                
                // Prompt for the base to convert into
                System.out.println("Convert to: ");
                System.out.println("1. Binary");
                System.out.println("2. Decimal");
                System.out.println("3. Octal");
                System.out.println("4. Hexadecimal");
                System.out.println("Enter selection: ");
                
                int convertSelection = scan.nextInt();
                scan.nextLine();
                
                // Read the actual number to convert, as a String (to support hex letters )
                System.out.println("Enter the number: ");
                String inputNumber = scan.nextLine();
                
                // Translate menu choices (1-4) into actual numeric bases (2, 10, 8, 16)
                int fromBase = choiceToBase(userSelection);
                int toBase = choiceToBase(convertSelection);
                
                // Convert: source base -> decimal -> target base
                int decimalValue = toDecimal(inputNumber, fromBase);
                String result = fromDecimal(decimalValue, toBase);
                
                System.out.println("Result: " + result);
                
                selection = userSelection;
            }
        }
        
        //menu option (1-4) to its corresponding numeric base
        public static int choiceToBase(int menuChoice)
        {
            switch (menuChoice)
            {
                case 1:
                    return 2;
                case 2:
                    return 10;
                case 3:
                    return 8;
                case 4:
                    return 16;
                default:
                    return -1; // sentinel value for an invalid menu choice
            }
        }
        
        // Converts a number string in an arbitrary base into its decimal (base 10) value
        public static int toDecimal(String numberString, int fromBase)
        {
            int result = 0;
            for (int i = 0; i < numberString.length(); i++)
            {
                char c = numberString.charAt(i);
                int digitValue = charToValue(c);
                result = result * fromBase + digitValue;
            }
            
            return result;
        }
        
        // Converts a decimal value into a string representation in the target base
        // using repeated division; remainders come out least-significant-digit first,
        // so each new digit is inserted at the front of the result.
        public static String fromDecimal(int decimalValue, int toBase)
        {
            if (decimalValue == 0)
            {
                return "0";
            }
            
            StringBuilder nums = new StringBuilder();
            
            while (decimalValue > 0)
            {
                int remainder = decimalValue % toBase;
                nums.insert(0, valueToChar(remainder));
                decimalValue = decimalValue / toBase;
            }
            
            return nums.toString();
        }
        
        // Converts a single character digit ('0'-'9', 'A'-'F', 'a'-'f') into its numeric value
        public static int charToValue(char c)
        {
            if (c >= '0' && c <= '9')
            {
                return c - '0';
            }
            else if (c >= 'A' && c <= 'F')
            {
                return 10 + (c - 'A');
            }
            else if (c >= 'a' && c <= 'f')
            {
                return 10 + (c - 'a');
            }
            else
            {
                return -1; // invalid char
            }
        }
        
        // Converts a numeric digit value (0-15) into its display character ('0'-'9' or 'A'-'F')
        public static char valueToChar(int v)
        {
            if (v < 10)
            {
                return (char) ('0' + v);
            }
            else
            {
                return (char) ('A' + (v - 10));
            }
        }
    }
    
    static class Image_Reader
    {
        public static void run (Scanner scan)
        {
            // Read an image file and print the RGB values of every pixel
            System.out.println("Enter the name of the image file: ");
            String filename = scan.nextLine();
            
            try
            {
                BufferedImage image = ImageIO.read(new File(filename));
                
                if (image == null)
                {
                    // getRGB()/read() can return null instead of throwing for unsupported formats
                    System.out.println("Could not read image (format is unsupported or incorrect file path).");
                    return;
                }
                
                int width = image.getWidth();
                int height = image.getHeight();
                
                for (int y = 0; y < height; y++)
                    for (int x = 0; x < width; x++)
                    {
                        // getRGB returns a packed 32-bit ARGB int; shift + mask to isolate each channel
                        int pixel = image.getRGB(x, y);
                        int red = (pixel >> 16) & 0xFF;
                        int green = (pixel >> 8) & 0xFF;
                        int blue  = pixel & 0xFF;
                        
                        System.out.println(x + ", " + y + " -> R:" + red + " G:" + green + " B:" + blue);
                    }
            }
            catch (IOException e)
            {
                System.out.println("Error reading file: " + e.getMessage());
            }
        }
    }
    
    static class Image_Creator
    {
        public static void run(Scanner scan)
        {
            // Build a new image from user-entered pixel values and save it to disk
            System.out.println("Enter width: ");
            int width = scan.nextInt();
            
            System.out.println("Enter height: ");
            int height = scan.nextInt();
            scan.nextLine(); // clear newline
            
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            
            for (int y = 0; y < height; y++)
            {
                for (int x = 0; x < width; x++)
                {
                    System.out.println("Pixel (" + x + ", " + y + ") - Enter red green blue: ");
                    int red = scan.nextInt();
                    int green = scan.nextInt();
                    int blue = scan.nextInt();
                    
                    // Pack the three 8-bit channels into a single 32-bit RGB int
                    int packed = (red << 16) | (green << 8) | blue;
                    
                    image.setRGB(x, y, packed);
                }
            }
            
            scan.nextLine();
            
            System.out.println("Enter output filename: ");
            
            String outputFilename = scan.nextLine();
            
            try
            {
                ImageIO.write(image, "png", new File(outputFilename));
                System.out.println("Image saved.");
            }
            catch (IOException e)
            {
                System.out.println("Error writing file.");
            }
        }
    }
    
    static class Boundary_Tests
    {
        public static void run(Scanner scan)
        {
            System.out.println("Running byte boundary tests. Please be patient\n");
            
            // (byte value, expected unsigned result)
            testByteToUnsigned((byte) 0, 0);
            testByteToUnsigned((byte) 127, 127);
            testByteToUnsigned((byte) -1, 255);
            testByteToUnsigned((byte) -128, 128); // most negative byte value
            
            System.out.println("\nRunning pixel round-trip tests. Please be patient\n");
            
            testPixelRoundTrip(0, 0, 0);         // black
            testPixelRoundTrip(255, 255, 255);   // white
            testPixelRoundTrip(255, 0, 0);       // red
            
            System.out.println("\nBoundary tests complete. Thank you for your patience.");
        }
        
        // Makes sure that a byte with 0xFF recovers the correct unsigned value
        private static void testByteToUnsigned(byte b, int expected)
        {
            int unsignedValue = b & 0xFF;
            String result = (unsignedValue == expected) ? "PASS" : "FAIL";
            
            System.out.println("byte " + b + " -> unsigned " + unsignedValue
                                   + " (expected " + expected + ") [" + result + "]");
        }
        
        // Confirms that packing RGB values into an int and unpacking them
        // returns the original values
        private static void testPixelRoundTrip(int red, int green, int blue)
        {
            int packed = (red << 16) | (green << 8) | blue;
            
            int unpackedRed   = (packed >> 16) & 0xFF;
            int unpackedGreen = (packed >> 8) & 0xFF;
            int unpackedBlue  = packed & 0xFF;
            
            boolean pass = (unpackedRed == red) && (unpackedGreen == green) && (unpackedBlue == blue);
            
            System.out.println("RGB(" + red + "," + green + "," + blue + ") -> packed " + packed
                                   + " -> unpacked (" + unpackedRed + "," + unpackedGreen + "," + unpackedBlue + ") ["
                                   + (pass ? "PASS" : "FAIL") + "]");
        }
    }
}
