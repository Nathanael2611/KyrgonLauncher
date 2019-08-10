package fr.nathanael2611.kyrgon.launcher.auth;

import fr.arinonia.launcherlib.authlib.Auth;
import fr.arinonia.launcherlib.authlib.exceptions.AuthenticationUnavailableException;
import fr.arinonia.launcherlib.authlib.exceptions.RequestException;
import fr.arinonia.launcherlib.authlib.responses.AuthenticationResponse;
import fr.nathanael2611.kyrgon.launcher.KyrgonLauncher;

import java.util.ArrayList;

public class AccountManager {

    private AuthenticationResponse authResponse;

    public void setAuthResponse(AuthenticationResponse authResponse) {
        this.authResponse = authResponse;
    }

    public AuthenticationResponse getAuthResponse() {
        return authResponse;
    }

    public void setSelectedAccount(Account account){
        KyrgonLauncher.getInstance().getUserInfos().setInfos(
                account.getEmail(),
                account.getPassword()
        );
    }

    public Account getSelectedAccount(){
        return new Account(
                KyrgonLauncher.getInstance().getUserInfos().getUsername(),
                KyrgonLauncher.getInstance().getUserInfos().getPassword()
        );
    }

    public static class Account{
        private String email;
        private String password;

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

        public Account(
                String email, String password
        ){
            this.email = email;
            this.password = password;
        }

        public void login() throws AuthenticationUnavailableException, RequestException {
            KyrgonLauncher.getInstance().getAccountManager().setAuthResponse(Auth.authenticate(
                    getEmail(), getPassword()
            ));
        }
    }

}
