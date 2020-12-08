

package restaurantpos;

import java.util.ArrayList;

/**
 *
 * @author JSupport
 */
public class ItemDB {

    public static String mainMenuCodes[]    =   {"FOOD","BEVE","FOOD","BEVE","FOOD","BEVE"};
    public static String mainMenuDesc[]     =   {"FOOD","BEVERAGES","FOOD","BEVERAGES","FOOD","BEVERAGES"};
    private static ArrayList list;

    public static ArrayList getSubMenu(String mainMenuCodes){

        list    =   new ArrayList();
        if(mainMenuCodes.equals("FOOD")){
            String subCode[]  =   {"P","B"};
            String subDesc[]  =   {"PIZZA","BURGER"};

            list.add(subCode);
            list.add(subDesc);

        }else if(mainMenuCodes.equals("BEVE")){
            String subCode[]  =   {"FJ","HB"};
            String subDesc[]  =   {"Fruit Juice","Hot Beverages"};

            list.add(subCode);
            list.add(subDesc);
        }
        return list;
    }
}
