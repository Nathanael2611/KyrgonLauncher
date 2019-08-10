package fr.arinonia.launcherlib.authlib.responses;

import fr.arinonia.launcherlib.authlib.Profile;

public class AuthenticationResponse extends LoginResponse {

  private Profile[] availableProfiles;

  public AuthenticationResponse(String accessToken, String clientToken, Profile selectedProfile, Profile[] availableProfiles) {
    super(accessToken, clientToken, selectedProfile);
    this.availableProfiles = availableProfiles;
  }

  public Profile[] getAvailableProfiles() {
    return availableProfiles;
  }

}
