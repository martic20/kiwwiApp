package local.martic20.img;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marti.casas on 17/05/17.
 */

public class Elements {
    public String name,desc, type,price;
    public int img;


    public Elements(String name,String desc,String type,String price,  int img) {
       this.name = name;
        this.desc=desc;
        this.img = getImgId(img);
        this.price=price;
        this.type=type;
    }

    public Elements(String name,String img) {
        this.name = name;
        this.desc="";
        this.img = getImgId(Integer.valueOf(img));
        this.price="";
        this.type="";
    }


    public static int getImgId(int num){
        switch(num){
            case 0:
                return R.drawable.dish1;
            case 1:
                return R.drawable.dish2;
            case 2:
                return R.drawable.dish3;
            case 3:
                return R.drawable.dish4;
            case 4:
                return R.drawable.dessert5;
            case 5:
                return R.drawable.dessert6;
            case 6:
                return R.drawable.drink7;
            case 7:
                return R.drawable.drink8;
        }
        return R.drawable.logo;
    }
    // This method creates an ArrayList that has three Person objects
    // Checkout the project associated with this tutorial on Github if
    // you want to use the same images.

}
