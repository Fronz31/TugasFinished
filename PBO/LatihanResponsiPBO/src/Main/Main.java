/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Main;

import Model.*;
import View.*;

public class Main {

    public static void main(String[] args) {
        // TODO code application logic here
        Connector.Connect();
        System.out.println("Menjalan Sistem GUI...");
        new ViewLogin();
    }
}
