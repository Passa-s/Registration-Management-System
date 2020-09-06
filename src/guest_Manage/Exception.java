package guest_Manage;

public class Exception {
	
	public static class AddGuestFailedException extends RuntimeException{
        public AddGuestFailedException(){
            super();
        }

        public AddGuestFailedException(String message){
            super(message);
        }
    }

}
