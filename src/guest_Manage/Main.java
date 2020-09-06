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
				 System.out.println("Version restored");
			 }

		 } catch (FileNotFoundException e) {
			 System.out.println("File not found. Insert new data.");
			 /*if (guestsList == null) {
				 introduction();
			 }*/
		 } //catch (NullPointerException e) {
			
			 //introduction();
			 
	        //}
		
		System.out.println("Bine ati venit!");
		//sc.nextLine();

		
		if (guestsList == null) {
			System.out.println("Introduceti numarul de locuri disponibile: ");
			
			while (!sc.hasNextInt()) {
				   System.out.println("Introduceti un numar:");
				   sc.nextLine();
			}
			int locuri = sc.nextInt();
			
			guestsList = new GuestsList(locuri);
		}
		
		boolean quit = true;
		sc.nextLine();
		
		while (quit) {
			System.out.println("Asteapta comanda: (help - Afiseaza lista de comenzi)");
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
					System.out.println("Se va sterge persoana din lista");
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
					System.out.println("Numarul de locuri libere este de: " + guestsList.getAvailableSeatsNo());
					break;
				case "guests_no":
					System.out.println("Numarul de oameni care participa la eveniment este: " + guestsList.getRegisteredGuestsNumber());
					break;
				case "waitlist_no":
					System.out.println("Numarul de oameni care sunt in asteptare: " + guestsList.getQueuedNumber());
					break;
				case "subscribe_no":
					System.out.println("Numarul total de persoane inscrise: " + guestsList.getAllRegistrations());
					break;
				case "search":
					System.out.println("Introduceti sirul de caractere dupa care vreti sa faceti cautarea: ");
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
					System.out.println("Aplicatia se inchide...");
					save(guestsList);
					break;
				default:
					System.out.println("Comanda introdusa nu este valida. \nIncercati inca o data.");
					break;
				}
			} sc.close();
		}

	// ...................................... Additional Methods............................................
	
	// This method registers guests to event (gets personal information)
	public static Guest add() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Introduceti numele: ");
		String lastName = sc.nextLine();
		System.out.println("Introduceti prenumele: ");
		String firstName = sc.nextLine();
		System.out.println("Introduceti adresa de e-mail: ");
		String email = sc.nextLine();
		/*while (!email.contains("@")) {
			System.out.println("Va rugam introduceti o adresa corecta");
			email = sc.nextLine();
		}*/
		System.out.println("Introduceti numarul de telefon (format \"+0724147649\") ");
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
		
		System.out.println("Alege modul de autentificare, tastand: ");
		System.out.println("\t 1. Nume si prenume \n\t 2. Email \n\t 3. Numar de telefon (format \"+0724147649\")");
		int input = sc.nextInt();
		sc.nextLine();
		
		switch (input) {
			case 1:
				System.out.println("Introduceti prenumele: ");
				String firstName = sc.nextLine();
				System.out.println("Introduceti numele: ");
				String lastName = sc.nextLine();
				String fullName = firstName + " " + lastName;
				return list.getGuestByName(fullName);
			case 2:
				System.out.println("Introduceti adresa de email: ");
				String email = sc.nextLine();
				return list.getGuestByEmail(email);
			case 3:
				System.out.println("Introduceti numarul de telefon (format \"+0724147649\")");
				String phoneNo = sc.nextLine();
				return list.getGuestByPhoneNo(phoneNo);
			default:
				System.out.println("Comanda introdusa nu este valida. \nIncercati inca o data.");
				break;
		} //sc.close();
		return null;
	}
	// Additional method for updating personal information
	private static String[] getInfoForUpdate() {
		Scanner sc = new Scanner(System.in);
		
		String[] newInfo = new String[2];
		
		System.out.println("Alege ce vrei sa modifici: ");
		System.out.println("\t 1. Nume \n\t 2. Prenume \n\t 3. Email" +
							" \n\t 4. Numar de telefon (format \"+0724147649\")");
		int input = sc.nextInt();
		sc.nextLine();
		
		switch (input) {
			case 1:
				System.out.println("Te rog sa introduci numele de familie nou: ");
				String newLastName = sc.nextLine();
				newInfo[0] = newLastName;
				newInfo[1] = "lastName";
				break;
			case 2:
				System.out.println("Te rog sa introduci prenumele nou: ");
				String newFirstName = sc.nextLine();
				newInfo[0] = newFirstName;
				newInfo[1] = "firstName";
				break;
			case 3:
				System.out.println("Te rog sa introduci noul email: ");
				String newEmail = sc.nextLine();
				newInfo[0] = newEmail;
				newInfo[1] = "email";
				break;
			case 4:
				System.out.println("Te rog sa introduci noul numar de telefon: ");
				String newPhoneNo = sc.nextLine();
				newInfo[0] = newPhoneNo;
				newInfo[1] = "phoneNo";
				break;
			default:
				System.out.println("Comanda introdusa nu este valida. \nIncercati inca o data.");
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
