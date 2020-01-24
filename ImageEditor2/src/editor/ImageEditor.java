package editor;

import java.io.*;

import java.util.Scanner;





public class ImageEditor {


    public static void main(String[] args) {

        try {

            //Read in the data from the input file


            Scanner myScanner = new Scanner(new File(args[0]));

            // myScanner.useDelimiter("(\\\\s+)(#[^\\\\n]*\\\\n)?(\\\\s*)|(#s*)|(#[^\\\\n]*\\\\n)(\\\\s*)");

            //start reading the data

            Image myImage = new Image();

            int width = 0;

            int height = 0;

            myScanner.next();//ignore the magic number at the beginning

            width = myScanner.nextInt();

            height = myScanner.nextInt();

            int maxValue = myScanner.nextInt();//this value is always 255

            myPixel[][] myPixels = new myPixel[height][width];

            for (int i = 0; i < height; i++) {

                for (int j = 0; j < width; j++) {

                    myPixel newPixel = new myPixel();

                    newPixel.setRed(myScanner.nextInt());

                    newPixel.setGreen(myScanner.nextInt());

                    newPixel.setBlue(myScanner.nextInt());

                    myPixels[i][j] = newPixel;

                }

            }

            myImage.setMyPixelMap(myPixels);

            myScanner.close();


            //Manipulate the data depending on the choice of the user

            Image newImage = new Image();

            myPixel[][] myNewPixels = new myPixel[height][width];

            switch (args[2]) {

                case "invert":

                    for (int i = 0; i < height; i++) {

                        for (int j = 0; j < width; j++) {

                            myPixel newPixel = new myPixel();

                            newPixel.setRed(maxValue - myImage.getMyPixelMap()[i][j].getRed());

                            newPixel.setGreen(maxValue - myImage.getMyPixelMap()[i][j].getGreen());

                            newPixel.setBlue(maxValue - myImage.getMyPixelMap()[i][j].getBlue());

                            myNewPixels[i][j] = newPixel;

                        }

                    }

                    newImage.setMyPixelMap(myNewPixels);

                    break;

                case "grayscale":

                    for (int i = 0; i < height; i++) {

                        for (int j = 0; j < width; j++) {

                            myPixel newPixel = new myPixel();

                            int newValue = (myImage.getMyPixelMap()[i][j].getRed() +

                                    myImage.getMyPixelMap()[i][j].getGreen() +

                                    myImage.getMyPixelMap()[i][j].getBlue()) / 3;

                            newPixel.setRed(newValue);

                            newPixel.setGreen(newValue);

                            newPixel.setBlue(newValue);

                            myNewPixels[i][j] = newPixel;

                        }

                    }

                    newImage.setMyPixelMap(myNewPixels);

                    break;

                case "emboss":

                    final int BORDER_CASE_VALUE = 128;

                    for (int i = 0; i < height; i++) {

                        for (int j = 0; j < width; j++) {

                            //Border Case

                            if ((i == 0) || (j == 0)) {

                                myPixel newPixel = new myPixel();

                                newPixel.setBlue(BORDER_CASE_VALUE);

                                newPixel.setGreen(BORDER_CASE_VALUE);

                                newPixel.setRed(BORDER_CASE_VALUE);

                                myNewPixels[i][j] = newPixel;

                            } else {//Non-Border Case

                                myPixel newPixel = new myPixel();

                                int redDiff = myImage.getMyPixelMap()[i][j].getRed() - myImage.getMyPixelMap()[i - 1][j - 1].getRed();

                                int greenDiff = myImage.getMyPixelMap()[i][j].getGreen() - myImage.getMyPixelMap()[i - 1][j - 1].getGreen();

                                int blueDiff = myImage.getMyPixelMap()[i][j].getBlue() - myImage.getMyPixelMap()[i - 1][j - 1].getBlue();

                                int maxDifference = Math.max(Math.abs(redDiff), Math.max(Math.abs(greenDiff), Math.abs(blueDiff)));

                                int scaleValue;

                                if (maxDifference == Math.abs(redDiff)) {

                                    maxDifference = redDiff;

                                } else if (maxDifference == Math.abs(greenDiff)) {

                                    maxDifference = greenDiff;

                                } else {

                                    maxDifference = blueDiff;

                                }

                                scaleValue = BORDER_CASE_VALUE + maxDifference;

                                if (scaleValue < 0)

                                    scaleValue = 0;

                                if (scaleValue > 255)

                                    scaleValue = 255;

                                newPixel.setRed(scaleValue);

                                newPixel.setGreen(scaleValue);

                                newPixel.setBlue(scaleValue);

                                myNewPixels[i][j] = newPixel;

                            }

                        }

                    }

                    newImage.setMyPixelMap(myNewPixels);

                    break;

                case "motionblur":

                    int blurLength = Integer.parseInt(args[3]);

                    if (blurLength <= 0)//Invalid Input

                        System.out.println("Usage: java ImageEditor in-file out-file"

                                + " (grayscale|invert|emboss|motionblur motion-blur-length");

                    for (int i = 0; i < height; i++) {

                        for (int j = 0; j < width; j++) {

                            myPixel newPixel = new myPixel();

                            int sumRed = 0;

                            int sumGreen = 0;

                            int sumBlue = 0;

                            int length = 0;

                            for (int k = j; (k < width) && (k < j + blurLength); k++, length++) {

                                sumRed += myImage.getMyPixelMap()[i][k].getRed();

                                sumGreen += myImage.getMyPixelMap()[i][k].getGreen();

                                sumBlue += myImage.getMyPixelMap()[i][k].getBlue();

                            }

                            int avgRed = sumRed / length;

                            int avgGreen = sumGreen / length;

                            int avgBlue = sumBlue / length;

                            newPixel.setRed(avgRed);

                            newPixel.setGreen(avgGreen);

                            newPixel.setBlue(avgBlue);

                            myNewPixels[i][j] = newPixel;

                        }

                    }

                    newImage.setMyPixelMap(myNewPixels);

                    break;

                default://if non of the top four, it means input error

                    System.out.println("Usage: java ImageEditor in-file out-file"

                            + " (grayscale|invert|emboss|motionblur motion-blur-length");

                    break;

            }


            //Print the new image

            StringBuilder myBuilder = new StringBuilder();

            PrintWriter myWriter = new PrintWriter(new BufferedWriter(new FileWriter(args[1])));

            myBuilder.append("P3\n");

            myBuilder.append(width);

            myBuilder.append(" ");

            myBuilder.append(height);

            myBuilder.append("\n");

            myBuilder.append(maxValue);

            myBuilder.append("\n");

            for (int i = 0; i < height; i++) {

                for (int j = 0; j < width; j++) {

                    myBuilder.append(newImage.getMyPixelMap()[i][j].getRed());

                    myBuilder.append(" ");

                    myBuilder.append(newImage.getMyPixelMap()[i][j].getGreen());

                    myBuilder.append(" ");

                    myBuilder.append(newImage.getMyPixelMap()[i][j].getBlue());

                    myBuilder.append("  ");

                }

                myBuilder.append("\n");

            }

            myWriter.write(myBuilder.toString());

            myWriter.close();

        } catch (Exception myException) {

            System.out.println("Usage: java ImageEditor in-file out-file"

                    + " (grayscale|invert|emboss|motionblur motion-blur-length");

            myException.printStackTrace();

        }

    }
}


