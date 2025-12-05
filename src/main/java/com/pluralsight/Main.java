
package com.pluralsight;

import com.pluralsight.UserInterface.UserInterface;

public class Main {
    /**
     * Main entry point for the Car Dealership application
     * Initializes and starts the user interface
     */
    public static void main(String[] args) {
        UserInterface ui = new UserInterface();
        ui.display();
    }
}