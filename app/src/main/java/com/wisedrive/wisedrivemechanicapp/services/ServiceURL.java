    package com.wisedrive.wisedrivemechanicapp.services;
    public class ServiceURL
    {
        private final static Boolean isProduction = false;
        private final static String ProductURL = "https://dealerappapis.wisedrive.in/";

        private final static String DevelopmentURL = "http://164.52.217.96:30027/";

        public final static String BASEURL = isProduction ? ProductURL : DevelopmentURL;

        public static String add_car_exteriorimagesurl="mechanicVehicle/addexteriorphotos";
        public static String add_engine_images="inspection/addimagesvideos";




    }