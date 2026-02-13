public class UserInterface {
    private ProgramControl pc;


    UserInterface(){
        pc = new ProgramControl();
    }

    // Constructor for testing
    UserInterface(ProgramControl pc, String[] args){
        this.pc = pc;
        inputLogic(args);
    }

    public String inputLogic(String[] args){
        if(args.length == 0){
            return pc.start();
        }
        else if(args.length == 1){
            return pc.start(args[0]);
        }
        else if(args.length == 2){
            return pc.start(args[0], args[1]);
        }
        else{
            return "Invalid number of command line arguments: " + args.length +
                    "\nPlease refer to userinterface.txt for valid commands";
        }
    }
}
