package apis;

import utilities.PropertiesLoader;

public class ApiBase {

    private static final PropertiesLoader propertiesLoader = new PropertiesLoader();
    public static String ApiBaseURL = propertiesLoader.getProperty("baseUrl")+"/api";
    public static final int SUCCESS = 200;


}
