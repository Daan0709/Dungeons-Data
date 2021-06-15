package nl.hu.dungeonsanddata.security;

import nl.hu.dungeonsanddata.domain.Account;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

public class MySecurityContext implements SecurityContext {
    private Account account;
    private String scheme;

    public MySecurityContext(Account account, String scheme){
        this.account = account;
        this.scheme = scheme;
    }

    @Override
    public Principal getUserPrincipal(){
        return this.account;
    }

    @Override
    public boolean isUserInRole(String s){
        if (account.getRole() != null){
            return s.equals(account.getRole());
        }
        return false;
    }

    @Override
    public boolean isSecure(){
        return "https".equals(this.scheme);
    }

    @Override
    public String getAuthenticationScheme(){
        return SecurityContext.BASIC_AUTH;
    }
}
