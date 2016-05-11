package com.arindam.das;

import com.arindam.das.driver.Config;

public class Main {

    static void regexMatch(String str, String regex){
        System.out.println(str + " matches " + regex + ":: " + str.matches(regex));
    }

    public static void main(String[] args) {
//        Config config = Config.load("/Users/arindam.das/Documents/config.txt", new String[]{"ubuntu", "production"});
//        System.out.println(config.get("common.paid_users_size_limit"));
            String s1 = "ipp_visits_data_test_2015_12_23_1927";
            String regex = "ipp_visits_data_test_[0-9]{4}_[0-9]{2}_[0-9]{2}_[0-9]{4}";
            regexMatch(s1,regex);
    }
}
