/**
 *Created by errornosignal on 1/23/2017.
 * HW1 Loan Calculator [PROG-1403]
 * LoanCalculator.java - A program for calculating monthly payments, which are generated from related values input by a user.
 * @author: Reid Nolan
 * @since: 01/23/2017
 * @version: 1.0
*/

import java.util.Scanner;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.NumberFormat;
import java.util.InputMismatchException;

/**Takes user input and calculates monthly loan payment*/
public class LoanCalculator
{
    /**main method*/
    public static void main(String[] args)
    {
        //infinite loop (option to terminate program at "continue" prompt)
        while(true)
        {
            //method calls
            getHeader();            //displays program header
            getMonthlyPayment();    //prompts user for data, performs calculations, and displays results
            getContinue();          //allows user to re-iterate the program or terminate
        }
    }

    /**Displays program header*/
    private static void getHeader()
    {
        //format and display program header
        System.out.println("Loan Calculator");
        System.out.println("");
        System.out.println("DATA ENTRY");
    }

    /**Calculate and output formatted calculation results.*/
    private static void getMonthlyPayment()
    {
        //declare and initialize variables
        BigDecimal loanPrincipal = BigDecimal.ZERO;
        BigDecimal interestRate = BigDecimal.ZERO;
        BigDecimal numberYears = BigDecimal.ZERO;
        BigDecimal monthlyPayment = BigDecimal.ZERO;
        final BigDecimal kMONTHS_IN_YEAR = new BigDecimal("12");
        final BigDecimal kINTEREST_DIVISOR = new BigDecimal("1200.00");
        final BigDecimal kONE_HUNDRED = new BigDecimal("100");
        final MathContext kMC = MathContext.DECIMAL64;
        int numberYearsINT = 0;

        //declaration and initialization of output format variables
        final NumberFormat percent = NumberFormat.getPercentInstance();
        final NumberFormat currency = NumberFormat.getCurrencyInstance();
        percent.setMinimumFractionDigits(1);

        //set values of output variables from return values of methods
        loanPrincipal = getLoanPrincipal();
        interestRate = getInterestRate();
        numberYearsINT = getNumberYears();

        //convert integer to BigDecimal for ease of calculation
        numberYears = BigDecimal.valueOf(numberYearsINT);

        //calculate and store monthly payment amount
        monthlyPayment = loanPrincipal.multiply(interestRate.divide(kINTEREST_DIVISOR, kMC).divide
                (BigDecimal.ONE.subtract(BigDecimal.ONE.add(interestRate.divide(kINTEREST_DIVISOR, kMC), kMC).pow
                        ((numberYears.multiply(kMONTHS_IN_YEAR, kMC)).negate().intValue(), kMC), kMC), kMC));

        //output formatted results of calculations
        System.out.println("");
        System.out.println("FORMATTED RESULTS");
        System.out.println("Loan amount:          " + currency.format(loanPrincipal));
        System.out.println("Yearly interest rate: " + percent.format(interestRate.divide(kONE_HUNDRED, kMC)));
        System.out.println("Number of years:      " + numberYears);
        System.out.println("Monthly payment:      " + currency.format(monthlyPayment));
        System.out.println("");
    }

    /**
     * Prompts user for input, validates entry, and returns value of loan amount input by the user.
     * @return loanPrincipal
     */
    private static BigDecimal getLoanPrincipal()
    {
        //declare and initialize variable for while loop condition
        boolean principalIsValid = false;

        //loop for validating loan amount entered by user
        while(!principalIsValid)
        {
            //create new scanner
            Scanner input = new Scanner(System.in);

            try
            {
                //declare and initialize variables
                final BigDecimal kLOAN_MIN = BigDecimal.ZERO;
                final BigDecimal kLOAN_MAX = new BigDecimal("1000000.00");
                BigDecimal loanPrincipal = BigDecimal.ZERO;

                //prompt user and read input
                System.out.print(String.format("%-28s", "Enter loan amount: "));
                loanPrincipal = input.nextBigDecimal();

                //validation of user input
                if(kLOAN_MIN.compareTo(loanPrincipal) < 0)
                {
                    if(kLOAN_MAX.compareTo(loanPrincipal) > 0)
                    {
                        //set loop condition and return loan amount value
                        principalIsValid = true;
                        return loanPrincipal;
                    }
                    else
                    {
                        //display error message on invalid input and retry
                        System.out.println("Error! Number must be less than " + kLOAN_MAX + ".");
                        input.nextLine();
                    }
                }
                else
                {
                    //display error message on invalid input and retry
                    System.out.println("Error! Number must be greater than " + kLOAN_MIN + ".");
                    input.nextLine();
                }
            }
            //exception handler -retry
            catch(InputMismatchException IOE)
            {
                //display warning and clear input stream
                System.out.println("Error! Invalid decimal value. Try again.");
                input.reset();
                input.next();
            }
        }
        return BigDecimal.ZERO;
    }

