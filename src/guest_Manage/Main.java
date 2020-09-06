package guest_Manage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;


public class Main {

	// Command line printing method
	public static void help() {
		System.out.println("help             - Afiseaza aceasta lista de comenzi");
		System.out.println("add              - Adauga o noua persoana (inscriere)");
		System.out.println("check            - Verifica daca o persoana este inscrisa la eveniment");
		System.out.println("remove           - Sterge o persoana existenta din lista");
		System.out.println("update           - Actualizeaza detaliile unei persoane");
		System.out.println("guests           - Lista de persoane care participa la eveniment");
		System.out.println("waitlist         - Persoanele din lista de asteptare");
		System.out.println("available        - Numarul de locuri libere");
		System.out.println("guests_no        - Numarul de persoane care participa la eveniment");
		System.out.println("waitlist_no      - Numarul de persoane din lista de asteptare");
		System.out.println("subscribe_no     - Numarul total de persoane inscrise");
		System.out.println("search           - Cauta toti invitatii conform sirului de caractere introdus");
		System.out.println("reset            - Sterge toate datele din liste.");
		System.out.println("quit             - Inchide aplicatia");
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub

		Scanner sc = new Scanner(System.in);

		
		
		GuestsList guestsList = null;
		
		try {
			guestsList = restoreVersion();
			 
			 //if (guestsList == null) {
				 //System.out.println("File is empty. Insert new data.");
				 //introduction();
			 //}
			 if (guestsList != null) {
				 System.out.println("Version restored. This version already contains registrations.");
				 System.out.println("Please press enter to reach the list of commands");
				 System.out.println();
			 }

		 } catch (FileNotFoundException e) {
			 System.out.println("File not found. Insert new data.");
			 /*if (guestsList == null) {
				 introduction();
			 }*/
		 } //catch (NullPointerException e) {
			
			 //introduction();
			 
	        //}
		
		System.out.println("Welcome!");
		//sc.nextLine();

		
		if (guestsList == null) {
			System.out.println("Insert number of available entries: ");
			
			while (!sc.hasNextInt()) {
				   System.out.println("Insert a number:");
				   sc.nextLine();
			}
			int locuri = sc.nextInt();
			
			guestsList = new GuestsList(locuri);
		}
		
		boolean quit = true;
		sc.nextLine();
		
		while (quit) {
			System.out.println("Awaiting command: (help - prints the list of commands)");
			String command = sc.nextLine();
			
			switch(command) {
				case "help":
					help();
					break;
				case "add":
					guestsList.addGuest(add());
					break;
				case "check":
					guestsList.checkIfSubscribed(getGuest(guestsList));
					break;
				case "remove":
					System.out.println("Guest will be removed from list");
					guestsList.remove(getGuest(guestsList));
					break;
				case "update":
					Guest guestForUpdate = getGuest(guestsList);
					if (guestForUpdate != null) {
						guestsList.update(guestForUpdate, getInfoForUpdate());
					}
					break;
				case "guests":
					guestsList.printGuestsList();
					break;
				case "waitlist":
					guestsList.printWaitList();
					break;
				case "available" :
					System.out.println("Number of available entries: " + guestsList.getAvailableSeatsNo());
					break;
				case "guests_no":
					System.out.println("Number of participants: " + guestsList.getRegisteredGuestsNumber());
					break;
				case "waitlist_no":
					System.out.println("Waiting list size: " + guestsList.getQueuedNumber());
					break;
				case "subscribe_no":
					System.out.println("Total registrations: " + guestsList.getAllRegistrations());
					break;
				case "search":
					System.out.println("Insert characters to search registry: ");
					String search = sc.nextLine();
					guestsList.printPartialSearchReg(guestsList.partialSearchReg(search));
					break;
				case "reset":
                    reset();
                    quit = false;
                    System.out.println("File reset successful!");
                    break;
				case "quit":
					quit = false;
					System.out.println("The app is closing...");
					save(guestsList);
					break;
				default:
					System.out.println("The command is not valid. \nTry again.");
					break;
				}
			} sc.close();
		}

	// ...................................... Additional Methods............................................
	
