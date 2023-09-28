/*Grading tags in for all lines marked with *		_x_

The visitor pattern is used 				        _x_
Handles bad input with 1 try-catch			        _x_
Threw the exception in tier 8 (rezone)	     		_x_

Tier 1: running and menu working 			        _x_
Tier 2: set any object at 0, 0 				        _x_
Tier 3: set any object a anywhere			        _x_
Tier 4: handles bad input at this point			    _x_
Tier 5: default grid displays properly 			    _x_
Tier 6: count types * 					            _x_
Tier 7: coloring and menus completed*			    _x_
Tier 8: Rezone *					                _x_
Tier 9: Fix roads*			  		                _x_
     All adjacent pullable objects removed		        _x_
     At least one pullable objects are pulled inwards	_x_
*/
package berry_keiran;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * author: Keiran Berry
 *
 * The City program outputs a menu to the console,
 * giving the user options to interact with the city.
 * The city begins as all blank tiles, and is output
 * to the console after each operation is completed.
 * The user can set and unset tiles to each of the types,
 * set the city to a preset default, and get the counts of
 * each type of zone. The user can also choose to rezone
 * the city, fix the roads, or quit the program.
 */
public class CityStart {
    public static Scanner cin;

    /**
     * \brief Main function for the program
     *
     * Contains try-catch block, outputs the menu, and
     * sends the program to the corresponding function
     * based on what the user requests.
     *
     * @param args
     */
    public static void main(String[] args) {

            City c = new City();
            cin = new Scanner(System.in);
            String menu =
                    "1) Set Tile\n"+
                    "2) Make Default City\n"+
                    "3) Count Zones\n"+
                    "4) Set Tile Color\n"+
                    "5) Rezone\n"+
                    "6) Fix Roads\n"+
                    "0) Quit\n";
            int input = -1;
            while(input != 0) {
                try{
                    System.out.print('\n');
                    c.print();
                    System.out.println(menu);
                    System.out.print("Choice:> ");

                    input = cin.nextInt();
                    if(input > 6 || input < 0){
                        throw new ArithmeticException();
                    }

                    switch(input){
                        case 1 -> setTile(c);
                        case 2 -> makeDefaultCity(c);
                        case 3 -> countZones(c);
                        case 4 -> setTileColor(c);
                        case 5 -> rezone(c);
                        case 6 -> fixRoads(c);
                    }
                }
                catch(ArithmeticException arithmeticException){
                    System.out.print("Number is out of range");
                    cin.nextLine();
                }
                catch(InputMismatchException inputMismatchException){
                    System.out.print("please input an integer");
                    cin.nextLine();
                }
                catch(IllegalStateException illegalStateException){
                    System.out.print("Insufficient open areas");
                }
                catch(Exception ex){
                    cin.nextLine();
                }
            }
    }

    /**
     * \brief Sets tile to specific type
     *
     * Gets inputs for tile type and location
     * from the user, and changes the corresponding
     * tile
     *
     * @param c
     */
    private static void setTile(City c){
        System.out.print("input tile type 1) greenspace 2) water 3) road 4) building 5) empty:> ");
        int input = cin.nextInt();
        if(input < 1 || input > 5){
            throw new ArithmeticException();
        }

        int type = input;

        System.out.print("input location (x y): ");
        int y = cin.nextInt();
        int x = cin.nextInt();

        if(x > 7 || x < 0 || y > 7 || y < 0){
            throw new ArithmeticException();
        }

        c.changeTile(type, x, y);
    }

    /**
     * \brief Make default city
     *
     * Make the current city the default.
     * Uses a 2d array of ints to set the
     * entire city to a preset default.
     *
     * @param c
     */
    private static void makeDefaultCity(City c){
        int[][] defaults= {
                {3, 3, 3, 3, 3, 3, 3},
                {3, 4, 3, 5, 3, 5, 5},
                {3, 3, 3, 3, 3, 2, 2},
                {3, 4, 3, 5, 3, 1, 2},
                {3, 4, 5, 4, 3, 1, 2},
                {3, 4, 4, 4, 3, 1, 2},
                {3, 3, 3, 3, 3, 1, 2}
        };

        for(int i = 0; i < 7; i++){
            for(int j = 0; j < 7; j++){
                c.changeTile(defaults[i][j], i, j);
            }
        }
    }

    /**
     * \brief Count zones
     *
     * Counts how many of each tile type the
     * city contains. Prints the results to the
     * console.
     *
     * @param c
     */
    private static void countZones(City c){
        //GRADING: COUNT
        GetCounts countVisitor = new GetCounts();
        c.accept(countVisitor);
        System.out.print("empty: ");
        System.out.println(countVisitor.getCountEmpty());
        System.out.print("buildings: ");
        System.out.println(countVisitor.getCountBuilding());
        System.out.print("greenspaces: ");
        System.out.println(countVisitor.getCountGreenspace());
        System.out.print("roads: ");
        System.out.println(countVisitor.getCountRoads());
        System.out.print("water: ");
        System.out.println(countVisitor.getCountWater());
    }

    /**
     * \brief Set tile color
     *
     * Takes in user input for tile color and the
     * type of tile for which to change the color.
     * Changes the color of the corresponding groups
     * of tiles in the city.
     *
     * @param c
     */
    private static void setTileColor(City c){
        System.out.print("Input tile type 1) building 2) road 3) non-structure:> ");
        int input = cin.nextInt();
        ColorText.Color color = ColorText.Color.BLACK;

        if(input < 1 || input > 3){
            throw new ArithmeticException();
        }

        System.out.print("Input color 1) red 2) orange 3) blue 4) green 5) black:> ");
        int selection = cin.nextInt();

        if(selection < 1 || selection > 5){
            throw new ArithmeticException();
        }

        switch(selection){
            case 1 -> color = ColorText.Color.RED;
            case 2 -> color = ColorText.Color.ORANGE;
            case 3 -> color = ColorText.Color.BLUE;
            case 4 -> color = ColorText.Color.GREEN;
            case 5 -> color = ColorText.Color.BLACK;
            default -> throw new ArithmeticException();
        }

        //GRADING: COLOR
        ChangeColor colorVisitor = new ChangeColor(color, input);
        c.accept(colorVisitor);
    }

    /**
     * \brief Rezone city
     *
     * If there are fewer than 5 empty space tiles,
     * rezones the city so that the empty tiles are
     * greenspace.
     *
     * @param c
     */
    private static void rezone(City c){
        GetCounts getCounts = new GetCounts();
        c.accept(getCounts);
        if(getCounts.getCountEmpty() > 4){
            throw new IllegalStateException();
        }
        //GRADING: REZONE
        Rezone rezone = new Rezone(c);
        c.accept(rezone);
    }

    /**
     * \brief Fix roads
     *
     * Upon user request, changes the road tiles
     * to reflect the location of roads surrounding the
     * current tile.
     *
     * @param c
     */
    private static void fixRoads(City c){
        FixRoads fixRoads = new FixRoads(c.getTiles());
        c.accept(fixRoads);
    }
}
