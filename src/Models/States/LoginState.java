package Models.States;

import Models.FileManagers.ProfilesFileManager;
import Models.Game;
import Models.Logger;
import Views.MenuPanel;

public class LoginState extends State {

    public LoginState(Game game){
        super(game);
    }

    public String login(String username , String password){
        if(username.equals("") || password.equals("")){
            return "Please fill all the boxes";
        }
        if(!ProfilesFileManager.getProfilesFileManager().doesPlayerNameExists(username)){
            return "Username doesn't exists";
        }else if(!ProfilesFileManager.getProfilesFileManager().checkPlayer(username , password)){
            return "Password is not correct";
        }else{
            this.game.setState(this.game.getMenuState());
            this.game.setPlayer(ProfilesFileManager.getProfilesFileManager().getPlayer(username));
            Logger.getLogger().log("Login" , "Successful login" , this.game.getPlayer());
            MenuPanel.getMenuPanel().setGoldLabel(this.game.getPlayer().getPlayerGolds());
            return "Successful";
        }
    }

    private boolean isUsernameAvailable(String username){
        return !ProfilesFileManager.getProfilesFileManager().doesPlayerNameExists(username);
    }

    public String signUp(String username , String password){
        if(username.equals("") || password.equals("")){
            return "Please fill all the boxes";
        }
        if(isUsernameAvailable(username)){
            ProfilesFileManager.getProfilesFileManager().createNewPlayer(username , password);
            this.game.setState(this.game.getMenuState());
            this.game.setPlayer(ProfilesFileManager.getProfilesFileManager().getPlayer(username));
            Logger.getLogger().createPlayerLog(this.game.getPlayer());
            MenuPanel.getMenuPanel().setGoldLabel(this.game.getPlayer().getPlayerGolds());
            return "Successful";
        }else{
            return "Username already exists";
        }
    }

}
