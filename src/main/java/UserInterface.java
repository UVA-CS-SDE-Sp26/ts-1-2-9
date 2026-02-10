public class UserInterface {
        private ProgramControl pc;

        UserInterface(String[] args){
            this.pc = new ProgramControl();
            inputLogic(args);
        }



    /**
     * Checks the amount of inputs within the
     */
    public void inputLogic(String[] args){
        if(args.length == 1){
            pc.start();
        }
        else if(args.length == 2){
            pc.start(args[1]);
        }
        else if(args.length == 3){
            pc.start(args[1], args[2]);
        }
        else{
            System.out.print("Invalid number of command line arguments. " +
                    "Please refer to userinterface.txt for valid commands");
        }
    }
}
