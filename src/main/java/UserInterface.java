public class UserInterface {
    UserInterface(String[] args){
        inputLogic(args);
    }
    public void inputLogic(String[] args){
        if(args == null){System.out.print("passed");}
        else{System.out.print("failed");}
    }
}
