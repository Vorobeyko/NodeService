package com.nodeservice.AD;

import com.nodeservice.Configuration.Properties;
import javax.naming.AuthenticationNotSupportedException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import java.util.Hashtable;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.core.env.Environment;

/**
 * Created by avorobey on 21.09.2016.
 * @author Vorobey Alexandr
 * Класс для связи с ActiveDirectory.
 * Для корректной работы данного класа необходимо создать файл conf.properies в котором будут находиться параметры подключения
 */

@Component
public class ActiveDirectory {

    private final Logger _log = LogManager.getLogger(this.getClass());

    private Environment myProperties;

    private static Hashtable<String, String> ldapEnv = new Hashtable<String, String>();
    private static InitialDirContext ldapContext;
    private static SearchControls searchCtls;
    private static String searchBase;
    private static String returnedAtts[]={"sn","givenName", "samAccountName","mail"}; //TODO: Убрать в конфигурационный файл

    private void setLDAPConnection (){
        try {
            this.myProperties = Properties.env;
            ldapEnv.put(Context.INITIAL_CONTEXT_FACTORY,myProperties.getProperty("INITIAL_CONTEXT_FACTORY"));
            ldapEnv.put(Context.PROVIDER_URL, myProperties.getProperty("PROVIDER_URL"));
            ldapEnv.put(Context.SECURITY_AUTHENTICATION, myProperties.getProperty("SECURITY_AUTHENTICATION"));
            ldapEnv.put(Context.SECURITY_PRINCIPAL, myProperties.getProperty("SECURITY_PRINCIPAL"));
            ldapEnv.put(Context.SECURITY_CREDENTIALS, myProperties.getProperty("SECURITY_CREDENTIALS"));
            ldapEnv.put("com.sun.jndi.ldap.read.timeout", "1500");//TODO: Убрать в конфигурационный файл
            ldapContext = new InitialDirContext(ldapEnv);
            searchCtls = new SearchControls();
            searchCtls.setReturningAttributes(returnedAtts);
            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            searchBase = myProperties.getProperty("AD_SEARCH_BASE");
        } catch (AuthenticationNotSupportedException e){
            _log.fatal("Ошибка в заданном параметре SECURITY_AUTHENTICATION в конфигурационном файле" + e);
        }
        catch (NullPointerException e){
            _log.error("Параметры не могут быть нулевыми. Необходимо проверить корректность инициализации myProperties");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public boolean checkUser (String loginUser) throws NamingException{
        this.setLDAPConnection();
        boolean findUser = false;
        try {
            if (!loginUser.equals("admin")) {
                String searchFilter = myProperties.getProperty("AD_SEARCH_FILTER") + loginUser + "))";
                int totalResults = 0;

                NamingEnumeration<SearchResult> answer = ldapContext.search(searchBase, searchFilter, searchCtls);
                while (answer.hasMoreElements()) {
                    SearchResult sr = answer.next();
                    totalResults++;
                    findUser = true;
                    Attributes attrs = sr.getAttributes();
                    _log.info("Авторизация прошла успешно. Полное имя пользователя: " + sr.getName() + "," +
                            "Логин: " + attrs.get("samAccountName") + ", Email: " + attrs.get("mail"));
                }
                if (totalResults == 0) {
                    _log.warn("Запрашиваемый пользователь не найден");
                    return findUser;
                }
            }
        } catch (NullPointerException e){
            _log.error("Один из параметров не может быть нулевым. Возможно стоит проверить корректность инициализации myProperties " + e);
        }
        return findUser;
    }

    public String getNameUser(String nameUser) throws NamingException {
        this.setLDAPConnection();
        String searchFilter = myProperties.getProperty("AD_SEARCH_FILTER") + nameUser + "))";
        NamingEnumeration<SearchResult> answer = ldapContext.search(searchBase, searchFilter, searchCtls);
        while (answer.hasMoreElements()) {
            javax.naming.directory.SearchResult sr = answer.next();
            String getName = sr.getName();
            String userFullName[] = getName.split("=");
            return userFullName[1];
        }
        return "admin";
    }

    public String getUsersEmail (String userFullName) throws NamingException {
        this.setLDAPConnection();
        try {
            String searchFilter = myProperties.getProperty("AD_SEARCH_FILTER_USER_EMAIL") + userFullName + "))";
            NamingEnumeration<SearchResult> answer = ldapContext.search(searchBase, searchFilter, searchCtls);
            while (answer.hasMoreElements()) {
                SearchResult sr = answer.next();
                Attributes attrs = sr.getAttributes();
                String getUserEmail = attrs.get("mail").toString();
                String userEmail[] = getUserEmail.split(" ");
                _log.info("E-mail: " + userEmail[1] + " найден в базе данных ActiveDirectory!");
                return userEmail[1];
            }
            _log.warn("Пользователь не найден");
        } catch (NamingException ne) {
            _log.error("Ошибка связи с Active Directory");
            ne.printStackTrace();
        }
        return null;
    }
}