	// This method registers guests to event (gets personal information)
	public static Guest add() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Insert surname: ");
		String lastName = sc.nextLine();
		System.out.println("Insert name: ");
		String firstName = sc.nextLine();
		System.out.println("Insert e-mail: ");
		String email = sc.nextLine();
		/*while (!email.contains("@")) {
			System.out.println("Va rugam introduceti o adresa corecta");
			email = sc.nextLine();
		}*/
		System.out.println("Insert phone no (format \"+0724147649\") ");
		String phoneNo = sc.nextLine();
		/*String phoneDigits = phoneNo.substring(1);
		while ((!phoneDigits.matches("[0-9]+")) && (!phoneNo.startsWith("+"))) {
			System.out.println("Va rugam introduceti un numar valid");
			phoneNo = sc.nextLine();
		}*/
			
		Guest newGuest = new Guest(firstName, lastName, email, phoneNo);
	
		return newGuest;
	}
	// Method used for authentication
	public static Guest getGuest(GuestsList list) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Select the authentication method: ");
		System.out.println("\t 1. Surname & name \n\t 2. E-mail \n\t 3. Phone no (format \"+0724147649\")");
		int input = sc.nextInt();
		sc.nextLine();
		
		switch (input) {
			case 1:
				System.out.println("Insert name: ");
				String firstName = sc.nextLine();
				System.out.println("Insert surname: ");
				String lastName = sc.nextLine();
				String fullName = firstName + " " + lastName;
				return list.getGuestByName(fullName);
			case 2:
				System.out.println("Insert e-mail: ");
				String email = sc.nextLine();
				return list.getGuestByEmail(email);
			case 3:
				System.out.println("Insert phone no (format \"+0724147649\")");
				String phoneNo = sc.nextLine();
				return list.getGuestByPhoneNo(phoneNo);
			default:
				System.out.println("The command is not valid. \nTry again.");
				break;
		} //sc.close();
		return null;
	}
	// Additional method for updating personal information
	private static String[] getInfoForUpdate() {
		Scanner sc = new Scanner(System.in);
		
		String[] newInfo = new String[2];
		
		System.out.println("Choose to modify: ");
		System.out.println("\t 1. Surname \n\t 2. Name \n\t 3. E-mail" +
							" \n\t 4. Phone no (format \"+0724147649\")");
		int input = sc.nextInt();
		sc.nextLine();
		
		switch (input) {
			case 1:
				System.out.println("Please insert new surname: ");
				String newLastName = sc.nextLine();
				newInfo[0] = newLastName;
				newInfo[1] = "lastName";
				break;
			case 2:
				System.out.println("TPlease insert new first name: ");
				String newFirstName = sc.nextLine();
				newInfo[0] = newFirstName;
				newInfo[1] = "firstName";
				break;
			case 3:
				System.out.println("Please insert new e-mail: ");
				String newEmail = sc.nextLine();
				newInfo[0] = newEmail;
				newInfo[1] = "email";
				break;
			case 4:
				System.out.println("Please insert new phone no: ");
				String newPhoneNo = sc.nextLine();
				newInfo[0] = newPhoneNo;
				newInfo[1] = "phoneNo";
				break;
			default:
				System.out.println("The command is not valid. \nTry again.");
				break;
		} //sc.close();
		return newInfo;
	}
	
    private static void save(GuestsList gl) throws IOException{
        try (ObjectOutputStream binaryFileOut = new ObjectOutputStream(
            new BufferedOutputStream(new FileOutputStream("saveGL.dat")))){
            binaryFileOut.writeObject(gl);
        }
    }
	
	private static GuestsList restoreVersion()throws IOException, ClassNotFoundException, EOFException {
		GuestsList gl = null;

        try (ObjectInputStream binaryFileOut = new ObjectInputStream(
            new BufferedInputStream(new FileInputStream("saveGL.dat")))) {
        	
            gl = (GuestsList) binaryFileOut.readObject();
            
        } catch (EOFException e) {
            System.out.println("End of file!");
        } 

        return gl;
    }

    private static void reset() throws IOException{
        try (ObjectOutputStream reset = new ObjectOutputStream(
        	new BufferedOutputStream(new FileOutputStream("saveGL.dat")))){
            reset.reset();
        }
    }
	

	
}
