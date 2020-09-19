package com.arindam.das.imc;

import com.arindam.das.imc.core.manager.Moderator;

public class App
{
    public static void main( String[] args ) {
        Moderator.getInstance().run();
    }
}
