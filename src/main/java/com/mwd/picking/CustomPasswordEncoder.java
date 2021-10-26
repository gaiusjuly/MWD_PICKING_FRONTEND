package com.mwd.picking;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//import org.springframework.security.crypto.bcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.apache.logging.log4j.Logger;
//import org.apache.logging.log4j.LogManager;

import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomPasswordEncoder implements PasswordEncoder {
    private static final Logger logger = LoggerFactory.getLogger(CustomPasswordEncoder.class);
    //Logger logger = LogManager.getLogger(CustomPasswordEncoder.class);
    @Override
    public String encode(CharSequence rawPassword) {
        logger.debug("rawPassword "+ rawPassword);

//        String hashed = BCrypt.hashpw(rawPassword.toString(), BCrypt.gensalt(12));
    
//        return rawPassword.toString();
    	    String SHA = ""; 
            try{
                MessageDigest sh = MessageDigest.getInstance("SHA-256"); 
                String str = rawPassword.toString();
                logger.debug("at encode raw data " + str);
                sh.update(str.getBytes()); 
                byte byteData[] = sh.digest();
      
                StringBuffer sb = new StringBuffer(); 
                for(int i = 0 ; i < byteData.length ; i++){
                    sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
                }
                SHA = sb.toString();
            }catch(NoSuchAlgorithmException e){
                //e.printStackTrace();
            	System.err.println(e.getMessage());
                SHA = null; 
            }
        logger.debug("after encode raw data " + SHA);
            return SHA;
        
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {

       // return BCrypt.checkpw(rawPassword.toString(), encodedPassword);
        logger.debug("matches not encode "+ rawPassword);
        logger.debug("matches encode"+ encode(rawPassword));
        logger.debug("encodedPassword "+ encodedPassword);
    	return encode(rawPassword.toString()).equals(encodedPassword);
//    	return true;
    }

}