    /**
     * Prompts user for input, validates entry, and returns value of interest rate input by the user.
     * @return interestRate
     */
    private static BigDecimal getInterestRate()
    {
        //declare and initialize variable for while loop condition
        boolean interestIsValid = false;

        //loop for validating interest amount entered by user
        while(!interestIsValid)
        {
            //create new scanner
            Scanner input = new Scanner(System.in);

            try
            {
                //declare and initialize variables
                final BigDecimal kINTEREST_MIN = BigDecimal.ZERO;
                final BigDecimal kINTEREST_MAX = new BigDecimal("20.00");
                BigDecimal interestRate = BigDecimal.ZERO;

                //prompt user and read input
                System.out.print("Enter yearly interest rate: ");
                interestRate = input.nextBigDecimal();

                //validation of user input
                if(kINTEREST_MIN.compareTo(interestRate) < 0)
                {
                    if(kINTEREST_MAX.compareTo(interestRate) > 0)
                    {
                        //set loop condition and return interest rate of loan
                        interestIsValid = true;
                        return interestRate;
                    }
                    else
                    {
                        //display error message on invalid input and retry
                        System.out.println("Error! Number must be less than " + kINTEREST_MAX + ".");
                        input.nextLine();
                    }
                }
                else
                {
                    //display error message on invalid input and retry
                    System.out.println("Error! Number must be greater than " + kINTEREST_MIN + ".");
                    input.nextLine();
                }
            }
            //exception handler -retry
            catch(InputMismatchException IOE)
            {
                //display warning and clear input stream
                System.out.println("Error! Invalid decimal value. Try again.");
                input.reset();
                input.next();
            }
        }
        return BigDecimal.ZERO;
    }

    /**
     * Prompts user for input, validates entry, and returns value of number of years input by the user.
     * @return numberYears
     */
    private static int getNumberYears()
    {
        //declare and initialize variable for while loop condition
        boolean yearsIsValid = false;

        //loop for validating number of years entered by user
        while(!yearsIsValid)
        {
            //create new scanner
            Scanner input = new Scanner(System.in);

            try
            {
                //declare and initialize variables
                final int kYEARS_MIN = 0;
                final int kYEARS_MAX = 100;
                int numberYears = 0;

                //prompt user and read input
                System.out.print(String.format("%-28s", "Enter number of years: "));
                numberYears = input.nextInt();

                //validation of user input
                if(numberYears > kYEARS_MIN)
                {
                    if(numberYears < kYEARS_MAX)
                    {
                        //set loop condition and return term of loan
                        yearsIsValid = true;
                        return numberYears;
                    }
                    else
                    {
                        //display error message on invalid input and retry
                        System.out.println("Error! Number must be less than " + kYEARS_MAX + ".");
                        input.nextLine();
                    }
                }
                else
                {
                    //display error message on invalid input and retry
                    System.out.println("Error! Number must be greater than " + kYEARS_MIN + ".");
                    input.nextLine();
                }
            }
            //exception handler -retry
            catch(InputMismatchException IOE)
            {
                //display warning and clear input stream
                System.out.println("Error! Invalid integer value. Try again.");
                input.reset();
                input.next();
            }
        }
        return 0;
    }

    /**
     * Prompts user for input, validates entry, and allows user to continue or terminate the program.
     */
    private static void getContinue()
    {
        //create new scanner
        Scanner reader = new Scanner(System.in);

        try
        {
            //loop for allowing user to run the program again or terminate
            while(true)
            {
                //prompt user and read input
                String goOn = "";
                System.out.print("Continue? (y/n): ");
                goOn = reader.nextLine();

                //validation of user input
                if(goOn.length() == 0)
                {
                    //display error message on empty input and retry
                    System.out.println("Error! This entry is required. Try again.");
                }
                //re-iterate program
                else if (goOn.equals("y") || goOn.equals("Y"))
                {
                    //declare and initialize variable for stars
                    final int starCount = 37;

                    //print line of stars between blank lines
                    System.out.println("");
                    for(int stars = 0; stars < starCount; stars++)
                    {
                        System.out.print("*");
                    }
                    System.out.println("");
                    System.out.println("");
                    break;
                }
                //terminate program
                else if(goOn.equals("n") || goOn.equals("N"))
                {
                    //display exit confirmation message and terminate
                    System.out.println("exiting program...");
                    System.exit(0);
                }
                else
                {
                    //display error message on invalid input and retry
                    System.out.println("Error! Entry must be 'y' or 'n'. Try again.");
                }
            }
        }
        //exception handler -retry
        catch(InputMismatchException IOE)
        {
            //display warning and clear input stream
            System.out.println("Error! Invalid character value. Try again.");
            reader.reset();
            reader.next();
        }
    }
}
//END OF PROGRAM