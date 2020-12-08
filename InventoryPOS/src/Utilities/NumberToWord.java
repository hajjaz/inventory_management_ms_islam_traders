/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

/**
 *
 * @author Hajjaz
 */
public class NumberToWord {
    public NumberToWord()
        {

        }
        private static String[] units = {
            "", "One", "Two", "Three", "Four", "Five", "Six", "Seven",
            "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen",
            "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"
        };

        private static String[] tens = {
            "", // 0
            "", // 1
            "Twenty", // 2
            "Thirty", // 3
            "Forty", // 4
            "Fifty", // 5
            "Sixty", // 6
            "Seventy", // 7
            "Eighty", // 8
            "Ninety" // 9
        };

        private static String convert(int n)
        {
            if (n < 0)
            {
                return "Minus " + convert(-n);
            }

            if (n < 20)
            {
                return units[n];
            }

            if (n < 100)
            {
                return tens[n / 10] + ((n % 10 != 0) ? " " : "") + units[n % 10];
            }

            if (n < 1000)
            {
                return units[n / 100] + " Hundred" + ((n % 100 != 0) ? " " : "") + convert(n % 100);
            }

            if (n < 1000000)
            {
                return convert(n / 1000) + " Thousand" + ((n % 1000 != 0) ? " " : "") + convert(n % 1000);
            }

            if (n < 1000000000)
            {
                return convert(n / 1000000) + " Million" + ((n % 1000000 != 0) ? " " : "") + convert(n % 1000000);
            }

            return convert(n / 1000000000) + " Billion" + ((n % 1000000000 != 0) ? " " : "") + convert(n % 1000000000);
        }

        private static String doubleConvert(Double n)
        {
            
            String pass = n+"";
            System.out.println("Amount = "+pass);
            String[] token = pass.split("\\.");
            System.out.println("Token1 = "+token[0]+", Token2 = "+token[1]);
            if (token.length > 1)
            {
                String first = token[0];
                String last = token[1];
                try
                {
                    pass = convert(Integer.parseInt(first)) + " ";
                    pass = pass + "Point";
                    for (int i = 0; i < last.length(); i++)
                    {
                        String get = "";
                        get = convert(Integer.parseInt(last));
                        if (get.equals("") || get == null)
                        {
                            pass = pass + " " + "Zero";
                        }
                        else
                        {
                            pass = pass + " " + get;
                        }
                    }

                }
                catch (Exception nf)
                {
                }
            }
            else
            {
                pass = convert(Integer.parseInt(pass));
            }
            return pass;
        }

        /**
         * Returns the Word representation of the given Double Number
         * 
         * @param number Double number
         * @return String form of the number
         */
        public String Convert(Double number)
        {
            String s = "";
            s = doubleConvert(number);
            return s;
        }
        /**
         * Returns the Word representation of the given Integer Number
         * 
         * @param number Integer number
         * @return String form of the number
         */
        public String Convert(int number)
        {
            String s = "";
            s = convert(number);
            return s;
        }

        /**
         * Returns the Word representation of the given Double Number
         * 
         * @param number Double number
         * @return String form of the number
         */
        public static String ConvertDouble(Double number)
        {
            String s = "";
            s = doubleConvert(number);
            return s;
        }
        /**
         * Returns the Word representation of the given Integer Number
         * 
         * @param number Integer number
         * @return String form of the number
         */
        public static String ConvertInteger(int number)
        {
            String s = "";
            s = convert(number);
            return s;
        }
}
