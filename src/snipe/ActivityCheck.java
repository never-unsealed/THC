package snipe;

import toxic.Main;

public class ActivityCheck {

    public ActivityCheck(){


        Main.isActive = true;

        long started = System.currentTimeMillis();

        while(System.currentTimeMillis() - started < 300000);

        Main.isActive = false;


    }

}
