public class UserInterface {
    UserInterface(String[] args){
        inputLogic(args);
    }

    /**
     * Checks the amount of inputs within the
     */
    public void inputLogic(String[] args){
        if(args.length == 1){
            ProgramControl pc = new ProgramControl();
        }
        else if(args.length == 2){
            ProgramControl pc = new ProgramControl(args[1]);
        }
        else if(args.length == 3){
            ProgramControl pc = new ProgramControl(args[1], args[2]);
        }
        else{
            System.out.print("Invalid number of command line arguments. " +
                    "Please refer to userinterface.txt for valid commands");
        }
    }
}
