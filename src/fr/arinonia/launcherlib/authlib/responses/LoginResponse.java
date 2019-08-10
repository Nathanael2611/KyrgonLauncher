package fr.arinonia.launcherlib.authlib.responses;

import fr.arinonia.launcherlib.authlib.Profile;

public class LoginResponse {

  private String accessToken;
  private String clientToken;
  private Profile selectedProfile;

  public LoginResponse(String accessToken, String clientToken, Profile selectedProfile) {
    this.accessToken = accessToken;
    this.clientToken = clientToken;
    this.selectedProfile = selectedProfile;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public String getClientToken() {
    return clientToken;
  }

  public Profile getSelectedProfile() {
    return selectedProfile;
  }

}
