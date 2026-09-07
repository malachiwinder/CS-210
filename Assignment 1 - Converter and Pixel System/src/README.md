# HW1

A menu-driven Java program combining several small utilities: an ASCII/decimal converter, a number base converter, image pixel tools, and a set of boundary-case tests.

## Requirements

- Java JDK 8 or later
- Image files for testing the pixel reader (PNG or JPG)

## Compiling

```
javac HW1.java
```

## Running

```
java HW1
```

You'll be shown a menu:

```
1. ASCII Converter
2. Number Base Converter
3. Image Reader
4. Image Creator
5. Boundary Test Cases
6. Quit
Enter selection:
```

Enter the number corresponding to the tool you want to use. After a tool finishes, you'll return to this menu. Enter `6` to exit the program.

## Features

### 1. ASCII Converter

Prompts for a name (or any string) and prints each character alongside its decimal ASCII value.

**Example:**
```
Enter your name:
Al
A-->65
l-->108
```

### 2. Number Base Converter

Converts a number between binary, decimal, octal, and hexadecimal.

**Steps:**
1. Choose the base to convert *from*.
2. Choose the base to convert *to*.
3. Enter the number (hex digits may be upper or lowercase).

**Example:**
```
Convert from: 4 (Hexadecimal)
Convert to: 2 (Decimal)
Enter the number: 2F
Result: 47
```

Enter `0` at the "Convert from" prompt to return to the main menu.

### 3. Image Reader

Reads an image file (PNG/JPG) and prints the red, green, and blue values for every pixel, along with its `(x, y)` coordinates.

**Example:**
```
Enter the name of the image file:
test.png
0, 0 -> R:255 G:255 B:255
1, 0 -> R:0 G:0 B:0
...
```

**Note:** output length scales with image size — for large images, this can print a very large number of lines. Testing with a small image (e.g. 5x5 or 10x10 pixels) is recommended when verifying correctness.

### 4. Image Creator

Builds a new image from manually entered pixel values and saves it to disk as a PNG.

**Steps:**
1. Enter the image width and height.
2. For every `(x, y)` pixel (prompted in order), enter red, green, and blue values.
3. Enter an output filename to save the result.

**Example:**
```
Enter width: 2
Enter height: 1
Pixel (0, 0) - Enter red green blue: 255 0 0
Pixel (1, 0) - Enter red green blue: 0 255 0
Enter output filename: out.png
Image saved.
```

**Note:** this prompts for every pixel individually, so it's only practical for small test images.

### 5. Boundary Test Cases

Runs a set of built-in checks confirming that signed/unsigned byte conversion and RGB pixel packing behave correctly at their boundary values, including zero, the largest unsigned byte value, and negative two's-complement values. Prints a PASS/FAIL result for each check — no input required.

**Cases covered:**
- Byte-to-unsigned conversion: `0`, `127`, `-1` (two's complement for unsigned `255`), and `-128` (most negative byte value)
- RGB pixel pack/unpack round trips at `(0,0,0)`, `(255,255,255)`, and `(255,0,0)`

## Known Limitations

- No input validation for digits that are invalid for a selected base (e.g., entering `9` for binary).
- No handling for unsupported/corrupt image files beyond a basic null check.
- Hexadecimal digits must be within `A`-`F` / `a`-`f`; other characters are treated as invalid internally but not yet surfaced as a user-facing error message.
- Image Creator does not validate that entered red/green/blue values fall within 0-255.

## File Structure

All code lives in a single file, `HW1.java`, using nested static classes to separate functionality:

- `HW1` — entry point, displays the main menu and delegates to the appropriate tool
- `ASCII_Converter` — character-to-ASCII conversion logic
- `Number_Base_Converter` — base conversion logic (binary/decimal/octal/hex)
- `Image_Reader` — reads an image and prints pixel RGB values
- `Image_Creator` — builds an image from user-entered pixel values and saves it
- `Boundary_Tests` — runs built-in PASS/FAIL checks on byte and pixel boundary values

## AI Policy Acknowledgement

I have neither given nor received unauthorized aid in completing this work, nor have I presented someone
else's work as my own.

I acknowledge that AI was used to draft this README and assist with some clarification, bugs, and errors in my program.