package practice.Miscellaneous;


//https://leetcode.com/problems/validate-ip-address/

public class ValidateIPAddress {
    private boolean checkIpv4(String ip) {
        int n = ip.length();
        if(ip.charAt(0)=='.' || ip.charAt(n-1)=='.') {
            return false;
        }
        String[] parts = ip.split("\\.");
        if(parts.length!=4) {
            return false;
        }
        for(int i=0; i<parts.length; i++) {
            try {
                int val = Integer.parseInt(parts[i]);
                if(val<0 || parts[i].length()>3) {
                    return false;
                } else if(val<10 && parts[i].length()>1) {
                    return false;
                } else if(val<100 && parts[i].length()>2) {
                    return false;
                } else if(val>255) {
                    return false;
                }
            } catch(NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    private boolean checkIpv6(String ip) {
        int n = ip.length();
        if(ip.charAt(0)==':' || ip.charAt(n-1)==':') {
            return false;
        }
        String[] parts = ip.split(":");
        if(parts.length!=8){
            return false;
        }
        for(int i=0; i<8; i++) {
            if(parts[i].length()==0) {
                return false;
            } else if(parts[i].length()==5
                    && (parts[i].charAt(0)!='0' || parts[i].charAt(1)=='0')) {
                return false;
            } else if(parts[i].length()>5) {
                return false;
            } else {
                for(int j=0; j<parts[i].length(); j++) {
                    char c = Character.toLowerCase(parts[i].charAt(j));
                    if(!(c>='0'&&c<='9' || c>='a'&&c<='f')) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public String validIPAddress(String IP) {
        if(IP.indexOf('.')!=-1 && checkIpv4(IP)) {
            return "IPv4";
        } else if(IP.indexOf(':')!=-1 && checkIpv6(IP)) {
            return "IPv6";
        }
        return "Neither";
    }
}

class ValidateIPAddressDriver {
    public static void main(String[] args){
        ValidateIPAddress validateIPAddress = new ValidateIPAddress();
        System.out.println(validateIPAddress.validIPAddress("2001:0db8:85a3:0:0:8A2E:0370:7334:"));
        System.out.println(validateIPAddress.validIPAddress("1.1.1.1."));
        System.out.println(validateIPAddress.validIPAddress("2001:0db8:85a3:0000:0:8A2E:0370:733a"));
        System.out.println(validateIPAddress.validIPAddress("2001:0db8:85a3:0:0:8A2E:0370:73341"));
    }

}
