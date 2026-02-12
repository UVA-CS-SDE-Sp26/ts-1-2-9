public class UserInterface {
    private ProgramControl pc;

    UserInterface(String[] args){this(new ProgramControl(), args);}

    // Constructor for testing
    UserInterface(ProgramControl pc, String[] args){
        this.pc = pc;
        inputLogic(args);
    }

    public String inputLogic(String[] args){
        if(args.length == 1){
            return pc.start();
        }
        else if(args.length == 2){
            return pc.start(args[1]);
        }
        else if(args.length == 3){
            return pc.start(args[1], args[2]);
        }
        else{
            return "Invalid number of command line arguments. " +
                    "Please refer to userinterface.txt for valid commands";
        }
    }
}
